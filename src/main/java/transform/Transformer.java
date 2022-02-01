package transform;

import com.google.common.base.Stopwatch;
import datastructures.other.Tuples;
import io.netty.util.internal.ConcurrentSet;
import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.objects.ObjectBigArrayBigList;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.bson.Document;
import org.bson.RawBsonDocument;
import system.Log;
import transform.Model.Retweet;
import transform.Model.Tweet;
import utils.FutureUtils;
import utils.StrUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isRegularFile;
import static java.text.NumberFormat.getNumberInstance;
import static java.util.Locale.GERMANY;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static system.Contracts.checkState;
import static utils.FileUtils.recursiveFiles;

public class Transformer {

    private final List<Path> mongoDumpFiles;
    private final List<Path> xzFiles;
    private final AtomicInteger count;
    final BigList<Retweet> retweets;
    final BigList<Tweet> tweets;
    final Status status;

    public Transformer() {
        mongoDumpFiles = new ArrayList<>();
        retweets = new ObjectBigArrayBigList<>();
        tweets = new ObjectBigArrayBigList<>();
        count = new AtomicInteger();
        xzFiles = new ArrayList<>();
        status = new Status();
        status.start();
        status.on();
    }

    // This is a dedicated thread for status printing.
    // It runs independent of the loading process.
    class Status extends Thread {
        AtomicBoolean active = new AtomicBoolean(true);
        Stopwatch watch = Stopwatch.createStarted();
        void on() { active.set(true); }
        void off() { active.set(false); }
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10_000);
                    if (active.get()) {
                        var str = getNumberInstance(GERMANY).format(count.get());
                        System.out.println("---------------------------------- " + watch);
                        System.out.println("LOADED ENTITIES: " + StrUtils.Color.red(str));
                        System.out.println("TWEETS:          " + tweets.size64());
                        System.out.println("RETWEETS:        " + retweets.size64());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Loads a batch from a mongo-dump (bson gzip)
    private CompletableFuture<Tuples.Tuple2<List<Tweet>, List<Retweet>>>
    loadMongoDumpBatch(Path _path) {
        return CompletableFuture.supplyAsync(() -> {
            final var frm = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            final var twt = new ArrayList<Tweet>(30_000_000);
            final var rtw = new ArrayList<Retweet>(30_000_000);
            int cnt = 0;
            try {
                var fileIs = new FileInputStream(_path.toFile());
                var stream = new GZIPInputStream(fileIs, 8192);
                var sizeAr = new byte[4];
                var sizeBu = ByteBuffer.wrap(sizeAr);
                while (stream.readNBytes(sizeAr, 0, 4) > 0) {
                    var sizeVa = sizeBu.order(LITTLE_ENDIAN).getInt();
                    var dataAr = new byte[sizeVa];
                    stream.readNBytes(dataAr, 4, sizeVa - 4);
                    System.arraycopy(sizeAr, 0, dataAr, 0, 4);
                    var bson = new RawBsonDocument(dataAr);
                    var uid = bson.getInt64("_id").longValue();
                    var dta = bson.getDocument("data");
                    if (dta.containsKey("retweeted_status"))
                        rtw.add(Retweet.of(uid, dta, frm));
                    else twt.add(Tweet.of(uid, dta, frm));
                    ++cnt;
                    sizeBu.flip();
                }
                stream.close();
                fileIs.close();
            }
            catch (IOException
                    | ParseException e) {
                e.printStackTrace();
            }
            count.addAndGet(cnt);
            return Tuples.of(twt, rtw);
        }, Executor.fixed);
    }

    // Loads a batch from Michaels mongoDumpFiles
    private CompletableFuture<Tuples.Tuple2<List<Tweet>, List<Retweet>>>
    loadMichaelBatch(Path _path) {
        return CompletableFuture.supplyAsync(() -> {
            final var twt = new ArrayList<Tweet>(1_000_000);
            final var rtw = new ArrayList<Retweet>(1_000_000);
            final var frm = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
            int cnt = 0;
            try {
                var fileIs = new FileInputStream(_path.toFile());
                var stream = new XZCompressorInputStream(fileIs, false);
                var reader = new BufferedReader(new InputStreamReader(stream));
                var cuLine = reader.readLine();
                while (cuLine != null) {
                    var dta = Document.parse(cuLine);
                    var usr = dta.get("user", Document.class);
                    var uid = Long.valueOf(dta.getString("id_str"));
                    var userId = Long.valueOf(usr.getString("id_str"));
                    var language = dta.getString("lang");
                    var createdAt = frm.parse(dta.getString("created_at"));
                    if (dta.containsKey("retweeted_status")) {
                        var ruid = Long.valueOf(dta.get("retweeted_status", Document.class).getString("id_str"));
                        rtw.add(new Retweet(uid, userId, ruid, createdAt, language));
                    }
                    else {
                        twt.add(new Tweet(uid, userId, createdAt, language));
                    }
                    cnt++;
                    cuLine = reader.readLine();
                }
                reader.close();
                stream.close();
                fileIs.close();
            } catch (IOException
                    | ParseException e) {
                e.printStackTrace();
            }
            count.addAndGet(cnt);
            return Tuples.of(twt, rtw);
        }, Executor.fixed);
    }

    public Transformer loadMongoDump(Path folder) throws IOException {
        checkState(isDirectory(folder)); Log.info("LOAD DUMP");
        Files.walk(folder)
                .filter(f -> isRegularFile(f))
                .forEach(mongoDumpFiles::add);
        var watch = Stopwatch.createStarted();
        var futures = mongoDumpFiles.stream()
                .map(this::loadMongoDumpBatch)
                .collect(toList());
        var tuples = FutureUtils.allAsList(futures).join();
        var f1 = CompletableFuture.runAsync(() -> {
                    for (var t : tuples) { tweets.addAll(t._1); } },
                Executor.fixed);
        var f2 = CompletableFuture.runAsync(() -> {
                    for (var t : tuples) { retweets.addAll(t._2); } },
                Executor.fixed);
        CompletableFuture.allOf(f1, f2)
                .thenRun(status::off)
                .join();
        System.out.println("LOADED MONGO DUMP FILES: " + watch);
        return this;
    }

    public Transformer loadXzDump(Path folder) throws InterruptedException {
        checkState(isDirectory(folder)); Log.info("LOAD MICHAEL");
        xzFiles.addAll(recursiveFiles(folder, "xz"));
        final var futures = new ConcurrentSet<CompletableFuture>();
        final var twt = Collections.synchronizedList(new ArrayList<Tweet>());
        final var rtw = Collections.synchronizedList(new ArrayList<Retweet>());
        for (var path : xzFiles) {
            int err = 0;
            RETRY:
            if (futures.size() < 100) {
                var f = loadMichaelBatch(path);
                futures.add(f);
                f.thenAccept(tuple -> {
                    twt.addAll(tuple._1);
                    rtw.addAll(tuple._2);
                });
                f.thenRun(() -> futures.remove(f));
            }
            else {
                System.out.println("WAIT " + futures.size() + " ERROR: " + err++);
                Thread.sleep(10_000);
                break RETRY;
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        var f1 = CompletableFuture.runAsync(() -> tweets.addAll(twt), Executor.fixed);
        var f2 = CompletableFuture.runAsync(() -> retweets.addAll(rtw), Executor.fixed);
        CompletableFuture.allOf(f1, f2)
                .thenRun(status::off)
                .join();
        return this;
    }

    public Transformer map() {
        final var watch = Stopwatch.createStarted();
        // UNIQUIFY RETWEETS
        System.out.println("START UNIQUIFY RETWEETS " + retweets.size());
        final var rtwSet = new HashSet<>(retweets);
        retweets.clear(); retweets.addAll(rtwSet);
        rtwSet.clear();
        System.out.println("STOP UNIQUIFY RETWEETS  " + retweets.size() + " " + watch);
        final var twtMap = new HashMap<Long, Tweet>();
        for (long i = 0; i < tweets.size64(); i++) {
            final var tweet = tweets.get(i);
            twtMap.put(tweet.tweetId, tweet);
        }
        System.out.println("LOADED MAP:              " + watch);
        int cntErr = 0;
        for (var retweet : retweets) {
            var tweet = twtMap.get(retweet.retweetedId);
            if (nonNull(tweet)) tweet.retweets.add(retweet);
            else ++cntErr;
        }
        tweets.clear();
        tweets.addAll(twtMap.values());
        System.out.println("MAPPED:                  " + watch + " E " + cntErr);
        return this;
    }

//    public Loader store(Path file) {
//        var db = DBMaker
//                .fileDB(file.toFile())
//                .fileMmapEnable()
//                .concurrencyScale(60)
//                .concurrencyDisable()
//                .closeOnJvmShutdown()
//                .executorEnable()
//                .make();
//        Map<Long, Tweet> fileDb = cast(db
//                .hashMap("tweets", Serializer.LONG, Serializer.JAVA)
//                .createOrOpen());
//        for (int i = 0; i < tweets.size(); i++) {
//            var tweet = tweets.get(i);
//            fileDb.put(tweet.tweetId, tweet);
//        }
//        return this;
//    }
}

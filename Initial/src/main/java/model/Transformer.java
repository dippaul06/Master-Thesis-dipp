package model;

import com.google.common.collect.Lists;
import model.Model.Status;
import magma.concurrent.NBHashMap;
import magma.system.Executor;
import magma.system.Log;
import magma.utils.Utils;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.bson.BsonArray;
import org.bson.BsonValue;
import org.bson.RawBsonDocument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.nio.file.Files.isDirectory;
import static java.util.Objects.isNull;
import static magma.exa.base.contract.Require.isTrue;
import static magma.utils.BsonUtils.int64;
import static magma.utils.FileUtils.recursiveFiles;
import static magma.utils.Utils.*;
import static main.Config.*;
import static model.Locator.Location.fromJson;


public class Transformer {

    static Status toMinTweet(RawBsonDocument bson, SimpleDateFormat format) {
        long twtId = int64(bson, "id");
        String txt = extractString(bson, "full_text");
        var res = new Status(twtId, txt);
        if (bson.get("user").isDocument()) {
            var userBson = bson.getDocument("user");
            res.usrId = extractIdStr(userBson);
            Locator.get.resolve(extractString(userBson, "location"))
                            .ifPresent(l -> res.loc = l);
        } else if (bson.get("user").isInt64()) {
            res.usrId = extractLong(bson, "user");
        }
        var hashtags = entities("hashtags", bson);
        for (var tag : hashtags) {
            if (tag.isDocument()) {
                var doc = tag.asDocument();
                if (doc.containsKey("text")) {
                    res.hashtags.add(Utils.extractString(doc, "text"));
                }
            }
        }
        res.date = extractCreatedAt(bson, format);
        res.isRetweet = isRetweet(bson);
        if (!res.isRetweet) {
            res.retweetCnt = extractInt(bson, "retweet_count");
        }
        else {
            var rtw = bson.getDocument("retweeted_status");
            long rspTwtId = int64(rtw, "id");
            long rspUsrId;
            if (rtw.get("user").isDocument()) {
                var usrBson = bson.getDocument("user");
                rspUsrId = extractIdStr(usrBson);
            } else if (bson.get("user").isInt64()) {
                rspUsrId = extractLong(rtw, "user");
            } else {
                throw new IllegalStateException("SOMETHING WRONG WITH RT");
            }
            res.rtwId = rspTwtId;
            res.rtuId = rspUsrId;
        }
        return res;
    }


    private static List<Status> readMinTweets(Path dtaFolder) throws InterruptedException {
        Log.info("READ MIN TWEETS");
        return loadJson(dtaFolder);
    }

    private static BsonArray entities(String field, RawBsonDocument bson) {
        var entities = bson.getDocument("entities");
        if (isNull(entities)
                || !entities.containsKey(field)
                || !entities.isArray(field))
            return null;
        return entities.getArray(field);
    }

    private static final AtomicInteger count = new AtomicInteger();
    private static CompletableFuture<List<Status>>
    loadBatch(Path _path) {
        System.out.println("DEBUG: LOAD PATH " + _path);
        return CompletableFuture.supplyAsync(() -> {
            final var docs = new ArrayList<Status>(1_000_000);
            int cnt = 0;
            try {
                final var format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
                final var fileIs = new FileInputStream(_path.toFile());
                final var stream = new XZCompressorInputStream(fileIs, false);
                final var reader = new BufferedReader(new InputStreamReader(stream));
                var cuLine = reader.readLine();
                while (cuLine != null) {
                    var bson = RawBsonDocument.parse(cuLine);
                    docs.add(toMinTweet(bson, format));
                    cnt++;
                    cuLine = reader.readLine();
                }
                reader.close();
                stream.close();
                fileIs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            count.addAndGet(cnt);
            return docs;
        }, Executor.fixed);
    }

    private static List<Status> loadJson(Path tgtFolder) throws InterruptedException {
        isTrue(isDirectory(tgtFolder));
        Log.info("LOAD XZ");
        List<Path> xzFiles = new ArrayList<>();
        xzFiles.addAll(recursiveFiles(tgtFolder, "xz"));
        final var futures = ConcurrentHashMap.newKeySet();
        final var bson = Collections.synchronizedList(new ArrayList<Status>(100_000_000));
        for (var path : xzFiles) {
            System.out.println("THIS IS A ANOTHER TEST");
            int err = 0;
            RETRY:
            if (futures.size() < 100) {
                var f = loadBatch(path);
                futures.add(f);
                f.thenAccept(twts -> {
                    System.out.println("-===========> ADD " + twts.size() + " CAPACITY: " + bson.size());
                    bson.addAll(twts);
                });
//                f.thenAccept(bson::addAll);
                f.thenRun(() -> futures.remove(f));
            }
            else {
                System.out.println("WAIT " + futures.size() + " ERROR: " + err++);
                Thread.sleep(10_000);
                break RETRY;
            }
        }
        System.out.println("TTTT " + futures.size());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        Thread.sleep(1_000);
        System.out.println("TTTT " + futures.size());
        return bson;
    }

    // From Tweets to csv
    // folder is the target folder
    public static void run(Path dtaFolder, Path tgtFolder) throws InterruptedException, IOException {
        isTrue(Files.isDirectory(tgtFolder));
        isTrue(Files.isDirectory(dtaFolder));
        Locator.get.readJohannesLocations(LOCATIONS_FILE_1_LOC, LOCATIONS_FILE_1_SLV);
//        Locator.get.readJohannesLocations(LOCATIONS_FILE_2_LOC, LOCATIONS_FILE_2_SLV);
//        Locator.get.readJohannesLocations(LOCATIONS_FILE_3_LOC, LOCATIONS_FILE_3_SLV);
        System.out.println(Locator.get);
        var lst = readMinTweets(dtaFolder);
        int G = 140;
        final var split = Lists.partition(lst, (lst.size() + G - 1) / G);
        final var cnt = new AtomicInteger();
        var futures = ConcurrentHashMap.newKeySet();
        for (List<Status> minTweets : split) {
            var f = CompletableFuture.runAsync(() -> {
                try {
                    var _cnt = cnt.incrementAndGet();
                    compress(minTweets, _cnt, tgtFolder);
                    System.out.println(_cnt + "-----> " + minTweets.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            futures.add(f);
            f.thenRun(() -> futures.remove(f));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("------->" + lst.size());
        System.out.println("--------------------->");
        System.out.println("--------------------->");
        System.out.println("--------------------->");
        System.out.println("--------------------->");
        Locator.get.exportUnresolvedLocations();
    }

    static void compress(List<Status> minTweets, int nmn, Path folder) throws IOException {
        OutputStream fout = Files.newOutputStream(folder.resolve("dip_initial_data_short" + String.format("%04d", nmn) +".tar.lz4"));
        BufferedOutputStream out = new BufferedOutputStream(fout);
        FramedLZ4CompressorOutputStream lzOut = new FramedLZ4CompressorOutputStream(out);
        for (var minTweet : minTweets) {
            var str = (minTweet.toJson() + System.lineSeparator()).getBytes();
            lzOut.write(str, 0, str.length);
        }
        lzOut.close();
    }

    private static CompletableFuture<List<RawBsonDocument>> loadLz4Batch(Path _path) {
        System.out.println("DEBUG: LOAD PATH " + _path);
        return CompletableFuture.supplyAsync(() -> {
            final var docs = new ArrayList<RawBsonDocument>(1_000_000);
            try {
                InputStream fin = Files.newInputStream(_path);
                BufferedInputStream in = new BufferedInputStream(fin);
                FramedLZ4CompressorInputStream zIn = new FramedLZ4CompressorInputStream(in);
                var reader = new BufferedReader(new InputStreamReader(zIn));
                var cuLine = reader.readLine();
                int cnt = 0;
                while (cuLine != null) {
                    var dta = RawBsonDocument.parse(cuLine);
                    cuLine = reader.readLine();
                    docs.add(dta);
                    cnt++;
                }
                System.out.println("------->> " + cnt);
                zIn.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return docs;
        });
    }

    static AtomicInteger cnt = new AtomicInteger();
    public static List<Status> decompress(Path folder) {
        final var futures = ConcurrentHashMap.newKeySet();
        final var minTweets = new NBHashMap<List<Status>, List<Status>>();
        for (Path lz4 : recursiveFiles(folder, "lz4")) {
            int err = 0;
            RETRY:
            if (futures.size() < 140) {
                var f = loadLz4Batch(lz4)
                        .thenApplyAsync(bsons -> {
                                final var list = new ArrayList<Status>();
                                System.out.println(bsons.size());
                                for (int i = 0; i < bsons.size(); i++) {
                                    final var bson = bsons.get(i);
                                    final var minTweet = new Status(
                                            extractLong(bson, "tId"),
                                            Utils.extractString(bson, "txt")
                                    );
                                    minTweet.usrId = extractLong(bson, "uId");
                                    minTweet.isRetweet = extractBoolean(bson, "rtw");
                                    minTweet.date = extractTimestamp(bson, "dte");
                                    final var tags = new HashSet<String>();
                                    for (BsonValue tag : bson.getArray("tag")) {
                                        tags.add(tag.asString().getValue());
                                    }
                                    minTweet.hashtags = tags;
                                    list.add(minTweet);
                                    minTweet.loc = fromJson(bson.getDocument("loc"));
                                }
                                System.out.println("------> " + cnt.incrementAndGet());
                                return list;
                            });
                var f1 = f.thenAccept(l -> {
                    minTweets.put(l, l);
                    System.out.println("STORED");
                });
                futures.add(f1);
                f.thenRun(() -> futures.remove(f1));
            }
            else {
                System.out.println("WAIT " + futures.size() + " ERROR: " + err++);
                try { Thread.sleep(10_000); }
                catch (InterruptedException e) { e.printStackTrace(); }
                break RETRY;
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("START");
        var res = minTweets.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("LOADED: " + res.size());
        return res;
    }
}

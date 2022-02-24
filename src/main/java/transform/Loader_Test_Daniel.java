package transform;
import utils.Utils;

import datastructures.other.Tuples;
import io.netty.util.internal.ConcurrentSet;
import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.objects.ObjectBigArrayBigList;
import model.Store;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.RawBsonDocument;
import system.Log;
import transform.Model.Retweet;
import transform.Model.Tweet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.nio.file.Files.isDirectory;
import static system.Contracts.checkState;
import static utils.FileUtils.recursiveFiles;

// This is only to give an example
// to Dip and will be deleted later
// on.
// What we want to do here is we
// want to connect the new Loader
// (old transformer) to the store.
// Thus, we have to get rid of the
// model the Transformer is using
// right now and inject the RawBson
// directly to our store.
public class Loader_Test_Daniel {

    private final AtomicInteger count;
    private final List<Path> xzFiles;
    final BigList<Retweet> retweets;
    final BigList<Tweet> tweets;
    //final Status status;



    public Loader_Test_Daniel() {

        count = new AtomicInteger();
        xzFiles = new ArrayList<>();
        retweets = new ObjectBigArrayBigList<>();
        tweets = new ObjectBigArrayBigList<>();
//        status = new Status();
//        status.start();
//        status.on();
    }

    // THIS READS THE FILE (ONLY ONE!!!)
    private CompletableFuture<List<RawBsonDocument>>
    loadMichaelBatch(Path _path) {
        System.out.println("DEBUG: LOAD PATH " + _path);
        return CompletableFuture.supplyAsync(() -> {
            final var docs = new ArrayList<RawBsonDocument>(1_000_000);
            int cnt = 0;
            try {
                var fileIs = new FileInputStream(_path.toFile());
                var stream = new XZCompressorInputStream(fileIs, false);
                var reader = new BufferedReader(new InputStreamReader(stream));
                var cuLine = reader.readLine();
                while (cuLine != null) {
                    System.out.println("DEBUG: cuLine: " +  cuLine);
                    //var dta = Document.parse(cuLine);
                    var dta = RawBsonDocument.parse(cuLine);
                    // TRANSFORM:  Document --> RawBsonDocument || BsonDocument
                    docs.add(dta);
                    cnt++;
                    cuLine = reader.readLine();
                    System.out.println(cuLine);
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


    // LOAD INTO STORE
    public Loader_Test_Daniel loadXzDump(Path folder) throws InterruptedException {
        checkState(isDirectory(folder)); Log.info("LOAD XZ");
        System.out.println("THIS IS A TEST");
        xzFiles.addAll(recursiveFiles(folder, "xz"));
        final var futures = new ConcurrentSet<CompletableFuture>();
        final var bson = Collections.synchronizedList(new ArrayList<RawBsonDocument>());
//        final var rtw = Collections.synchronizedList(new ArrayList<RawBsonDocument>());
        for (var path : xzFiles) {
            System.out.println("THIS IS A ANOTHER TEST");
            int err = 0;
            RETRY:
            if (futures.size() < 100) {
                var f = loadMichaelBatch(path);
                futures.add(f);
                f.thenAccept(list -> {


                    bson.addAll(list);


//                    System.out.println("THIS IS A ANOTHER TEST FROM THE FUTURE");
//
//                    // TODO: What you get here is a list of RawBsonDocument. We want to transform these into
//                    // Tweet, Retweet objects. But remember we want the retweet objects from our model in
//                    // transform/Model! So you need a way to tranform them. I gave an example below.
//                    // PLEASER CONSIDER TO LOOP OVER THE FILES (SEE BELOW AFTER MAYBE BETTER).
//
//                    // IN utils.Utils you may find methods that help you decide what is what.
//
                    twt.addAll(list.stream().map(HERE COMES THE FUNCTION THAT TRANSFORMS RawBsonDocument -> Tweet).collect(Collectors.toList()));
                    rtw.addAll(list.stream().map(HERE COMES THE FUNCTION THAT TRANSFORMS RawBsonDocument -> Retweet).collect(Collectors.toList()));


                    Tweet.addall(list.stream().map(utils.Utils.isTweet(bson)).collect(Collectors.toList()));
//
//                    // MAYBE BETTER
//
                    for (var rawBsonDocument : list) {
                        if (IS TWEET) {
                            twt.add(rawBsonDocument -> tweet);
                        }
                        else if (IS RETWEET) {
                            rtw.add(rawBsonDocument -> retweet);
                        }
                    }


                    for (var rawBsonDocument : list) {
                        if (Tweet) {
                            Tweet.add(utils.Utils.isTweet(rawBsonDocument));
                        }
                    }


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

        for (var rawBsonDocument : bson) {
            Store.get.store((BsonDocument) bson);
        }
        
//        var f1 = CompletableFuture.runAsync(() -> tweets.addAll(twt), Executor.fixed);
//        var f2 = CompletableFuture.runAsync(() -> retweets.addAll(rtw), Executor.fixed);
//        CompletableFuture.allOf(f1, f2)
//                //.thenRun(status::off)
//                .join();
        return this;
    }

}

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
//    final BigList<Retweet> retweets;
//    final BigList<Tweet> tweets;
    //final Status status;



    public Loader_Test_Daniel() {
        count = new AtomicInteger();
        xzFiles = new ArrayList<>();
//        retweets = new ObjectBigArrayBigList<>();
//        tweets = new ObjectBigArrayBigList<>();
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
//                    System.out.println("DEBUG: cuLine: " +  cuLine);
                    //var dta = Document.parse(cuLine);
                    var dta = RawBsonDocument.parse(cuLine);
                    // TRANSFORM:  Document --> RawBsonDocument || BsonDocument
                    docs.add(dta);
                    cnt++;
                    cuLine = reader.readLine();
//                    System.out.println(cuLine);
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
        System.out.println("START TO LOAD STUFF INTO STORE");
        var cnt = 0;
        for (var rawBsonDocument : bson) {
            if (cnt++ % 10_000 == 0) {
                System.out.println("===============================");
                System.out.println(Store.get);
                System.out.println("===============================");
            }
            Store.get.store(rawBsonDocument);
        }
        
//        var f1 = CompletableFuture.runAsync(() -> tweets.addAll(twt), Executor.fixed);
//        var f2 = CompletableFuture.runAsync(() -> retweets.addAll(rtw), Executor.fixed);
//        CompletableFuture.allOf(f1, f2)
//                //.thenRun(status::off)
//                .join();
        return this;
    }

}

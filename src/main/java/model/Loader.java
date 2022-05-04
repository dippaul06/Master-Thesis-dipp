package model;

import io.netty.util.internal.ConcurrentSet;
import magma.system.Executor;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.bson.RawBsonDocument;
import magma.system.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.Files.isDirectory;
import static magma.exa.base.contract.Require.isTrue;
import static magma.utils.FileUtils.recursiveFiles;

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
public class Loader {

    private final AtomicInteger count;
    private final List<Path> xzFiles;

    public Loader() {
        count = new AtomicInteger();
        xzFiles = new ArrayList<>();
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
                    var dta = RawBsonDocument.parse(cuLine);
                    // TRANSFORM:  Document --> RawBsonDocument || BsonDocument
                    docs.add(dta);
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

//    // LOAD INTO STORE
//    public Loader loadXzDump(Path folder) throws InterruptedException {
//        isTrue(isDirectory(folder)); Log.info("LOAD XZ");
//        xzFiles.addAll(recursiveFiles(folder, "xz"));
//        final var futures = new ConcurrentSet<CompletableFuture>();
//        final var bson = Collections.synchronizedList(new ArrayList<RawBsonDocument>(100_000_000));
//        for (var path : xzFiles) {
//            System.out.println("THIS IS A ANOTHER TEST");
//            int err = 0;
//            RETRY:
//            if (futures.size() < 100) {
//                var f = loadMichaelBatch(path);
//                futures.add(f);
//                f.thenAccept(bson::addAll);
//                f.thenRun(() -> futures.remove(f));
//            }
//            else {
//                System.out.println("WAIT " + futures.size() + " ERROR: " + err++);
//                Thread.sleep(10_000);
//                break RETRY;
//            }
//        }
//        System.out.println("FUTURES SET UP");
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//        System.out.println("START TO LOAD STUFF INTO STORE " + bson.size());
//        for (var rawBsonDocument : bson) {
//            Store.get.store(rawBsonDocument);
//        }
//        System.out.println("===============================");
//        System.out.println(Store.get);
//        System.out.println("===============================");
//        return this;
//    }

    public List<RawBsonDocument> loadXzJson(Path folder) throws InterruptedException {
        isTrue(isDirectory(folder)); Log.info("LOAD XZ");
        xzFiles.addAll(recursiveFiles(folder, "xz"));
        final var futures = ConcurrentHashMap.newKeySet();
        final var bson = Collections.synchronizedList(new ArrayList<RawBsonDocument>(100_000_000));
        for (var path : xzFiles) {
            System.out.println("THIS IS A ANOTHER TEST");
            int err = 0;
            RETRY:
            if (futures.size() < 100) {
                var f = loadMichaelBatch(path);
                futures.add(f);
                f.thenAccept(bson::addAll);
                f.thenRun(() -> futures.remove(f));
            }
            else {
                System.out.println("WAIT " + futures.size() + " ERROR: " + err++);
                Thread.sleep(10_000);
                break RETRY;
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        Thread.sleep(1_000);
        return bson;
    }
}

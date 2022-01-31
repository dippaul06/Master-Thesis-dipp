package model;

import com.google.common.base.Stopwatch;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.bson.RawBsonDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static io.reactivex.rxjava3.core.BackpressureStrategy.BUFFER;
import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isRegularFile;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static utils.Contracts.*;
import static utils.FutureUtils.allAsList;
import static system.Log.info;
import static model.Model.*;
import static model.Model.StatusState.*;
import static model.Model.UserState.DELETED;
import static model.Model.UserState.NOT_AVAILABLE;
import static utils.Utils.loadIdsFromFile;

public enum Loader {
    //get;


    //Trying to load
    get();


    // ----------------------------------------------
    //  LOADING DATA FROM MICHAEL'S FILES.
    // ----------------------------------------------

    // received the folder containing the
    // corona tweets. We filter for a list
    // of words which are supposed to
    // be contained in the full_text
    public Loader load(Path folder) throws IOException {
        checkArgument(isDirectory(folder)); info("LOAD");
        var watch = Stopwatch.createStarted();
        var futures = Files.walk(folder)
                .filter(f -> isRegularFile(f))
                .map(this::loadFile)
                .collect(toList());
        var results = allAsList(futures).join();
        for (var batch : results)
            for (var bson : batch)
                Store.get.store(bson);
        return this;
    }

    // since the dataset is split in multple files
    // we use a thread for each file in order to parse
    // it.
    public CompletableFuture<List<RawBsonDocument>> loadFile(Path file) {
        return CompletableFuture.supplyAsync(() -> {
            var result = new ArrayList<RawBsonDocument>();
            try {
                var fileIs = new FileInputStream(file.toFile());
                var stream = new GZIPInputStream(fileIs, 8192);
                var sizeAr = new byte[4];
                var sizeBu = ByteBuffer.wrap(sizeAr);

                while (stream.readNBytes(sizeAr, 0, 4) > 0) {
                    var sizeVa = sizeBu.order(LITTLE_ENDIAN).getInt();
                    var dataAr = new byte[sizeVa];
                    stream.readNBytes(dataAr, 4, sizeVa - 4);
                    System.arraycopy(sizeAr, 0, dataAr, 0, 4);
                    var bson = new RawBsonDocument(dataAr);
                    var txt = bson.getString("full_text").getValue();
                    if (txt.contains(" 5G ")
                            || txt.contains(" #5G ")
                            || txt.contains(" 5g ")
                            || txt.contains(" #5g ")
                            || txt.startsWith("5G ")
                            || txt.startsWith("5g ")) {
                        result.add(bson);
                    }
                    sizeBu.flip();
                }
                stream.close();
                fileIs.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    // ----------------------------------------------
    //  LOADING 5G - TWEETS.
    // ----------------------------------------------
    // loading the 5G Tweets we have extracted
    // from Michael's dataset. We have the
    // following files containing all the
    //
    //      1. retweetQuoted.gz
    //      2. retweets.gz
    //      3. tweets.gz
    //      4. tweetsQuoted.gz
    //      5. tweetsQuotedReply.gz
    //      6. tweetsReply.gz
    //
    // All tweets we load here have to be labeled as
    // initial.
    public static class Source implements FlowableOnSubscribe<RawBsonDocument> {
        final Path file;
        Source(Path _file) { file = _file; }
        public void subscribe(FlowableEmitter<RawBsonDocument> emitter) {
            try {
                var fileIs = new FileInputStream(file.toFile());
                var stream = new GZIPInputStream(fileIs, 8192);
                var sizeAr = new byte[4];
                var sizeBu = ByteBuffer.wrap(sizeAr);
                while (stream.readNBytes(sizeAr, 0, 4) > 0) {
                    var sizeVa = sizeBu.order(LITTLE_ENDIAN).getInt();
                    var dataAr = new byte[sizeVa];
                    stream.readNBytes(dataAr, 4, sizeVa - 4);
                    System.arraycopy(sizeAr, 0, dataAr, 0, 4);
                    var bson = new RawBsonDocument(dataAr);
                    emitter.onNext(bson);
                    sizeBu.flip();
                }
                stream.close();
                fileIs.close();
            } catch (IOException e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
        public static Flowable<RawBsonDocument> read(Path file) {
            return Flowable.create(new Source(file), BUFFER);
        }
    }

    public static void loadInitialData(Path dir, Store store) throws IOException {
        Files.list(dir)
                .filter(pth -> isRegularFile(pth))
                .forEach(file ->
                        Source.read(file)
                                .map(store::store)
                                .doOnNext(stati -> {
                                    stati.stream()
                                            .filter(Objects::nonNull)
                                            .forEach(status -> status.state(IS_SOURCE));
                                })
                                .blockingSubscribe());
        System.out.println(store);
    }

    // ----------------------------------------------
    //  LOADING ENRICHED DATA.
    // ----------------------------------------------
    // After extracting the stati containing 5g as a
    // keyword we tried to reconstruct the threads
    // containg these stati. These tweets will be laeded
    // here. Attention the stati also contain the
    // initial stati which isn't a problem to this
    // point because the will not be overwritten.
    public static void loadEnrichedData(Path dir, Store store) throws IOException {
        var files = Files.list(dir)
                .filter(pth -> isRegularFile(pth))
                .collect(Collectors.toSet());
        for (var file : files) {
            System.out.println("FILE: " + file.toString());
            var bsons = Source.read(file).toList().blockingGet();
            for (var bson : bsons) {
                var status = store.storeUncheckedStatus(bson);
                if (isNull(status)) continue;
                if (status.state().equals(UNKNOWN))
                    status.state(ENRICHED);
            }
        }
    }

    // ----------------------------------------------
    //  LOADING RELOADED DATA.
    // ----------------------------------------------
    // We want to reload each status from the initial
    // dataset for checking whether the status is deleted
    // or not or if any of the numbers like retweetCount
    // replyCount, etc changed dramatically.
    private static void loadReloadedData(Path dir, Store store) throws IOException {
        Files.list(dir)
                .filter(pth -> isRegularFile(pth))
                .forEach(file ->
                        Source.read(file)
                                .doOnNext(bson -> {
                                    store.statusOf(bson)
                                            .ifPresentOrElse(status -> {
                                                        if (status instanceof Retweet)
                                                            System.out.println("IS RETWEET " + status.id());
                                                        checkNotNull(status.oldBson());
                                                        checkIsNull(status.newBson());
                                                        status.newBson(bson); },
                                                    () -> { throw new IllegalArgumentException("TWEET NOT THERE "); }); })
                                .blockingSubscribe());
    }



    public static void loadUsers(Path dir, Store store) {
        checkArgument(isRegularFile(dir.resolve("userList")));
        checkArgument(isRegularFile(dir.resolve("reloaded-users.gz")));
        checkArgument(isRegularFile(dir.resolve("deleted-users.gz")));
        var ids = new LongOpenHashSet(loadIdsFromFile(dir.resolve("userList")));
        var reloaded = new HashMap<Long, User>();
        Source.read(dir.resolve("reloaded-users.gz"))
                .map(User::newUserFromReloadedUserProfile)
                .doOnNext(user -> reloaded.put(user.id(), user))
                .blockingSubscribe();
        var deleted = new LongOpenHashSet(ids);
        deleted.removeAll(reloaded.keySet());
        System.out.println("LOADED IDS: " + ids.size());
        System.out.println("RELOADED:   " + reloaded.size());
        System.out.println("DELETED:    " + deleted.size());

        var users = new HashMap<Long, User>();
        Source.read(dir.resolve("deleted-users.gz"))
                .map(User::newUserFromReloadedUserProfile)
                .doOnNext(user -> { user.state = DELETED; })
                .doOnNext(user -> deleted.remove(user.id()))
                .doOnNext(user -> users.put(user.id(), user))
                .blockingSubscribe();

        System.out.println("LOADED IDS: " + ids.size());
        System.out.println("RELOADED:   " + reloaded.size());
        System.out.println("DELETED:    " + deleted.size());

        for (var uid : deleted) {
            var user = User.newUserFromId(uid);
            user.state = NOT_AVAILABLE;
            users.put(uid, user);
        }
        users.putAll(reloaded);
        store.storeUsers(users);
        int usersWithoutBson = 0;
        for (User u: store.users.values())
            if (u.versions.isEmpty()) ++usersWithoutBson;
        System.out.println("USERS WITHOUT BSON: " + usersWithoutBson);

        var map = new HashMap<>(store.users);
        map.forEach((id, user) -> {
            if (user.id() == -1L) {
                System.out.println("FOUND ID " + user.id() + " " + id + "========================================");
                store.users.remove(id);
            }
        });
    }

    public static Store loadAll(Path dir, Store store) throws IOException {
        checkArgument(isDirectory(dir));
        loadInitialData(dir.resolve("initial"), store);
        loadEnrichedData(dir.resolve("enriched"), store);
        loadReloadedData(dir.resolve("reloaded"), store);
        loadUsers(dir.resolve("users"), store);
        return store;
    }
}

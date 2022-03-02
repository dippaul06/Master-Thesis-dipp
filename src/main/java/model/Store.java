//package model;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Iterables;
//import org.bson.BsonDocument;
//import utils.Utils;
//
//import java.nio.file.Path;
//import java.util.*;
//import java.util.stream.Stream;
//
//import static java.nio.file.Files.isDirectory;
//import static java.util.Objects.isNull;
//import static java.util.Objects.nonNull;
//import static model.Model.*;
//import static system.Contracts.*;
//import static transform.Model.*;
//import static utils.BsonUtils.beautiful;
//import static utils.BsonUtils.int64;
//import static utils.MainUtils.TCast.cast;
//import static utils.Utils.*;
//
//// TODO bei undummy add Tweet
//public enum Store {
//    get;
//
//
//    // Status count
//    int stsCnt = 0;
//    public Map<Long, User> users = new HashMap<>();
//    public Map<Long, Tweet> tweets = new HashMap<>();
//    public Map<Long, Quote> quotes = new HashMap<>();
//    public Map<Long, Reply> replies = new HashMap<>();
//    public Map<Long, Retweet> retweets = new HashMap<>();
//    public Map<Long, ThreadSource<?>> dummies = new HashMap<>();
//    public Map<Long, QuotedReply> quotedReplies = new HashMap<>();
//    public Map<Long, QuotedRetweet> quotedRetweets = new HashMap<>();
//
//    // TODO TRANSFORM STATUS IST MEGA LANGSAM!!!!
//    public List<Status<?>> store(BsonDocument bson) {
//        var lst = new ArrayList<Status<?>>();
//        for (var _bson : transformStatus(bson)) {
//            var id = Utils.extractId(_bson);
//            if (id != 1246401563125518336L
//                    && id != 1239318882428620801L
//                    && id != 1223756754821009408L
//                    && id != 1243174595542294528L
//                    && id != 1240672989584203781L) {
//                lst.add(storeUncheckedStatus(_bson));
//            }
//        }
//        return lst;
//    }
//
//    public void storeUsers(Map<Long, User> _users) {
//        stati().forEachRemaining(status -> {
//            if (!status.isDummy()) {
//                var bson = checkNotNull(status.oldBson());
//                if (bson.containsKey("user")) {
//                    if (bson.get("user").isDocument()) {
//                        var userDoc = bson.get("user").asDocument();
//                        var userId = extractIdStr(userDoc);
//                        var user = _users.get(userId);
//                        checkNotNull(user);
//                        user.addVersion(bson);
//                        if (nonNull(status.newBson())) {
//                            user.addVersion(status.newBson()); }
//                        if (status instanceof Tweet) user.tweets.put(status.id(), (Tweet) status);
//                        else if (status instanceof Quote) user.quotes.put(status.id(), (Quote) status);
//                        else if (status instanceof Reply) user.replies.put(status.id(), (Reply) status);
//                        else if (status instanceof Retweet) user.retweets.put(status.id(), (Retweet) status);
//                        else if (status instanceof QuotedReply) user.quotedReplies.put(status.id(), (QuotedReply) status);
//                        else if (status instanceof QuotedRetweet) user.quotedRetweets.put(status.id(), (QuotedRetweet) status);
//                        else throw new IllegalStateException();
//                    }
//                    else if (bson.get("user").isInt64()
//                            || bson.get("user").isInt64()) {
//                        var userId = extractLong(bson, "user");
//                        var user = _users.get(userId);
//                        checkNotNull(user);
//                        user.addVersion(bson);
//                        if (nonNull(status.newBson())) {
//                            user.addVersion(status.newBson());
//                        }
//                        if (status instanceof Tweet) user.tweets.put(status.id(), (Tweet) status);
//                        else if (status instanceof Quote) user.quotes.put(status.id(), (Quote) status);
//                        else if (status instanceof Reply) user.replies.put(status.id(), (Reply) status);
//                        else if (status instanceof Retweet) user.retweets.put(status.id(), (Retweet) status);
//                        else if (status instanceof QuotedReply) user.quotedReplies.put(status.id(), (QuotedReply) status);
//                        else if (status instanceof QuotedRetweet) user.quotedRetweets.put(status.id(), (QuotedRetweet) status);
//                        else throw new IllegalStateException();
//                    }
//                    else throw new IllegalStateException();
//                }
//            }
//        });
//        users.putAll(_users);
//    }
//
//    Status<?> storeUncheckedStatus(BsonDocument bson) {
//        checkArgument(nonNull(bson), "IS NULL");
//        if (isTweet(bson)) return addTweet(bson);
//        else if (isRetweet(bson)) return addRetweet(bson);
//        else if (isReply(bson)) return addReply(bson);
//        else if (isQuote(bson)) return addQuote(bson);
//        else if (isQuotedReply(bson)) return addQuotedReply(bson);
//        else if (isQuotedRetweet(bson)) return addQuotedRetweet(bson);
//        else throw new IllegalStateException(beautiful(bson));
//    }
//
//    public Status<?> storeStatus(Status<?> sts) {
//        if (sts instanceof Tweet) return tweets.put(sts.id(), (Tweet) sts);
//        else if (sts instanceof Quote) return quotes.put(sts.id(), (Quote) sts);
//        else if (sts instanceof Reply) return replies.put(sts.id(), (Reply) sts);
//        else if (sts instanceof Retweet) return retweets.put(sts.id(), (Retweet) sts);
//        else if (sts instanceof QuotedReply) return quotedReplies.put(sts.id(), (QuotedReply) sts);
//        else if (sts instanceof QuotedRetweet) return quotedRetweets.put(sts.id(), (QuotedRetweet) sts);
//        else if (sts instanceof ThreadSource<?>) return dummies.put(sts.id(), (ThreadSource<?>) sts);
//        throw new IllegalStateException();
//    }
//
//    // TODO: is not allowed to return dummies - FIX PLEASE
//    private Status<?> _statusOf(long uid) {
//        Status<?> res = tweets.get(uid);
//        if (nonNull(res)) return res;
//        res = quotes.get(uid);
//        if (nonNull(res)) return res;
//        res = replies.get(uid);
//        if (nonNull(res)) return res;
//        res = retweets.get(uid);
//        if (nonNull(res)) return res;
//        res = quotedReplies.get(uid);
//        if (nonNull(res)) return res;
//        res = quotedRetweets.get(uid);
//        if (nonNull(res)) return res;
//        res = dummies.get(uid);
//        return res;
//    }
//
//    private Status<?> _statusOf(BsonDocument bson) {
//        checkArgument(bson.containsKey("id"));
//        return _statusOf(int64(bson, "id"));
//    }
//
//    public Optional<Status<?>> statusOf(BsonDocument bson) {
//        return Optional.ofNullable(_statusOf(bson));
//    }
//
//    private Status<?> unDummy(BsonDocument bson, ThreadSource<?> dummy) {
//        checkState(dummy.isDummy(), nonNull(bson));
//        Status<?> res = null;
//        if (isTweet(bson)) {
//            res = new Tweet(bson);
//            //
//        }
//        else if (isRetweet(bson)) {
//            var oid = int64(bson, "retweeted_status");
//            ThreadSource<?> parent = (ThreadSource<?>) _statusOf(oid);
//            if (isNull(parent)) checkIsNull(storeStatus(parent = new ThreadSource<>(oid)));
//            res = new Retweet(bson, cast(parent));
//            ((ThreadSource<? extends Status<?>>) parent).addChild(res);
//        }
//        else if (isQuote(bson)) {
//            var oid = int64(bson, "quoted_status_id");
//            ThreadSource<?> parent = (ThreadSource<?>) _statusOf(oid);
//            if (isNull(parent)) checkIsNull(storeStatus(parent = new ThreadSource<>(oid)));
//            res = new Quote(bson, cast(parent));
//            ((ThreadSource<? extends Status<?>>) parent).addChild(res);
//            ((ThreadSource<?>) res).copyChildrenFrom(dummy);
//        }
//        else if (isReply(bson)) {
//            var oid = int64(bson, "in_reply_to_status_id");
//            ThreadSource<?> parent = (ThreadSource<?>) _statusOf(oid);
//            if (isNull(parent)) checkIsNull(storeStatus(parent = new ThreadSource<>(oid)));
//            res = new Reply(bson, cast(parent));
//            ((ThreadSource<? extends Status<?>>) parent).addChild(res);
//            ((ThreadSource<?>) res).copyChildrenFrom(dummy);
//        }
//        else if (isQuotedReply(bson)) {
//            var qid = int64(bson, "quoted_status_id");
//            ThreadSource<?> qte = (ThreadSource<?>) _statusOf(qid);
//            if (isNull(qte)) checkIsNull(storeStatus(qte = new ThreadSource<>(qid)));
//            var rid = int64(bson, "in_reply_to_status_id");
//            ThreadSource<?> rpl = (ThreadSource<?>) _statusOf(rid);
//            if (isNull(rpl)) checkIsNull(storeStatus(rpl = new ThreadSource<>(rid)));
//            res = new QuotedReply(bson, rpl, qte);
//            qte.addChild(res); rpl.addChild(res);
//            ((ThreadSource<?>) res).copyChildrenFrom(dummy);
//        }
//        else if (isQuotedRetweet(bson)) {
//            var qid = int64(bson, "quoted_status_id");
//            ThreadSource<?> qte = (ThreadSource<?>) _statusOf(qid);
//            if (isNull(qte)) checkIsNull(storeStatus(qte = new ThreadSource<>(qid)));
//            var rid = int64(bson, "retweeted_status");
//            ThreadSource<?> rtw = (ThreadSource<?>) _statusOf(rid);
//            if (isNull(rtw)) checkIsNull(storeStatus(rtw = new ThreadSource<>(rid)));
//            res = new QuotedRetweet(bson, rtw, qte);
//            qte.addChild(res); rtw.addChild(res);
//        }
//        else throw new IllegalStateException(bson.toJson());
//        if (nonNull(dummy.parent)) throw new IllegalStateException(bson.toJson());
//        return res;
//    }
//
//    private static IllegalStateException
//    existingException(Status<?> sts, BsonDocument bson, Class<?> clazz) {
//        return new IllegalStateException(
//                beautiful(bson) + "\n" +
//                        clazz.isInstance(sts)     + "\n" +
//                        sts.getClass().getName() + "  " + clazz.getName());
//    }
//
//    private <T extends Status<T>> T
//    existing(Status<?> status,
//             BsonDocument bson,
//             Class<T> clazz) {
//        if (++stsCnt % 10_000 == 0)
//            System.out.println(this.toString());
//        // make sure the status is ether an Object
//        // of the given class or a dummy
//        if (!clazz.isInstance(status)
//                && (clazz.isInstance(status) || !status.isDummy()))
//            throw existingException(status, bson, clazz);
//        if (status.isDummy()) {
//            status = unDummy(bson, checkNotNull(dummies.remove(status.id())));
//            storeStatus(status);
//        }
//        return clazz.cast(status);
//    }
//
//    private Tweet addTweet(BsonDocument bson) {
//        var sts = _statusOf(bson);
//        // CASE: we have already a status
//        // ether a dummy or the tweet itself
//        if (nonNull(sts) && int64(bson, "id")
//                == 1235998549235634178L)
//            return null;
//        if (nonNull(sts))
//            return existing(sts, bson, Tweet.class);
//        sts = new Tweet(bson);
//        storeStatus(sts);
//        return (Tweet) sts;
//    }
//
//    private Retweet addRetweet(BsonDocument bson) {
//        var rwt = _statusOf(bson);
//        // CASE: Retweet is already
//        // stored. Ether as a Tweet
//        // or a dummy
//        if (nonNull(rwt))
//            return existing(rwt, bson, Retweet.class);
//        // CASE: We have never seen this retweet
//        // before....
//        // id of the source tweet.
//        var oid = int64(bson, "retweeted_status");
//        // We have to check whether the source tweet is
//        // already there. In case it is not,
//        // has to be created.
//        var src = _statusOf(oid);
//        if (isNull(src))
//            checkIsNull(storeStatus(src = new ThreadSource<>(oid)));
//        else if (!(src instanceof ThreadSource<?>))
//            throw new IllegalStateException();
//        // Now create the new retweet
//        // object and set the parent
//        rwt = new Retweet(bson, (ThreadSource<?>) src);
//        // then set the child for the
//        // parent
//        ((ThreadSource<? extends Status<?>>) src)
//                .addChild(rwt);
//        // and store the Retweet ...
//        storeStatus(rwt);
//        return (Retweet) rwt;
//    }
//
//    // SAME AS RETWEET
//    private Quote addQuote(BsonDocument bson) {
//        var qte = _statusOf(bson);
//        if (nonNull(qte))
//            return existing(qte, bson, Quote.class);
//        // handle parent
//        var oid = int64(bson, "quoted_status_id");
//        var src = _statusOf(oid);
//        if (isNull(src))
//            checkIsNull(storeStatus(src = new ThreadSource<>(oid)));
//        else if (!(src instanceof ThreadSource<?>))
//            throw new IllegalStateException();
//        // add
//        qte = new Quote(bson, (ThreadSource<?>) src);
//        ((ThreadSource<? extends Status<?>>) src)
//                .addChild(qte);
//        storeStatus(qte);
//        return (Quote) qte;
//    }
//
//    private Reply addReply(BsonDocument bson) {
//        var rpl = _statusOf(bson);
//        if (nonNull(rpl) && int64(bson, "id")
//                == 1235998549235634178L)
//            return null;
//        if (nonNull(rpl))
//            return existing(rpl, bson, Reply.class);
//        var oid = int64(bson, "in_reply_to_status_id");
//        var src = _statusOf(oid);
//        if (isNull(src)) checkIsNull(storeStatus(src = new ThreadSource<>(oid)));
//        else if (!(src instanceof ThreadSource<?>)) throw new IllegalStateException();
//        rpl = new Reply(bson, (ThreadSource<?>) src);
//        ((ThreadSource<? extends Status<?>>) src)
//                .addChild(rpl);
//        storeStatus(rpl);
//        return (Reply) rpl;
//    }
//
//    private QuotedReply addQuotedReply(BsonDocument bson) {
//        var qrl = _statusOf(bson);
//        if (nonNull(qrl))
//            return existing(qrl, bson, QuotedReply.class);
//        var qid = int64(bson, "quoted_status_id");
//        ThreadSource<?> qte = (ThreadSource<?>) _statusOf(qid);
//        if (isNull(qte)) checkIsNull(storeStatus(qte = new ThreadSource<>(qid)));
//        var rid = int64(bson, "in_reply_to_status_id");
//        ThreadSource<?> rpl = (ThreadSource<?>) _statusOf(rid);
//        if (isNull(rpl)) checkIsNull(storeStatus(rpl = new ThreadSource<>(rid)));
//        qrl = new QuotedReply(bson, rpl, qte);
//        qte.addChild(qrl); rpl.addChild(qrl);
//        storeStatus(qrl);
//        return (QuotedReply) qrl;
//    }
//
//    private QuotedRetweet addQuotedRetweet(BsonDocument bson) {
//        var qrt = _statusOf(bson);
//        if (nonNull(qrt))
//            return existing(qrt, bson, QuotedRetweet.class);
//        var qid = int64(bson, "quoted_status_id");
//        ThreadSource<?> qte = (ThreadSource<?>) _statusOf(qid);
//        if (isNull(qte)) checkIsNull(storeStatus(qte = new ThreadSource<>(qid)));
//        var rid = int64(bson, "retweeted_status");
//        ThreadSource<?> rtw = (ThreadSource<?>) _statusOf(rid);
//        if (isNull(rtw)) checkIsNull(storeStatus(rtw = new ThreadSource<>(rid)));
//        qrt = new QuotedRetweet(bson, rtw, qte);
//        qte.addChild(qrt); rtw.addChild(qrt);
//        storeStatus(qrt);
//        return (QuotedRetweet) qrt;
//    }
//
//    public Iterator<Status<?>> stati() {
//        return Iterables.concat(
//                        tweets.values(),
//                        retweets.values(),
//                        quotes.values(),
//                        replies.values(),
//                        quotedRetweets.values(),
//                        quotedReplies.values(),
//                        dummies.values())
//                .iterator();
//    }
//
//    public Stream<Status<?>> stati(Predicate<Status<?>> pred) {
//        var list = new ArrayList<Status<?>>();
//        stati().forEachRemaining(list::add);
//        return list.stream().filter(pred);
//    }
//
//    public Optional<Status<?>> statusById(long id) {
//        Status<?> result = tweets.get(id);
//        if (isNull(result))
//            result = quotes.get(id);
//        if (isNull(result))
//            result = replies.get(id);
//        if (isNull(result))
//            result = retweets.get(id);
//        if (isNull(result))
//            result = quotedReplies.get(id);
//        if (isNull(result))
//            result = quotedRetweets.get(id);
//        return Optional.ofNullable(result);
//    }
//
//    Store checkpoint(Path out) {
//        checkArgument(isDirectory(out));
//        System.out.println("CHECKPOINT " + out);
//        persistStati(out, "tweets.gz", tweets.values());
//        persistStati(out, "retweets.gz", retweets.values());
//        persistStati(out, "quotes.gz", quotes.values());
//        persistStati(out, "replies.gz", replies.values());
//        persistStati(out, "quotedReplies.gz", quotedReplies.values());
//        persistStati(out, "quotedRetweets.gz", quotedRetweets.values());
//        return this;
//    }
//
//    public String toString() {
//        return "-------------------------------------------------->\n" +
//                "TWEETS:          " + tweets.size() + "\n" +
//                "RETWEETS:        " + retweets.size() + "\n" +
//                "QUOTES:          " + quotes.size() + "\n" +
//                "REPLIES:         " + replies.size() + "\n" +
//                "QUOTED_REPLIES:  " + quotedReplies.size() + "\n" +
//                "QUOTED_RETWEETS: " + quotedRetweets.size() + "\n" +
//                "DUMMIES:         " + dummies.size();
//    }
//}

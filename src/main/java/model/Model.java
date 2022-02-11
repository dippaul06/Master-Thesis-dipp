package model;


import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.eclipse.collections.api.factory.SortedMaps;
import system.Self;
import utils.BsonUtils;
import utils.Utils;

import java.util.*;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.Model.UserState.DELETED;
import static system.Contracts.checkArgument;
import static system.Contracts.checkNotNull;
import static utils.BsonUtils.beautiful;
import static utils.BsonUtils.int64;
import static utils.Utils.*;

// ----------------------------------------------
//  MODEL.
// ----------------------------------------------
public enum Model {
    ;

    // ----------------------------------------------
    //  ID.
    // ----------------------------------------------
    public interface Id {
        long id();
    }

    // ----------------------------------------------
    //  STATUS.
    // ----------------------------------------------
    public enum StatusState {IS_SOURCE, UNKNOWN, ENRICHED}

    private static LongList EMPTY_MENTIONS = new LongArrayList();
    private static List<String> EMPTY_S_LIST = new ArrayList<>();

    public static abstract class Status<T extends Status<T>> implements Id, Self<T> {
        private boolean isPermanentlyDeleted = false;
        private BsonDocument oldBson, newBson;
        private StatusState state = StatusState.UNKNOWN;
        private long userId;
        private final long id;

        Status(long _id) {
            id = _id;
            userId = -1L;
        }

        Status(BsonDocument _bson) {
            id = int64(_bson, "id");
            oldBson(_bson);
        }

        public boolean isDummy() {
            return isNull(oldBson);
        }

        public boolean isDeleted() {
            return nonNull(oldBson) && isNull(newBson);
        }

        boolean isBeefedUp() {
            return isNull(newBson);
        }

        public long id() {
            return id;
        }

        public long userId() {
            return userId;
        }

        public StatusState state() {
            return state;
        }

        public Date createdAt() {
            checkState(!isDummy());
            return extractCreatedAt(oldBson);
        }

        public String text() {
            checkState(!isDummy());
            return extractString(oldBson, "full_text");
        }

        public BsonDocument oldBson() {
            return oldBson;
        }

        public void oldBson(BsonDocument bson) {
            userId = extractUserId(bson);
            oldBson = bson;
        }

        public BsonDocument newBson() {
            return newBson;
        }

        public void newBson(BsonDocument bson) {
            userId = extractUserId(bson);
            newBson = bson;
        }

        T state(StatusState _state) {
            state = _state;
            return self();
        }

        boolean contains5G() {
            var str = oldBson.getString("full_text")
                    .getValue();
            return str.contains(" 5G ")
                    || str.contains(" #5G ")
                    || str.contains(" 5g ")
                    || str.contains(" #5g ")
                    || str.startsWith("5G ")
                    || str.startsWith("5g ");
        }

        ThreadSource<T> unDummy(ThreadSource<?> dummy) {
            checkState(dummy.isDummy());
            checkState(this instanceof ThreadSource<?>);
            ((ThreadSource<?>) this).copyChildrenFrom(dummy);
            return (ThreadSource<T>) this;
        }

        private BsonArray entities(String field) {
            var bson = isNull(newBson) ? oldBson : newBson;
            if (isNull(bson) || !bson.containsKey("entities"))
                return null;
            var entities = bson.getDocument("entities");
            if (isNull(entities)
                    || !entities.containsKey(field)
                    || !entities.isArray(field))
                return null;
            return entities.getArray(field);
        }

        public LongList mentionIds() {
            var mentions = entities("user_mentions");
            if (isNull(mentions)) return EMPTY_MENTIONS;
            var result = new LongArrayList();
            for (var user : mentions) {
                if (user.isDocument()) {
                    var doc = user.asDocument();
                    if (doc.containsKey("id")) {
                        result.add(Utils.extractId(doc));
                    }
                }
            }
            return result;
        }

        public List<String> urls() {
            var urls = entities("urls");
            if (isNull(urls)) return EMPTY_S_LIST;
            var result = new ArrayList<String>();
            for (var url : urls) {
                if (url.isDocument()) {
                    var doc = url.asDocument();
                    if (doc.containsKey("expanded_url")) {
                        result.add(Utils.extractString(doc, "expanded_url"));
                    }
                }
            }
            return result;
        }

        public List<String> hashtags() {
            var hashtags = entities("hashtags");
            if (isNull(hashtags)) return EMPTY_S_LIST;
            var result = new ArrayList<String>();
            for (var tag : hashtags) {
                if (tag.isDocument()) {
                    var doc = tag.asDocument();
                    if (doc.containsKey("text")) {
                        result.add(Utils.extractString(doc, "text"));
                    }
                }
            }
            return result;
        }
    }

    // ----------------------------------------------
    //  RETWEET.
    // ----------------------------------------------
    public static class Retweet extends Status<Retweet> {
        private ThreadSource<?> parent;

        Retweet(BsonDocument retweet, ThreadSource<?> _parent) {
            super(retweet);
            checkNotNull(parent = _parent);
        }

        public ThreadSource<?> parent() {
            return parent;
        }
    }

    // ----------------------------------------------
    //  QUOTED-RETWEET.
    // ----------------------------------------------
    public static class QuotedRetweet extends Status<QuotedRetweet> {
        private ThreadSource<?> rtweSrc, quotSrc;

        QuotedRetweet(BsonDocument bson, ThreadSource<?> _rtwSrc, ThreadSource<?> _quotSrc) {
            super(bson);
            checkNotNull(rtweSrc = _rtwSrc);
            checkNotNull(quotSrc = _quotSrc);
        }

        public ThreadSource<?> quotSrc() {
            return quotSrc;
        }

        public ThreadSource<?> rtweSrc() {
            return rtweSrc;
        }
    }

    // ----------------------------------------------
    // SOURCE.
    // ----------------------------------------------
    public static class ThreadSource<T extends Status<T>> extends Status<T> {
        protected ThreadSource<?> parent;
        protected Map<Long, Quote> quotes = new HashMap<>();
        protected Map<Long, Reply> replies = new HashMap<>();
        protected Map<Long, Retweet> retweets = new HashMap<>();
        protected Map<Long, ThreadSource<?>> dummies = new HashMap<>();
        protected Map<Long, QuotedReply> quotedReplies = new HashMap<>();
        protected Map<Long, QuotedRetweet> quotedRetweets = new HashMap<>();

        ThreadSource(BsonDocument bson) {
            super(bson);
        }

        ThreadSource(long id) {
            super(id);
        }

        T addChild(Status<?> sts) {
            if (sts instanceof Retweet) retweets.put(sts.id(), (Retweet) sts);
            else if (sts instanceof Reply) replies.put(sts.id(), (Reply) sts);
            else if (sts instanceof Quote) quotes.put(sts.id(), (Quote) sts);
            else if (sts instanceof QuotedReply) quotedReplies.put(sts.id(), (QuotedReply) sts);
            else if (sts instanceof QuotedRetweet) quotedRetweets.put(sts.id(), (QuotedRetweet) sts);
            else if (sts instanceof ThreadSource<?> && sts.isDummy()) dummies.put(sts.id(), (ThreadSource<?>) sts);
            else throw new IllegalStateException(String.valueOf(sts.id()));
            return self();
        }

        protected void copyChildrenFrom(ThreadSource<?> dummy) {
            dummy.quotes.values().forEach(quote -> quote.parent = this);
            dummy.replies.values().forEach(reply -> reply.parent = this);
            dummy.retweets.values().forEach(retweet -> retweet.parent = this);
            dummy.dummies.values().forEach(_dummy -> _dummy.parent = this);
            dummy.quotedReplies.values().forEach(quotedReply -> {
                if (quotedReply.quotSrc.id() == this.id())
                    quotedReply.quotSrc = this;
                else if (quotedReply.replSrc.id() == this.id())
                    quotedReply.replSrc = this;
                else throw new IllegalStateException();
            });
            dummy.quotedRetweets.values().forEach(quotedRetweet -> {
                if (quotedRetweet.quotSrc.id() == this.id())
                    quotedRetweet.quotSrc = this;
                else if (quotedRetweet.rtweSrc.id() == this.id())
                    quotedRetweet.rtweSrc = this;
                else throw new IllegalStateException();
            });
            quotes.putAll(dummy.quotes);
            retweets.putAll(dummy.retweets);
            replies.putAll(dummy.replies);
            dummies.putAll(dummy.dummies);
            quotedRetweets.putAll(dummy.quotedRetweets);
            quotedReplies.putAll(dummy.quotedReplies);
        }

        public ThreadSource<?> parent() {
            return parent;
        }

        public Map<Long, Quote> quotes() {
            return quotes;
        }

        public Map<Long, Reply> replies() {
            return replies;
        }

        public Map<Long, Retweet> retweets() {
            return retweets;
        }

        public Map<Long, ThreadSource<?>> dummies() {
            return dummies;
        }

        public Map<Long, QuotedReply> quotedReplies() {
            return quotedReplies;
        }

        public Map<Long, QuotedRetweet> quotedRetweets() {
            return quotedRetweets;
        }
    }

    // ----------------------------------------------
    //  TWEET.
    // ----------------------------------------------
    public static class Tweet extends ThreadSource<Tweet> {
        Tweet(BsonDocument bson) {
            super(bson);
        }

        Tweet(long id) {
            super(id);
        }
    }

    // ----------------------------------------------
    //  QUOTE.
    // ----------------------------------------------
    public static class Quote extends ThreadSource<Quote> {
        Quote(BsonDocument bson, ThreadSource<?> _parent) {
            super(bson);
            checkNotNull(parent = _parent);
        }

        public ThreadSource<?> parent() {
            return parent;
        }
    }

    // ----------------------------------------------
    //  REPLY.
    // ----------------------------------------------
    public static class Reply extends ThreadSource<Reply> {
        Reply(BsonDocument bson, ThreadSource<?> _parent) {
            super(bson);
            checkNotNull(parent = _parent);
        }

        public ThreadSource<?> parent() {
            return parent;
        }
    }

    // ----------------------------------------------
    //  QUOTED-REPLY.
    // ----------------------------------------------
    public static class QuotedReply extends ThreadSource<QuotedReply> {
        private ThreadSource<?> replSrc, quotSrc;

        QuotedReply(BsonDocument bson, ThreadSource<?> _replSrc, ThreadSource<?> _quotSrc) {
            super(bson);
            checkNotNull(replSrc = _replSrc);
            checkNotNull(quotSrc = _quotSrc);
        }

        public ThreadSource<?> replSrc() {
            return replSrc;
        }

        public ThreadSource<?> quotSrc() {
            return quotSrc;
        }
    }

    // ----------------------------------------------
    //  USER.
    // ----------------------------------------------

    public enum UserState { DELETED, EXISTS, UNKNOWN, NOT_AVAILABLE }

    public static class User implements Id {
        public UserState state;
        private final long id;
        private long index;

        public Map<Date, BsonDocument> versions
                = SortedMaps.mutable.with(Date::compareTo);

        private User(long _id) {
            state = UserState.UNKNOWN;
            id = _id;
        }

        private User(BsonDocument bson) {
            checkArgument(bson.containsKey("text"), "VERSION IS NOT A TWEET");
            checkArgument(bson.containsKey("user"), "TWEET DOES NOT CONTAIN USER");
            if (bson.get("user").isDocument()) {
                var userBson = bson.getDocument("user");
                state = UserState.EXISTS;
                id = extractIdStr(userBson);
                addVersion(userBson);
            } else if (bson.get("user").isInt64()
                    || bson.get("user").isInt64()) {
                state = UserState.UNKNOWN;
                id = extractLong(bson, "user");
            } else throw new IllegalStateException();
        }

        Map<Long, Tweet> tweets = new HashMap<>();
        Map<Long, Quote> quotes = new HashMap<>();
        Map<Long, Reply> replies = new HashMap<>();
        Map<Long, Retweet> retweets = new HashMap<>();
        Map<Long, QuotedReply> quotedReplies = new HashMap<>();
        Map<Long, QuotedRetweet> quotedRetweets = new HashMap<>();

        public Map<Long, Retweet> retweets() {
            return retweets;
        }

        public void addVersion(BsonDocument bson) {
            if (!bson.containsKey("full_text"))
                throw new IllegalStateException(beautiful(bson));
            checkArgument(bson.containsKey("user"), "VERSION IS NOT A TWEET");
            if (!bson.get("user").isDocument()) {
                return;
            }
            var userBson = bson.getDocument("user");
            checkArgument(extractIdStr(userBson) == id, "BSON ID DOES NOT MATCH USER ID");
            versions.put(extractCreatedAt(userBson), bson);
        }

        public static User newUserFromId(long userId) {
            return new User(userId);
        }

        public static User newUserFromTweet(BsonDocument bson) {
            return new User(bson);
        }

        public static User newUserFromReloadedUserProfile(BsonDocument bson) {
            var user = new User(extractIdStr(bson));
            user.versions.put(new Date(), bson);
            user.state = UserState.EXISTS;
            return user;
        }

        public String toString() {
            return id + "";
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass())
                return false;
            User user = (User) o;
            return new EqualsBuilder()
                    .append(id, user.id)
                    .isEquals();
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .toHashCode();
        }

        int cnt = 0;

        public BsonDocument first() {
            checkState(versions.size() > 0, "USER:  " + id() + " " + cnt++);
            for (BsonDocument doc : versions.values()) {
                if (doc.containsKey("full_text")) {
                    return doc.getDocument("user");
                } else return doc;
            }
            throw new IllegalStateException();
        }

        public boolean isDeleted() {
            return state == DELETED;
        }

        public long id() {
            return id;
        }

        public Date createdAt() {
            return extractCreatedAt(first());
        }

        public boolean isProtected() {
            if (!first().containsKey("protected")) return false;
            else return extractBoolean(first(), "protected");
        }

        public boolean isVerified() {
            if (!first().containsKey("verified")) return false;
            else return extractBoolean(first(), "verified");
        }

        public int followerCount() {
            return extractInt(first(), "followers_count");
        }

        public int friendsCount() {
            if (!first().containsKey("friends_count")) {
                System.out.println("NUMBER OF VERSIONS: " + versions.size());
                System.out.println("ID: " + id());
                versions.values()
                        .stream()
                        .map(BsonUtils::beautiful)
                        .forEach(System.out::println);
                throw new IllegalStateException();
            }
            return extractInt(first(), "friends_count");
        }

        public int favouritesCount() {
            return extractInt(first(), "favourites_count");
        }

        public String description() {
            return extractString(first(), "description");
        }

        public int statusesCount() {
            return extractInt(first(), "statuses_count");
        }

        public String screenName() {
            return extractString(first(), "screen_name");
        }

        public String location() {
            return extractString(first(), "location");
        }
//        public String url() { if (first().isEmpty()) return ""; return extractString(first(), "url"); }
    }
}
package transform;


import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.bson.BsonDocument;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public enum Model {
    ;

    // ----------------------------------------------
    //  USER.
    // ----------------------------------------------
    public static class User implements Serializable {
        public final Object2IntOpenHashMap<String> langs = new Object2IntOpenHashMap<>();;
        public final ArrayList<Tweet> tweets = new ArrayList<>();
        public final long id;
        public User(long _id) {
            id = _id;
        }
        public User addTweet(Tweet tweet) {
            langs.addTo(tweet.language, 1);
            tweets.add(tweet);
            return this;
        }
        static User of(long id) {
            return new User(id);
        }
    }

    // ----------------------------------------------
    //  RETWEET.
    // ----------------------------------------------
    public static class Retweet implements Serializable {
        public final long tweetId, userId, retweetedId;
        public final String language;
        public final Date createdAt;
        public Retweet(long _tweetId, long _userId, long _retweetedId,
                       Date _createdAt, String _language) {
            tweetId = _tweetId;
            userId = _userId;
            createdAt = _createdAt;
            language = _language;
            retweetedId = _retweetedId;
        }
        public String toString() {
            return "Retweet{" +
                    "tweetId=" + tweetId +
                    ", userId=" + userId +
                    ", retweetedId=" + retweetedId +
                    ", language='" + language + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass()
                    != o.getClass())
                return false;
            var retweet = (Retweet) o;
            return tweetId == retweet.tweetId;
        }

        public int hashCode() {
            return Long.valueOf(tweetId).hashCode();
        }

        public static Retweet of(long uid, BsonDocument dta, SimpleDateFormat format) throws ParseException {
            var dte = format.parse(dta.getString("created_at").getValue());
            var lng = dta.getString("lang").getValue();
            var usr = dta.getInt64("user").longValue();
            var rid = dta.getInt64("retweeted_status").longValue();
            return new Retweet(uid, usr, rid, dte, lng);
        }
    }

    // ----------------------------------------------
    //  TWEET.
    // ----------------------------------------------
    // TODO add mentions
    public static class Tweet implements Serializable {
        public final ArrayList<Retweet> retweets = new ArrayList<>();
        public final long tweetId, userId;
        public final String language;
        public final Date createdAt;
        public Tweet(long _tweetId, long _userId,
                     Date _createdAt, String _language) {
            tweetId = _tweetId;
            userId = _userId;
            createdAt = _createdAt;
            language = _language;
        }
        public String toString() {
            return "Tweet{" +
                    "retweets=" + retweets +
                    ", tweetId=" + tweetId +
                    ", userId=" + userId +
                    ", language='" + language + '\'' +
                    ", createdAt=" + createdAt +
                    '}';
        }
        public static Tweet of(long uid, BsonDocument dta, SimpleDateFormat format) throws ParseException {
            var dte = format.parse(dta.getString("created_at").getValue());
            var lng = dta.getString("lang").getValue();
            var usr = dta.getInt64("user").longValue();
            return new Tweet(uid, usr, dte, lng);
        }
    }
}
package model;

import model.Locator.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

public enum Model {
    ;

    public static class Status {
        public final static Location emptyLoc = new Location("","", "", "");
        public boolean isRetweet = false;
        public Set<String> hashtags;
        public long twtId, usrId;
        public long rtwId, rtuId;
        public int retweetCnt;
        public Location loc;
        public String txt;
        public Date date;
        public Status(long twtId, String txt) {
            this.hashtags = new HashSet<>();
            this.twtId = twtId;
            this.txt = txt;
        }
        public String toString() {
            return "MinTweet{" +
                    "hashtags=" + hashtags +
                    ", twtId=" + twtId +
                    ", usrId=" + usrId +
                    ", loc=" + loc +
                    ", txt='" + txt + '\'' +
                    ", date=" + date +
                    ", isRetweet=" + isRetweet +
                    '}';
        }
        public JSONObject toJson() {
            var json = new JSONObject();
            json.put("tId", twtId);
            json.put("uId", usrId);
            json.put("loc", isNull(loc)? emptyLoc.toJson(): loc.toJson());
            json.put("rtw", isRetweet);
            if (!isRetweet) {
                json.put("rtwCnt", retweetCnt);
            }
            else {
                json.put("rtwId", rtwId);
                json.put("rtuId", rtuId);
            }
            var tags = new JSONArray();
            tags.addAll(hashtags);
            json.put("tag", tags);
            json.put("dte", date.getTime());
            json.put("txt", txt.replace(System.lineSeparator(), " "));
            return json;
        }
    }

    public static class User {
        public long usrId;
    }
}

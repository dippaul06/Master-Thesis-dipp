package experiments;

import experiments.Timeline.Bucket;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import magma.system.Log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static magma.utils.FileUtils.newBufferedWriterAppend;
import static magma.utils.FileUtils.newFile;
import static magma.utils.Utils.sortMap;

public class Hashtags {

    final Timeline timeLine;

    public Hashtags(Timeline timeLine) {
        this.timeLine = timeLine;
    }

    public void top1000Tweets(Path rltFle) throws IOException {
        final var all = new Object2IntOpenHashMap<String>();
        final var twt = new Object2IntOpenHashMap<String>();
        final var rtw = new Object2IntOpenHashMap<String>();
        final var tweets = timeLine.tweets();
        for (int i = 0; i < tweets.size(); i++) {
            var tweet = tweets.get(i);
                for (var tag : tweet.hashtags) {
                    all.addTo(tag.toLowerCase(), 1);
                }
            if (tweet.isRetweet) {
                for (var tag : tweet.hashtags) {
                    rtw.addTo(tag.toLowerCase(), 1);
                }
            }
            else {
                for (var tag : tweet.hashtags) {
                    twt.addTo(tag.toLowerCase(), 1);
                }
            }
        }
        var writer = newBufferedWriterAppend(newFile(rltFle));
        var result = sortMap(all, 1000);
        writer.write("tag,all,tweet,retweet\n");
        for (var val : result) {
            var tag = val.getKey();
            writer.write( tag + "," + val.getValue()
                                  + "," + twt.getInt(tag)
                                  + "," + rtw.getInt(tag)
                                  + "\n");
        }
        writer.close();
    }

    public void top10TweetsByDay(Path rltFle) throws IOException {
        var buckets = timeLine.splitByDay().buckets();
        writeTagsToFile(timeLine.splitByDay().buckets(), rltFle);
    }

    public void top10TweetsByHour(Path rltFle) throws IOException {
        var buckets = timeLine.splitByHour().buckets();
        writeTagsToFile(buckets, rltFle);
    }

    private void writeTagsToFile(List<Bucket> buckets, Path rltFle) throws IOException {
        var writer = newBufferedWriterAppend(newFile(rltFle));
        writer.write("date,tag,all,tweet,retweet\n");
        for (var bucket : buckets) {
            final var all = new Object2IntOpenHashMap<String>();
            final var twt = new Object2IntOpenHashMap<String>();
            final var rtw = new Object2IntOpenHashMap<String>();
            final var tweets = bucket.statuses;
            for (int i = 0; i < tweets.size(); i++) {
                var tweet = tweets.get(i);
                for (var tag : tweet.hashtags) {
                    all.addTo(tag.toLowerCase(), 1);
                }
                if (tweet.isRetweet) {
                    for (var tag : tweet.hashtags)
                        rtw.addTo(tag.toLowerCase(), 1);
                }
                else {
                    for (var tag : tweet.hashtags)
                        twt.addTo(tag.toLowerCase(), 1);
                }
            }
            var result = sortMap(all, 3);
            for (var val : result) {
                Log.info("IN RESULT: " + result.size());
                var tag = val.getKey();
                writer.write(bucket.start + "," + tag
                        + "," + val.getValue()
                        + "," + twt.getInt(tag)
                        + "," + rtw.getInt(tag)
                        + "\n");
            }
        }
        writer.close();
    }

    public void top10PairsTweet(Path rltFle) throws IOException {
        var writer = newBufferedWriterAppend(newFile(rltFle));
        writer.write("mT,t1,c1,t2,c2,t3,c3,t4,c4,t5,c5,t6,c6,t7,c7,t8,c8,t9,c9,t10,c10,\n");
        final var tweets = timeLine.tweets();
        final var top10 = new Object2IntOpenHashMap<String>();
        for (var tweet : tweets) {
            if (!tweet.isRetweet) {
                for (var tag : tweet.hashtags) {
                    top10.addTo(tag, 1);
                }
            }
        }
        final var top = sortMap(top10, 10);
        for (var val : top) {
            final var mainTag = val.getKey();
            final var cntTags = new Object2IntOpenHashMap<String>();
            for (var tweet : tweets) {
                var tags = tweet.hashtags;
                if (tags.contains(mainTag)) {
                    for (var tag : tags) {
                        cntTags.addTo(tag, 1);
                    }
                }
            }
            var lst = sortMap(cntTags, 10);
            writer.write(mainTag
                                 + "," + lst.get(0).getKey()
                                 + "," + lst.get(0).getValue()
                                 + "," + lst.get(1).getKey()
                                 + "," + lst.get(1).getValue()
                                 + "," + lst.get(2).getKey()
                                 + "," + lst.get(2).getValue()
                                 + "," + lst.get(3).getKey()
                                 + "," + lst.get(3).getValue()
                                 + "," + lst.get(4).getKey()
                                 + "," + lst.get(4).getValue()
                                 + "," + lst.get(5).getKey()
                                 + "," + lst.get(5).getValue()
                                 + "," + lst.get(6).getKey()
                                 + "," + lst.get(6).getValue()
                                 + "," + lst.get(7).getKey()
                                 + "," + lst.get(7).getValue()
                                 + "," + lst.get(8).getKey()
                                 + "," + lst.get(8).getValue()
                                 + "," + lst.get(9).getKey()
                                 + "," + lst.get(9).getValue()
                                 + "\n");
        }
        writer.close();
    }

    public static void run(Timeline timeline, Path resultFolder) throws IOException {
        Log.info("START ANALYSE HASHTAGS");
        var inst = new Hashtags(timeline);
        Log.info("======================> A");
        inst.top1000Tweets(resultFolder.resolve("hashtags__top__1000.csv"));
        Log.info("======================> B");
        inst.top10TweetsByDay(resultFolder.resolve("hashtags__top10__by_day.csv"));
        Log.info("======================> C");
        inst.top10TweetsByHour(resultFolder.resolve("hashtags__top__10_by_hour.csv"));
        Log.info("======================> D");
        inst.top10PairsTweet(resultFolder.resolve("hashtags__top__10_pairs.csv"));
    }
}

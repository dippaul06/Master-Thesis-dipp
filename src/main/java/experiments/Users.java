//package experiments;
//
//import experiments.Timeline.Bucket;
//import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
//import magma.system.Log;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.List;
//
//import static magma.utils.FileUtils.newBufferedWriterAppend;
//import static magma.utils.FileUtils.newFile;
//import static magma.utils.Utils.sortMap;
//
//public class Users {
//
//    final Timeline timeLine;
//
//    public Users(Timeline timeLine) {
//        this.timeLine = timeLine;
//    }
//
//    public void top1000Users(Path rltFle) throws IOException {
//        final var allUsr = new Long2IntOpenHashMap();
//        final var rtwUsr = new Long2IntOpenHashMap();
//        final var twtUsr = new Long2IntOpenHashMap();
//        final var tweets = timeLine.tweets();
//        for (int i = 0; i < tweets.size(); i++) {
//            var tweet = tweets.get(i);
//            allUsr.addTo(tweet.usrId, 1);
//            if (tweet.isRetweet) {
//                rtwUsr.addTo(tweet.usrId, 1);
//            }
//            else {
//                twtUsr.addTo(tweet.usrId, 1);
//            }
//        }
//        var writer = newBufferedWriterAppend(newFile(rltFle));
//        var result = sortMap(allUsr, 1000);
//        writer.write("user,all,tweet,retweet\n");
//        for (var val : result) {
//            var usr = val.getLongKey();
//            writer.write( usr + "," + val.getIntValue()
//                    + "," + twtUsr.get(usr)
//                    + "," + rtwUsr.get(usr)
//                    + "\n");
//        }
//        writer.close();
//    }
//
//    private void writeUsersToFile(List<Bucket> buckets, Path rltFle) throws IOException {
//        var writer = newBufferedWriterAppend(newFile(rltFle));
//        writer.write("date,tag,all,tweet,retweet,bucket\n");
//        for (var bucket : buckets) {
//            final var allUsr = new Long2IntOpenHashMap();
//            final var twtUsr = new Long2IntOpenHashMap();
//            final var rtwUsr = new Long2IntOpenHashMap();
//            final var tweets = bucket.statuses;
//            for (int i = 0; i < tweets.size(); i++) {
//                var tweet = tweets.get(i);
//                allUsr.addTo(tweet.usrId, 1);
//                if (tweet.isRetweet) {
//                    rtwUsr.addTo(tweet.usrId, 1);
//                }
//                else {
//                    twtUsr.addTo(tweet.usrId, 1);
//                }
//            }
//            var result = sortMap(allUsr, 100);
//            for (var val : result) {
//                Log.info("IN RESULT: " + result.size());
//                var usr = val.getLongKey();
//                writer.write(bucket.start + "," + usr
//                        + "," + val.getIntValue()
//                        + "," + twtUsr.get(usr)
//                        + "," + rtwUsr.get(usr)
//                        + "," + bucket.statuses.size()
//                        + "\n");
//            }
//        }
//        writer.close();
//    }
//
//    public void top10UsersByDay(Path rltFle) throws IOException {
//        writeUsersToFile(timeLine.splitByDay().buckets(), rltFle);
//    }
//
//    public void top10UserByHour(Path rltFle) throws IOException {
//        writeUsersToFile(timeLine.splitByHour().buckets(), rltFle);
//    }
//
//    public void top10UserByWeek(Path rltFle) throws IOException {
//        writeUsersToFile(timeLine.splitByHour().buckets(), rltFle);
//    }
//
//    public static void run(Timeline timeline, Path resultFolder) throws IOException {
//        Log.info("START ANALYSE HASHTAGS");
//        var inst = new Users(timeline);
//        Log.info("======================> A");
//        inst.top1000Users(resultFolder.resolve("users__top__1000.csv"));
//        Log.info("======================> B");
//        inst.top10UsersByDay(resultFolder.resolve("users__top__10__by_day.csv"));
//        Log.info("======================> C");
//        inst.top10UserByHour(resultFolder.resolve("users__top__10_by_hour.csv"));
//        Log.info("======================> D");
//        inst.top10UserByWeek(resultFolder.resolve("users__top__10_by_week.csv"));
//    }
//}

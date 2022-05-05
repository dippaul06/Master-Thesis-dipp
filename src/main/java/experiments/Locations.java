//package experiments;
//
//import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
//import magma.system.Log;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.List;
//
//import static experiments.Timeline.*;
//import static java.util.Objects.isNull;
//import static magma.utils.FileUtils.newBufferedWriterAppend;
//import static magma.utils.FileUtils.newFile;
//import static magma.utils.Utils.sortMap;
//
//public class Locations {
//
//    final Timeline timeLine;
//
//    public Locations(Timeline timeLine) {
//        this.timeLine = timeLine;
//    }
//
//    public void top1000Countries(Path rltFle) throws IOException {
//        final var all = new Object2IntOpenHashMap<String>();
//        final var twt = new Object2IntOpenHashMap<String>();
//        final var rtw = new Object2IntOpenHashMap<String>();
//        final var tweets = timeLine.tweets();
//        for (int i = 0; i < tweets.size(); i++) {
//            var tweet = tweets.get(i);
//            if (isNull(tweet.loc)) continue;
//            if (tweet.loc.countryCode.isEmpty()) continue;
//            var cc = tweet.loc.countryCode.get().toLowerCase();
//            if (cc.equals("") || cc.equals("none")) continue;
//            all.addTo(cc, 1);
//            if (tweet.isRetweet) {
//                rtw.addTo(cc, 1);
//            }
//            else {
//                twt.addTo(cc, 1);
//            }
//        }
//        var writer = newBufferedWriterAppend(newFile(rltFle));
//        var result = sortMap(all, 1000);
//        writer.write("country,all,tweet,retweet\n");
//        for (var val : result) {
//            var cc = val.getKey();
//            writer.write( cc + "," + val.getValue()
//                    + "," + twt.getInt(cc)
//                    + "," + rtw.getInt(cc)
//                    + "\n");
//        }
//        writer.close();
//    }
//
//    public void top1000Cities(Path rltFle) throws IOException {
//        final var all = new Object2IntOpenHashMap<String>();
//        final var twt = new Object2IntOpenHashMap<String>();
//        final var rtw = new Object2IntOpenHashMap<String>();
//        final var tweets = timeLine.tweets();
//        for (int i = 0; i < tweets.size(); i++) {
//            var tweet = tweets.get(i);
//            if (isNull(tweet.loc)) continue;
//            if (tweet.loc.city.isEmpty()) continue;
//            var cc = tweet.loc.city.get().toLowerCase();
//            if (cc.equals("") || cc.equals("none")) continue;
//            all.addTo(cc, 1);
//            if (tweet.isRetweet) {
//                rtw.addTo(cc, 1);
//            }
//            else {
//                twt.addTo(cc, 1);
//            }
//        }
//        var writer = newBufferedWriterAppend(newFile(rltFle));
//        var result = sortMap(all, 1000);
//        writer.write("city,all,tweet,retweet\n");
//        for (var val : result) {
//            var cc = val.getKey();
//            writer.write( cc + "," + val.getValue()
//                    + "," + twt.getInt(cc)
//                    + "," + rtw.getInt(cc)
//                    + "\n");
//        }
//        writer.close();
//    }
//
//    public void top10CountriesByDay(Path rltFle) throws IOException {
//        var buckets = timeLine.splitByDay().buckets();
//        writeCountriesToFile(timeLine.splitByDay().buckets(), rltFle);
//    }
//
//    public void top10CountriesByHour(Path rltFle) throws IOException {
//        var buckets = timeLine.splitByHour().buckets();
//        writeCountriesToFile(buckets, rltFle);
//    }
//
//    private void writeCountriesToFile(List<Bucket> buckets, Path rltFle) throws IOException {
//        var writer = newBufferedWriterAppend(newFile(rltFle));
//        writer.write("date,country,all,tweet,retweet\n");
//        for (var bucket : buckets) {
//            final var all = new Object2IntOpenHashMap<String>();
//            final var twt = new Object2IntOpenHashMap<String>();
//            final var rtw = new Object2IntOpenHashMap<String>();
//            final var tweets = bucket.statuses;
//            for (int i = 0; i < tweets.size(); i++) {
//                var tweet = tweets.get(i);
//                if (isNull(tweet.loc)) continue;
//                if (tweet.loc.city.isEmpty()) continue;
//                var cc = tweet.loc.city.get().toLowerCase();
//                if (cc.equals("") || cc.equals("none")) continue;
//                all.addTo(cc, 1);
//                if (tweet.isRetweet) {
//                    rtw.addTo(cc, 1);
//                }
//                else {
//                    twt.addTo(cc, 1);
//                }
//            }
//            var result = sortMap(all, 3);
//            for (var val : result) {
//                Log.info("IN RESULT: " + result.size());
//                var cc = val.getKey();
//                writer.write(bucket.start + "," + cc
//                        + "," + val.getValue()
//                        + "," + twt.getInt(cc)
//                        + "," + rtw.getInt(cc)
//                        + "\n");
//            }
//        }
//        writer.close();
//    }
//
//    public static void run(Timeline timeline, Path resultFolder) throws IOException {
//        Log.info("START ANALYSE HASHTAGS");
//        var inst = new Locations(timeline);
//        Log.info("======================> A");
//        inst.top1000Countries(resultFolder.resolve("locations__top_1000_countries.csv"));
//        Log.info("======================> B");
//        inst.top1000Cities(resultFolder.resolve("locations__top_1000_cities.csv"));
//        Log.info("======================> C");
//        inst.top10CountriesByDay(resultFolder.resolve("locations__top_1000_countries_by_day.csv"));
//        Log.info("======================> D");
//        inst.top10CountriesByHour(resultFolder.resolve("locations__top_1000_countries_by_hour.csv"));
//    }
//}

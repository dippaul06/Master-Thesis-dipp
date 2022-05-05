//package experiments;
//
//import magma.system.Log;
//import model.Model.Status;
//import magma.utils.TimeUtils;
//
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.function.Function;
//
//import static model.Transformer.decompress;
//import static java.util.Comparator.comparingLong;
//
//public class Timeline {
//
//    private final List<Bucket> buckets;
//    private final List<Status> tweets;
//    private final Date bigBang, amagedon;
//
//    public Timeline(Path folder) {
//        buckets = new ArrayList<>();
//        tweets = decompress(folder)
//                .stream()
//                .sorted(comparingLong(t -> t.date.getTime()))
//                .toList();
//        bigBang  = tweets.get(0).date;
//        amagedon = tweets.get(tweets.size() - 1).date;
//        Log.info("TIMELINE DONE");
//    }
//
//    public static class Bucket {
//        final Date start;
//        final List<Status> statuses;
//        Bucket(Date start) {
//            this.start = start;
//            this.statuses = new ArrayList<>();
//        }
//    }
//
//    public Timeline split(Function<Date, Date> incr) {
//        if (!buckets.isEmpty()) buckets.clear();
//        var time = incr.apply(bigBang);
//        final var twts = tweets;
//        for (int i = 0; i < twts.size();) {
//            Status twt;
//            var bucket = new Bucket(time);
//            while ((twt = twts.get(i++)).date.before(time)
//                    && i < twts.size()) {
//                bucket.statuses.add(twt);
//            }
//            time = incr.apply(time);
//            buckets.add(bucket);
//        }
//        return this;
//    }
//
//    public Timeline splitByHour() {
//        var split = split(TimeUtils::addHour);
//        Log.info("SPLIT INTO: " + split.buckets.size() + " BUCKETS");
//        return split;
//    }
//
//    public Timeline splitByDay() {
//        var split = split(TimeUtils::addDay);
//        Log.info("SPLIT INTO: " + split.buckets.size() + " BUCKETS");
//        return split;
//    }
//
//    public Timeline splitByWeek() {
//        var split = split(TimeUtils::addWeek);
//        Log.info("SPLIT INTO: " + split.buckets.size() + " BUCKETS");
//        return split;
//    }
//
//    public List<Bucket> buckets() { return buckets; }
//    public List<Status> tweets() { return tweets; }
//    public Date bigBang() { return bigBang; }
//    public Date amagedon() { return amagedon; }
//}

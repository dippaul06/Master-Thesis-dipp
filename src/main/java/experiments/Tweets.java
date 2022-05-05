//package experiments;
//
//import model.Model.Status;
//import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
//import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
//import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
//import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
//import magma.utils.FileUtils;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.regex.Pattern;
//
//public class Tweets {
//
//    final Timeline timeLine;
//
//    public Tweets(Timeline timeLine) {
//        this.timeLine = timeLine;
//    }
//
//    private static final Pattern pattern1 = Pattern.compile("@\\S+");
//    private static final Pattern pattern2 = Pattern.compile(" +");
//    private static final Pattern pattern3 = Pattern.compile(" $");
//    static String prepareTxt(String txt){
//        final var lower = txt.toLowerCase();
//        final var rs1 = pattern1.matcher(lower).replaceAll("");
//        final var rs2 = pattern2.matcher(rs1).replaceAll(" ");
//        final var rs3 = pattern3.matcher(rs2).replaceAll("");
//        return rs3;
//    }
//
//    private void similar(Path resultFile) throws IOException {
//        var mapGlo = new HashMap<String, Long2ObjectMap<Status>>();
//        final var tweets = timeLine.tweets();
//        for (int i = 0; i < tweets.size(); i++) {
//            final var tweet = tweets.get(i);
//            final var text = prepareTxt(tweet.txt);
//            if (text.startsWith("rt")) continue;
//            if (!mapGlo.containsKey(text)) {
//                var mapLcl = new Long2ObjectOpenHashMap<Status>();
//                mapLcl.put(tweet.twtId, tweet);
//                mapGlo.put(text, mapLcl);
//            }
//            else {
//                mapGlo.get(text).put(tweet.twtId, tweet);
//            }
//        }
//        if (!Files.exists(resultFile)) Files.createFile(resultFile);
//        var writer = FileUtils.newBufferedWriterAppend(resultFile);
//        mapGlo.entrySet().stream()
//                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
//                .limit(1000)
//                .forEach(e -> {
//
//                    var json = new JSONObject();
//                    json.put("text", e.getKey());
//                    json.put("number_tweets", e.getValue().size());
//
//                    var _locations = new Object2IntOpenHashMap<String>();
//                    var _userIds = new Long2IntOpenHashMap();
//                    var _twtIds = new Long2IntOpenHashMap();
//
//                    for (var tweet : e.getValue().values()) {
//                        _userIds.addTo(tweet.usrId, 1);
//                        _twtIds.addTo(tweet.twtId, 1);
//                        if (tweet.loc != null) {
//                            tweet.loc.countryCode.ifPresent(c -> _locations.addTo(c, 1));
//                        }
//                    }
//
//                    json.put("number_locations", _locations.size());
//                    var locations = new JSONArray();
//                    _locations.object2IntEntrySet().stream()
//                            .sorted((e1, e2) -> Integer.compare(e2.getIntValue(), e1.getIntValue()))
//                            .limit(10)
//                            .forEach(loc -> {
//                                var locObj = new JSONObject();
//                                locObj.put("CC", loc.getKey());
//                                locObj.put("num", loc.getIntValue());
//                                locations.add(locObj);
//                            });
//
//                    json.put("number_userIds", _userIds.size());
//                    var usrIds = new JSONArray();
//                    _userIds.long2IntEntrySet().stream()
//                            .sorted((e1, e2) -> Integer.compare(e2.getIntValue(), e1.getIntValue()))
//                            .limit(10)
//                            .forEach(loc -> {
//                                var locObj = new JSONObject();
//                                locObj.put("id", loc.getLongKey());
//                                locObj.put("num", loc.getIntValue());
//                                usrIds.add(locObj);
//                            });
//
//                    json.put("number_twtIds", _twtIds.size());
//                    var twtIds = new JSONArray();
//                    _twtIds.long2IntEntrySet().stream()
//                            .limit(10)
//                            .sorted((e1, e2) -> Integer.compare(e2.getIntValue(), e1.getIntValue()))
//                            .forEach(loc -> {
//                                var locObj = new JSONObject();
//                                locObj.put("id", loc.getLongKey());
//                                locObj.put("num", loc.getIntValue());
//                                twtIds.add(locObj);
//                            });
//
//                    json.put("locations", locations);
//                    json.put("userIds", usrIds);
//                    json.put("twtIds", twtIds);
//
//                    try {
//                        writer.write(json.toJSONString());
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                });
//        writer.close();
//    }
//
//    public static void run(Timeline timeline, Path resultFolder) throws IOException {
//        new Tweets(timeline).similar(resultFolder.resolve("similar_tweets.json"));
//    }
//}

package utils;

import com.google.common.collect.Lists;
import datastructures.other.Tuples;
import datastructures.other.Tuples.Tuple2;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.RawBsonDocument;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.VertexScoringAlgorithm;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

import static java.lang.Long.parseLong;
import static java.nio.file.Files.isRegularFile;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.*;
import static system.Contracts.checkArgument;
import static system.Contracts.checkState;
import static utils.BsonUtils.beautiful;
import static model.Model.*;

public enum Utils {
    ;

    public static <V extends Id, E> DefaultUndirectedGraph<Long, DefaultEdge> copyGraph(graph.Graph<V, E> graph) {
        var result = new DefaultUndirectedGraph<Long, DefaultEdge>(DefaultEdge.class);
        for (var vertexId : graph.vertexIds())
            result.addVertex(vertexId);
        for (var edgesIds : graph.edgesIds())
            result.addEdge(edgesIds._1, edgesIds._2, new DefaultEdge());
        return result;
    }

    public static <V extends Id, E> SimpleGraph<Long, DefaultEdge> simpleGraphOf(graph.Graph<V, E> graph) {
        var result = new SimpleGraph<Long, DefaultEdge>(DefaultEdge.class);
        for (var vertexId : graph.vertexIds())
            result.addVertex(vertexId);
        for (var edgesIds : graph.edgesIds())
            if (!edgesIds._1.equals(edgesIds._2))
                result.addEdge(edgesIds._1, edgesIds._2, new DefaultEdge());
        return result;
    }

    public static <V extends graph.Graph<?, ?>> List<V> reverseSortBySize(Collection<V> lst) {
        return Lists.reverse(lst.stream().sorted(comparingInt(col -> col.vertexSet().size()))
                .collect(Collectors.toList()));
    }

    public static class DegreeDistribution<V, E>  implements VertexScoringAlgorithm<V, Integer> {
        private final Map<V, Integer> scores = new Object2IntLinkedOpenHashMap<>();
        private final Graph<V, ?> graph;
        public DegreeDistribution(Graph<V, ?> _graph) { graph = _graph; }
        public Map<V, Integer> getScores() {
            if (!scores.isEmpty())
                return Collections.unmodifiableMap(scores);
            for (V v : graph.vertexSet())
                scores.put(v, graph.degreeOf(v));
            return Collections.unmodifiableMap(scores);
        }
        public Integer getVertexScore(V v) {
            if (scores.isEmpty()) getScores();
            return scores.get(v);
        }
    }

    public static LongList loadIdsFromFile(Path file) {
        checkArgument(isRegularFile(file));
        var ids = new LongArrayList(1_000_000);
        try {
            String line;
            var reader = new BufferedReader(new FileReader(file.toFile()));
            while (nonNull(line = reader.readLine())) ids.add(parseLong(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    static final SimpleDateFormat FORMATTER =
            new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

    public static Date extractCreatedAt(BsonDocument bson) {
        checkState(bson.containsKey("created_at"), "DOES NOT CONTAIN CREATED_AT");
        var str = bson.get("created_at"); checkState(str.isString(), "IS NOT A STRING");
        try { return FORMATTER.parse(str.asString().getValue()); }
        catch (ParseException e) { e.printStackTrace(); }
        throw new IllegalStateException();
    }

    public static LocalDateTime extractCreatedAtLclDateTime(BsonDocument bson) {
        return TimeUtils.dateToLocalDateTime(extractCreatedAt(bson));
    }

    public static int extractIntOrDefault(BsonDocument bson, String field, int _default) {
        if (isNull(bson) || !bson.containsKey(field)) return _default;
        return extractInt(bson, field);
    }

    public static String extractString(BsonDocument bson, String field) {
        checkState(bson.containsKey(field), "DOES NOT CONTAIN " + field);
        if (bson.get(field).isString())
            return bson.getString(field).getValue();
        else throw new IllegalStateException("IS NOT INT64/32");
    }

    public static long extractLong(BsonDocument bson, String field) {
        checkState(bson.containsKey(field), "DOES NOT CONTAIN " + field);
        if (bson.get(field).isInt64())
            return bson.getInt64(field).longValue();
        else if (bson.get(field).isInt32())
            return bson.getInt32(field).longValue();
        else throw new IllegalStateException("IS NOT INT64/32");
    }

    public static int extractInt(BsonDocument bson, String field) {
        checkState(bson.containsKey(field), "DOES NOT CONTAIN " + field);
        if (bson.get(field).isInt64())
            return bson.getInt64(field).intValue();
        else if (bson.get(field).isInt32())
            return bson.getInt32(field).intValue();
        else throw new IllegalStateException("IS NOT INT64/32");
    }

    public static boolean extractBoolean(BsonDocument bson, String field) {
        checkState(bson.containsKey(field), "DOES NOT CONTAIN " + field);
        if (bson.get(field).isBoolean())
            return bson.getBoolean(field).getValue();
        else throw new IllegalStateException("IS NOT BOOLEAN \n" + beautiful(bson));
    }

    public static long extractId(BsonDocument bson) {
        checkState(bson.containsKey("id"), "DOES NOT CONTAIN ID");
        if (bson.get("id").isInt64())
            return bson.getInt64("id").longValue();
        else if (bson.get("id").isInt32())
            return bson.getInt32("id").longValue();
        else throw new IllegalStateException("IS NOT INT64/32");
    }

    public static long extractIdStr(BsonDocument bson) {
        checkState(bson.containsKey("id_str"), "DOES NOT CONTAIN ID");
        if (bson.get("id_str").isString())
            return Long.parseLong(bson.get("id_str").asString().getValue());
        else throw new IllegalStateException("IS NOT S");
    }

    public static long extractUserId(BsonDocument bson) {
        if (bson.containsKey("user")) {
            var user = bson.get("user");
            if (user.isDocument())
                return extractId(extractUser(bson));
            else if (user.isInt64())
                return user.asInt64().longValue();
            else if (user.isInt32())
                return user.asInt32().longValue();
            else throw new IllegalStateException();
        }
        else throw new IllegalStateException();
    }

    private static BsonDocument extractUser(BsonDocument bson) {
        checkState(bson.containsKey("user"), "DOES NOT CONTAIN USER");
        var userBson = bson.get("user"); checkState(userBson.isDocument(), "IS NOT A DOCUMENT");
        return userBson.asDocument();
    }

    // stores
    public static void persistBsons(Path dir, String name, Collection<RawBsonDocument> bsons) {
        Path file = null;
        try { file = Files.createFile(dir.resolve(name)); }
        catch (IOException e) { e.printStackTrace(); }
        try {
            var outs = new FileOutputStream(requireNonNull(file).toFile());
            try (var gzip = new GZIPOutputStream(outs)) {
                for (var rBsn : bsons) {
                    var buff = rBsn.getByteBuffer();
                    var array = buff.array();
                    gzip.write(array, 0, buff.limit());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            outs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // stores
    static void persistStati(Path dir, String name, Collection<? extends Status<?>> stati) {
        persistBsons(dir, name,
                stati.stream()
                        .filter(status -> !(status.isDummy()))
                        .map(status -> status.oldBson().toJson())
                        .map(RawBsonDocument::parse)
                        .collect(Collectors.toList()));
    }

    // simple helper function which checks whether
    // two maps contain the exact same long keys
    static boolean checkEqualMaps(Map<Long, ?> m1, Map<Long, ?> m2) {
        return m1.keySet().stream().allMatch(m2::containsKey)
                && m2.keySet().stream().allMatch(m1::containsKey);
    }

    // simple helper that determines the
    // number of elements that are in
    // one map and not in the other
    // and vice versa. Please notice
    // that this doesnt work properly
    // it is intended for debug output.
    static Tuple2<Integer, Integer> difference(Map<Long, ?> newMap, Map<Long, ?> oldMap) {
        int resNew = newMap.size();
        for (long uid : newMap.keySet())
            if (oldMap.containsKey(uid))
                --resNew; else ++resNew;
        int resOld = oldMap.size();
        for (long uid : oldMap.keySet())
            if (newMap.containsKey(uid))
                --resOld; else ++resOld;
        return Tuples.of(resNew, resOld);
    }

    static List<BsonDocument> extractUsers(BsonDocument _bson) {
        var json = Document.parse(_bson.toJson());
        var stati = new LinkedList<Document>();
        var users = new LinkedList<BsonDocument>();
        if (json != null) flatternStatus(json, stati);
        for (var status : stati) {
            String date = null;
            if (status.containsKey("created_at"))
                date = status.getString("created_at");
            else throw new IllegalArgumentException("NO DATE IN STATUS");
            if (status.containsKey("user")
                    && status.get("user") instanceof Document) {
                var userDoc = json.get("user", Document.class);
                var id = parseLong(userDoc.getString("id_str"));
                userDoc.replace("id", new BsonInt64(id));
                userDoc.put("scraped_at", date);
                users.add(RawBsonDocument.parse(userDoc.toJson()));
            }
        }
        return users;
    }

    private static void flatternStatusForUser(Document json, List<Document> stati) {
        processNestedElement(json, "current_user_retweet", stati);
        processNestedElement(json, "retweeted_status", stati);
        processNestedElement(json, "quoted_status", stati);
        stati.add(json);
    }



    static List<BsonDocument> transformStatus(BsonDocument _bson) {
        var json = Document.parse(_bson.toJson());
        var stati = new LinkedList<Document>();
        if (json != null) flatternStatus(json, stati);
        for (var doc : stati) {
            var id = parseLong(doc.getString("id_str"));
            doc.replace("id", new BsonInt64(id));
            if (doc.containsKey("quoted_status_id") && nonNull(doc.get("quoted_status_id"))) {
                var qid = parseLong(doc.getString("quoted_status_id_str"));
                doc.replace("quoted_status_id", new BsonInt64(qid));
            }
            if (doc.containsKey("in_reply_to_status_id") && nonNull(doc.get("in_reply_to_status_id"))) {
                var rid = parseLong(doc.getString("in_reply_to_status_id_str"));
                doc.replace("in_reply_to_status_id", new BsonInt64(rid));
                if (doc.containsKey("in_reply_to_user_id") && nonNull(doc.get("in_reply_to_user_id"))) {
                    var uid = parseLong(doc.getString("in_reply_to_user_id_str"));
                    doc.replace("in_reply_to_user_id", new BsonInt64(uid));
                }
            }
        }
        return stati.stream().map(Document::toJson)
                .map(RawBsonDocument::parse)
                .collect(Collectors.toList());
    }

    // helper for transform status. Since documents retuned by
    // the twitter API can be nested this will unnest them.
    private static void flatternStatus(Document json, List<Document> stati) {
        // We have to replace the user id with the
        // id_str field since it can be corrupted
        // due to java-script wrong long parsing
        if (json.containsKey("user")
                && json.get("user") instanceof Document) {
            var userDoc = json.get("user", Document.class);
            var userId  = parseLong(userDoc.getString("id_str"));
            userDoc.replace("id", new BsonInt64(userId));
            json.replace("user", userDoc);
        }
        processNestedElement(json, "current_user_retweet", stati);
        processNestedElement(json, "retweeted_status", stati);
        processNestedElement(json, "quoted_status", stati);
        stati.add(json);
    }

    // helper for flattern status
    private static void processNestedElement(Document json, String key, List<Document> stati) {
        if (json.containsKey(key) &&  json.get(key) instanceof Document) { // info("CONTAINS " + key);
            var nested = json.get(key, Document.class);
            var idStr  = nested.getString("id_str");
            var idVal  = new BsonInt64(parseLong(idStr));
            flatternStatus(nested, stati);
            json.replace(key, idVal);
        }
    }


    // This function checks whether a Bson-Document
    // is a Tweet or a Retweet. A Tweet is a text
    // Message that has an author as well is not
    // a Retweet nor a Quote
    static boolean isTweet(BsonDocument bson) {
        return !bson.containsKey("retweeted_status")
                && (!bson.containsKey("quoted_status_id") || bson.get("quoted_status_id").isNull())
                && (!bson.containsKey("in_reply_to_status_id") || bson.get("in_reply_to_status_id").isNull());
    }

    // This function checks whether a Bson-Document
    // is a Retweet. A Retweet is more or less the
    // share of a Tweet with the restriction that
    // every respond to a Retweet is a respond to
    // the original Tweet itself.
    static boolean isRetweet(BsonDocument bson) {
        return (bson.containsKey("retweeted_status")
                && (!bson.containsKey("quoted_status_id") || bson.get("quoted_status_id").isNull())
                && (!bson.containsKey("in_reply_to_status_id") || bson.get("in_reply_to_status_id").isNull()));
    }

    // This function checks whether a Bson-Document
    // is a Quoted-Tweet. A Quoted Tweet or Quote
    // is a commented Retweet. In contrast to a Retweet
    // a Quote is a new Tweet object meaning the it is
    // possible to comment a Quote.
    static boolean isQuote(BsonDocument bson) {
        return !bson.containsKey("retweeted_status")
                && (bson.containsKey("quoted_status_id") && !bson.get("quoted_status_id").isNull())
                && (!bson.containsKey("in_reply_to_status_id") || bson.get("in_reply_to_status_id").isNull());
    }

    // This function checks whether a Bson-Document
    // is a Reply-Tweet. Replies are basically comments
    // on ether Tweets, Quotes or other Replies.
    static boolean isReply(BsonDocument bson) {
        return !bson.containsKey("retweeted_status")
                && (!bson.containsKey("quoted_status_id") || bson.get("quoted_status_id").isNull())
                && (bson.containsKey("in_reply_to_status_id") && !bson.get("in_reply_to_status_id").isNull());
    }

    // This function checks whether a Bson-Document
    // is a Quoted-Reply-Tweet. A quoted reply tweet
    // is a Reply that includes a Quote. A Quoted-Reply
    // can be performed by copying the link of a Tweet
    // in the comment-filed of another Tweet.
    static boolean isQuotedReply(BsonDocument bson) {
        return !bson.containsKey("retweeted_status")
                && (bson.containsKey("quoted_status_id") && !bson.get("quoted_status_id").isNull())
                && (bson.containsKey("in_reply_to_status_id") && !bson.get("in_reply_to_status_id").isNull());
    }

    // This function checks whether a Bson-Document
    // is a Quoted-Retweet. A Quoted-Retweet is a
    // Retweet that retweeted a Quote instead of a
    // regular Tweet. Since this is a Retweed by
    // itself, every Reply to this Quoted-Retweet
    // is a Reply to the original Tweet.
    static boolean isQuotedRetweet(BsonDocument bson) {
        return bson.containsKey("retweeted_status")
                && (bson.containsKey("quoted_status_id") && !bson.get("quoted_status_id").isNull())
                && (!bson.containsKey("in_reply_to_status_id") || bson.get("in_reply_to_status_id").isNull());
    }
}

package magma.utils;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bson.BsonDocument;
import org.bson.BsonInt64;

public enum  BsonUtils {
    ;

    public static long obj2Long(Object obj) {
        // Really!?!?... fuck this
        if (obj == null) {
            return -1;
        }
        if (obj instanceof BsonInt64) {
            return ((BsonInt64) obj).getValue();
        }
        if (!(obj instanceof Number)) {
            return -1;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Integer) {
            return Long.valueOf((Integer) obj);
        }
        return -1;
    }

    public static String beautiful(BsonDocument doc) {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var jparser = new JsonParser();
        JsonElement je = jparser.parse(doc.toJson());
        return gson.toJson(je);
    }

    public static String beautiful(String doc) {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var jparser = new JsonParser();
        JsonElement je = jparser.parse(doc);
        return gson.toJson(je);
    }

    public static long int64(BsonDocument bson, String fieldName) {
        switch (bson.get(fieldName).getBsonType()) {
            case INT64:  return bson.getInt64(fieldName).longValue();
            case INT32:  return bson.getInt32(fieldName).longValue();
            case STRING: return Long.parseLong(bson.getString(fieldName).getValue());
            default: throw new IllegalStateException(bson.getInt64(fieldName).longValue() + "");
        }
    }
}

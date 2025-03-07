package com.ace.utilities;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GsonUtil implements JsonSerializer<LocalDateTime> {
    private static final Logger log = LogManager.getLogger(GsonUtil.class);

    // private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonUtil());
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static <T> String toFormattedJson(List<T> objects) {
        return toJson(objects);
    }


    public static <T> List<T> toList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }


    public static <T> T toObject(String json, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static Object toObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Object.class);
    }


    /**
     * json轉成map, 需要傳入key.class 和 value.class
     *
     * @param json
     * @param keyClazz
     * @param valClazz
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClazz, Class<V> valClazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, TypeToken.getParameterized(Map.class, keyClazz, valClazz).getType());
    }

    public static String formatJson(String json) {
        //在序列化的时候不忽略null值
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return gson.toJson(JsonParser.parseString(json));
    }


    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return new JsonPrimitive(date.getTime());
    }
}

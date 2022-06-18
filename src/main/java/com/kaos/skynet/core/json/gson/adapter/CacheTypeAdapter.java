package com.kaos.skynet.core.json.gson.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.Cache;

public class CacheTypeAdapter<K, V> extends TypeAdapter<Cache<K, V>> {
    @Override
    public Cache<K, V> read(JsonReader in) throws IOException {
        return null;
    }

    @Override
    public void write(JsonWriter out, Cache<K, V> value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            // 获取统计值
            var stats = value.getStats();
            // 序列化
            out.beginObject();
            out.name("hitCount");
            out.value(stats.hitCount());
            out.name("missCount");
            out.value(stats.missCount());
            out.name("loadSuccessCount");
            out.value(stats.loadSuccessCount());
            out.name("loadExceptionCount");
            out.value(stats.loadExceptionCount());
            out.name("evictionCount");
            out.value(stats.evictionCount());
            out.endObject();
        }
    }
}

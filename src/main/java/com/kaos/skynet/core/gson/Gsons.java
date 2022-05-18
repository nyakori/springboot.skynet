package com.kaos.skynet.core.gson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.skynet.core.gson.adapter.date.StandardLocalDateTypeAdapter;
import com.kaos.skynet.core.gson.adapter.datime.StandardLocalDateTimeTypeAdapter;
import com.kaos.skynet.core.gson.adapter.enums.DescriptionEnumTypeAdapter;
import com.kaos.skynet.core.gson.adapter.time.StandardLocalTimeTypeAdapter;

public class Gsons {
    /**
     * 创建通用格式的gson对象
     */
    public static Gson newGson() {
        // 创建构造器
        var builder = new GsonBuilder();

        // 注册时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new DescriptionEnumTypeAdapter<>());

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, new StandardLocalDateTypeAdapter());

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, new StandardLocalTimeTypeAdapter());

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, new StandardLocalDateTimeTypeAdapter());

        // 创建gson对象
        return builder.create();
    }
}

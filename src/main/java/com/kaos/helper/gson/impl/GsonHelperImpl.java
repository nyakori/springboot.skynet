package com.kaos.helper.gson.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.helper.gson.GsonHelper;
import com.kaos.helper.gson.adapter.EnumTypeAdapter;
import com.kaos.his.enums.IEnum;

public class GsonHelperImpl implements GsonHelper {
    /**
     * Gson实体
     */
    Gson gson = null;

    /**
     * 默认构造函数
     */
    public GsonHelperImpl() {
        this.gson = new Gson();
    }

    /**
     * 带参构造
     */
    public GsonHelperImpl(String dateFormat) {
        GsonBuilder builder = new GsonBuilder();

        // 设置时间转换格式
        if (dateFormat != null) {
            builder.setDateFormat(dateFormat);
        }

        // 注册转换器
        builder.registerTypeHierarchyAdapter(IEnum.class, new EnumTypeAdapter<>());

        this.gson = builder.create();
    }

    @Override
    public Gson getGson() {
        return this.gson;
    }

    @Override
    public String toJson(Object src) {
        return this.gson.toJson(src);
    }

    @Override
    public <T> T FromJson(String json, Class<T> typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }
}

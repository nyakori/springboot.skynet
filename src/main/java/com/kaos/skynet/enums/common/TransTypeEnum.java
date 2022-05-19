package com.kaos.skynet.enums.common;

import com.kaos.skynet.core.type.Enum;

public enum TransTypeEnum implements Enum {
    Positive("1", "正交易"), Negative("2", "负交易");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;

    /**
     * 构造
     * 
     * @param index
     * @param description
     */
    TransTypeEnum(String index, String description) {
        this.value = index;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

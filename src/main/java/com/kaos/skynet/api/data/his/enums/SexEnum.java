package com.kaos.skynet.api.data.his.enums;

import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum implements Enum {
    Male("M", "男"),
    Female("F", "女"),
    Unknown("U", "未知"),
    Auto("A", "自动");

    /**
     * 数据库存值
     */
    private String value;

    /**
     * 描述存值
     */
    private String description;
}

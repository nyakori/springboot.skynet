package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

public enum DeptOwnEnum implements IEnum<DeptOwnEnum> {
    All("0", "全院区"), Sourth("1", "南院区"), North("2", "北院区"), East("3", "东津院区");

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
    DeptOwnEnum(String index, String description) {
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

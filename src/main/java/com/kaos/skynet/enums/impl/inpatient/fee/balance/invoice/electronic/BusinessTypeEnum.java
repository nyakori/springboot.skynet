package com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.enums.Enum;

public enum BusinessTypeEnum implements Enum {
    住院("01", "住院"),
    门诊("02", "门诊"),
    急诊("03", "急诊"),
    门特("04", "门特"),
    体检("05", "体检"),
    挂号("06", "挂号"),
    住院预交金("07", "住院预交金"),
    体检预交金("08", "体检预交金");

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
    BusinessTypeEnum(String index, String description) {
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

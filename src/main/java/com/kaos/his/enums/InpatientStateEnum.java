package com.kaos.his.enums;

import com.kaos.his.enums.util.IEnum;

public enum InpatientStateEnum implements IEnum<InpatientStateEnum> {
    住院登记("R", "住院登记"), 病房接诊("I", "病房接诊"), 出院登记("B", "出院登记"), 出院结算("O", "出院结算"), 预约出院("P", "预约出院"), 无费退院("N", "无费退院");

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
    InpatientStateEnum(String value, String description) {
        this.value = value;
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
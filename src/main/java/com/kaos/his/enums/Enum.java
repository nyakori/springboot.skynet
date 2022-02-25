package com.kaos.his.enums;

/**
 * 定制枚举接口
 */
public interface Enum {
    /**
     * 写入数据库的值
     * 
     * @return
     */
    String getValue();

    /**
     * 序列化入json的值
     * 
     * @return
     */
    String getDescription();
}

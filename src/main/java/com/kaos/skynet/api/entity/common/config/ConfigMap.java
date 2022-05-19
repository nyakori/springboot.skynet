package com.kaos.skynet.api.entity.common.config;

import com.kaos.skynet.enums.common.ValidStateEnum;

/**
 * 控制变量（KAOS.CONFIG_VARIABLE || KAOS.CONFIG_LIST）
 */
public class ConfigMap {
    /**
     * 开关名
     */
    public String name = null;

    /**
     * 开关值
     */
    public String value = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 备注
     */
    public String remark = null;
}

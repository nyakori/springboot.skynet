package com.kaos.his.mapper.common.config;

import com.kaos.his.entity.common.config.DawnCodeType;

public interface DawnCodeTypeMapper {
    /**
     * 主键查询
     * 
     * @param constTypeId 常量名；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnCodeType queryDawnCodeType(String constTypeId);
}

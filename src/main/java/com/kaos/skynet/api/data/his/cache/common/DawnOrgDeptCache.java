package com.kaos.skynet.api.data.his.cache.common;

import com.kaos.skynet.api.data.his.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.his.mapper.common.DawnOrgDeptMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 科室编码 -> 科室信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class DawnOrgDeptCache extends Cache<String, DawnOrgDept> {
    /**
     * 数据库接口
     */
    DawnOrgDeptCache(DawnOrgDeptMapper deptMapper) {
        super(500, new Converter<String, DawnOrgDept>() {
            @Override
            public DawnOrgDept convert(String source) {
                return deptMapper.queryDept(source);
            }
        });
    }
}

package com.kaos.skynet.api.data.cache.inpatient;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 住院流水号 -> 住院信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class FinIprInMainInfoCache extends Cache<String, FinIprInMainInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(String.class, 500, new Converter<String,FinIprInMainInfo>() {
            @Override
            public FinIprInMainInfo convert(String source) {
                return inMainInfoMapper.queryInMainInfo(source);
            }
        });
    }
}

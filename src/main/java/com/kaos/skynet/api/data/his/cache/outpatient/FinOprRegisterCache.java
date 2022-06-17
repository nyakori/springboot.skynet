package com.kaos.skynet.api.data.his.cache.outpatient;

import com.kaos.skynet.api.data.his.entity.outpatient.FinOprRegister;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.skynet.core.type.Cache;

import com.kaos.skynet.core.type.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Component
public class FinOprRegisterCache extends Cache<FinOprRegisterCache.Key, FinOprRegister> {
    FinOprRegisterCache(FinOprRegisterMapper registerMapper) {
        super(100, new Converter<Key, FinOprRegister>() {
            @Override
            public FinOprRegister convert(Key source) {
                return registerMapper.queryRegister(source.clinicCode, source.transType);
            }
        });
    }

    @Builder
    @EqualsAndHashCode
    public static class Key {
        /**
         * 门诊号
         */
        private String clinicCode;

        /**
         * 交易类型
         */
        private TransTypeEnum transType;
    }
}

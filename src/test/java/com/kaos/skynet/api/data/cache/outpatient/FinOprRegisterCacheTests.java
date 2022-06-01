package com.kaos.skynet.api.data.cache.outpatient;

import com.kaos.skynet.api.data.enums.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOprRegisterCacheTests {
    @Autowired
    FinOprRegisterCache registerCache;

    @Test
    void get() {
        registerCache.get(FinOprRegisterCache.Key.builder()
                .clinicCode("clinicCode")
                .transType(TransTypeEnum.Positive)
                .build());
    }
}

package com.kaos.his.cache.common;

import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.FeeStatTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.inf.ICache;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComFeeCodeStatCacheTests {
    @Autowired
    ICache<Pair<FeeStatTypeEnum, MinFeeEnum>, FinComFeeCodeStat> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue(new Pair<FeeStatTypeEnum, MinFeeEnum>(FeeStatTypeEnum.门诊发票, MinFeeEnum.B超));
    }
}

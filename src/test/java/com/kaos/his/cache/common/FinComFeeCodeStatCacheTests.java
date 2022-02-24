package com.kaos.his.cache.common;

import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComFeeCodeStatCacheTests {
    @Autowired
    ICache<ReportTypeEnum, ICache<MinFeeEnum, FinComFeeCodeStat>> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue(ReportTypeEnum.门诊发票).getValue(MinFeeEnum.B超);
    }
}

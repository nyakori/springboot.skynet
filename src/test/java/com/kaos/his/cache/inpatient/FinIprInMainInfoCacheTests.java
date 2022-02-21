package com.kaos.his.cache.inpatient;

import com.kaos.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoCacheTests {
    @Autowired
    ICache<String, FinIprInMainInfo> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("ZY010000705856");
    }    
}

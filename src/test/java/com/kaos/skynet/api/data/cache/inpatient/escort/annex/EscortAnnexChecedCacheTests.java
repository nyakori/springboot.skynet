package com.kaos.skynet.api.data.cache.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexChecedCacheTests {
    @Autowired
    EscortAnnexCheckCache.MasterCache annexCheckedMasterCache;

    @Autowired
    EscortAnnexCheckCache.SlaveCache annexCheckedSlaveCache;

    @Test
    public void getCacheValue() {
        this.annexCheckedMasterCache.get("0000000565");
        this.annexCheckedSlaveCache.get(new EscortAnnexCheckCache.Key() {
            {
                setCardNo("0123456789");
                setOffset(14);
            }
        });
    }
}

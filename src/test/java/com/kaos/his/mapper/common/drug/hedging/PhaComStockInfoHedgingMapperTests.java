package com.kaos.his.mapper.common.drug.hedging;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PhaComStockInfoHedgingMapperTests {
    @Autowired
    PhaComStockInfoHedgingMapper phaComStockInfoHedgingMapper;

    @Test
    public void queryStockInfo() {
        this.phaComStockInfoHedgingMapper.queryStockInfo("1240", null);
        this.phaComStockInfoHedgingMapper.queryStockInfo("1240", "Y00000016786");
    }
}

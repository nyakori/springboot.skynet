package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsArrangeMapperTests {
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    @Test
    void queryMetOpsArranges() {
        metOpsArrangeMapper.queryMetOpsArranges(MetOpsArrangeMapper.Key.builder()
                .operationNo("operationNo")
                .build());
    }
}

package com.kaos.his.outpatient;

import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.mapper.outpatient.OutpatientMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OutpatientMapperTests {
    @Autowired
    OutpatientMapper outpatientMapper;

    @Test
    public void queryOutpatient() {
        this.outpatientMapper.queryOutpatient("159001", TransTypeEnum.Positive);
    }
}

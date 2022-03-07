package com.kaos.skynet.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoMapperTests {
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    @Test
    public void queryInMainInfo() {
        this.inMainInfoMapper.queryInMainInfo("ZY010000705856");
    }

    @Test
    public void queryInMainInfos() {
        this.inMainInfoMapper.queryInpatients("2000003605", null, null, null);
        this.inMainInfoMapper.queryInpatients("2000003605", 10, null, null);
    }
}
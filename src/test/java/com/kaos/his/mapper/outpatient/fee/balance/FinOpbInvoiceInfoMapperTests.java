package com.kaos.his.mapper.outpatient.fee.balance;

import com.kaos.his.enums.impl.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOpbInvoiceInfoMapperTests {
    @Autowired
    FinOpbInvoiceInfoMapper invoiceInfoMapper;

    @Test
    public void queryInvoiceInfo() {
        this.invoiceInfoMapper.queryInvoiceInfo("000000009433", TransTypeEnum.Positive, "155786192");
    }
}

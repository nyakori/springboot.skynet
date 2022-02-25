package com.kaos.his.mapper.inpatient.surgery;

import java.util.ArrayList;

import com.kaos.his.enums.impl.inpatient.surgery.SurgeryArrangeRoleEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsArrangeTests {
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    @Test
    public void queryMetOpsArranges() {
        this.metOpsArrangeMapper.queryMetOpsArranges("742", new ArrayList<SurgeryArrangeRoleEnum>() {
            {
                add(SurgeryArrangeRoleEnum.Operator);
                add(SurgeryArrangeRoleEnum.Helper1);
            }
        });
    }
}

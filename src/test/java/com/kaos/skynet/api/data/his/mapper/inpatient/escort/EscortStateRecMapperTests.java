package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortStateRecMapperTests {
    @Autowired
    EscortStateRecMapper stateRecMapper;

    @Test
    void queryEscortStateRecs() {
        stateRecMapper.queryEscortStateRecs("0000001170");
    }

    @Test
    void queryFirstEscortStateRec() {
        stateRecMapper.queryFirstEscortStateRec("0000001170");
    }

    @Test
    void queryLastEscortStateRec() {
        stateRecMapper.queryLastEscortStateRec("0000001170");
    }
}

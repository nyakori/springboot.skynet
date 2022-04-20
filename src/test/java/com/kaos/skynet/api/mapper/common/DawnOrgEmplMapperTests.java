package com.kaos.skynet.api.mapper.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgEmplMapperTests {
    @Autowired
    DawnOrgEmplMapper employeeMapper;

    @Test
    public void queryEmployee() throws Exception {
        this.employeeMapper.queryEmployee(null);
        this.employeeMapper.queryEmployee("000306");
    }

    @Test
    public void queryInformalEmployee() throws Exception {
        this.employeeMapper.queryOuterEmployee(null);
        this.employeeMapper.queryOuterEmployee("W00801");
    }

    @Test
    public void queryValidEmployees() throws Exception {
        this.employeeMapper.queryValidEmployees();
    }
}

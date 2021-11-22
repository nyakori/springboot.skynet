package com.kaos.his.service;

import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.mapper.personnel.InpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    InpatientMapper departmentMapper;

    public Patient Run(String param) {
        return this.departmentMapper.QueryInpatient(param);
    }
}

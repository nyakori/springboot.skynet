package com.kaos.his.service.outpatient.impl;

import java.util.List;

import com.kaos.his.cache.common.DawnOrgEmplCache;
import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.service.outpatient.FeeService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeServiceImpl implements FeeService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpServiceImpl.class.getName());

    /**
     * 挂号接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 门诊费用接口
     */
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    /**
     * 职工cache
     */
    DawnOrgEmplCache dawnOrgEmplCache = DawnOrgEmplCache.getInstance();

    @Override
    public void undrugPriced(String clinicCode, String operCode, List<String> itemCodes, String termNo) {
        // 查询挂号记录
        var register = this.registerMapper.queryRegisterRec(clinicCode, TransTypeEnum.Positive);
        if (register == null) {
            throw new RuntimeException("未查询到挂号记录, 无法划价");
        }
    }
}

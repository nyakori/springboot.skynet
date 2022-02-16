package com.kaos.his.service.outpatient;

import java.util.Date;

import com.kaos.his.enums.common.NoonEnum;
import com.kaos.his.enums.outpatient.RegisterPayModeEnum;

public interface RegService {
    /**
     * 挂免费号
     * 
     * @param cardNo   就诊卡号
     * @param idenNo   身份证号
     * @param deptCode 看诊科室
     * @param docCode  看诊医生
     * @param seeDate  看诊时间
     * @param noon     午别
     * @param operCode 挂号操作员
     * @param payMode  支付方式
     */
    void freeRegister(String cardNo, String deptCode, String doctCode, Date seeDate, NoonEnum noon, String operCode,
            RegisterPayModeEnum payMode);
}

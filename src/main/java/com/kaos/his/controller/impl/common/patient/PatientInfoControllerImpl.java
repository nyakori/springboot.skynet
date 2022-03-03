package com.kaos.his.controller.impl.common.patient;

import javax.validation.constraints.NotBlank;

import com.kaos.his.cache.Cache;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.common.patient.PatientInfoController;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.util.DateHelpers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/common/patient/info", "/ms/common/entityinfo" })
public class PatientInfoControllerImpl implements PatientInfoController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(PatientInfoControllerImpl.class);

    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, ComPatientInfo> patientInfoCache;

    @Override
    @RequestMapping(value = { "query", "queryPatientInfo" }, method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryPatientInfoRsp queryPatientInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo) {
        // 记录日志
        this.logger.info(String.format("查询患者信息(cardNo = %s)", cardNo));

        // 调用服务
        var patient = this.patientInfoCache.getValue(cardNo);
        if (patient == null) {
            throw new RuntimeException("就诊卡不存在!");
        }

        // 构造响应体
        var rspBody = new QueryPatientInfoRsp();
        rspBody.cardNo = patient.cardNo;
        rspBody.name = patient.name;
        rspBody.sex = patient.sex;
        rspBody.age = DateHelpers.getAge(patient.birthday).toString();
        rspBody.idenNo = patient.identityCardNo;
        rspBody.tel = patient.homeTel;

        return rspBody;
    }
}

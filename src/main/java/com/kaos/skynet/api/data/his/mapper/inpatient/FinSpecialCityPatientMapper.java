package com.kaos.skynet.api.data.his.mapper.inpatient;

import com.kaos.skynet.api.data.his.entity.inpatient.FinSpecialCityPatient;

public interface FinSpecialCityPatientMapper {
    /**
     * 查询患者特殊标识
     * 
     * @param inpatientNo 住院流水号
     * @return
     */
    FinSpecialCityPatient querySpecialCityPatient(String inpatientNo);
}

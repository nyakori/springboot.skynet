package com.kaos.his.service.inpatient;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.his.enums.EscortStateEnum;

public interface EscortService {
    /**
     * 注册陪护人
     * 
     * @param patientCardNo
     * @param helperCardNo
     * @param emplCode
     * @param remark
     * @return
     */
    EscortMainInfo registerEscort(String patientCardNo, String helperCardNo, String emplCode, String remark);

    /**
     * 修改陪护证状态
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     * @param remark
     * @return
     */
    List<EscortStateRec> updateEscortState(String escortNo, EscortStateEnum state, String emplCode, String remark);

    /**
     * 查询陪护证，主键查询
     * 
     * @param escortNo
     * @return
     */
    EscortMainInfo queryEscortStateInfo(String escortNo);

    /**
     * 查询被陪护的患者
     * 
     * @param patientCardNo
     * @return
     */
    List<EscortMainInfo> queryPatientInfos(String helperCardNo);

    /**
     * 查询陪护人信息
     * 
     * @param patientCardNo
     * @return
     */
    List<EscortMainInfo> queryHelperInfos(String patientCardNo);
}

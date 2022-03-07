package com.kaos.skynet.mapper.inpatient;

import java.util.List;

import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.enums.impl.inpatient.InStateEnum;

public interface FinIprInMainInfoMapper {
    /**
     * 查询住院主表记录
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIprInMainInfo queryInMainInfo(String inpatientNo);

    /**
     * 查询最后一个目标状态的住院实体，排序依据为住院证编号
     * 
     * @param cardNo   患者卡号; 等于 {@code null} 时，不作为判断条件
     * @param happenNo 住院证号; 等于 {@code null} 时，不作为判断条件
     * @param deptCode 科室编码; 等于 {@code null} 时，不作为判断条件
     * @param states   在院状态; 等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIprInMainInfo> queryInpatients(String cardNo, Integer happenNo, String deptCode, List<InStateEnum> states);
}

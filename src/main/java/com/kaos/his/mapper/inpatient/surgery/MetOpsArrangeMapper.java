package com.kaos.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.his.enums.inpatient.surgery.SurgeryArrangeRoleEnum;

public interface MetOpsArrangeMapper {
    /**
     * 查询列表
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，不作为判断条件
     * @param roles       安排内容；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsArrange> queryMetOpsArranges(String operationNo, List<SurgeryArrangeRoleEnum> roles);
}

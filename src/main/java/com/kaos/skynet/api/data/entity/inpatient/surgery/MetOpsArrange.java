package com.kaos.skynet.api.data.entity.inpatient.surgery;

import com.google.common.base.Objects;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术人员安排（XYHIS.MET_OPS_ARRANGE）
 */
@Getter
@Setter
@Builder
public class MetOpsArrange {
    /**
     * 手术编号
     */
    private String operationNo;

    /**
     * 角色
     */
    private SurgeryArrangeRoleEnum role;

    /**
     * 职工编码
     */
    private String emplCode;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsArrange) {
            var that = (MetOpsArrange) arg0;
            return StringUtils.equals(this.operationNo, that.operationNo)
                    && this.role == that.role
                    && StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.operationNo, this.role, this.emplCode);
    }
}

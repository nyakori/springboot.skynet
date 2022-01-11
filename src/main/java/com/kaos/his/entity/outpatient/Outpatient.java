package com.kaos.his.entity.outpatient;

import java.util.Date;

import com.kaos.his.entity.common.Department;
import com.kaos.his.entity.common.Patient;
import com.kaos.his.enums.TransTypeEnum;

/**
 * 门诊患者
 */
public class Outpatient extends Patient {
    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 挂号科室编码
     */
    public String regDeptCode = null;

    /**
     * 关联实体
     */
    public class AssociateEntity extends Patient.AssociateEntity {
        public Department regDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}

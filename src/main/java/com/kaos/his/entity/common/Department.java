package com.kaos.his.entity.common;

import com.kaos.his.enums.DeptOwnEnum;

/**
 * 实体：科室信息（XYHIS.DAWN_ORG_DEPT）
 */
public class Department {
    /**
     * 科室编码
     */
    public String code;

    /**
     * 科室名称
     */
    public String name;

    /**
     * 院区
     */
    public DeptOwnEnum deptOwn;
}

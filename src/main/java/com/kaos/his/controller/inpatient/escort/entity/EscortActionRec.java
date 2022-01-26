package com.kaos.his.controller.inpatient.escort.entity;

import java.util.Date;

import com.kaos.his.enums.inpatient.escort.EscortActionEnum;

public class EscortActionRec {
    /**
     * 状态序号
     */
    public Integer recNo = null;

    /**
     * 状态
     */
    public EscortActionEnum action = null;

    /**
     * 记录时间
     */
    public Date recDate = null;
}

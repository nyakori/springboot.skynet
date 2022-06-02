package com.kaos.skynet.api.data.entity.inpatient.surgery;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.MetOpsInciTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryInspectResultEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryKindEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 手术申请（XYHIS.MET_OPS_APPLY）
 */
@Getter
@Setter
@Builder
public class MetOpsApply {
    /**
     * 手术编码
     */
    private String operationNo;

    /**
     * 门诊：门诊号；
     * 住院：住院流水号；
     */
    private String clinicCode;

    /**
     * 住院号 {@link MetOpsApply.AssociateEntity#inMainInfo}
     */
    private String patientNo;

    /**
     * 住院科室, 由于患者可能存在转科, 由该字段记录申请手术的时候所在的科室
     */
    @Getter
    private String inDeptCode;

    /**
     * 手术诊断
     */
    private String diagnosis;

    /**
     * 手术类型
     */
    private SurgeryKindEnum surgeryKind;

    /**
     * 手术医生编码 {@link MetOpsApply.AssociateEntity#opsDoc}
     */
    private String opsDocCode;

    /**
     * 指导医生编码 {@link MetOpsApply.AssociateEntity#guiDoc}
     */
    private String guiDocCode;

    /**
     * 预约时间（计划手术时间）
     */
    private LocalDateTime preDate;

    /**
     * 麻醉类型
     */
    private AnesTypeEnum anesType;

    /**
     * 手术科室（三个院区各自的手术室） {@link MetOpsApply.AssociateEntity#surgeryDept}
     */
    private String surgeryDeptCode;

    /**
     * 申请医生编码 {@link MetOpsApply.AssociateEntity#applyDoc}
     */
    private String applyDocCode;

    /**
     * 申请时间
     */
    private LocalDateTime applyDate;

    /**
     * 申请备注
     */
    private String applyNote;

    /**
     * 审批医生编码 {@link MetOpsApply.AssociateEntity#apprDoc}
     */
    private String apprDocCode;

    /**
     * 审批时间
     */
    private LocalDateTime apprDate;

    /**
     * 审批备注
     */
    private String apprNote;

    /**
     * 麻醉医生编码 {@link MetOpsApply.AssociateEntity#anesDoc}
     */
    private String anesDocCode;

    /**
     * 手术等级
     */
    private SurgeryDegreeEnum degree;

    /**
     * 切口类型
     */
    private MetOpsInciTypeEnum inciType;

    /**
     * 是否首台
     */
    @Getter
    private Boolean firstFlag;

    /**
     * 检验结果 [BLOOD_NUM]
     */
    private SurgeryInspectResultEnum inspectResult;

    /**
     * 台次
     */
    @Getter
    private String order;

    /**
     * 手术状态
     */
    private SurgeryStatusEnum surgeryStatus;

    /**
     * 结束标识
     */
    private Boolean finishFlag;

    /**
     * 麻醉标识
     */
    private Boolean anesFlag;

    /**
     * 非计划标识
     */
    private Boolean unplannedFlag;

    /**
     * 非计划手术家属签字
     */
    private Boolean signedFlag;

    /**
     * 是否已计费
     */
    private Boolean chargeFlag;

    /**
     * 是否重症
     */
    private Boolean heavyFlag;

    /**
     * 是否特殊手术
     */
    private Boolean specialFlag;

    /**
     * 申请人编码 {@link MetOpsApply.AssociateEntity#operEmpl}
     */
    private String operCode;

    /**
     * 申请时间
     */
    private LocalDateTime operDate;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    /**
     * 手术间编码 {@link MetOpsApply.AssociateEntity#room}
     */
    private String roomId;

    /**
     * 手术部位|手术体位
     */
    private String position;

    /**
     * 作废标识
     */
    private Boolean cancelFlag;

    /**
     * 发布标识，发布后医生可见
     */
    private Boolean publishFlag;

    /**
     * 手术名称备注
     */
    private String surgeryNameNote;

    /**
     * 手术标识
     */
    private String operRemark;

    /**
     * 门诊流水号（日间手术专用）
     */
    private String daySurgeryClinicCode;

    /**
     * 术中冰冻标识
     */
    private Boolean frozenFlag;

    /**
     * 术中冰冻开始时间
     */
    private LocalDateTime beginFrozenDate;

    /**
     * 术中冰冻结束时间
     */
    private LocalDateTime endFrozenDate;

    /**
     * 术后是否转入ICU
     */
    private Boolean icuFlag;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsApply) {
            var that = (MetOpsApply) arg0;
            return StringUtils.equals(this.operationNo, that.operationNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.operationNo);
    }

    /**
     * 手术麻醉方式字典
     */
    @Getter
    @AllArgsConstructor
    public enum AnesTypeEnum implements Enum {
        全麻("1", "全麻"), 局麻("2", "局麻"), 硬膜外麻醉("3", "硬膜外麻醉"), 腰麻("4", "腰麻"), 氯胺酮("5", "氯胺酮"), 臂丛麻醉("6", "臂丛麻醉"),
        基础麻醉("7", "基础麻醉"),
        硬膜外封闭("8", "硬膜外封闭"), 气管内麻醉("9", "气管内麻醉"), 腰硬联合麻醉("10", "腰硬联合麻醉"), 静脉麻醉("11", "静脉麻醉"),
        颈丛麻醉("12", "颈丛麻醉"), 局麻_心电监护("13", "局麻+心电监护"), 局麻_备气管内麻("14", "局麻(备气管内麻)"), 测试麻醉("15", "测试麻醉"), 缺省("16", "-");
    
        /**
         * 数据库存值
         */
        private String value;
    
        /**
         * 描述存值
         */
        private String description;
    }
}

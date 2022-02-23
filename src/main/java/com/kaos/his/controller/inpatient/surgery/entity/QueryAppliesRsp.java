package com.kaos.his.controller.inpatient.surgery.entity;

import java.util.Date;
import java.util.List;

import com.kaos.his.enums.common.SexEnum;
import com.kaos.his.enums.inpatient.surgery.AnesTypeEnum;
import com.kaos.his.enums.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.his.enums.inpatient.surgery.SurgeryInspectResultEnum;

/**
 * 接口 QueryMetOpsAppliesInDept 的响应体
 */
public class QueryAppliesRsp {
    /**
     * 结果集数量
     */
    public Integer size = null;

    /**
     * 数据集
     */
    public static class Data {
        /**
         * 手术间
         */
        public String roomNo = null;

        /**
         * 预约时间
         */
        public Date apprDate = null;

        /**
         * 住院号
         */
        public String patientNo = null;

        /**
         * 科室
         */
        public String deptName = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 诊断
         */
        public String diagnosis = null;

        /**
         * 手术名称
         */
        public String surgeryName = null;

        /**
         * 手术标识
         */
        public String operRemark = null;

        /**
         * 手术分级
         */
        public SurgeryDegreeEnum degree = null;

        /**
         * 术者
         */
        public String surgeryDocName = null;

        /**
         * 助手
         */
        public String helperNames = null;

        /**
         * 麻醉种类
         */
        public AnesTypeEnum anesType = null;

        /**
         * 主麻
         */
        public String anesDoc1 = null;

        /**
         * 副麻
         */
        public String anesDoc2 = null;

        /**
         * 洗手护士
         */
        public String washNurseNames = null;

        /**
         * 巡回护士
         */
        public String itinerantNurseNames = null;

        /**
         * 特殊要求
         */
        public String applyNote = null;

        /**
         * 检验结果
         */
        public SurgeryInspectResultEnum inspectResult = null;

        /**
         * 是否公布
         */
        public String publishFlag = null;

        /**
         * ERAS信息
         */
        public String eras = null;

        /**
         * VTE信息
         */
        public String vte = null;

        /**
         * 手术状态 (手麻系统中的状态)
         */
        public String surgeryState = null;
    }

    /**
     * 数据
     */
    public List<Data> data = null;
}

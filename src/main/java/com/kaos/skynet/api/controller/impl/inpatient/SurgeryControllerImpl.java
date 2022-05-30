package com.kaos.skynet.api.controller.impl.inpatient;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.SurgeryController;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgEmplCache;
import com.kaos.skynet.api.data.cache.common.MetOpsnWyDocCache;
import com.kaos.skynet.api.data.cache.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsItemMapper;
import com.kaos.skynet.api.service.inf.inpatient.SurgeryService;
import com.kaos.skynet.core.http.handler.impl.DocareHttpHandler;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/surgery")
public class SurgeryControllerImpl implements SurgeryController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * HttpHelper
     */
    DocareHttpHandler httpClient;

    /**
     * 接口：手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    /**
     * 手术申请记录表查询接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 手术项目表查询接口
     */
    @Autowired
    MetOpsItemMapper metOpsItemMapper;

    /**
     * 手术安排接口
     */
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    /**
     * 手术间缓存
     */
    @Autowired
    Cache<String, MetOpsRoom> roomCache;

    /**
     * 职工信息缓存
     */
    @Autowired
    DawnOrgEmplCache emplCache;

    /**
     * 外院医生缓存
     */
    @Autowired
    MetOpsnWyDocCache wyDocCache;

    /**
     * 住院主表缓存
     */
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    /**
     * 科室缓存
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 床位缓存
     */
    @Autowired
    ComBedInfoCache bedInfoCache;

    /**
     * 患者基本信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 构造响应体元素
     * 
     * @param item
     * @return
     */
    private QueryAppliesRsp.Data createQueryMetOpsAppliesInDeptRspBody(MetOpsApply apply) {
        // 创建实体
        var rspBody = new QueryAppliesRsp.Data();

        // 手术间
        if (apply.associateEntity.room != null) {
            rspBody.roomNo = apply.associateEntity.room.roomName;
        } else {
            rspBody.roomNo = apply.roomId;
        }

        // 预约时间
        rspBody.apprDate = apply.apprDate;

        // 住院号
        rspBody.patientNo = apply.patientNo;
        if (apply.associateEntity.inMainInfo != null) {
            // 定位住院实体
            var inMainInfo = apply.associateEntity.inMainInfo;

            // 患者科室
            var dept = deptCache.get(inMainInfo.getDeptCode());
            if (dept != null) {
                rspBody.deptName = dept.getDeptName();
            } else {
                rspBody.deptName = inMainInfo.getDeptCode();
            }

            // 床号
            var bed = bedInfoCache.get(inMainInfo.getBedNo());
            if (bed != null) {
                rspBody.bedNo = bed.getBriefBedNo();
            } else {
                rspBody.bedNo = inMainInfo.getBedNo();
            }

            // 患者姓名、性别、年龄
            var patientInfo = patientInfoCache.get(inMainInfo.getCardNo());
            if (patientInfo != null) {
                rspBody.name = patientInfo.getName();
                rspBody.sex = patientInfo.getSex();
                rspBody.age = Period.between(patientInfo.getBirthday().toLocalDate(), LocalDate.now());
            } else {
                rspBody.name = inMainInfo.getName();
            }

            // ERAS
            rspBody.eras = inMainInfo.getErasInpatient() != null && inMainInfo.getErasInpatient() ? "是" : "否";

            // VTE
            rspBody.vte = inMainInfo.getVte();
        }

        // 诊断
        rspBody.diagnosis = apply.diagnosis;

        // 手术名称
        if (apply.associateEntity.metOpsItem != null) {
            rspBody.surgeryName = apply.associateEntity.metOpsItem.itemName;
        }

        // 手术标识
        rspBody.operRemark = apply.operRemark;

        // 手术级别
        rspBody.degree = apply.degree;

        // 术者
        if (apply.associateEntity.opsDoc != null) {
            rspBody.surgeryDocName = apply.associateEntity.opsDoc.getEmplName();
        } else {
            rspBody.surgeryDocName = apply.opsDocCode;
        }

        // 手术安排相关
        if (apply.associateEntity.metOpsArranges != null) {
            // 定位手术安排
            var arranges = apply.associateEntity.metOpsArranges;

            // 助手
            List<MetOpsArrange> reg = Lists.newArrayList();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper1));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper2));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper3));
            reg.removeIf(Objects::isNull);
            rspBody.helperNames = reg.stream().map(x -> {
                // 检索院内医生实体
                var empl = emplCache.get(x.emplCode);
                if (empl != null) {
                    return empl.getEmplName();
                }
                // 检索院外医生
                var wyDoc = wyDocCache.get(x.emplCode);
                if (wyDoc != null) {
                    return wyDoc.getEmplName();
                }
                return x.emplCode;
            }).collect(Collectors.joining(";"));

            // 麻醉医生1
            var arrange = arranges.get(SurgeryArrangeRoleEnum.Anaesthetist);
            if (arrange != null) {
                if (arrange.associateEntity.employee != null) {
                    rspBody.anesDoc1 = arrange.associateEntity.employee.getEmplName();
                } else {
                    rspBody.anesDoc1 = arrange.emplCode;
                }
            }

            // 麻醉医生2
            arrange = arranges.get(SurgeryArrangeRoleEnum.AnaesthesiaHelper);
            if (arrange != null) {
                if (arrange.associateEntity.employee != null) {
                    rspBody.anesDoc2 = arrange.associateEntity.employee.getEmplName();
                } else {
                    rspBody.anesDoc2 = arrange.emplCode;
                }
            }

            // 洗手护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse1));
            reg.removeIf(Objects::isNull);
            rspBody.washNurseNames = reg.stream().map(x -> {
                // 检索院内医生实体
                var empl = emplCache.get(x.emplCode);
                if (empl != null) {
                    return empl.getEmplName();
                }

                return x.emplCode;
            }).collect(Collectors.joining(";"));

            // 巡回护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse1));
            reg.removeIf(Objects::isNull);
            rspBody.itinerantNurseNames = reg.stream().map(x -> {
                // 检索院内医生实体
                var empl = emplCache.get(x.emplCode);
                if (empl != null) {
                    return empl.getEmplName();
                }

                return x.emplCode;
            }).collect(Collectors.joining(";"));
        }

        // 麻醉种类
        rspBody.anesType = apply.anesType;

        // 特殊需求
        rspBody.applyNote = apply.applyNote;

        // 检验结果
        rspBody.inspectResult = apply.inspectResult;

        // 是否公布
        rspBody.publishFlag = apply.publishFlag != null && apply.publishFlag ? "是" : "否";

        return rspBody;
    }

    @Override
    @RequestMapping(value = "querySurgeries", method = RequestMethod.POST, produces = MediaType.JSON)
    public QueryAppliesRsp querySurgeries(@RequestBody @Valid QueryAppliesReq req) {
        // 记录日志
        log.info("查询手术申请, 入参:");
        log.info(this.json.toJson(req));

        // 调用服务
        var rs = this.surgeryService.queryApplies(req.deptCode, req.roomNo, req.beginDate, req.endDate, req.states);

        // 记录日志
        log.info(String.format("查询科室手术(count = %d)", rs.size()));

        // 获取手麻系统中的状态
        var reqBody = new QueryStatesReq();
        reqBody.applyNos = rs.stream().map((x) -> {
            return x.operationNo;
        }).toList();
        var stateMap = this.httpClient.postForObject("ms/operation/queryStates", reqBody, QueryStatesRsp.class).states;

        // 构造响应体
        var rspBodies = new QueryAppliesRsp();
        rspBodies.size = rs.size();
        rspBodies.data = Lists.newArrayList();
        for (var item : rs) {
            var rspItem = this.createQueryMetOpsAppliesInDeptRspBody(item);
            if (stateMap.containsKey(item.operationNo)) {
                rspItem.surgeryState = stateMap.get(item.operationNo);
            }
            rspBodies.data.add(rspItem);
        }

        return rspBodies;
    }

    /**
     * Web请求Body
     */
    public class QueryStatesReq {
        /**
         * 手术申请号
         */
        public List<String> applyNos = null;
    }

    /**
     * Web响应body
     */
    public class QueryStatesRsp {
        /**
         * 状态清单
         */
        public Map<String, String> states = null;
    }
}

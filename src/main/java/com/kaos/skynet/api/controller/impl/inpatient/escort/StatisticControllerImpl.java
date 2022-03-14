package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.impl.common.ComPatientInfoCache;
import com.kaos.skynet.api.cache.impl.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.StatisticController;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.enums.impl.inpatient.InStateEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/escort/statistic")
public class StatisticControllerImpl implements StatisticController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(StatisticControllerImpl.class);

    /**
     * 陪护业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 床位cache
     */
    @Autowired
    ComBedInfoCache bedInfoCache;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * LIS数据接口
     */
    @Autowired
    LisResultNewMapper lisResultMapper;

    @Override
    @RequestMapping(value = "queryEscortRsp", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryEscortRsp> queryEscortRsp(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 查询指定科室的在院患者
        var pats = this.inMainInfoMapper.queryInpatients(null, null, deptCode, Lists.newArrayList(InStateEnum.病房接诊));

        // 声明结果集
        List<QueryEscortRsp> ret = Lists.newArrayList();

        // 时间格式工具
        var formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 构造结果集
        for (FinIprInMainInfo inMainInfo : pats) {
            // 构造结果元素
            QueryEscortRsp item = new QueryEscortRsp();
            item.inDate = inMainInfo.inDate;
            var bed = this.bedInfoCache.getValue(inMainInfo.bedNo);
            if (bed != null) {
                item.bedNo = bed.getBriefBedNo();
            }
            var patient = this.patientInfoCache.getValue(inMainInfo.cardNo);
            if (patient != null) {
                item.name = patient.name;
                item.cardNo = patient.cardNo;
                item.healthCode = patient.healthCode;
                item.travelCode = patient.travelCode;
                item.highRiskFlag = patient.highRiskFlag;
                item.highRiskArea = patient.highRiskArea;
            }
            var lisResult = this.lisResultMapper.queryInspectResult(inMainInfo.patientNo,
                    Lists.newArrayList("SARS-CoV-2-RNA"), null, null);
            if (lisResult != null && !lisResult.isEmpty()) {
                Collections.sort(lisResult, (x, y) -> {
                    return y.inspectDate.compareTo(x.inspectDate);
                });
                var test = lisResult.get(0);
                item.nucleicAcidResult = String.format("%s(%s)", test.result, formater.format(test.inspectDate));
            }
            ret.add(item);
            // 检索陪护
            var escorts = this.escortService.queryHelperInfos(patient.cardNo);
            if (escorts.size() >= 1) {
                var escort = escorts.get(0);
                var helper = this.patientInfoCache.getValue(escort.helperCardNo);
                item.escort1Name = helper.name;
                item.escort1CardNo = helper.cardNo;
                item.escort1IdenNo = helper.identityCardNo;
                var helperRets = this.lisResultMapper.queryInspectResult(helper.cardNo,
                        Lists.newArrayList("SARS-CoV-2-RNA"), null, null);
                if (helperRets != null && !helperRets.isEmpty()) {
                    Collections.sort(helperRets, (x, y) -> {
                        return y.inspectDate.compareTo(x.inspectDate);
                    });
                    var test = helperRets.get(0);
                    item.escort1NucleicAcidResult = String.format("%s(%s)", test.result,
                            formater.format(test.inspectDate));
                }
                item.escort1Tel = helper.linkmanTel;
                item.escort1HealthCode = helper.healthCode;
                item.escort1TravelCode = helper.travelCode;
                item.escort1HighRiskFlag = helper.highRiskFlag;
                item.escort1HighRiskArea = helper.highRiskArea;
            }
            if (escorts.size() >= 2) {
                var escort = escorts.get(1);
                var helper = this.patientInfoCache.getValue(escort.helperCardNo);
                item.escort2Name = helper.name;
                item.escort2CardNo = helper.cardNo;
                item.escort2IdenNo = helper.identityCardNo;
                var helperRets = this.lisResultMapper.queryInspectResult(helper.cardNo,
                        Lists.newArrayList("SARS-CoV-2-RNA"), null, null);
                if (helperRets != null && !helperRets.isEmpty()) {
                    Collections.sort(helperRets, (x, y) -> {
                        return y.inspectDate.compareTo(x.inspectDate);
                    });
                    var test = helperRets.get(0);
                    item.escort2NucleicAcidResult = String.format("%s(%s)", test.result,
                            formater.format(test.inspectDate));
                }
                item.escort2Tel = helper.linkmanTel;
            }
        }

        return ret;
    }
}

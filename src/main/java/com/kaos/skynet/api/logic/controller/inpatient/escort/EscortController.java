package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinIprPrepayInCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortMainInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortStateRecCache;
import com.kaos.skynet.api.data.his.cache.inpatient.escort.EscortVipCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.his.tunnel.BedNoTunnel;
import com.kaos.skynet.api.data.his.tunnel.DeptNameTunnel;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortLock;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.IntegerUtils;
import com.kaos.skynet.core.util.StringUtils;
import com.kaos.skynet.core.util.converter.StringToEnumConverterFactory;
import com.kaos.skynet.core.util.json.adapter.BooleanTypeAdapter_10;
import com.kaos.skynet.core.util.json.adapter.EnumTypeAdapter_Value;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.extern.log4j.Log4j;

@CrossOrigin
@PassToken
@Log4j
@Validated
@RestController
@RequestMapping("/api/inpatient/escort")
public class EscortController {
    /**
     * ?????????
     */
    @Autowired
    EscortLock escortLock;

    /**
     * ????????????
     */
    @Autowired
    EscortService escortService;

    /**
     * ??????????????????
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * ?????????????????????
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * ??????????????????
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * ??????????????????
     */
    @Autowired
    EscortMainInfoCache escortMainInfoCache;

    /**
     * ??????????????????
     */
    @Autowired
    EscortStateRecCache escortStateRecCache;

    /**
     * VIP??????
     */
    @Autowired
    EscortVipCache escortVipCache;

    /**
     * ??????????????????
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * ???????????????
     */
    @Autowired
    FinIprPrepayInCache prepayInCache;

    /**
     * ??????????????????
     */
    StringToEnumConverterFactory valueStringToEnumConverterFactory = new StringToEnumConverterFactory(true);

    /**
     * ??????????????????
     */
    StringToEnumConverterFactory descriptionStringToEnumConverterFactory = new StringToEnumConverterFactory(false);

    /**
     * ???????????????
     */
    @Autowired
    BedNoTunnel bedNoTunnel;

    /**
     * ?????????????????????
     */
    @Autowired
    DeptNameTunnel deptNameTunnel;

    /**
     * ?????????????????????????????????
     */
    final Converter<String, String> patientIdxToCardNoConverter = new Converter<String, String>() {
        @Override
        public String convert(String patientIdx) {
            // ????????????????????????
            FinIprInMainInfo inMainInfo = inMainInfoMapper.queryInMainInfo("ZY01".concat(patientIdx));
            if (inMainInfo != null) {
                return inMainInfo.getCardNo();
            }
            return patientIdx;
        };
    };

    @ApiName("???????????????")
    @RequestMapping(value = "bind", method = RequestMethod.POST, produces = MediaType.JSON)
    String bind(@RequestBody @Valid Bind.ReqBody reqBody) throws Exception {
        // ??????????????????
        String patientCardNo = patientIdxToCardNoConverter.convert(reqBody.patientIdx);

        // ??????????????????
        var patientLock = escortLock.getHelperLock().grant(patientCardNo);
        var helperLock = escortLock.getHelperLock().grant(reqBody.helperCardNo);
        return LockExecutor.execute(Lists.newArrayList(patientLock, helperLock), () -> {
            return escortService.register(patientCardNo,
                    reqBody.helperCardNo,
                    reqBody.emplCode,
                    reqBody.regByWindow);
        });
    }

    static class Bind {
        static class ReqBody {
            /**
             * ?????????
             */
            @NotBlank(message = "????????????<?????????or????????????>????????????")
            @Size(message = "????????????<?????????or????????????>????????????", min = 10, max = 10)
            String patientIdx = null;

            /**
             * ???????????????
             */
            @NotBlank(message = "???????????????????????????")
            @Size(message = "???????????????????????????", min = 10, max = 10)
            String helperCardNo = null;

            /**
             * ???????????????
             */
            @NotBlank(message = "???????????????????????????")
            String emplCode = null;

            /**
             * ?????????????????????
             */
            Boolean regByWindow = false;
        }
    }

    /**
     * ????????????
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     */
    @ApiName("?????????????????????")
    @RequestMapping(value = "updateState", method = RequestMethod.POST, produces = MediaType.JSON)
    String updateState(@RequestBody @Valid UpdateState.ReqBody reqBody) {
        // ?????????????????????????????????????????????????????????
        LockExecutor.execute(escortLock.getStateLock().grant(reqBody.escortNo), () -> {
            escortService.updateState(reqBody.escortNo, reqBody.state, reqBody.emplCode, "?????????????????????");
        });

        return reqBody.escortNo;
    }

    static class UpdateState {
        static class ReqBody {
            /**
             * ????????????
             */
            @NotBlank(message = "????????????????????????")
            String escortNo;

            /**
             * ???????????????
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            EscortStateRec.StateEnum state;

            /**
             * ?????????
             */
            @NotBlank(message = "?????????????????????")
            String emplCode;
        }
    }

    /**
     * ????????????
     * 
     * @param escortNo
     * @param action
     */
    @ApiName("?????????????????????")
    @RequestMapping(value = "recordAction", method = RequestMethod.POST, produces = MediaType.JSON)
    String recordAction(@RequestBody @Valid RecordAction.ReqBody reqBody) {
        // ?????????????????????????????????????????????????????????
        LockExecutor.execute(escortLock.getActionLock().grant(reqBody.escortNo), () -> {
            escortService.recordAction(reqBody.escortNo, reqBody.action, "?????????????????????");
        });

        return reqBody.escortNo;
    }

    static class RecordAction {
        static class ReqBody {
            /**
             * ????????????
             */
            @NotBlank(message = "????????????????????????")
            String escortNo;

            /**
             * ???????????????
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            EscortActionRec.ActionEnum action;
        }
    }

    /**
     * ??????????????????
     * 
     * @param escortNo
     * @return
     */
    @ApiName("?????????????????????")
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    QueryStateInfo.RspBody queryStateInfo(@RequestBody @Valid QueryStateInfo.ReqBody reqBody) {
        // ????????????
        var escortInfo = escortMainInfoCache.get(reqBody.escortNo);
        if (escortInfo == null) {
            log.error(String.format("?????????????????????", reqBody.escortNo));
            throw new RuntimeException("?????????????????????".concat(reqBody.escortNo));
        }

        // ???????????????
        var rspBuilder = QueryStateInfo.RspBody.builder();
        rspBuilder.patientCardNo(escortInfo.getPatientCardNo());
        rspBuilder.helperCardNo(escortInfo.getHelperCardNo());
        rspBuilder.regDate(escortStateRecMapper.queryFirstEscortStateRec(reqBody.escortNo).getRecDate());
        rspBuilder.state(escortStateRecMapper.queryLastEscortStateRec(reqBody.escortNo).getState());

        return rspBuilder.build();
    }

    static class QueryStateInfo {
        static class ReqBody {
            /**
             * ????????????
             */
            @NotBlank(message = "????????????????????????")
            String escortNo;
        }

        @Builder
        static class RspBody {
            /**
             * ????????????
             */
            String patientCardNo;

            /**
             * ???????????????
             */
            @SerializedName("escortCardNo")
            String helperCardNo;

            /**
             * ????????????
             */
            LocalDateTime regDate;

            /**
             * ????????????<?????????>
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            StateEnum state;
        }
    }

    /**
     * ?????????????????????
     * 
     * @param helperCardNo
     * @return
     */
    @ApiName("????????????????????????")
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryPatientInfo.RspBody> queryPatientInfo(@RequestBody @Valid QueryPatientInfo.ReqBody reqBody) {
        // ????????????
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .helperCardNo(reqBody.helperCardNo)
                        .states(Lists.newArrayList(
                                StateEnum.?????????????????????,
                                StateEnum.??????????????????????????????,
                                StateEnum.????????????????????????????????????,
                                StateEnum.?????????,
                                StateEnum.??????))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // ????????????body
        return escortInfos.stream().map(x -> {
            var rsp = QueryPatientInfo.RspBody.builder();
            rsp.cardNo = x.getPatientCardNo();
            var patient = patientInfoCache.get(rsp.cardNo);
            if (patient != null) {
                rsp.name = patient.getName();
                rsp.sex = patient.getSex();
                rsp.age = Period.between(patient.getBirthday().toLocalDate(), LocalDate.now());
            }
            var inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                    .cardNo(x.getPatientCardNo())
                    .happenNo(x.getHappenNo())
                    .build());
            if (inMainInfos != null && inMainInfos.size() == 1) {
                var inMainInfo = inMainInfos.get(0);
                rsp.deptName = deptNameTunnel.tunneling(inMainInfo.getDeptCode());
                rsp.bedNo = bedNoTunnel.tunneling(inMainInfo.getBedNo());
                rsp.patientNo = inMainInfo.getPatientNo();
            } else {
                var builder = FinIprPrepayInCache.Key.builder();
                builder.cardNo(x.getPatientCardNo());
                builder.happenNo(x.getHappenNo());
                var prepayIn = prepayInCache.get(builder.build());
                if (prepayIn != null) {
                    rsp.deptName = deptNameTunnel.tunneling(prepayIn.getPreDeptCode());
                    rsp.bedNo = bedNoTunnel.tunneling(prepayIn.getBedNo());
                }
            }
            var vip = escortVipCache.get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.escortNo = x.getEscortNo();
            rsp.states = escortStateRecCache.get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            rsp.actions = Lists.newArrayList();
            return rsp.build();
        }).toList();
    }

    static class QueryPatientInfo {
        static class ReqBody {
            /**
             * ???????????????
             */
            @NotBlank(message = "???????????????????????????")
            String helperCardNo;
        }

        @Builder
        static class RspBody {
            /**
             * ????????????
             */
            String cardNo;

            /**
             * ??????
             */
            String name;

            /**
             * ??????
             */
            SexEnum sex;

            /**
             * ??????
             */
            Period age;

            /**
             * ??????
             */
            String deptName;

            /**
             * ??????
             */
            String bedNo;

            /**
             * ?????????
             */
            String patientNo;

            /**
             * ????????????
             */
            @JsonAdapter(value = BooleanTypeAdapter_10.class)
            Boolean freeFlag;

            /**
             * ????????????
             */
            String escortNo;

            /**
             * ????????????
             */
            List<EscortStateRec> states;

            /**
             * ????????????
             */
            List<EscortActionRec> actions;
        }
    }

    @ApiName("???????????????????????????")
    @RequestMapping(value = "queryHelperInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryHelperInfo.RspBody> queryHelperInfo(@RequestBody @Valid QueryHelperInfo.ReqBody reqBody) {
        // ????????????
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .patientCardNo(reqBody.patientCardNo)
                        .states(Lists.newArrayList(
                                StateEnum.?????????????????????,
                                StateEnum.??????????????????????????????,
                                StateEnum.????????????????????????????????????,
                                StateEnum.?????????,
                                StateEnum.??????))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // ????????????body
        return escortInfos.stream().map(x -> {
            var rsp = QueryHelperInfo.RspBody.builder();
            rsp.cardNo = x.getHelperCardNo();
            var helper = patientInfoCache.get(x.getHelperCardNo());
            if (helper != null) {
                rsp.name = helper.getName();
                rsp.sex = helper.getSex();
                rsp.age = Period.between(helper.getBirthday().toLocalDate(), LocalDate.now());
            }
            var vip = escortVipCache.get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.escortNo = x.getEscortNo();
            rsp.states = escortStateRecCache.get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            rsp.actions = Lists.newArrayList();
            return rsp.build();
        }).toList();
    }

    static class QueryHelperInfo {
        static class ReqBody {
            /**
             * ????????????
             */
            @NotBlank(message = "????????????????????????")
            String patientCardNo;
        }

        @Builder
        static class RspBody {
            /**
             * ????????????
             */
            String cardNo;

            /**
             * ??????
             */
            String name;

            /**
             * ??????
             */
            SexEnum sex;

            /**
             * ??????
             */
            Period age;

            /**
             * ????????????
             */
            @JsonAdapter(value = BooleanTypeAdapter_10.class)
            Boolean freeFlag;

            /**
             * ????????????
             */
            String escortNo;

            /**
             * ????????????
             */
            List<EscortStateRec> states;

            /**
             * ????????????
             */
            List<EscortActionRec> actions;
        }
    }
}

package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprPrepayInCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.EscortVipCache;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.entity.inpatient.ComBedInfo;
import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.enums.inpatient.escort.EscortActionEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.json.Json;
import com.kaos.skynet.core.json.gson.adapter.bool.NumericBooleanTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.enums.ValueEnumTypeAdapter;
import com.kaos.skynet.core.thread.Threads;
import com.kaos.skynet.core.type.converter.string.enums.factory.DescriptionStringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.string.enums.factory.ValueStringToEnumConverterFactory;
import com.kaos.skynet.core.type.utils.IntegerUtils;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/escort")
public class EscortController {
    /**
     * 陪护锁
     */
    @Autowired
    EscortLock escortLock;

    /**
     * 陪护服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 数据缓存
     */
    @Autowired
    DataCache dataCache;

    /**
     * 住院主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 枚举值转换器
     */
    @Autowired
    ValueStringToEnumConverterFactory valueStringToEnumConverterFactory;

    /**
     * 枚举值转换器
     */
    @Autowired
    DescriptionStringToEnumConverterFactory descriptionStringToEnumConverterFactory;

    /**
     * 患者索引转卡号的转换器
     */
    final Converter<String, String> patientIdxToCardNoConverter = new Converter<String, String>() {
        @Override
        public String convert(String patientIdx) {
            // 尝试检索住院实体
            FinIprInMainInfo inMainInfo = inMainInfoMapper.queryInMainInfo("ZY01".concat(patientIdx));
            if (inMainInfo != null) {
                return inMainInfo.getCardNo();
            }
            return patientIdx;
        };
    };

    /**
     * 床号转缩略床号
     */
    final Converter<String, String> bedNoConverter = new Converter<String, String>() {
        @Override
        public String convert(String bedNo) {
            // 尝试检索住院实体
            ComBedInfo bedInfo = dataCache.getBedInfoCache().get(bedNo);
            if (bedInfo != null) {
                return bedInfo.getBriefBedNo();
            }
            return bedNo;
        };
    };

    /**
     * 床号转缩略床号
     */
    final Converter<String, String> deptNameConverter = new Converter<String, String>() {
        @Override
        public String convert(String deptCode) {
            // 尝试检索住院实体
            DawnOrgDept dept = dataCache.getDeptCache().get(deptCode);
            if (dept != null) {
                return dept.getDeptName();
            }
            return deptCode;
        };
    };

    /**
     * 注册陪护证
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.TEXT)
    public String register(@RequestBody @Valid RegisterReqBody req) {
        // 记录日志
        log.info(String.format("登记陪护证, 入参%s", json.toJson(req)));

        // 获取就诊卡号
        String patientCardNo = patientIdxToCardNoConverter.convert(req.getPatientIdx());

        // 带锁运行业务
        return Threads.newLockExecutor().link(escortLock.getPatientLock().getLock(patientCardNo))
                .link(escortLock.getHelperLock().getLock(req.getHelperCardNo()))
                .execute(() -> {
                    return escortService.register(patientCardNo,
                            req.getHelperCardNo(),
                            req.getEmplCode(),
                            req.getRegByWindow());
                });
    }

    @Getter
    public static class RegisterReqBody {
        /**
         * 住院号
         */
        @NotNull(message = "患者索引<住院号or就诊卡号>不能为空")
        @Size(message = "患者索引<住院号or就诊卡号>长度异常", min = 10, max = 10)
        private String patientIdx = null;

        /**
         * 陪护人卡号
         */
        @NotNull(message = "陪护人卡号不能为空")
        @Size(message = "陪护人卡号长度异常", min = 10, max = 10)
        private String helperCardNo = null;

        /**
         * 操作员编码
         */
        @NotNull(message = "操作员编码不能为空")
        private String emplCode = null;

        /**
         * 备注
         */
        private String remark = null;

        /**
         * 是否为窗口操作
         */
        private Boolean regByWindow = null;
    }

    /**
     * 更新状态
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     */
    @RequestMapping(value = "updateState", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void updateState(@NotNull(message = "陪护证号不能为空") String escortNo,
            String state,
            @NotNull(message = "操作员编码不能为空") String emplCode) {
        // 解析状态参数
        EscortStateEnum ptr = null;
        if (ptr == null) {
            ptr = valueStringToEnumConverterFactory.getConverter(EscortStateEnum.class).convert(state);
        }
        if (ptr == null) {
            ptr = descriptionStringToEnumConverterFactory.getConverter(EscortStateEnum.class).convert(state);
        }
        final EscortStateEnum stateEnum = ptr;

        // 入参日志
        log.info(String.format("修改陪护证状态<escortNo = %s, state = %s, emplCode = %s>", escortNo,
                stateEnum == null ? "null" : stateEnum.getDescription(), emplCode));

        // 加状态操作锁，防止同时操作同一个陪护证
        Threads.newLockExecutor().link(escortLock.getStateLock().getLock(escortNo)).execute(() -> {
            escortService.updateState(escortNo, stateEnum, emplCode, "收到客户端请求");
        });
    }

    /**
     * 记录动作
     * 
     * @param escortNo
     * @param action
     */
    @RequestMapping(value = "recordAction", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void recordAction(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "记录的动作不能为空") EscortActionEnum action) {
        // 入参日志
        log.info(String.format("记录陪护证行为<escortNo = %s, action = %s>", escortNo, action.getDescription()));

        // 加状态操作锁，防止同时操作同一个陪护证
        Threads.newLockExecutor().link(escortLock.getActionLock().getLock(escortNo)).execute(() -> {
            escortService.recordAction(escortNo, action, "收到客户端请求");
        });
    }

    /**
     * 查询陪护状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryStateInfoRsp queryStateInfo(@NotNull(message = "陪护证号不能为空") String escortNo) {
        // 入参日志
        log.info(String.format("查询陪护证状态<escortNo = %s>", escortNo));

        // 调用业务
        var escortInfo = dataCache.getEscortMainInfoCache().get(escortNo);
        if (escortInfo == null) {
            log.error(String.format("不存在的陪护号", escortNo));
            throw new RuntimeException(String.format("不存在的陪护号", escortNo));
        }

        // 构造响应体
        var rsp = new QueryStateInfoRsp();
        rsp.patientCardNo = escortInfo.getPatientCardNo();
        rsp.helperCardNo = escortInfo.getHelperCardNo();
        var states = dataCache.getEscortStateRecCache().get(escortNo);
        if (states != null && !states.isEmpty()) {
            states.sort((x, y) -> {
                return IntegerUtils.compare(x.getRecNo(), y.getRecNo());
            });
            rsp.regDate = states.get(0).getRecDate();
            Collections.reverse(states);
            rsp.state = states.get(0).getState();
        }

        return rsp;
    }

    /**
     * 查询状态的响应
     */
    public static class QueryStateInfoRsp {
        /**
         * 患者卡号
         */
        public String patientCardNo = null;

        /**
         * 陪护人卡号
         */
        @SerializedName(value = "escortCardNo")
        public String helperCardNo = null;

        /**
         * 注册时间
         */
        public LocalDateTime regDate = null;

        /**
         * 当前状态<枚举值>
         */
        @JsonAdapter(ValueEnumTypeAdapter.class)
        public EscortStateEnum state = null;
    }

    /**
     * 根据患者查陪护
     * 
     * @param helperCardNo
     * @return
     */
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryPatientInfoRsp> queryPatientInfo(@NotNull(message = "陪护人卡号不能为空") String helperCardNo) {
        // 入参日志
        log.info(String.format("查询陪护患者信息<helperCardNo = %s>", helperCardNo));

        // 调用业务
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .helperCardNo(helperCardNo)
                        .states(Lists.newArrayList(
                                EscortStateEnum.无核酸检测结果,
                                EscortStateEnum.等待院内核酸检测结果,
                                EscortStateEnum.等待院外核酸检测结果审核,
                                EscortStateEnum.生效中,
                                EscortStateEnum.其他))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        return escortInfos.stream().map(x -> {
            QueryPatientInfoRsp rsp = new QueryPatientInfoRsp();
            rsp.cardNo = x.getPatientCardNo();
            var patient = dataCache.getPatientInfoCache().get(rsp.cardNo);
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
                rsp.deptName = deptNameConverter.convert(inMainInfo.getDeptCode());
                rsp.bedNo = bedNoConverter.convert(inMainInfo.getBedNo());
                rsp.patientNo = inMainInfo.getPatientNo();
            } else {
                var prepayIn = dataCache.getPrepayInCache().get(
                        FinIprPrepayInCache.Key.builder()
                                .cardNo(x.getPatientCardNo())
                                .happenNo(x.getHappenNo()).build());
                if (prepayIn != null) {
                    rsp.deptName = deptNameConverter.convert(prepayIn.getPreDeptCode());
                    rsp.bedNo = bedNoConverter.convert(prepayIn.getBedNo());
                }
            }
            var vip = dataCache.getEscortVipCache().get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.states = dataCache.getEscortStateRecCache().get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            return rsp;
        }).toList();
    }

    /**
     * 查询患者信息响应
     */
    public static class QueryPatientInfoRsp {
        /**
         * 就诊卡号
         */
        public String cardNo = null;

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
        public Period age = null;

        /**
         * 科室
         */
        public String deptName = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 住院号
         */
        public String patientNo = null;

        /**
         * 免费标识
         */
        @JsonAdapter(value = NumericBooleanTypeAdapter.class)
        public Boolean freeFlag = null;

        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 状态列表
         */
        public List<EscortStateRec> states = null;

        /**
         * 动作列表
         */
        public List<EscortActionRec> actions = null;
    }

    @RequestMapping(value = "queryHelperInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryHelperInfoRsp> queryHelperInfo(@NotNull(message = "患者卡号不能为空") String patientCardNo) {
        // 入参日志
        log.info(String.format("查询患者陪护人信息<patientCardNo = %s>", patientCardNo));

        // 调用业务
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .patientCardNo(patientCardNo)
                        .states(Lists.newArrayList(
                                EscortStateEnum.无核酸检测结果,
                                EscortStateEnum.等待院内核酸检测结果,
                                EscortStateEnum.等待院外核酸检测结果审核,
                                EscortStateEnum.生效中,
                                EscortStateEnum.其他))
                        .build());
        if (escortInfos == null) {
            return null;
        }

        // 构造响应body
        return escortInfos.stream().map(x -> {
            QueryHelperInfoRsp rsp = new QueryHelperInfoRsp();
            rsp.cardNo = x.getHelperCardNo();
            var helper = dataCache.getPatientInfoCache().get(x.getHelperCardNo());
            if (helper != null) {
                rsp.name = helper.getName();
                rsp.sex = helper.getSex();
                rsp.age = Period.between(helper.getBirthday().toLocalDate(), LocalDate.now());
            }
            var vip = dataCache.getEscortVipCache().get(
                    EscortVipCache.Key.builder()
                            .cardNo(x.getPatientCardNo())
                            .happenNo(x.getHappenNo()).build());
            if (vip != null) {
                rsp.freeFlag = Boolean.valueOf(StringUtils.equals(vip.getHelperCardNo(), x.getHelperCardNo()));
            }
            rsp.escortNo = x.getEscortNo();
            rsp.states = dataCache.getEscortStateRecCache().get(x.getEscortNo());
            rsp.states.sort((a, b) -> {
                return IntegerUtils.compare(a.getRecNo(), b.getRecNo());
            });
            return rsp;
        }).toList();
    }

    /**
     * 查询陪护人响应
     */
    public static class QueryHelperInfoRsp {
        /**
         * 就诊卡号
         */
        public String cardNo = null;

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
        public Period age = null;

        /**
         * 免费标识
         */
        @JsonAdapter(value = NumericBooleanTypeAdapter.class)
        public Boolean freeFlag = null;

        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 状态列表
         */
        public List<EscortStateRec> states = null;

        /**
         * 行为列表
         */
        public List<EscortActionRec> actions = null;
    }
}

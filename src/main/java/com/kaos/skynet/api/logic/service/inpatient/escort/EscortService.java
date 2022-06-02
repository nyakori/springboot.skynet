package com.kaos.skynet.api.logic.service.inpatient.escort;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec.ActionEnum;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.mapper.common.SequenceMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortVipMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EscortService {
    /**
     * 缓存
     */
    @Autowired
    DataCache cache;

    /**
     * 陪护业务核心
     */
    @Autowired
    CoreService coreService;

    /**
     * 序列获取器
     */
    @Autowired
    SequenceMapper sequenceMapper;

    /**
     * VIP表接口
     */
    @Autowired
    EscortVipMapper vipMapper;

    /**
     * 主表接口
     */
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    /**
     * 状态表接口
     */
    @Autowired
    EscortStateRecMapper stateRecMapper;

    /**
     * 状态表接口
     */
    @Autowired
    EscortActionRecMapper actionRecMapper;

    /**
     * 登记陪护证
     * 
     * @param patientCardNo 患者卡号
     * @param helperCardNo  陪护人卡号
     * @param operCode      操作员
     * @param escape        逃逸码, 逃逸部分校验
     * @return
     */
    @Transactional
    public String register(String patientCardNo, String helperCardNo, String operCode, Boolean escape) {
        // 模拟登记
        var simulateResult = coreService.simulateRegister(patientCardNo, helperCardNo, escape);
        var mainInfo = simulateResult.getMainInfo();

        // 模拟成功后正式更新数据库
        var vip = vipMapper.queryEscortVip(mainInfo.getPatientCardNo(), mainInfo.getHappenNo());
        if (vip == null) {
            vipMapper.insertEscortVip(EscortVip.builder()
                    .patientCardNo(patientCardNo)
                    .happenNo(mainInfo.getHappenNo())
                    .recDate(LocalDateTime.now()).build());
        }

        // 插入主表
        mainInfo.setEscortNo(StringUtils.leftPad(sequenceMapper.query("KAOS.SEQ_ESCORT_NO"), 10, '0'));
        mainInfoMapper.insertEscortMainInfo(mainInfo);

        // 插入状态表
        stateRecMapper.insertEscortStateRec(EscortStateRec.builder()
                .escortNo(simulateResult.getMainInfo().getEscortNo())
                .recNo(1)
                .state(simulateResult.getStateResult().getState())
                .recEmplCode(operCode)
                .recDate(LocalDateTime.now())
                .remark(simulateResult.getStateResult().getReason()).build());

        return simulateResult.getMainInfo().getEscortNo();
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo 陪护证编号
     * @param state    状态
     * @param operCode 状态
     * @param remark   状态
     */
    @Transactional
    public void updateState(String escortNo, StateEnum state, String operCode, String remark) {
        // 检索陪护实体
        var mainInfo = mainInfoMapper.queryEscortMainInfo(escortNo);
        if (mainInfo == null) {
            throw new RuntimeException(String.format("陪护证不存在(%s)", escortNo));
        }

        // 若未设置状态，则获取实时状态
        if (state == null) {
            var stateResult = coreService.getState(mainInfo);
            state = stateResult.getState();
            remark = stateResult.getReason();
        }

        // 读取状态列表
        var stateRecs = cache.getEscortStateRecCache().get(escortNo);
        if (stateRecs == null || stateRecs.isEmpty()) {
            stateRecMapper.insertEscortStateRec(EscortStateRec.builder()
                    .escortNo(escortNo)
                    .recNo(1)
                    .state(state)
                    .recEmplCode(operCode)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        } else if (stateRecs.get(0).getState() != state) {
            stateRecMapper.insertEscortStateRec(EscortStateRec.builder()
                    .escortNo(escortNo)
                    .recNo(stateRecs.get(0).getRecNo() + 1)
                    .state(state)
                    .recEmplCode(operCode)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        }

        // 更新缓存
        cache.getEscortStateRecCache().refresh(escortNo);
    }

    /**
     * 登记陪护行为
     * 
     * @param escortNo 陪护证编号
     * @param action   状态
     * @param remark   备注信息
     */
    @Transactional
    public void recordAction(String escortNo, ActionEnum action, String remark) {
        // 检索陪护实体
        var mainInfo = mainInfoMapper.queryEscortMainInfo(escortNo);
        if (mainInfo == null) {
            throw new RuntimeException(String.format("陪护证不存在(%s)", escortNo));
        }

        // 检索最后一个行为
        var actionRecs = actionRecMapper.queryActions(escortNo);
        if (actionRecs == null || actionRecs.isEmpty()) {
            actionRecMapper.insertAction(EscortActionRec.builder()
                    .escortNo(escortNo)
                    .recNo(1)
                    .action(action)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        } else {
            actionRecMapper.insertAction(EscortActionRec.builder()
                    .escortNo(escortNo)
                    .recNo(actionRecs.get(0).getRecNo() + 1)
                    .action(action)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        }
    }
}

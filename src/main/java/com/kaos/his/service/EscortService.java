package com.kaos.his.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaos.his.entity.credential.Escort;
import com.kaos.his.entity.credential.HospitalizationCertificate;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.mapper.credential.EscortMapper;
import com.kaos.his.mapper.credential.HospitalizationCertificateMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;
import com.kaos.his.mapper.personnel.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 陪护证系统的接口
 */
@Service
public class EscortService {
    /**
     * 陪护人实体接口
     */
    @Autowired
    EscortMapper escortMapper;

    /**
     * 住院证实体接口
     */
    @Autowired
    HospitalizationCertificateMapper hospitalizationCertificateMapper;

    /**
     * 住院患者实体接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 患者实体接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 根据陪护人卡号，查询有效的被陪护患者信息
     * 
     * @param cardNo 陪护人卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    public List<Escort> QueryActiveEscortedPatient(String cardNo) {
        // 声明结果集
        var resultSet = new ArrayList<Escort>();

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.GetEscortsByHelperCardNo(cardNo);

        // 辅助字典 - 记录患者的最近一次住院证，加速大批量数据时的查询
        Map<String, HospitalizationCertificate> hosCtfDict = new HashMap<String, HospitalizationCertificate>();

        // 筛选出有效的陪护证
        for (Escort escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 提取住院证，如果住院证不是患者最近的一张陪护证，说明被关联的患者已经出院，陪护证应当自动失效
            var relatehosCtf = escort.hospitalizationCertificate;
            HospitalizationCertificate latestHosCtf = null;
            if (hosCtfDict.containsKey(relatehosCtf.cardNo)) {
                latestHosCtf = hosCtfDict.get(relatehosCtf.cardNo);
            } else {
                latestHosCtf = this.hospitalizationCertificateMapper
                        .GetLatestHospitalizationCertificate(relatehosCtf.cardNo);
                hosCtfDict.put(relatehosCtf.cardNo, latestHosCtf);
            }
            if (relatehosCtf.happenNo != latestHosCtf.happenNo) {
                continue;
            }

            // 如果已入院，则将住院患者实体更新为住院实体
            var inpatient = this.inpatientMapper.GetInpatientByCardNoAndHappenNo(relatehosCtf.cardNo,
                    relatehosCtf.happenNo);
            if (inpatient != null) {
                escort.hospitalizationCertificate.patient = inpatient;
            }

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }
}

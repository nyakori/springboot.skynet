package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortCard;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortCardMapper {
    /**
     * 获取陪护证
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    EscortCard GetEscort(String escortNo);

    /**
     * 根据陪护人卡号查询关联的陪护证
     * 
     * @param escortNo 陪护人卡号
     * @return
     */
    List<EscortCard> GetEscortsByHelperCardNo(String cardNo);

    /**
     * 根据陪护人卡号查询关联的陪护证
     * 
     * @param cardNo
     * @return
     */
    List<EscortCard> GetEscortsByPatientCardNoAndHappenNo(String cardNo, Integer happenNo);
}

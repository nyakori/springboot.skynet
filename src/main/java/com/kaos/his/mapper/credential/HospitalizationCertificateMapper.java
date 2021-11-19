package com.kaos.his.mapper.credential;

import com.kaos.his.entity.credential.HospitalizationCertificate;

import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationCertificateMapper {
    /**
     * 获取住院证
     * 
     * @param cardNo
     * @param happenNo
     * @return
     */
    HospitalizationCertificate GetHospitalizationCertificate(String cardNo, Integer happenNo);

    /**
     * 获取指定患者的最近的一张住院证
     * 
     * @param cardNo
     * @return
     */
    HospitalizationCertificate GetLatestHospitalizationCertificate(String cardNo);
}

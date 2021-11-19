package com.kaos.his.service.credential;

import com.kaos.his.entity.credential.HospitalizationCertificate;
import com.kaos.his.mapper.credential.HospitalizationCertificateMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalizationCertificateService {
    /**
     * 接口
     */
    @Autowired
    private HospitalizationCertificateMapper hospitalizationCertificateMapper;

    /**
     * 服务
     * 
     * @param cardNo
     * @param happenNo
     * @return
     */
    public HospitalizationCertificate GetHospitalizationCertificateByCardNoAndHappenNo(String cardNo,
            Integer happenNo) {
        return this.hospitalizationCertificateMapper.GetHospitalizationCertificate(cardNo, happenNo);
    }

    /**
     * 获取最近的一张住院证
     * 
     * @param cardNo
     * @return
     */
    public HospitalizationCertificate GetLatestHospitalizationCertificateByCardNo(String cardNo) {
        return this.hospitalizationCertificateMapper.GetLatestHospitalizationCertificate(cardNo);
    }
}

package com.kaos.skynet.api.service.inf.outpatient.fee;

import java.util.List;

/**
 * 划价业务
 */
public interface PricingService {
    /**
     * 门诊非药品划价
     * 
     * @param clinicCode 门诊号
     * @param operCode   划价员
     * @param itemCodes  项目编码列表
     * @param termNo     非药品术语号
     */
    void undrugPriced(String clinicCode, String operCode, List<String> itemCodes, String termNo);
}

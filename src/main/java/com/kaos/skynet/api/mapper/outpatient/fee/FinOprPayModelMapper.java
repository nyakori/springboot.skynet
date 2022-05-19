package com.kaos.skynet.api.mapper.outpatient.fee;

import java.util.List;

import com.kaos.skynet.api.entity.outpatient.fee.FinOprPayModel;

public interface FinOprPayModelMapper {
    /**
     * 根据卡号/住院号查询交易记录
     * 
     * @param cardNo    开始时间；等于 {@code null} 时，将 IS NULL 作为条件
     * @param busNo     参照号；等于 {@code null} 时，不作为条件
     * @param invoiceNo 结算发票号；等于 {@code null} 时，不作为条件
     * @return
     */
    List<FinOprPayModel> queryPayModels(String cardNo, String busNo, String invoiceNo);
}

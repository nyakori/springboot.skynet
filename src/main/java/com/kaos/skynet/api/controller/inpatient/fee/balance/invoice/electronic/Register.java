package com.kaos.skynet.api.controller.inpatient.fee.balance.invoice.electronic;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.impl.AbstractController;
import com.kaos.skynet.api.enums.common.SourceTypeEnum;
import com.kaos.skynet.api.service.inf.inpatient.fee.balance.invoice.ElectronicInvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/ms/inpatient/fee/balance/invoice/electronic")
public class Register extends AbstractController {
    /**
     * 电子发票事务
     */
    @Autowired
    ElectronicInvoiceService electronicInvoiceService;

    /**
     * 查询手术申请记录
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.JSON)
    public RspBody register(@RequestBody @Valid ReqBody req) {
        // 入参记录
        log.info(String.format("开具结算电子发票: %s", this.gson.toJson(req)));

        // 调用开票事务
        this.electronicInvoiceService.register(req.invoiceNo, req.sourceType, req.alipayCode, req.weChatOrderNo,
                req.openId);

        return null;
    }

    private class ReqBody {
        /**
         * HIS系统内的发票号
         */
        @NotNull(message = "HIS发票号不能为空")
        @Getter
        private String invoiceNo = null;

        /**
         * 业务来源
         */
        @NotNull(message = "业务源类型不能为空")
        @Getter
        private SourceTypeEnum sourceType = null;

        /**
         * 支付宝小程序号
         */
        @Getter
        private String alipayCode = null;

        /**
         * 微信号小程序号
         */
        @Getter
        private String weChatOrderNo = null;

        /**
         * 微信交易号
         */
        @Getter
        private String openId = null;
    }

    private class RspBody {

    }
}

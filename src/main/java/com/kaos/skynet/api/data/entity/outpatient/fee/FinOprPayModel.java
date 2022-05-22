package com.kaos.skynet.api.data.entity.outpatient.fee;

import java.util.Date;

import com.kaos.skynet.api.enums.common.PayTypeEnum;
import com.kaos.skynet.api.enums.common.TradeCodeEnum;

import lombok.Data;

/**
 * 支付方式表（XYHIS.FIN_OPR_PAYMODEL）
 */
@Data
public class FinOprPayModel {
    /**
     * 门诊号
     */
    private String clinicCode = null;

    /**
     * 就诊卡号，若为住院患者，则为住院号
     */
    private String cardNo = null;

    /**
     * 患者姓名
     */
    private String name = null;

    /**
     * 操作员编码
     */
    private String operCode = null;

    /**
     * 操作时间
     */
    private Date operDate = null;

    /**
     * 支付类型
     */
    private PayTypeEnum payType = null;

    /**
     * 交易类型
     */
    private TradeCodeEnum tradeCode = null;

    /**
     * 金额
     */
    private Double amt = null;

    /**
     * 银行卡号
     */
    private String tranCardNum = null;

    /**
     * 银行名称
     */
    private String bankNo = null;

    /**
     * 终端号
     */
    private String termId = null;

    /**
     * 交易检索参考号
     */
    private String referNum = null;

    /**
     * 交易终端流水号(用作挂号交易流水号)
     */
    private String traceNum = null;

    /**
     * 交易日期+时间
     */
    private Date trDateTime = null;

    /**
     * 交易商户号 （用作挂号交易支付流水号）
     */
    private String merchantNum = null;

    /**
     * 持卡人姓名
     */
    private String bankName = null;

    /**
     * 批次号
     */
    private String batchNum = null;

    /**
     * 卡有效期
     */
    private Date expireDate = null;

    /**
     * 授权码
     */
    private String authCode = null;

    /**
     * 原交易检索号
     */
    private String orgReferNum = null;

    /**
     * 发票号
     */
    private String ext1 = null;

    /**
     * 跑批回写状态(1:跑批成功;0或空:未跑批)
     */
    private String ext2 = null;

    /**
     * 是否跑批(空或0:不跑批;1:需跑批)
     */
    private String ext3 = null;

    /**
     * 1-正交易 2-负交易
     */
    private String ext4 = null;

    /**
     * 1:现场挂号; 2:预约挂号; 3:门诊收费; -3:门诊退费;4:住院收费;负的为相应退号退费
     */
    private String ext5 = null;

    /**
     * 预约表主键
     */
    private String recId = null;

    /**
     * 流水号
     */
    private String payId = null;

    /**
     * 银行类型:1-工行 2-招行 3-建行
     */
    private String merchanTag = null;

    /**
     * 平台交易ID(源启状态回写用)
     */
    private String tranId = null;

    /**
     * 交易发起方 1：后台跑批 2：窗口 3：自助
     */
    private String placeFlag = null;

    /**
     * 是否已打印，打印为1
     */
    private Boolean printFlag = null;

    /**
     * 银医二期当日撤销标记(1:当日撤销)
     */
    private Boolean cancelDay = null;

    /**
     * 平台结算号
     */
    private String pingTaiJSBH = null;

    /**
     * 平台结算号回写日期
     */
    private Date pingTaiJSBHDate = null;

    /**
     * 医院内收退费编号
     */
    private String yuanNeiPayNo = null;

    /**
     * 医院内收退费时间
     */
    private Date yuanNeiPayTime = null;

    /**
     * 有效标识，空为有效,2作废
     */
    private String flag = null;

    /**
     * 作废时间
     */
    private Date flagDate = null;

    /**
     * 结算操作员
     */
    private String balanceNo = null;
}
package com.kaos.his.controller.impl.cache.common.fee;

import com.kaos.his.controller.inf.cache.ICacheController;
import com.kaos.his.entity.common.fee.FinComFeeCodeStat;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.inf.ICache;
import com.kaos.inf.ICache.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/fee/code")
public class FinComFeeCodeStatCacheController
        implements ICacheController<ReportTypeEnum, ICache<MinFeeEnum, FinComFeeCodeStat>> {
    /**
     * 实体信息服务
     */
    @Autowired
    ICache<ReportTypeEnum, ICache<MinFeeEnum, FinComFeeCodeStat>> feeCodeStatCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<ReportTypeEnum, ?> show() {
        return this.feeCodeStatCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(ReportTypeEnum key) {
        this.feeCodeStatCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.feeCodeStatCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.feeCodeStatCache.invalidateAll();
        return "清空缓存成功";
    }
}

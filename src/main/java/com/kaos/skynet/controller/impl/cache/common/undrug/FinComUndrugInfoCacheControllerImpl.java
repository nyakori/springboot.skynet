package com.kaos.skynet.controller.impl.cache.common.undrug;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.cache.Cache.View;
import com.kaos.skynet.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.common.undrug.FinComUndrugInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/undrug/info")
public class FinComUndrugInfoCacheControllerImpl implements CacheController<String, FinComUndrugInfo> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, FinComUndrugInfo> undrugInfoCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<String, ?> show() {
        return this.undrugInfoCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.undrugInfoCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.undrugInfoCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.undrugInfoCache.invalidateAll();
        return "清空缓存成功";
    }
}

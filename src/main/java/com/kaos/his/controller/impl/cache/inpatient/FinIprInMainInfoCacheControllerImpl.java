package com.kaos.his.controller.impl.cache.inpatient;

import javax.validation.constraints.NotNull;

import com.kaos.his.cache.Cache;
import com.kaos.his.cache.Cache.View;
import com.kaos.his.controller.inf.cache.CacheController;
import com.kaos.his.entity.inpatient.FinIprInMainInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/inpatient/main")
public class FinIprInMainInfoCacheControllerImpl implements CacheController<String, FinIprInMainInfo> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, FinIprInMainInfo> inMainInfoCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<String, ?> show() {
        return this.inMainInfoCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.inMainInfoCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.inMainInfoCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.inMainInfoCache.invalidateAll();
        return "清空缓存成功";
    }
}

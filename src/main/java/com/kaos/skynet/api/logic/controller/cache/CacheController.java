package com.kaos.skynet.api.logic.controller.cache;

import java.util.Map;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@PassToken
@Validated
@RestController
@RequestMapping("/api/cache")
class CacheController {
    @Autowired
    DataCache dataCache;

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("显示缓存状态")
    @RequestMapping(value = "show", method = RequestMethod.POST, produces = MediaType.JSON)
    Map<String, Object> show() {
        return dataCache.showCacheLog();
    }
}

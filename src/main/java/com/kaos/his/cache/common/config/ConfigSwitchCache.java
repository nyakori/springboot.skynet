package com.kaos.his.cache.common.config;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.his.mapper.common.config.ConfigSwitchMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigSwitchCache implements ICache<String, ConfigSwitch> {
    /**
     * 数据库接口
     */
    @Autowired
    ConfigSwitchMapper switchMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ConfigSwitchCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, ConfigSwitch> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, ConfigSwitch>() {
                @Override
                public ConfigSwitch load(String key) throws Exception {
                    return ConfigSwitchCache.this.switchMapper.queryConfigSwitch(key);
                };
            });

    @Override
    public ConfigSwitch getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key);
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public View<String, ConfigSwitch> show() {
        View<String, ConfigSwitch> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

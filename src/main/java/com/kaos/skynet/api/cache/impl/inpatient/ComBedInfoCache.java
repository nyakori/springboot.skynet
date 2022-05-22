package com.kaos.skynet.api.cache.impl.inpatient;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.data.entity.inpatient.ComBedInfo;
import com.kaos.skynet.api.data.mapper.inpatient.ComBedInfoMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 床位编码 -> 床位信息
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class ComBedInfoCache implements Cache<String, ComBedInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    ComBedInfoMapper bedInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ComBedInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<ComBedInfo>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<ComBedInfo>>() {
                @Override
                public Optional<ComBedInfo> load(String key) throws Exception {
                    var ref = ComBedInfoCache.this.bedInfoMapper.queryBedInfo(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public ComBedInfo getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key).orNull();
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View show() {
        View view = new View();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

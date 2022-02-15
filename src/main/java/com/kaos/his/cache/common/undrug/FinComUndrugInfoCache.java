package com.kaos.his.cache.common.undrug;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.undrug.FinComUndrugInfo;
import com.kaos.his.mapper.common.undrug.FinComUndrugInfoMapper;
import com.kaos.inf.ICache;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class FinComUndrugInfoCache implements ICache<FinComUndrugInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinComUndrugInfoMapper undrugInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinComUndrugInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, FinComUndrugInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, FinComUndrugInfo>() {
                @Override
                public FinComUndrugInfo load(String key) throws Exception {
                    return FinComUndrugInfoCache.this.undrugInfoMapper.queryUndrugInfo(key);
                };
            });

    /**
     * 禁止实例化
     * 
     * @throws ExecutorException
     */
    private FinComUndrugInfoCache() throws ExecutorException {
    }

    @Override
    public FinComUndrugInfo getValue(String key) {
        try {
            return this.cache.get(key);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ConcurrentMap<String, FinComUndrugInfo> show() {
        return this.cache.asMap();
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }

    /**
     * 静态内部类
     */
    static class InnerFinComUndrugInfoCache {
        static FinComUndrugInfoCache undrugInfoCache = new FinComUndrugInfoCache();
    }

    /**
     * 获取单例
     * 
     * @return
     */
    public static FinComUndrugInfoCache getCache() {
        return InnerFinComUndrugInfoCache.undrugInfoCache;
    }
}

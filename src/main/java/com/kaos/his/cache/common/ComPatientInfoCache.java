package com.kaos.his.cache.common;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.mapper.common.ComPatientInfoMapper;
import com.kaos.inf.ICache;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 患者基本信息cache
 */
public class ComPatientInfoCache implements ICache<ComPatientInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    ComPatientInfoMapper patientInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ComPatientInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, ComPatientInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, ComPatientInfo>() {
                @Override
                public ComPatientInfo load(String key) throws Exception {
                    return ComPatientInfoCache.this.patientInfoMapper.queryPatientInfo(key);
                };
            });

    /**
     * 禁止实例化
     * 
     * @throws ExecutorException
     */
    private ComPatientInfoCache() throws ExecutorException {
    }

    @Override
    public ComPatientInfo getValue(String key) {
        try {
            return this.cache.get(key);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ConcurrentMap<String, ComPatientInfo> show() {
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
    static class InnerComPatientInfoCache {
        static ComPatientInfoCache comPatientInfoCache = new ComPatientInfoCache();
    }

    /**
     * 获取单例
     * 
     * @return
     */
    public static ComPatientInfoCache getCache() {
        return InnerComPatientInfoCache.comPatientInfoCache;
    }
}

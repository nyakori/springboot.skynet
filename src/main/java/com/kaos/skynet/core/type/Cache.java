package com.kaos.skynet.core.type;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.core.type.converter.Converter;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class Cache<K, V> {
    /**
     * 缓存实体
     */
    LoadingCache<K, Optional<V>> loadingCache;

    /**
     * 后初始化
     * 
     * @param size
     * @param converter
     */
    protected Cache(Integer size, Converter<K, V> converter) {
        // 构造缓存实体
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .refreshAfterWrite(15, TimeUnit.SECONDS)
                .recordStats()
                .build(new CacheLoader<K, Optional<V>>() {
                    @Override
                    public Optional<V> load(K key) throws Exception {
                        return Optional.fromNullable(converter.convert(key));
                    }
                });
    }

    /**
     * 从缓存中获取值
     * 
     * @param key
     * @return
     */
    public V get(K key) {
        // 不支持空索引
        if (key == null) {
            log.warn("缓存不支持空索引");
            return null;
        }

        // 检索缓存
        try {
            return loadingCache.get(key).orNull();
        } catch (Exception e) {
            log.warn(String.format("检索缓存出现异常(%s)", e.getMessage()));
            return null;
        }
    }

    /**
     * 刷新缓存的值
     * 
     * @param key
     */
    public void refresh(K key) {
        // 检索缓存
        loadingCache.refresh(key);
    }

    /**
     * 展示缓存内容
     * 
     * @return
     */
    public CacheStats getStats() {
        return loadingCache.stats();
    }
}

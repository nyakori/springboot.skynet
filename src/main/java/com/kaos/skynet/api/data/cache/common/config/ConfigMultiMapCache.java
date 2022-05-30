package com.kaos.skynet.api.data.cache.common.config;

import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.common.config.ConfigMultiMap;
import com.kaos.skynet.api.data.mapper.common.config.ConfigMultiMapMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;

@Component
public class ConfigMultiMapCache {
    @Autowired
    MasterCache masterCache;

    @Autowired
    SlaveCache slaveCache;

    @Component
    public static class MasterCache extends Cache<MasterCache.Key, ConfigMultiMap> {
        @Autowired
        ConfigMultiMapMapper configMultiMapMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(Key.class, 100, new Converter<Key, ConfigMultiMap>() {
                @Override
                public ConfigMultiMap convert(Key source) {
                    return configMultiMapMapper.queryConfigMultiMap(source.name, source.value);
                }
            });
        }

        @Data
        @Builder
        public static class Key {
            /**
             * 变量名
             */
            private String name;

            /**
             * 变量值
             */
            private String value;
        }
    }

    @Component
    public static class SlaveCache extends Cache<String, List<ConfigMultiMap>> {
        @Autowired
        ConfigMultiMapMapper configMultiMapMapper;

        @Override
        @PostConstruct
        protected void postConstruct() {
            super.postConstruct(String.class, 100, new Converter<String, List<ConfigMultiMap>>() {
                @Override
                public List<ConfigMultiMap> convert(String source) {
                    return configMultiMapMapper.queryConfigMultiMaps(source);
                }
            });
        }
    }
}

package com.kaos.skynet.core.spring.net;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.kaos.skynet.core.json.GsonWrapper;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestTemplateWrapper {
    /**
     * 实际的restTemplate
     */
    final RestTemplate restTemplate = new RestTemplate(Lists.newArrayList(new JsonHttpMessageConverter()));

    /**
     * 计时器
     */
    final Stopwatch timer = Stopwatch.createUnstarted();

    /**
     * 序列化工具
     */
    final GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 对端地址
     */
    final String host;

    /**
     * 对端端口号
     */
    final Integer port;

    /**
     * 构造函数
     */
    public RestTemplateWrapper(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 发送post请求
     * 
     * @param <T>
     * @param url
     * @param reqBody
     * @param classOfT
     * @return
     */
    public <T> T post(String url, Object reqBody, Class<T> classOfT) {
        try {
            // 启动计时器
            timer.reset();
            timer.start();

            // 发送请求
            return restTemplate.postForObject(String.format("http://%s:%d%s", host, port, url), reqBody, classOfT);
        } finally {
            // 停止计时
            timer.stop();

            // 记录日志
            log.info(String.format("网络调用<%s>耗时<%s>", url, timer.toString()));
        }
    }

    /**
     * 发送GET请求
     * 
     * @param <T>
     * @param url
     * @param classOfT
     * @param uriVariables
     * @return
     */
    public <T> T get(String url, Class<T> classOfT, Object... uriVariables) {
        try {
            // 启动计时器
            timer.reset();
            timer.start();

            // 发送请求
            return restTemplate.getForObject(String.format("http://%s:%d%s", host, port, url), classOfT, uriVariables);
        } finally {
            // 停止计时
            timer.stop();

            // 记录日志
            log.info(String.format("网络调用<%s>耗时<%s>", url, timer.toString()));
        }
    }

    /**
     * json适配器
     */
    class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
        @Override
        protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
            return gsonWrapper.fromJson(reader, resolvedType);
        }

        @Override
        protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            gsonWrapper.toJson(object, writer);
        }
    }
}

package com.kaos.skynet.core.util.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kaos.skynet.core.config.spring.exception.ConversionException;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
    /**
     * 字符串格式
     */
    private SimpleDateFormat formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public StringToDateConverter(String format) {
        this.formatter = new SimpleDateFormat(format);
    }

    @Override
    public Date convert(String source) {
        try {
            return formatter.parse(source);
        } catch (Exception e) {
            throw new ConversionException(String.class, Date.class, e.getMessage());
        }
    }
}

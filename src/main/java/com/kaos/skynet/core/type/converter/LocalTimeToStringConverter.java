package com.kaos.skynet.core.type.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

public class LocalTimeToStringConverter implements Converter<LocalTime, String> {
    /**
     * 字符串格式
     */
    private DateTimeFormatter formatter;

    /**
     * 构造函数
     * 
     * @param format
     */
    public LocalTimeToStringConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String convert(LocalTime source) {
        try {
            // 判空
            if (source == null) {
                return null;
            }

            // 格式化
            return source.format(formatter);
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}

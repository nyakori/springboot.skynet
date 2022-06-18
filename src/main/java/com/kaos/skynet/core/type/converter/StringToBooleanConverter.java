package com.kaos.skynet.core.type.converter;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringToBooleanConverter implements Converter<String, Boolean> {
    /**
     * 真值
     */
    private String trueValue;

    /**
     * 假值
     */
    private String falseValue;

    @Override
    public Boolean convert(String source) {
        try {
            if (source == null) {
                return null;
            } else if (source.equals(trueValue)) {
                return true;
            } else if (source.equals(falseValue)) {
                return false;
            } else {
                throw new RuntimeException("映射不存在");
            }
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }
}

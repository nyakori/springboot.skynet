package com.kaos.skynet.core.type.converter.period.string;

import java.time.Period;

import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractPeriodToStringConverter implements Converter<Period, String> {
    /**
     * 是否缩略为0的值
     */
    Boolean briefFlag = null;

    /**
     * 年后缀
     */
    String yearSuffix = null;

    /**
     * 月后缀
     */
    String monthSuffix = null;

    /**
     * 日后缀
     */
    String daySuffix = null;

    /**
     * 简略转换
     * 
     * @param source
     * @return
     */
    private String briefConvert(Period source) {
        // 构造结果串
        String retStr = "";

        // 年
        if (source.getYears() != 0) {
            retStr += String.format("%d%s", source.getYears(), yearSuffix);
        }

        // 月
        if (source.getMonths() != 0) {
            retStr += String.format("%d%s", source.getMonths(), monthSuffix);
        }

        // 日
        if (source.getDays() != 0) {
            retStr += String.format("%d%s", source.getDays(), daySuffix);
        }

        return retStr;
    }

    /**
     * 完整转换
     */
    private String completeConvert(Period source) {
        // 构造结果串
        String retStr = "";

        // 年
        retStr += String.format("%d%s", source.getYears(), yearSuffix);

        // 月
        retStr += String.format("%d%s", source.getMonths(), monthSuffix);

        // 日
        retStr += String.format("%d%s", source.getDays(), daySuffix);

        return retStr;
    }

    @Override
    public String convert(Period source) {
        if (briefFlag) {
            return this.briefConvert(source);
        } else {
            return this.completeConvert(source);
        }
    }
}

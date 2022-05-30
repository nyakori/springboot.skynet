package com.kaos.skynet.core.type.converter.string.enums;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public class ValueStringToEnumConverter<E extends Enum> implements Converter<String, E> {
    /**
     * 记录实际的E的类型
     */
    public Class<E> classOfE;

    @Override
    public E convert(String source) {
        // 判空
        if (source == null) {
            return null;
        }
        // 轮训检查
        for (E e : classOfE.getEnumConstants()) {
            if (e.getValue().equals(source)) {
                return e;
            }
        }
        log.warn(String.format("类型转换 String -> %s : { source = %s } 异常, err = 无有效枚举值", classOfE.getName(), source));
        return null;
    }
}

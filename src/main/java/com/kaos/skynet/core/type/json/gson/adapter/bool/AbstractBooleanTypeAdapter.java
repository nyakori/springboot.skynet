package com.kaos.skynet.core.type.json.gson.adapter.bool;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.bool.string.AbstractBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.AbstractStringToBooleanConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbstractBooleanTypeAdapter extends TypeAdapter<Boolean> {
    /**
     * LocalDate转字符串的转换器
     */
    AbstractBooleanToStringConverter booleanToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    AbstractStringToBooleanConverter stringToBooleanConverter;

    @Override
    public Boolean read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToBooleanConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(booleanToStringConverter.convert(value));
        }
    }
}

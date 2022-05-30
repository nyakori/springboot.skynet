package com.kaos.skynet.core.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.StandardDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToDateConverter;

import org.springframework.stereotype.Component;

@Component("StandardDateTypeAdapter")
public class StandardDateTypeAdapter extends AbstractDateTypeAdapter {
    public StandardDateTypeAdapter() {
        super(new StandardDateToStringConverter(), new StandardStringToDateConverter());
    }
}

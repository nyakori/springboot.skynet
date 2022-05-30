package com.kaos.skynet.core.json.gson.adapter.period;

import com.kaos.skynet.core.type.converter.period.string.AgePeriodToStringConverter;

import org.springframework.stereotype.Component;

@Component("AgePeriodTypeAdapter")
public class AgePeriodTypeAdapter extends AbstractPeriodTypeAdapter {
    public AgePeriodTypeAdapter() {
        super(new AgePeriodToStringConverter());
    }
}

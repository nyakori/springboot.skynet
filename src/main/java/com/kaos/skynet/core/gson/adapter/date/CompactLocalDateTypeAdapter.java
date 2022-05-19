package com.kaos.skynet.core.gson.adapter.date;

import com.kaos.skynet.core.type.converter.local.date.string.CompactLocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.date.CompactStringToLocalDateConverter;

public class CompactLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public CompactLocalDateTypeAdapter() {
        super(new CompactLocalDateToStringConverter(), new CompactStringToLocalDateConverter());
    }
}

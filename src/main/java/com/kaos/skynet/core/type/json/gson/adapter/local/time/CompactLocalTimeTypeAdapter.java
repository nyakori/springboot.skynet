package com.kaos.skynet.core.type.json.gson.adapter.local.time;

import com.kaos.skynet.core.type.converter.local.time.CompactLocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.time.CompactStringToLocalTimeConverter;

public class CompactLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public CompactLocalTimeTypeAdapter() {
        super(new CompactLocalTimeToStringConverter(), new CompactStringToLocalTimeConverter());
    }
}
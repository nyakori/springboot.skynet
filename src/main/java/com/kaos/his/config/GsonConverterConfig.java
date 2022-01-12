package com.kaos.his.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.his.enums.*;

import java.lang.reflect.Type;

public class GsonConverterConfig {
    /**
     * 枚举转换器
     */
    static class GsonEnumTypeAdapter<E extends IEnum> implements JsonSerializer<E>, JsonDeserializer<E> {

        private E[] enums;

        public GsonEnumTypeAdapter(Class<E> typeOfE) {
            this.enums = typeOfE.getEnumConstants();
        }

        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(((IEnum) src).getDescription());
            }
            return null;
        }

        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                var description = json.getAsString();
                for (E e : enums) {
                    if (e.getDescription().equals(description)) {
                        return e;
                    }
                }
            }
            return null;
        }
    }

    public static Gson GetConfiguredGson() {
        var builder = new GsonBuilder();

        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class));
        builder.registerTypeAdapter(DrugItemGradeEnum.class, new GsonEnumTypeAdapter<>(DrugItemGradeEnum.class));
        builder.registerTypeAdapter(DrugShiftTypeEnum.class, new GsonEnumTypeAdapter<>(DrugShiftTypeEnum.class));
        builder.registerTypeAdapter(DrugValidStateEnum.class, new GsonEnumTypeAdapter<>(DrugValidStateEnum.class));
        builder.registerTypeAdapter(EscortActionEnum.class, new GsonEnumTypeAdapter<>(EscortActionEnum.class));
        builder.registerTypeAdapter(EscortStateEnum.class, new GsonEnumTypeAdapter<>(EscortStateEnum.class));
        builder.registerTypeAdapter(InpatientStateEnum.class, new GsonEnumTypeAdapter<>(InpatientStateEnum.class));
        builder.registerTypeAdapter(OutpatientStateEnum.class, new GsonEnumTypeAdapter<>(OutpatientStateEnum.class));
        builder.registerTypeAdapter(FinIprPrepayInStateEnum.class, new GsonEnumTypeAdapter<>(FinIprPrepayInStateEnum.class));
        builder.registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class));
        builder.registerTypeAdapter(TransTypeEnum.class, new GsonEnumTypeAdapter<>(TransTypeEnum.class));
        builder.registerTypeAdapter(BalancePayTransKindEnum.class,
                new GsonEnumTypeAdapter<>(BalancePayTransKindEnum.class));
        builder.registerTypeAdapter(PayWayEnum.class, new GsonEnumTypeAdapter<>(PayWayEnum.class));
        builder.registerTypeAdapter(SurgeryKindEnum.class, new GsonEnumTypeAdapter<>(SurgeryKindEnum.class));
        builder.registerTypeAdapter(AnesTypeEnum.class, new GsonEnumTypeAdapter<>(AnesTypeEnum.class));
        builder.registerTypeAdapter(SurgeryDegreeEnum.class, new GsonEnumTypeAdapter<>(SurgeryDegreeEnum.class));
        builder.registerTypeAdapter(SurgeryStatusEnum.class, new GsonEnumTypeAdapter<>(SurgeryStatusEnum.class));
        builder.registerTypeAdapter(DeptStateEnum.class, new GsonEnumTypeAdapter<>(DeptStateEnum.class));
        builder.registerTypeAdapter(PositionEnum.class, new GsonEnumTypeAdapter<>(PositionEnum.class));
        builder.registerTypeAdapter(RankEnum.class, new GsonEnumTypeAdapter<>(RankEnum.class));
        builder.registerTypeAdapter(BedStateEnum.class, new GsonEnumTypeAdapter<>(BedStateEnum.class));
        builder.registerTypeAdapter(InpatientSourceEnum.class, new GsonEnumTypeAdapter<>(InpatientSourceEnum.class));
        builder.registerTypeAdapter(ValidStateEnum.class, new GsonEnumTypeAdapter<>(ValidStateEnum.class));
        builder.registerTypeAdapter(MetOpsInciTypeEnum.class, new GsonEnumTypeAdapter<>(MetOpsInciTypeEnum.class));
        builder.registerTypeAdapter(SurgeryInspectResultEnum.class,
                new GsonEnumTypeAdapter<>(SurgeryInspectResultEnum.class));
        builder.registerTypeAdapter(SurgeryArrangeRoleEnum.class,
                new GsonEnumTypeAdapter<>(SurgeryArrangeRoleEnum.class));

        return builder.create();
    }
}

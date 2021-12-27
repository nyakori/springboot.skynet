package com.kaos.his.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kaos.his.enums.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import org.springframework.core.convert.converter.Converter;

@Configuration
public class SpringParamConvertConfig extends WebMvcConfigurationSupport {

    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f = super.mvcConversionService();

        // 注册Date转换器
        f.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                // 创建格式化工具
                var fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // 格式化
                try {
                    return fmt.parse(source);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("格式化Date参数失败(%s)", e.getMessage()));
                }
            }
        });

        // 注册DeptOwnEnum转换器
        f.addConverter(new Converter<String, DeptOwnEnum>() {
            @Override
            public DeptOwnEnum convert(String source) {
                for (DeptOwnEnum e : DeptOwnEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册DrugItemGradeEnum转换器
        f.addConverter(new Converter<String, DrugItemGradeEnum>() {
            @Override
            public DrugItemGradeEnum convert(String source) {
                for (DrugItemGradeEnum e : DrugItemGradeEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册DrugShiftTypeEnum转换器
        f.addConverter(new Converter<String, DrugShiftTypeEnum>() {
            @Override
            public DrugShiftTypeEnum convert(String source) {
                for (DrugShiftTypeEnum e : DrugShiftTypeEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册DrugValidStateEnum转换器
        f.addConverter(new Converter<String, DrugValidStateEnum>() {
            @Override
            public DrugValidStateEnum convert(String source) {
                for (DrugValidStateEnum e : DrugValidStateEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册EscortActionEnum转换器
        f.addConverter(new Converter<String, EscortActionEnum>() {
            @Override
            public EscortActionEnum convert(String source) {
                for (EscortActionEnum e : EscortActionEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册EscortStateEnum转换器
        f.addConverter(new Converter<String, EscortStateEnum>() {
            @Override
            public EscortStateEnum convert(String source) {
                for (EscortStateEnum e : EscortStateEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册InpatientStateEnum转换器
        f.addConverter(new Converter<String, InpatientStateEnum>() {
            @Override
            public InpatientStateEnum convert(String source) {
                for (InpatientStateEnum e : InpatientStateEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册OutpatientStateEnum转换器
        f.addConverter(new Converter<String, OutpatientStateEnum>() {
            @Override
            public OutpatientStateEnum convert(String source) {
                for (OutpatientStateEnum e : OutpatientStateEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册PreinCardStateEnum转换器
        f.addConverter(new Converter<String, PreinCardStateEnum>() {
            @Override
            public PreinCardStateEnum convert(String source) {
                for (PreinCardStateEnum e : PreinCardStateEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册SexEnum转换器
        f.addConverter(new Converter<String, SexEnum>() {
            @Override
            public SexEnum convert(String source) {
                for (SexEnum e : SexEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        // 注册TransTypeEnum转换器
        f.addConverter(new Converter<String, TransTypeEnum>() {
            @Override
            public TransTypeEnum convert(String source) {
                for (TransTypeEnum e : TransTypeEnum.class.getEnumConstants()) {
                    if (e.getDescription().equals(source)) {
                        return e;
                    }
                }
                throw new IllegalArgumentException();
            }
        });

        return f;
    }

}
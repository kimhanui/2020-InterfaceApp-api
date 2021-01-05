package com.infe.app.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * DB에 boolean이 1,0으로 들어가므로 Y,N으로 바꾸기 위한 컨버터
 **/
@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    public Boolean convertToEntityAttribute(String s) {
        return "Y".equals(s);
    }
}
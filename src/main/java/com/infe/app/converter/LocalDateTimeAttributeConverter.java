package com.infe.app.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//@Converter를 사용하면 엔티티의 데이터를 변환해서 데이터베이스에 저장할 수 있다.
//     -AttributeConverter의 제네릭을 설정하고 아래 두 메소드를 오버라디으 해주면 된다.
//     -autoApply=true : 글로벌하게 설정. 특정 속성에 이를 갖다쓴다는 @Convert(converter = ..._)를
//                       등록 안해도 된다.
/**LocalDateTime과 Timestamp**/
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
}
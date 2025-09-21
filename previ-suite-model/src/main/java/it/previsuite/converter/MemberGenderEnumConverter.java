package it.previsuite.converter;

import it.previsuite.bean.enums.MemberGenderEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MemberGenderEnumConverter implements AttributeConverter<MemberGenderEnum, String> {

    @Override
    public String convertToDatabaseColumn(MemberGenderEnum attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public MemberGenderEnum convertToEntityAttribute(String dbData) {
        return MemberGenderEnum.fromValue(dbData);
    }
}
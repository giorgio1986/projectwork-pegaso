package it.previsuite.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MemberBeneficiaryTypeEnum {
    NATURAL_ENTITY("NATURAL_ENTITY"),
    LEGAL_ENTITY("LEGAL_ENTITY");

    private final String value;

    MemberBeneficiaryTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MemberBeneficiaryTypeEnum fromValue(String value) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static MemberBeneficiaryTypeEnum fromName(String name) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
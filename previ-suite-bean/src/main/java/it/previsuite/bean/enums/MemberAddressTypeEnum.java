package it.previsuite.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MemberAddressTypeEnum {
    RESIDENTIAL_ADDRESS("RESIDENTIAL_ADDRESS"),
    POSTAL_ADDRESS("POSTAL_ADDRESS"),
    TAX_DOMICILE("TAX_DOMICILE");

    private final String value;

    MemberAddressTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MemberAddressTypeEnum fromValue(String value) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static MemberAddressTypeEnum fromName(String name) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
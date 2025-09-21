package it.previsuite.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum MemberGenderEnum {
    MALE("M"),
    FEMALE("F"),
    NOT_SPECIFIED("N");

    private final String value;

    MemberGenderEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MemberGenderEnum fromValue(String value) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static MemberGenderEnum fromName(String name) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
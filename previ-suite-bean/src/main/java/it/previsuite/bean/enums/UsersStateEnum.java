package it.previsuite.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum UsersStateEnum {
    ACTIVE("ACTIVE"),
    TO_ACTIVATE("TO_ACTIVATE"),
    SUSPENDED("SUSPENDED"),
    TERMINATED("TERMINATED");

    private final String value;

    UsersStateEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static UsersStateEnum fromValue(String value) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static UsersStateEnum fromName(String name) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
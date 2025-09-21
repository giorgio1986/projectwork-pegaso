package it.previsuite.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum QuestionnaireAnswersTypeEnum {
    TEXT("TEXT"),
    MULTIPLE_CHOICE("MULTIPLE_CHOICE");

    private final String value;

    QuestionnaireAnswersTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static QuestionnaireAnswersTypeEnum fromValue(String value) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static QuestionnaireAnswersTypeEnum fromName(String name) {
        return Arrays
                .stream(values())
                .filter(entity -> entity.name().equals(name))
                .findFirst()
                .orElse(null);
    }
}
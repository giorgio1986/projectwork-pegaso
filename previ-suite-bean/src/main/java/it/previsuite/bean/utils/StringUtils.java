package it.previsuite.bean.utils;

public class StringUtils {

    private StringUtils() {}

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}

package it.previsuite.bean.utils;

import java.security.SecureRandom;

public class RandomStringUtils {
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomStringUtils() {

    }

    public static String random(int length, boolean letters, boolean numbers) {
        if(! letters && !numbers) {
            throw new IllegalArgumentException("At least one of 'letters' or 'numbers' must be true");
        }

        StringBuilder characterPool = new StringBuilder();

        if (letters) {
            characterPool.append(LETTERS);
        }

        if (numbers) {
            characterPool.append(NUMBERS);
        }

        String pool = characterPool.toString();
        StringBuilder result = new StringBuilder(length);

        for(int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(pool.length());
            result.append(pool.charAt(index));
        }

        return result.toString();
    }
}
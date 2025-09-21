package it.previsuite.bean.commons;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

public class Headers {
    private Headers() {

    }

    private static final String UTF8 = ";charset=UTF-8";
    public static final String X_API_KEY = "x-api-key";
    public static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;

    public static final String JSON_UTF8 = MediaType.APPLICATION_JSON + UTF8;
    public static final String TEXT_PLAIN_UTF8 = MediaType.TEXT_PLAIN + UTF8;
    public static final String MULTIPART_FORM_DATA_UTF8 = MediaType.MULTIPART_FORM_DATA + UTF8;
}
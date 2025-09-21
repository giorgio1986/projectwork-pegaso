package it.previsuite.bean.utils;

import it.previsuite.bean.exceptions.PreviException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static final String STANDARD_DATE_TIME = "dd-MM-yyyy HH:mm:ss";
    public static final String BASIC_ISO_DATE = "yyyyMMdd";
    public static final String ISO_LOCAL_DATE = "dd-MM-yyyy";
    public static final String ISO_LOCAL_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ISO_ZONED_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String EUROPEAN_LOCAL_DATE = "dd/MM/yyyy";
    public static final String EUROPEAN_LOCAL_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
    private static final String TIMEZONE = "Europe/Rome";

    private DateUtils() {

    }

    public static OffsetDateTime convert(Date date) {
        return date.toInstant().atZone(TimeZone.getTimeZone(TIMEZONE).toZoneId()).toOffsetDateTime();
    }
    public static Date convert(OffsetDateTime offsetDateTime) {
        return Date.from(offsetDateTime.toInstant());
    }
    public static Date convert(String date, String format) throws PreviException {
        if (StringUtils.isNullOrEmpty(date)) {
            return null;
        }

        try {
            return new SimpleDateFormat(format).parse(date);
        }
        catch (ParseException e) {
            throw new PreviException(String.format("Error the date %s not in format %s", date, format), e);
        }
    }

    public static String convert(OffsetDateTime offsetDateTime, String format) {
        Date date = convert(offsetDateTime);
        return convert(date, format);
    }
    public static String convert(LocalDateTime dateTime, String format) {
        Date date = Date.from(dateTime.atZone(TimeZone.getTimeZone(TIMEZONE).toZoneId()).toInstant());
        return convert(date, format);
    }
    public static String convert(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
    public static String convert(long timestamp, String format) {
        return new SimpleDateFormat(format).format(new Date(timestamp));
    }

    public static OffsetDateTime now() {
        return OffsetDateTime.ofInstant(OffsetDateTime.now().toInstant(), TimeZone.getTimeZone(TIMEZONE).toZoneId());
    }

    public static OffsetDateTime convertEpochTimestampToOffsetDateTime(Long timestamp) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getTimeZone(TIMEZONE).toZoneId());
    }

    public static Long convertOffsetDateTimeToEpochTimestamp(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant().getEpochSecond() * 1000;
    }
}
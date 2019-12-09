package com.company.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.HOURS;

public class DateTimeUtils {

    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String GIT_REQUEST_DATE_PATTERN_1 = "yyyy-MM-dd-H";
    public static final String GIT_REQUEST_DATE_PATTERN_2 = "yyyy-MM-dd-HH";
    public static final long ONE_HOUR = 1000 * 60 * 60;

    public static LocalDate toDate(String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UTC_PATTERN);
        LocalDate localDate = LocalDate.parse(dateStr, dtf);
        return localDate;
    }

    public static LocalDateTime parseDateTime(String dateStr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(UTC_PATTERN);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtf);
        return localDateTime;
    }

    public static List<String> exportDateBetweenTwoDates(String from, String to) {
        long fromDateMillis = toMillis(from, HOURS);
        long toDateMillis = toMillis(to, HOURS);
        List<Long> millsToExport = new ArrayList<>();
        for (long i = fromDateMillis; i <= toDateMillis; i += ONE_HOUR) {
            millsToExport.add(i);
        }
        List<String> datesToExport = millsToExport.
                stream()
                .map(millis -> formatDateTime(new Timestamp(millis).toLocalDateTime()))
                .collect(Collectors.toList());
        return datesToExport;
    }

    private static long toMillis(String dateStr, ChronoUnit toTruncate) {
        ZoneId id = ZoneId.systemDefault();
        LocalDateTime dateTime =
                toTruncate != null ? parseDateTime(dateStr).truncatedTo(toTruncate) : parseDateTime(dateStr);
        return ZonedDateTime.of(dateTime, id).toInstant().toEpochMilli();
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(GIT_REQUEST_DATE_PATTERN_1);
        if (localDateTime.getHour() > 12) {
            dtf = DateTimeFormatter.ofPattern(GIT_REQUEST_DATE_PATTERN_2);
        }

        return localDateTime.format(dtf);
    }

    public static long timeRange(String createdAt, String mergedAt) {
        long createAtMillis = toMillis(createdAt, null);
        long closedAtMillis = toMillis(mergedAt, null);
        return closedAtMillis - createAtMillis;
    }
}

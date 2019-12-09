package com.company.utils;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ValidationUtils {
    public static void assertValidDate(String from) {
        try {
            DateTimeUtils.parseDateTime(from);
        } catch (DateTimeParseException t) {
            throw new DateTimeParseException("Invalid Date Format. Please follow the UTC pattern", from, t.getErrorIndex());
        }
    }

    public static void assertFromDateLessThanToDate(String from, String to) throws OperationNotSupportedException {
        LocalDateTime fromDate = DateTimeUtils.parseDateTime(from);
        LocalDateTime toDate = DateTimeUtils.parseDateTime(to);
        if (fromDate.isAfter(toDate)) {
            throw new OperationNotSupportedException("From date must be less than or equal to date");
        }
    }
}

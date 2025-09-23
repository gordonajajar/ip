package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_ONLY = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter PRETTY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter PRETTY_DATETIME = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Parse dd/mm/yyyy or dd/mm/yyyy HHmm.
     * If only date is given, time defaults to midnight.
     */
    public static LocalDateTime parseString(String input) {
        String s = input.trim();
        try {
            return LocalDateTime.parse(s, SAVE_FORMAT);
        } catch (DateTimeParseException ignored) {
            // Fallthrough
        }

        try {
            return LocalDateTime.parse(s, DATE_TIME);
        } catch (DateTimeParseException e) {
            LocalDate date = LocalDate.parse(s, DATE_ONLY);
            return LocalDateTime.of(date, LocalTime.MIDNIGHT);
        }
    }

    public static String formatForSave(LocalDateTime dateTime) {
        return dateTime.format(SAVE_FORMAT);
    }

    public static String prettyPrint(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.toLocalDate().format(PRETTY_DATE);
        } else {
            return dateTime.format(PRETTY_DATETIME);
        }
    }
}

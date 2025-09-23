package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing, formatting, and pretty-printing dates and date-times.
 * Supports multiple input formats for parsing, ISO format for saving, and
 * human-readable formats for display.
 */
public class DateTimeUtil {
    private static final DateTimeFormatter DATE_ONLY = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter PRETTY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter PRETTY_DATETIME = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Parses a string into a LocalDateTime.
     * Accepts either a date-only string in dd/MM/yyyy format or
     * a date-time string in dd/MM/yyyy HHmm format.
     * If only a date is given, the time defaults to midnight.
     *
     * @param input String representing the date or date-time.
     * @return Parsed LocalDateTime.
     * @throws DateTimeParseException If the input cannot be parsed in any recognized format.
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

    /**
     * Formats a LocalDateTime into a string suitable for saving.
     * Uses ISO-8601 local date-time format.
     *
     * @param dateTime The date-time to format.
     * @return ISO-formatted string representing the date-time.
     */
    public static String formatForSave(LocalDateTime dateTime) {
        return dateTime.format(SAVE_FORMAT);
    }

    /**
     * Returns a human-readable string representation of the given date-time.
     * If the time is midnight, only the date is shown. Otherwise, both date and
     * time are included in a readable format (e.g., "Mar 15 2025, 2:30 PM").
     *
     * @param dateTime The date-time to format.
     * @return Pretty-printed string for display.
     */
    public static String prettyPrint(LocalDateTime dateTime) {
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.toLocalDate().format(PRETTY_DATE);
        } else {
            return dateTime.format(PRETTY_DATETIME);
        }
    }
}

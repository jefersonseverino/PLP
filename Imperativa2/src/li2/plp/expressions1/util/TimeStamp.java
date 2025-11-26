package li2.plp.expressions1.util;

import java.time.ZonedDateTime;
import java.time.ZoneId;

public class TimeStamp {

    public Integer year;
    public Integer month;
    public Integer day;
    public Integer hour;
    public Integer minute;
    public Integer second;

    public String tz_str;
    public String tz_signal;
    public Integer tz_hour;
    public Integer tz_minute;

    public Integer[] daysPerMonth = { 0, 
        31, // jan
        28, // fev
        31, // mar
        30, // abr
        31, // mai
        30, // jun
        31, // jul
        31, // ago
        30, // set
        31, // out
        30, // nov
        31  // dez
    };

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        validateTimestamp(year, month, day, hour, minute, second);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_str = null; // Sem timezone definido - hora local
    }

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, String tz_str) {
        validateTimestamp(year, month, day, hour, minute, second);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        
        String cleanTz = tz_str.replaceAll("\"", "");
        this.tz_str = cleanTz;
    }
 
    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second,
            String tz_signal, Integer tz_hour, Integer tz_minute) {
        validateTimestamp(year, month, day, hour, minute, second);
        validateTimezoneOffset(tz_hour, tz_minute);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_signal = tz_signal;
        this.tz_hour = tz_hour;
        this.tz_minute = tz_minute;
        
        String sign = tz_signal.equals("+") ? "+" : "-";
        this.tz_str = String.format("%s%02d:%02d", sign, tz_hour, tz_minute);
    }

    // Validation methods
    private static void validateTimestamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        if (!isValidYear(year)) {
            throw new IllegalArgumentException("Invalid year: " + year + ". Must be greater than or equal to 1.");
        }
        if (!isValidMonth(month)) {
            throw new IllegalArgumentException("Invalid month: " + month + ". Must be between 1 and 12.");
        }
        if (!isValidDay(day, month, year)) {
            throw new IllegalArgumentException("Invalid day: " + day + ". Must be between 1 and " + getDaysInMonth(month, year) + " for month " + month + ".");
        }
        if (!isValidHour(hour)) {
            throw new IllegalArgumentException("Invalid hour: " + hour + ". Must be between 0 and 23.");
        }
        if (!isValidMinute(minute)) {
            throw new IllegalArgumentException("Invalid minute: " + minute + ". Must be between 0 and 59.");
        }
        if (!isValidSecond(second)) {
            throw new IllegalArgumentException("Invalid second: " + second + ". Must be between 0 and 59.");
        }
    }

    private static void validateTimezoneOffset(Integer tzHour, Integer tzMinute) {
        if (!isValidTimezoneHour(tzHour)) {
            throw new IllegalArgumentException("Invalid timezone offset hour: " + tzHour + ". Must be between -12 and +14.");
        }
        if (!isValidTimezoneMinute(tzMinute)) {
            throw new IllegalArgumentException("Invalid timezone offset minute: " + tzMinute + ". Must be between 0 and 59.");
        }
    }

    private static boolean isValidYear(Integer year) {
        return year != null && year >= 1;
    }

    private static boolean isValidMonth(Integer month) {
        return month != null && month >= 1 && month <= 12;
    }

    private static boolean isValidDay(Integer day, Integer month, Integer year) {
        if (day == null || day < 1) {
            return false;
        }
        int maxDay = getDaysInMonth(month, year);
        return day <= maxDay;
    }

    private static int getDaysInMonth(Integer month, Integer year) {
        Integer[] daysPerMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month == 2 && isLeapYear(year)) {
            return 29;
        }
        return daysPerMonth[month];
    }

    private static boolean isLeapYear(Integer year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    private static boolean isValidHour(Integer hour) {
        return hour != null && hour >= 0 && hour <= 23;
    }

    private static boolean isValidMinute(Integer minute) {
        return minute != null && minute >= 0 && minute <= 59;
    }

    private static boolean isValidSecond(Integer second) {
        return second != null && second >= 0 && second <= 59;
    }

    private static boolean isValidTimezoneHour(Integer tzHour) {
        return tzHour != null && tzHour >= -12 && tzHour <= 14;
    }

    private static boolean isValidTimezoneMinute(Integer tzMinute) {
        return tzMinute != null && tzMinute >= 0 && tzMinute <= 59;
    }

    public boolean hasTimezone() {
        return tz_str != null;
    }

    public ZoneId getZoneId() {
        if (tz_str != null) {
            return TimezoneRegistry.toZoneId(tz_str);
        }
        if (tz_signal != null && tz_hour != null && tz_minute != null) {
            String offsetStr = String.format("%s%02d:%02d", 
                tz_signal.equals("+") ? "+" : "-", tz_hour, tz_minute);
            return ZoneId.of(offsetStr);
        }
        return null; // Sem timezone definido
    }

    public ZonedDateTime toZonedDateTime() {
        ZoneId zone = getZoneId();
        if (zone == null) {
            // Timestamp sem timezone - não pode converter para ZonedDateTime
            throw new RuntimeException("Cannot convert timestamp without timezone to ZonedDateTime");
        }
        return ZonedDateTime.of(year, month, day, hour, minute, second, 0, zone);
    }

    public TimeStamp convertToTimezone(String targetTimezone) {
        if (!TimezoneRegistry.isTimezoneSupported(targetTimezone)) {
            throw new RuntimeException("Unsupported timezone: " + targetTimezone);
        }

        // Se o timestamp não tem timezone, apenas atribui o timezone sem mudar a hora
        if (!hasTimezone()) {
            return new TimeStamp(year, month, day, hour, minute, second, targetTimezone);
        }

        // Se tem timezone, converte mantendo o mesmo instante
        ZoneId targetZone = TimezoneRegistry.toZoneId(targetTimezone);
        ZonedDateTime current = toZonedDateTime();
        ZonedDateTime converted = current.withZoneSameInstant(targetZone);

        return new TimeStamp(
            converted.getYear(),
            converted.getMonthValue(),
            converted.getDayOfMonth(),
            converted.getHour(),
            converted.getMinute(),
            converted.getSecond(),
            targetTimezone
        );
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
            year, month, day, hour, minute, second
        );
    }

}

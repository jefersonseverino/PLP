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
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_str = "UTC";
    }

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, String tz_str) {
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

    public ZoneId getZoneId() {
        if (tz_str != null) {
            return TimezoneRegistry.toZoneId(tz_str);
        }
        if (tz_signal != null && tz_hour != null && tz_minute != null) {
            String offsetStr = String.format("%s%02d:%02d", 
                tz_signal.equals("+") ? "+" : "-", tz_hour, tz_minute);
            return ZoneId.of(offsetStr);
        }
        return ZoneId.of("UTC");
    }

    public ZonedDateTime toZonedDateTime() {
        ZoneId zone = getZoneId();
        return ZonedDateTime.of(year, month, day, hour, minute, second, 0, zone);
    }

    public TimeStamp convertToTimezone(String targetTimezone) {
        if (!TimezoneRegistry.isTimezoneSupported(targetTimezone)) {
            throw new RuntimeException("Unsupported timezone: " + targetTimezone);
        }

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

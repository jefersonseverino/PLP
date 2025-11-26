package li2.plp.expressions1.util;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class TimezoneRegistry {

    private static final Map<String, ZoneId> TIMEZONE_MAP = new HashMap<>();

    static {
        TIMEZONE_MAP.put("UTC", ZoneId.of("UTC"));
        TIMEZONE_MAP.put("GMT", ZoneId.of("GMT"));
        
        TIMEZONE_MAP.put("America/New_York", ZoneId.of("America/New_York"));
        TIMEZONE_MAP.put("America/Chicago", ZoneId.of("America/Chicago"));
        TIMEZONE_MAP.put("America/Denver", ZoneId.of("America/Denver"));
        TIMEZONE_MAP.put("America/Los_Angeles", ZoneId.of("America/Los_Angeles"));
        TIMEZONE_MAP.put("America/Sao_Paulo", ZoneId.of("America/Sao_Paulo"));
        TIMEZONE_MAP.put("America/Argentina/Buenos_Aires", ZoneId.of("America/Argentina/Buenos_Aires"));
        
        TIMEZONE_MAP.put("Europe/London", ZoneId.of("Europe/London"));
        TIMEZONE_MAP.put("Europe/Paris", ZoneId.of("Europe/Paris"));
        TIMEZONE_MAP.put("Europe/Berlin", ZoneId.of("Europe/Berlin"));
        TIMEZONE_MAP.put("Europe/Madrid", ZoneId.of("Europe/Madrid"));
        TIMEZONE_MAP.put("Europe/Rome", ZoneId.of("Europe/Rome"));
        TIMEZONE_MAP.put("Europe/Amsterdam", ZoneId.of("Europe/Amsterdam"));
        TIMEZONE_MAP.put("Europe/Istanbul", ZoneId.of("Europe/Istanbul"));
        
        TIMEZONE_MAP.put("Asia/Tokyo", ZoneId.of("Asia/Tokyo"));
        TIMEZONE_MAP.put("Asia/Shanghai", ZoneId.of("Asia/Shanghai"));
        TIMEZONE_MAP.put("Asia/Hong_Kong", ZoneId.of("Asia/Hong_Kong"));
        TIMEZONE_MAP.put("Asia/Singapore", ZoneId.of("Asia/Singapore"));
        TIMEZONE_MAP.put("Asia/Dubai", ZoneId.of("Asia/Dubai"));
        TIMEZONE_MAP.put("Asia/Kolkata", ZoneId.of("Asia/Kolkata"));
        TIMEZONE_MAP.put("Asia/Bangkok", ZoneId.of("Asia/Bangkok"));
        
        TIMEZONE_MAP.put("Australia/Sydney", ZoneId.of("Australia/Sydney"));
        TIMEZONE_MAP.put("Australia/Melbourne", ZoneId.of("Australia/Melbourne"));
        TIMEZONE_MAP.put("Australia/Brisbane", ZoneId.of("Australia/Brisbane"));
    }

    /**
     * Normaliza o formato do timezone.
     * Converte formatos como "UTC+09:00" ou "UTC-03:00" para "+09:00" ou "-03:00"
     */
    private static String normalizeTimezone(String timezone) {
        if (timezone == null) {
            return null;
        }
        // Converte UTC+HH:MM ou UTC-HH:MM para +HH:MM ou -HH:MM
        if (timezone.matches("UTC[+-]\\d{2}:\\d{2}")) {
            return timezone.substring(3); 
        }
        // Converte GMT+HH:MM ou GMT-HH:MM para +HH:MM ou -HH:MM
        if (timezone.matches("GMT[+-]\\d{2}:\\d{2}")) {
            return timezone.substring(3); 
        }
        return timezone;
    }

    public static boolean isTimezoneSupported(String timezone) {
        if (timezone == null) {
            return false;
        }
        
        String normalized = normalizeTimezone(timezone);
        
        if (TIMEZONE_MAP.containsKey(normalized)) {
            return true;
        }
        
        if (normalized.matches("[+-]\\d{2}:\\d{2}")) {
            return true;
        }
        
        return false;
    }

    public static ZoneId toZoneId(String timezone) {
        if (timezone == null) {
            throw new RuntimeException("Timezone cannot be null");
        }
        
        String normalized = normalizeTimezone(timezone);
        
        if (TIMEZONE_MAP.containsKey(normalized)) {
            return TIMEZONE_MAP.get(normalized);
        }
        
        if (normalized.matches("[+-]\\d{2}:\\d{2}")) {
            return ZoneId.of(normalized);
        }
        
        throw new RuntimeException("Unsupported timezone: " + timezone);
    }
}

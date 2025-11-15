package li2.plp.expressions1.util;

import java.security.Timestamp;
import java.util.Optional;

public class TimeStamp {

    /* Date area */
    public Integer year;
    public Integer month;
    public Integer day;
    public Integer hour;
    public Integer minute;
    public Integer second;

    /* Timezone area */
    public String tz_str;
    public String tz_signal;
    public Integer tz_hour;
    public Integer tz_minute;

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        System.out.println("CAllig constructor number one");

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, String tz_str) {
        System.out.println("CAllig constructor number two");
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_str = tz_str;
    }
 
    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second,
            String tz_signal, Integer tz_hour, Integer tz_minute) {
        System.out.println("CAllig constructor number three");
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_signal = tz_signal;
        this.tz_hour = tz_hour;
        this.tz_minute = tz_minute;
    }
}

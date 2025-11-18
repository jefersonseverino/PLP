package li2.plp.expressions1.util;

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

    public TimeStamp(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second,
            Optional<String> tz_str, Optional<String> tz_signal, Optional<Integer> tz_hour,
            Optional<Integer> tz_minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.tz_str = tz_str.orElse(null);
        this.tz_signal = tz_signal.orElse(null);
        this.tz_hour = tz_hour.orElse(null);
        this.tz_minute = tz_minute.orElse(null);
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d %02d:%02d:%02d",
            year, month, day, hour, minute, second
        );
    }

}

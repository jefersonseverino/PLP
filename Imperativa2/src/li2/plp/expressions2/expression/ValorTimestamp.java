package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimeStamp;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;

public class ValorTimestamp extends ValorConcreto<TimeStamp> {

    public ValorTimestamp(TimeStamp valor) {
        super(valor);
    }

    public Tipo getTipo(li2.plp.expressions2.memory.AmbienteCompilacao amb) {
        return TipoPrimitivo.TIMESTAMP;
    }

    public String toString() {
        String dateTime = String.format("@%04d-%02d-%02d@%02d:%02d:%02d",
            valor().year, valor().month, valor().day,
            valor().hour, valor().minute, valor().second);
        
        if (valor().tz_str != null) {
            return dateTime + " \"" + valor().tz_str + "\"";
        }
        if (valor().tz_signal != null && valor().tz_hour != null && valor().tz_minute != null) {
            return dateTime + " " + valor().tz_signal + String.format("%02d:%02d", valor().tz_hour, valor().tz_minute);
        }
        
        return dateTime;
    }

    public String acessProperty(String prop) {
        switch (prop) {
            case "year":
                return valor().year.toString();
            case "month":
                return valor().month.toString();
            case "day":
                return valor().day.toString();
            case "hour":
                return valor().hour.toString();
            case "minute":
                return valor().minute.toString();
            case "second":
                return valor().second.toString();
            case "tz":
                if (valor().tz_str != null) {
                    return valor().tz_str;
                }
                if (valor().tz_signal != null && valor().tz_hour != null && valor().tz_minute != null) {
                    return valor().tz_signal + String.format("%02d:%02d", valor().tz_hour, valor().tz_minute);
                }
                return ""; 
            default:
                throw new RuntimeException("Invalid property for timestamp: " + prop);
        }
    }

    public Integer getYear() {
        return valor().year;
    }

    public Integer getMonth() {
        return valor().month;
    }

    public Integer getDay() {
        return valor().day;
    }

    public Integer getHour() {
        return valor().hour;
    }

    public Integer getMinute() {
        return valor().minute;
    }

    public Integer getSecond() {
        return valor().second;
    }

    public boolean isLeapYear(Integer year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    public Integer[] getDaysPerMonth() {
        return valor().daysPerMonth;
    }

    public Integer totalSeconds() {
        Integer total = 0;
        total += this.getSecond();
        total += this.getMinute() * 60;
        total += this.getHour() * 3600;
        total += this.getDay() * 86400;
        total += this.getMonth() * 2592000;
        total += this.getYear() * 31536000;
        return total;
    }

    public ValorTimestamp convertToTimezone(String targetTimezone) {
        TimeStamp converted = valor().convertToTimezone(targetTimezone);
        return new ValorTimestamp(converted);
    }

    public ValorTimestamp clone() {
        return new ValorTimestamp(this.valor());
    }

    public boolean isLess(ValorTimestamp other) {
        return this.totalSeconds() < other.totalSeconds();
    }

    public boolean isEqual(ValorTimestamp other) {
        return this.totalSeconds().equals(other.totalSeconds());
    }

}

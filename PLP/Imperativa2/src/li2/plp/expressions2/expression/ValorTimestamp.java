package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimeStamp;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import java.util.Optional;

public class ValorTimestamp extends ValorConcreto<TimeStamp> {

    /**
     * Cria um objeto encapsulando o TimeStamp fornecido.
     */
    public ValorTimestamp(TimeStamp valor) {
        super(valor);
    }

    public ValorTimestamp(int year, int month, int day, int hour, int minute, int second) {
        super(new TimeStamp(year, month, day, hour, minute, second,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
    }

    public ValorTimestamp(int year, int month, int day, int hour, int minute, int second,
            String timezone) {
        super(new TimeStamp(year, month, day, hour, minute, second, Optional.ofNullable(timezone), Optional.empty(),
                Optional.empty(),
                Optional.empty()));
    }

    public ValorTimestamp(int year, int month, int day, int hour, int minute, int second,
            String tzSignal, Integer tzHour,
            Integer tzMinute) {
        super(new TimeStamp(year, month, day, hour, minute, second, Optional.empty(), Optional.ofNullable(tzSignal),
                Optional.ofNullable(tzHour),
                Optional.ofNullable(tzMinute)));
    }

    public Tipo getTipo(li2.plp.expressions2.memory.AmbienteCompilacao amb) {
        return TipoPrimitivo.TIMESTAMP;
    }

    public String toString() {
        return valor().day.toString() + "/" + valor().month.toString() + "/" + valor().year.toString() + " "
                + valor().hour.toString() + ":" + valor().minute.toString() + ":" + valor().second.toString();
    }

    public Integer acessProperty(String prop) {
        switch (prop) {
            case "year":
                return valor().year;
            case "month":
                return valor().month;
            case "day":
                return valor().day;
            case "hour":
                return valor().hour;
            case "minute":
                return valor().minute;
            case "second":
                return valor().second;
            default:
                throw new RuntimeException("Propriedade inválida para Timestamp: " + prop);
        }
    }

    public ValorTimestamp clone() {
        return new ValorTimestamp(this.valor());
    }

}

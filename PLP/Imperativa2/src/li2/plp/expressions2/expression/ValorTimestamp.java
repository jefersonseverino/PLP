package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimeStamp;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;

public class ValorTimestamp extends ValorConcreto<TimeStamp> {

    /**
     * Cria um objeto encapsulando o TimeStamp fornecido.
     */
    public ValorTimestamp(TimeStamp valor) {
        super(valor);
    }

    public Tipo getTipo(li2.plp.expressions2.memory.AmbienteCompilacao amb) {
        return TipoPrimitivo.TIMESTAMP;
    }

    public String toString() {
        return valor().day.toString() + "/" + valor().month.toString() + "/" + valor().year.toString() + " "
                + valor().hour.toString() + ":" + valor().minute.toString() + ":" + valor().second.toString();
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
                    return valor().tz_signal + valor().tz_hour.toString() + ":" + valor().tz_minute.toString();
                }
                return "Timezone is not defined";
            default:
                throw new RuntimeException("Invalid property for timestamp: " + prop);
        }
    }

    public ValorTimestamp clone() {
        return new ValorTimestamp(this.valor());
    }

}

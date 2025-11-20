package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimezoneRegistry;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import java.time.ZoneId;

public class ValorTimezone extends ValorConcreto<String> {

    public ValorTimezone(String timezone) {
        super(validateAndClean(timezone));
    }

    public ValorTimezone(boolean isPositive, int hours, int minutes) {
        super(buildOffset(isPositive, hours, minutes));
    }

    private static String validateAndClean(String timezone) {
        String cleaned = timezone.replaceAll("\"", "");
        if (!TimezoneRegistry.isTimezoneSupported(cleaned)) {
            throw new RuntimeException("Unsupported timezone: " + cleaned);
        }
        return cleaned;
    }

    private static String buildOffset(boolean isPositive, int hours, int minutes) {
        String offset = String.format("%s%02d:%02d", isPositive ? "+" : "-", hours, minutes);
        if (!TimezoneRegistry.isTimezoneSupported(offset)) {
            throw new RuntimeException("Unsupported timezone offset: " + offset);
        }
        return offset;
    }

    public ZoneId getZoneId() {
        return TimezoneRegistry.toZoneId(valor());
    }

    public String getTimezoneString() {
        return valor();
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoPrimitivo.STRING;
    }

    public String toString() {
        return valor();
    }

    public ValorTimezone clone() {
        return new ValorTimezone(this.valor());
    }
}

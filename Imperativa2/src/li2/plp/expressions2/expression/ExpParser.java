package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimeStamp;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;


public class ExpParser extends ExpUnaria {
 
    public ExpParser(Expressao exp) {
        super(exp, "parse");
    }

    public Valor avaliar(AmbienteExecucao amb) throws VariavelJaDeclaradaException, 
            VariavelNaoDeclaradaException {
        String str = ((ValorString) getExp().avaliar(amb)).valor();
        String[] parts = str.split("T", 2);
        String datePart = parts[0];
        String timePart = parts[1];

        String[] dateComponents = datePart.split("-", 3);
        Integer year = Integer.parseInt(dateComponents[0]);
        Integer month = Integer.parseInt(dateComponents[1]);
        Integer day = Integer.parseInt(dateComponents[2]);

        String[] timeComponents =  timePart.split(":", 3);
        Integer hour = Integer.parseInt(timeComponents[0]);
        Integer minute = Integer.parseInt(timeComponents[1]);
        Integer second = Integer.parseInt(timeComponents[2].substring(0, 2));

        try {
            if (timeComponents[2].length() > 2) { // has timezone
                String timezone = timeComponents[2].substring(2, timeComponents[2].length()).trim();
                if (timezone.contains("+") || timezone.contains("-")) {
                    String signal = timezone.substring(0, 1);
                    String[] tzComponents = timezone.substring(1).split(":");
                    Integer tzHour = Integer.parseInt(tzComponents[0]);
                    Integer tzMinute = Integer.parseInt(tzComponents[1]);

                    TimeStamp ts = new TimeStamp(year, month, day, hour, minute, second, signal, tzHour, tzMinute);
                    return new ValorTimestamp(ts);
                } else {
                    TimeStamp ts = new TimeStamp(year, month, day, hour, minute, second, timezone);
                    return new ValorTimestamp(ts);
                }
            }

            TimeStamp ts = new TimeStamp(year, month, day, hour, minute, second);
            return new ValorTimestamp(ts);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to parse timestamp: " + e.getMessage());
        }
    }

    protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) throws VariavelJaDeclaradaException, 
            VariavelNaoDeclaradaException {
        return (getExp().getTipo(amb).eString());
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoPrimitivo.TIMESTAMP;
    }

    @Override
    public ExpUnaria clone() {
        return new ExpParser(exp.clone());
    }
}

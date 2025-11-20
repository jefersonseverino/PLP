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
        String[] parts = str.split("T");
        String datePart = parts[0];
        String timePart = parts[1];

        String[] dateComponents = datePart.split("-");
        Integer year = Integer.parseInt(dateComponents[0]);
        Integer month = Integer.parseInt(dateComponents[1]);
        Integer day = Integer.parseInt(dateComponents[2]);

        String[] timeComponents =  timePart.split(":");
        Integer hour = Integer.parseInt(timeComponents[0]);
        Integer minute = Integer.parseInt(timeComponents[1]);
        Integer second = Integer.parseInt(timeComponents[2]);

        TimeStamp ts = new TimeStamp(year, month, day, hour, minute, second);
        return new ValorTimestamp(ts);
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

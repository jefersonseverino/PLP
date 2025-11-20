package li2.plp.expressions2.expression;

import java.util.regex.Pattern;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpFormat extends ExpBinaria {
    
    private static final Pattern HOUR_MINUTE_SECONDS 
        = Pattern.compile("^(HH:MM:SS)$");
    
    private static final Pattern HOUR_MINUTES 
        = Pattern.compile("^(HH:MM)$");
    
    private static final Pattern DATE_DD_MM_YYYY 
        = Pattern.compile("^(DD-MM-YYYY)$");
    
    private static final Pattern DATE_YYYY_MM_DD 
        = Pattern.compile("^(YYYY-MM-DD)$");

    public ExpFormat(Expressao esq, Expressao dir) {
        super(esq, dir, "format");
    }

    public Valor avaliar(AmbienteExecucao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        ValorTimestamp ts = (ValorTimestamp) getEsq().avaliar(ambiente);
        ValorString format = (ValorString) getDir().avaliar(ambiente);

        if (HOUR_MINUTE_SECONDS.matcher(format.valor()).matches()) {
            String formatted = String.format("%02d:%02d:%02d",
                    ts.getHour(), ts.getMinute(), ts.getSecond());
            return new ValorString(formatted);
        }

        if (HOUR_MINUTES.matcher(format.valor()).matches()) {
            String formatted = String.format("%02d:%02d",
                    ts.getHour(), ts.getMinute());
            return new ValorString(formatted);
        }

        if (DATE_DD_MM_YYYY.matcher(format.valor()).matches()) {
            String formatted = String.format("%02d/%02d/%04d",
                    ts.getDay(), ts.getMonth(), ts.getYear());
            return new ValorString(formatted);
        }

        if (DATE_YYYY_MM_DD.matcher(format.valor()).matches()) {
            String formatted = String.format("%04d-%02d-%02d",
                    ts.getYear(), ts.getMonth(), ts.getDay());
            return new ValorString(formatted);
        }

        throw new RuntimeException("Not supported format " + format);
    }

    protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return (getEsq().getTipo(ambiente).eTimeStamp() && getDir().getTipo(ambiente).eString());
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.STRING;
    }

    @Override
    public ExpBinaria clone() {
        return new ExpFormat(esq.clone(), dir.clone());
    }
}

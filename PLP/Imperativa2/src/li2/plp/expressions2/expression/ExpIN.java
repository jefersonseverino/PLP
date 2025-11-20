package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpIN extends ExpBinaria {
    
    public ExpIN(Expressao esq, ValorTimezone dir) {
        super(esq, dir, "in");
    }

    public Valor avaliar(AmbienteExecucao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Valor esqVal = getEsq().avaliar(amb);
        
        if (!(esqVal instanceof ValorTimestamp)) {
            throw new RuntimeException("Left operand of 'in' must be a Timestamp");
        }
        
        if (!(getDir() instanceof ValorTimezone)) {
            throw new RuntimeException("Right operand of 'in' must be a Timezone");
        }
        
        ValorTimestamp timestamp = (ValorTimestamp) esqVal;
        ValorTimezone timezone = (ValorTimezone) getDir();
        
        return timestamp.convertToTimezone(timezone.getTimezoneString());
    }

    public Tipo getTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.TIMESTAMP;
    }

    @Override
    protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo esqTipo = getEsq().getTipo(ambiente);
        boolean resultado = esqTipo.eIgual(TipoPrimitivo.TIMESTAMP);
        if (!resultado) {
            System.err.println("ExpIN: Tipo esperado TIMESTAMP, recebido " + esqTipo);
        }
        return resultado;
    }

    @Override
    public ExpIN clone() {
        return new ExpIN(this.esq.clone(), (ValorTimezone) this.dir.clone());
    }
}

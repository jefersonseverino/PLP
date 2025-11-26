package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions1.util.Duration;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpDiv extends ExpBinaria {

    public ExpDiv(Expressao esq, Expressao dir) {
        super(esq, dir, "/");
    }

    @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Valor valEsq = getEsq().avaliar(amb);
        Valor valDir = getDir().avaliar(amb);

        if (valEsq instanceof ValorDuration && valDir instanceof ValorInteiro) {
            int totalSec = ((ValorDuration) valEsq).valor().totalSeconds;
            int divisor = ((ValorInteiro) valDir).valor();
            
            if (divisor == 0) throw new RuntimeException("Divisão por zero");
            
            return new ValorDuration(new Duration(totalSec / divisor));
        }

        int divisor = ((ValorInteiro) valDir).valor();
        if (divisor == 0) throw new RuntimeException("Divisão por zero");
        
        return new ValorInteiro(((ValorInteiro) valEsq).valor() / divisor);
    }

    @Override
    protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = getEsq().getTipo(amb);
        Tipo tipoDir = getDir().getTipo(amb);

        if (tipoEsq.eIgual(TipoPrimitivo.DURATION) && tipoDir.eIgual(TipoPrimitivo.INTEIRO)) {
            return true;
        }
        if (tipoEsq.eIgual(TipoPrimitivo.INTEIRO) && tipoDir.eIgual(TipoPrimitivo.INTEIRO)) {
            return true;
        }

        return false;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = getEsq().getTipo(amb);

        if (tipoEsq.eIgual(TipoPrimitivo.DURATION)) {
            return TipoPrimitivo.DURATION;
        }
        return TipoPrimitivo.INTEIRO;
    }

    @Override
    public ExpBinaria clone() {
        return new ExpDiv(esq.clone(), dir.clone());
    }
}
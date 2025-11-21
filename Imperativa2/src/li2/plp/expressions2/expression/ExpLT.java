package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpLT extends ExpBinaria {

    public ExpLT(Expressao esq, Expressao dir) {
        super(esq, dir, "<");
    }

    public Valor avaliar(AmbienteExecucao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Valor esq = getEsq().avaliar(amb);
        Valor dir = getDir().avaliar(amb);

        if (esq instanceof ValorTimestamp && dir instanceof ValorTimestamp) {
            ValorTimestamp tsEsq = (ValorTimestamp) esq;
            ValorTimestamp tsDir = (ValorTimestamp) dir;
            return new ValorBooleano(tsEsq.isLess(tsDir));
        }

        throw new RuntimeException("Less than not implemented for these types");
    }   

	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return getEsq().getTipo(ambiente).eIgual(getDir().getTipo(ambiente));
	}


    public Tipo getTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.BOOLEANO;
    }

    @Override
    public ExpLT clone() {
        return new ExpLT(this.esq.clone(), this.dir.clone());
    }
}

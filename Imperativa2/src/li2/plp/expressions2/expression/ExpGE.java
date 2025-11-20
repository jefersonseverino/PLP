package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpGE extends ExpBinaria {

    public ExpGE(Expressao esq, Expressao dir) {
        super(esq, dir, ">");
    }

    public Valor avaliar(AmbienteExecucao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        ValorConcreto esq = (ValorConcreto) getEsq().avaliar(amb);
        ValorConcreto dir = (ValorConcreto) getDir().avaliar(amb);
        return new ValorBooleano(dir.isLessOrEquals(esq));
    }   

    public Tipo getTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.BOOLEANO;
    }

    @Override
    protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return getEsq().getTipo(ambiente).eIgual(getDir().getTipo(ambiente));
    }

    @Override
    public ExpGE clone() {
        return new ExpGE(this.esq.clone(), this.dir.clone());
    }

}

package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpAcessProperty extends ExpBinaria {

    public ExpAcessProperty(Expressao esq, Expressao dir) {
        super(esq, dir, ".");
    }

    public Valor avaliar(AmbienteExecucao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Expressao expressao = getEsq().reduzir(amb);
        String propName = getDir().toString();
        return new ValorString(((ValorTimestamp) expressao).acessProperty(propName));
    }

    public Tipo getTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.STRING;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao amb)
        throws VariavelJaDeclaradaException {
        if (!esq.checaTipo(amb)) {
            return false;
        }
        
        return checaTipoElementoTerminal(amb);
    }

    @Override
    protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return getEsq().getTipo(ambiente).eTimeStamp();
    }

    @Override
    public ExpAcessProperty clone() {
        return new ExpAcessProperty(this.esq.clone(), this.dir.clone());
    }

}

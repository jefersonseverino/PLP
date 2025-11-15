package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpAcessProperty extends ExpProperty {

    public ExpAcessProperty(Expressao expressao, Id propriedade) {
        super(expressao, propriedade);
    }

    public Tipo getTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return TipoPrimitivo.STRING;
    }

    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        expressao = expressao.reduzir(amb);
        String propName = propriedade.toString();
        return new ValorString(((ValorTimestamp) expressao).acessProperty(propName));
    }

    @Override
    public ExpAcessProperty clone() {
        return new ExpAcessProperty(getExpressao().clone(), (Id) getPropriedade().clone());
    }

}

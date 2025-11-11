package li2.plp.expressions2.expression;

import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 *  Expressões do tipo: expressao.propriedade
 */
public abstract class ExpProperty implements Expressao {
    
    Expressao expressao;
    
    Id propriedade;

    /**
     * @param expressao a expressão base
     * @param propriedade o identificador da propriedade
     */
    public ExpProperty(Expressao expressao, Id propriedade) {
        this.expressao = expressao;
        this.propriedade = propriedade;
    }

    /**
     * @return a expressão base
     */
    public Expressao getExpressao() {
        return expressao;
    }

    /**
     * @return o identificador da propriedade
     */
    public Id getPropriedade() {
        return propriedade;
    }

    /**
     * @param amb 
     * @return 
     * @exception VariavelJaDeclaradaException 
     * @exception VariavelNaoDeclaradaException
     */
    @Override
    public boolean checaTipo(AmbienteCompilacao amb)
            throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
        if (!expressao.checaTipo(amb)) {
            return false;
        }
        
        return checaTipoPropriedade(amb);
    }

    /**
     * @param amb
     * @return
     * @throws VariavelNaoDeclaradaException 
     * @throws VariavelJaDeclaradaException 
     */
    protected boolean checaTipoPropriedade(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return true;
    }

    /**
     * @return a expressão reduzida
     */
    @Override
    public Expressao reduzir(AmbienteExecucao ambiente) 
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        this.expressao = this.expressao.reduzir(ambiente);
        
        if (this.expressao instanceof Valor) {
            return acessarPropriedade(ambiente);
        }
        
        return this;
    }

    /**
     * @param ambiente o ambiente de execução
     * @return o valor da propriedade
     * @throws VariavelNaoDeclaradaException se a propriedade não existe
     */
    protected Expressao acessarPropriedade(AmbienteExecucao ambiente) 
            throws VariavelNaoDeclaradaException {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s.%s", expressao, propriedade);
    }

    @Override
    public abstract ExpProperty clone();
}
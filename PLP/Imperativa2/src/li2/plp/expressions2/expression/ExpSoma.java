package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de Soma.
 */
public class ExpSoma extends ExpBinaria {

	/**
	 * Controi uma Expressao de Soma com as sub-expressoes especificadas.
	 * Assume-se que estas sub-expressoes resultam em <code>ValorInteiro</code> 
	 * quando avaliadas.
	 * @param esq Expressao da esquerda
	 * @param dir Expressao da direita
	 */
	public ExpSoma(Expressao esq, Expressao dir) {
		super(esq, dir, "+");
	}


	/**
	 * Retorna o valor da Expressao de Soma
	 */
	public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		// System.out.println(getEsq().avaliar(amb));

		Valor esq = getEsq().avaliar(amb);
		Valor dir = getDir().avaliar(amb);

		if (esq instanceof ValorTimestamp && dir instanceof ValorDuration) {
			ValorTimestamp timestamp = (ValorTimestamp) esq;
			ValorDuration duration = (ValorDuration) dir;

			Integer[] daysPerMonth = timestamp.getDaysPerMonth();
			Integer year = timestamp.getYear();
			Integer month = timestamp.getMonth();
			Integer day = timestamp.getDay();
			Integer hour = timestamp.getHour();
			Integer minute = timestamp.getMinute();
			Integer second = timestamp.getSecond();

			Integer totalDuration = duration.getTotalSeconds();

			second += totalDuration;
			
			minute += second / 60;
			second %= 60;

			hour += minute / 60;
			minute %= 60;

			day += hour / 24;
			hour %= 24;

			if (month == 2 && timestamp.isLeapYear(year)) {
				daysPerMonth[2] = 29;
			}

			while (day > daysPerMonth[month]) {
				day -= daysPerMonth[month];
				month++;

				if (month > 12) {
					month = 1;
					year++;
				}

				if (month == 2) {
					daysPerMonth[2] = timestamp.isLeapYear(year) ? 29 : 28;
				}
    		}

			return new ValorTimestamp(year, month, day, hour, minute, second);
		}

		return new ValorInteiro(
			((ValorInteiro) getEsq().avaliar(amb)).valor() +
			((ValorInteiro) getDir().avaliar(amb)).valor() );
	}
	
	/**
	 * Realiza a verificacao de tipos desta expressao.
	 *
	 * @param ambiente o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *          <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          nao declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException se existir um identificador
	 *          declarado mais de uma vez no mesmo bloco do ambiente.
	 */
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return (getEsq().getTipo(ambiente).eInteiro() && getDir().getTipo(ambiente).eInteiro()
				|| getEsq().getTipo(ambiente).eTimeStamp() && getDir().getTipo(ambiente).eDuration());
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 *
	 * @param ambiente o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		return TipoPrimitivo.INTEIRO;
	}
	
	@Override
	public ExpBinaria clone() {
		return new ExpSoma(esq.clone(), dir.clone());
	}
}

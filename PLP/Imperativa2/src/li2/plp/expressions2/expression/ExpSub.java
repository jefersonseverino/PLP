package li2.plp.expressions2.expression;

import li2.plp.expressions1.util.TimeStamp;
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;
import li2.plp.expressions2.memory.AmbienteExecucao;
import li2.plp.expressions2.memory.VariavelJaDeclaradaException;
import li2.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class ExpSub extends ExpBinaria {

	public ExpSub(Expressao esq, Expressao dir) {
		super(esq, dir, "-");
	}

	public ValorTimestamp subTimestampAndDuration(ValorTimestamp timestamp, ValorDuration duration) {
		Integer[] daysPerMonth = timestamp.getDaysPerMonth();
		Integer year = timestamp.getYear();
		Integer month = timestamp.getMonth();
		Integer day = timestamp.getDay();
		Integer hour = timestamp.getHour();
		Integer minute = timestamp.getMinute();
		Integer second = timestamp.getSecond();

		Integer totalDuration = duration.getTotalSeconds();

		second -= totalDuration;

		while (second < 0) {
			second += 60;
			minute--;
		}

		while (minute < 0) {
			minute += 60;
			hour--;
		}

		while (hour < 0) {
			hour += 24;
			day--;
		}

		if (month == 2 && timestamp.isLeapYear(year)) {
			daysPerMonth[2] = 29;
		}

		while (day <= 0) {
			month--;

			if (month <= 0) {
				month = 12;
				year--;
			}

			if (month == 2) {
				daysPerMonth[2] = timestamp.isLeapYear(year) ? 29 : 28;
			}

			day += daysPerMonth[month];
		}

		TimeStamp ts = new TimeStamp(year, month, day, hour, minute, second);
		return new ValorTimestamp(ts);
	}

	public ValorDuration subTimestamps(ValorTimestamp t1, ValorTimestamp t2) {
		// TODO: check if t1 is greater than t2 !! Implement > operator for Timestamp type
		Integer totalSeconds1 = t1.totalSeconds();
		Integer totalSeconds2 = t2.totalSeconds();

		return new ValorDuration(totalSeconds1 - totalSeconds2);
	}

	@Override
	public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		Valor esq = getEsq().avaliar(amb);
		Valor dir = getDir().avaliar(amb);

		if (esq instanceof ValorTimestamp && dir instanceof ValorDuration) {
			ValorTimestamp timestamp = (ValorTimestamp) esq;
			ValorDuration duration = (ValorDuration) dir;
			return subTimestampAndDuration(timestamp, duration);
		}

		if (esq instanceof ValorTimestamp && dir instanceof ValorTimestamp) {
			ValorTimestamp t1 = (ValorTimestamp) esq;
			ValorTimestamp t2 = (ValorTimestamp) dir;
			return subTimestamps(t1, t2);
		}

		return new ValorInteiro(
				((ValorInteiro)esq).valor() -
				((ValorInteiro)dir).valor()
		);
	}

	@Override
	protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return (
			getEsq().getTipo(ambiente).eInteiro() && getDir().getTipo(ambiente).eInteiro()
			|| getEsq().getTipo(ambiente).eTimeStamp() && getDir().getTipo(ambiente).eDuration()
			|| getEsq().getTipo(ambiente).eTimeStamp() && getDir().getTipo(ambiente).eTimeStamp()
		);
	}

	@Override
	public Tipo getTipo(AmbienteCompilacao ambiente) {
		return TipoPrimitivo.INTEIRO;
	}

	@Override
	public ExpBinaria clone() {
		return new ExpSub(esq.clone(), dir.clone());
	}
}

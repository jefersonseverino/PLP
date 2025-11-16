package li2.plp.expressions2.expression;


import li2.plp.expressions1.util.Duration; 
import li2.plp.expressions1.util.Tipo;
import li2.plp.expressions1.util.TipoPrimitivo;
import li2.plp.expressions2.memory.AmbienteCompilacao;

public class ValorDuration extends ValorConcreto<Duration> {

    public ValorDuration(Duration valor) {
        super(valor);
    }

    public ValorDuration(int h, Integer m, Integer s) {
        super(new Duration(h, m, s));
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoPrimitivo.DURATION;
    }

    @Override
    public ValorDuration clone() {
        return new ValorDuration(this.valor());
    }

    @Override
    public String toString() {
        return this.valor().toString();
    }
}
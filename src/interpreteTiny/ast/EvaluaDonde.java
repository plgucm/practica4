package interpreteTiny.ast;

public class EvaluaDonde extends S {
	private E exp;
	private LDs decs;

	public EvaluaDonde(E exp, LDs decs) {
		this.exp = exp;
		this.decs = decs;
	}

	public E exp() {
		return exp;
	}

	public LDs decs() {
		return decs;
	}

	public TipoS tipo() {
		return TipoS.EVALUADONDE;
	}
}

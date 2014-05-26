package interpreteTiny.ast;

public class Evalua extends S {
	private E exp;

	public Evalua(E exp) {
		this.exp = exp;
	}

	public E exp() {
		return exp;
	}

	public TipoS tipo() {
		return TipoS.EVALUA;
	}
}

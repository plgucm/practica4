package interpreteTiny.ast;

public abstract class LDs {
	public abstract TipoLDs tipo();

	public LDs decs() {
		throw new UnsupportedOperationException("decs");
	}

	public String id() {
		throw new UnsupportedOperationException("id");
	}

	public E exp() {
		throw new UnsupportedOperationException("exp");
	}
}

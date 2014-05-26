package interpreteTiny.ast;

public class DCompuesta extends LDs {
	private String id;
	private E exp;
	private LDs decs;

	public DCompuesta(LDs decs, String id, E exp) {
		this.decs = decs;
		this.id = id;
		this.exp = exp;
	}

	public LDs decs() {
		return decs;
	}

	public String id() {
		return id;
	}

	public E exp() {
		return exp;
	}

	public TipoLDs tipo() {
		return TipoLDs.DCOMPUESTA;
	}
}

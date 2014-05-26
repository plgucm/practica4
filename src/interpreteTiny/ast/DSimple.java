package interpreteTiny.ast;

public class DSimple extends LDs {
	private String id;
	private E exp;

	public DSimple(String id, E exp) {
		this.id = id;
		this.exp = exp;
	}

	public String id() {
		return id;
	}

	public E exp() {
		return exp;
	}

	public TipoLDs tipo() {
		return TipoLDs.DSIMPLE;
	}
}

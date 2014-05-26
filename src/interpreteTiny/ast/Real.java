package interpreteTiny.ast;

public class Real extends E {
	private String v;

	public Real(String v) {
		this.v = v;
	}

	public String val() {
		return v;
	}

	public TipoE tipo() {
		return TipoE.ENTERO;
	}
}

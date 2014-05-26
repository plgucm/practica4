package interpreteTiny.ast;

public class Entero extends E {
	private String v;

	public Entero(String v) {
		this.v = v;
	}

	public String val() {
		return v;
	}

	public TipoE tipo() {
		return TipoE.ENTERO;
	}
}

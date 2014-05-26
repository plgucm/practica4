package interpreteTiny.ast;

public class Id extends E {
	private String id;

	public Id(String id) {
		this.id = id;
	}

	public String id() {
		return id;
	}

	public TipoE tipo() {
		return TipoE.ID;
	}
}

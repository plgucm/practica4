package modelo.tipos;



public class TipoID extends Tipo {

	private String id;

    public TipoID(String id) {
    	super(Tipos.IDENT);
    	this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    
    
}

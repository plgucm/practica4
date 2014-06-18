package modelo.tipos;



public class TipoPuntero extends Tipo {

    private Tipo tipo;

    public TipoPuntero(String id, Tipo tipo) {
    	super(id, Tipos.POINTER);
		this.tipo = tipo;
	}
    
    public Tipo getTipoPuntero() {
		return tipo;
	}


    public void setTipo(Tipo value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

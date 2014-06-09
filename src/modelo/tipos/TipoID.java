package modelo.tipos;



public class TipoID extends Tipo {

    private String identificador;


    public TipoID(String id) {
    	super(Tipos.IDENT);
		this.identificador = id;
	}


    public String getIdentificador() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.identificador;
    }


    public void setIdentificador(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.identificador = value;
    }

}

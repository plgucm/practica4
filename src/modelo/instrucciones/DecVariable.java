package modelo.instrucciones;
import modelo.tipos.Tipo;



public class DecVariable {

    private String identificador;
    private Tipo tipo;


    public DecVariable(String id, Tipo tipo) {
		this.identificador = id;
		this.tipo = tipo;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	

}

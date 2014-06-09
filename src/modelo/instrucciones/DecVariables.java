package modelo.instrucciones;
import modelo.tipos.Tipo;



public class DecVariables {

    private String identificador;
    private DecVariables decVariables;
    private Tipo tipo;


    public DecVariables(DecVariables dv, String id, Tipo tipo2) {
		this.decVariables = dv;
		this.identificador = id;
		this.tipo = tipo2;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public DecVariables getDecVariables() {
		return decVariables;
	}


	public void setDecVariables(DecVariables decVariables) {
		this.decVariables = decVariables;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	

}

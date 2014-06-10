package modelo.instrucciones;

import java.util.List;

import modelo.expresiones.Expresion;



public class Llamada extends Instruccion {

    private String identificador;
    private List<Expresion> params;

    public Llamada(String id, List<Expresion> params) {
    	super(TiposInstruccion.LLAMADA);
		this.identificador = id;
		this.params = params;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<Expresion> getParams() {
		return params;
	}

	public void setParams(List<Expresion> params) {
		this.params = params;
	}


	
}

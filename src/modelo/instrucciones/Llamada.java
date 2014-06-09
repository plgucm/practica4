package modelo.instrucciones;

import java.util.List;



public class Llamada extends Instruccion {

    private String identificador;
    private List<String> params;

    public Llamada(String id, List<String> params) {
		this.identificador = id;
		this.params = params;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}


	
}

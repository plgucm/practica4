package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Designador {

    private String identificador;
    private Expresion expresion;
    private Designador designador;


    public Designador(Designador designador2, String id) {
		this.identificador = id;
		this.designador = designador2;
	}


	public Designador(String id) {
		this.identificador = id;
	}


	public Designador(Designador designador2, Expresion exp) {
		this.designador = designador2;
		this.expresion = exp;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public Expresion getExpresion() {
		return expresion;
	}


	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}


	public Designador getDesignador() {
		return designador;
	}


	public void setDesignador(Designador designador) {
		this.designador = designador;
	}




	

}

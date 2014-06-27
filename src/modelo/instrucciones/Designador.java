package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Designador {

    private String identificador;
    private Expresion expresion;
    private Designador designador;
    private Tipo tipo;
    
    public enum Tipo {
    	ID, ARRAY, CAMPO_DE_STRUCT, PUNTERO
    }

	public Designador(String id) {
		this.identificador = id;
		this.tipo = Tipo.ID;
	}

    public Designador(Designador designador2, String id) {
		this.identificador = id;
		this.designador = designador2;
		this.tipo = Tipo.CAMPO_DE_STRUCT;
	}

	public Designador(Designador designador2, Expresion exp) {
		this.designador = designador2;
		this.expresion = exp;
		this.tipo = Tipo.ARRAY;
	}

	public Designador(Designador designador2) {
		this.designador = designador2;
		this.tipo = Tipo.PUNTERO;
	}
	
	public Tipo getTipo() {
		return tipo;
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

//	@Override
//	public String toString() {
//		if (tipo == Tipo.REGISTRO_DE_STRUCT){
//			return designador.designador.identificador + "->." + identificador + "\n";
//		} else {
//			return "";
//		}
//	}


	

}

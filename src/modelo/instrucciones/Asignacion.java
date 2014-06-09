package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Asignacion extends Instruccion {


	private Designador designador;
    private Expresion expresion;
    
    public Asignacion(Designador ds, Expresion exp) {
		this.designador = ds;
		this.expresion = exp;
	}

	public Designador getDesignador() {
		return designador;
	}

	public void setDesignador(Designador designador) {
		this.designador = designador;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

    

}

package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Write extends Instruccion {

	private Expresion expresion;
	
    public Write(Expresion exp) {
		this.expresion = exp;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

    

}

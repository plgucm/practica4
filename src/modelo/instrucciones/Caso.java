package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Caso extends Instruccion {

	private Bloque bloque;
    private Expresion expresion;

    public Caso(Expresion exp, Bloque bloque) {
    	super(TiposInstruccion.CASOS);
		this.expresion = exp;
		this.bloque = bloque;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
    
    

}

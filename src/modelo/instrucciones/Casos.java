package modelo.instrucciones;
import modelo.expresiones.Expresion;



public class Casos extends Instruccion {


	private Bloque bloque;
    private Casos casos;
    private Expresion expresion;

    public Casos(Casos casos, Expresion exp, Bloque bloque) {
		this.casos = casos;
		this.expresion = exp;
		this.bloque = bloque;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

	public Casos getCasos() {
		return casos;
	}

	public void setCasos(Casos casos) {
		this.casos = casos;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
    
    

}

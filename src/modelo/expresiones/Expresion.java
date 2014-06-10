package modelo.expresiones;

public abstract class Expresion {
	
	protected TipoExpresion tipoExpresion;

	public Expresion(TipoExpresion tipoExpresion) {
		this.tipoExpresion = tipoExpresion;
	}
	
	public TipoExpresion getTipoExpresion() {
		return tipoExpresion;
	}
	
	public void setTipoExpresion(TipoExpresion tipoExpresion) {
		this.tipoExpresion = tipoExpresion;
	}
	
	
}

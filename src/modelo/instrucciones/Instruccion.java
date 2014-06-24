package modelo.instrucciones;


public abstract class Instruccion {
	
	protected TiposInstruccion tipoInstruccion;
	
	public Instruccion(TiposInstruccion tipo){
		this.tipoInstruccion = tipo;
	}
	
	public TiposInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}
	
	public void setTipoInstruccion(TiposInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}
	
}

package modelo.instrucciones;

public class Delete extends Instruccion {

	private Designador designador;
	
    public Delete(Designador ds) {
    	super(TiposInstruccion.DELETE);
		this.designador = ds;
	}

	public Designador getDesignador() {
		return designador;
	}

	public void setDesignador(Designador designador) {
		this.designador = designador;
	}

    
    
}

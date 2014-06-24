package modelo.instrucciones;



public class New extends Instruccion {

	private Designador designador;
	
    public New(Designador ds) {
    	super(TiposInstruccion.NEW);
		this.designador = ds;
	}

	public Designador getDesignador() {
		return designador;
	}

	public void setDesignador(Designador designador) {
		this.designador = designador;
	}

	
    

}

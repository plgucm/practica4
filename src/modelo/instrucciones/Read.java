package modelo.instrucciones;



public class Read extends Instruccion {

	private Designador designador;
	
    public Read(Designador ds) {
    	super(TiposInstruccion.READ);
		this.designador = ds;
	}

	public Designador getDesignador() {
		return designador;
	}

	public void setDesignador(Designador designador) {
		this.designador = designador;
	}

    
    

}

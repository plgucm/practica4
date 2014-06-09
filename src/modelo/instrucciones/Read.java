package modelo.instrucciones;



public class Read extends Instruccion {

	private Designador designador;
	
    public Read(Designador ds) {
		this.designador = ds;
	}

	public Designador getDesignador() {
		return designador;
	}

	public void setDesignador(Designador designador) {
		this.designador = designador;
	}

    
    

}

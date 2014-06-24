package modelo.instrucciones;

import java.util.List;



public class Condicional extends Instruccion {
	
	private List<Caso> casos;

    public Condicional(List<Caso> casos) {
    	super(TiposInstruccion.IF);
		this.casos = casos;
	}

	public List<Caso> getCasos() {
		return casos;
	}
	
	public void setCasos(List<Caso> casos) {
		this.casos = casos;
	}
    

}

package modelo.instrucciones;

import java.util.List;



public class Bucle extends Instruccion {

    private List<Caso> casos;


    public Bucle(List<Caso> casos) {
    	super(TiposInstruccion.BUCLE);
    	this.casos = casos;
	}
    
    public List<Caso> getCasos() {
		return casos;
	}
    
    public void setCasos(List<Caso> casos) {
		this.casos = casos;
	}

	

}

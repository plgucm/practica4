package modelo.instrucciones;



public class Condicional extends Instruccion {
	
	private Casos casos;

    public Condicional(Casos casos2) {
    	super(TiposInstruccion.IF);
		this.casos = casos2;
	}

	public Casos getCasos() {
		return casos;
	}

	public void setCasos(Casos casos) {
		this.casos = casos;
	}
    
    
    
    

}

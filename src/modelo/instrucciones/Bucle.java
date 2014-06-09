package modelo.instrucciones;



public class Bucle extends Instruccion {

    private Casos casos;


    public Bucle(Casos casos2) {
    	this.casos = casos2;
	}


	public Casos getCasos() {
		return casos;
	}


	public void setCasos(Casos casos) {
		this.casos = casos;
	}

	

}

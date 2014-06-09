package modelo.instrucciones;



public class Programa {
	
	private DecTipos decTipos;
    private DecVariables decVariables;
    private DecSubprogramas decSubprogramas;
    private Bloque bloque;

    public Programa(DecTipos dt, DecVariables dv, DecSubprogramas ds, Bloque b) {
		this.decTipos = dt;
		this.decVariables = dv;
		this.decSubprogramas = ds;
		this.bloque = b;
	}

	public DecTipos getDecTipos() {
		return decTipos;
	}

	public void setDecTipos(DecTipos decTipos) {
		this.decTipos = decTipos;
	}

	public DecVariables getDecVariables() {
		return decVariables;
	}

	public void setDecVariables(DecVariables decVariables) {
		this.decVariables = decVariables;
	}

	public DecSubprogramas getDecSubprogramas() {
		return decSubprogramas;
	}

	public void setDecSubprogramas(DecSubprogramas decSubprogramas) {
		this.decSubprogramas = decSubprogramas;
	}

	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

    
	

}

package modelo.instrucciones;

import java.util.List;



public class Programa {
	
	private List<DecTipo> decTipos;
    private List<DecVariable> decVariables;
    private List<DecSubprograma> decSubprogramas;
    private Bloque bloque;

    public Programa(List<DecTipo> dt, List<DecVariable> dv, List<DecSubprograma> ds, Bloque b) {
		this.decTipos = dt;
		this.decVariables = dv;
		this.decSubprogramas = ds;
		this.bloque = b;
	}

    

	public List<DecTipo> getDecTipos() {
		return decTipos;
	}



	public void setDecTipos(List<DecTipo> decTipos) {
		this.decTipos = decTipos;
	}



	public List<DecVariable> getDecVariables() {
		return decVariables;
	}



	public void setDecVariables(List<DecVariable> decVariables) {
		this.decVariables = decVariables;
	}



	public List<DecSubprograma> getDecSubprogramas() {
		return decSubprogramas;
	}



	public void setDecSubprogramas(List<DecSubprograma> decSubprogramas) {
		this.decSubprogramas = decSubprogramas;
	}



	public Bloque getBloque() {
		return bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

    
	

}

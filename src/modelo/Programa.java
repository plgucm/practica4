package modelo;

public class Programa {

	private Bloque bloque;
	private DecVariables decVariables;
	private DecSubprogramas decSubprogramas;
	private DecTipos decTipos;

	public DecTipos getDecTipos() {
		return this.decTipos;
	}

	public void setDecTipos(DecTipos decTipos) {
		this.decTipos = decTipos;
	}

	public DecSubprogramas getDecSubprogramas() {
		return this.decSubprogramas;
	}

	public void setDecSubprogramas(DecSubprogramas decSubprogramas) {
		this.decSubprogramas = decSubprogramas;
	}

	public DecVariables getDecVariables() {
		return this.decVariables;
	}

	public void setDecVariables(DecVariables decVariables) {
		this.decVariables = decVariables;
	}

	public Bloque getBloque() {
		return this.bloque;
	}

	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}

}

package modelo.instrucciones;
import modelo.tipos.Tipo;



public class DecTipos {

    private String identificador;
    private DecTipos decTipos;
    private Tipo tipo;


    public DecTipos(DecTipos dt, String id, Tipo tipo2) {
		this.identificador = id;
		this.decTipos = dt;
		this.tipo = tipo2;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public DecTipos getDecTipos() {
		return decTipos;
	}


	public void setDecTipos(DecTipos decTipos) {
		this.decTipos = decTipos;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

    

}

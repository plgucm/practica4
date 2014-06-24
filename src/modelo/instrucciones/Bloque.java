package modelo.instrucciones;
import java.util.List;



public class Bloque extends Instruccion {
	
	private List<Instruccion> instrucciones;

    public Bloque(List<Instruccion> insts) {
    	super(TiposInstruccion.BLOQUE);
		this.instrucciones = insts;
	}

	public List<Instruccion> getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(List<Instruccion> instrucciones) {
		this.instrucciones = instrucciones;
	}

    

}

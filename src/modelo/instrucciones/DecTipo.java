package modelo.instrucciones;
import java.util.List;

import modelo.tipos.Tipo;



public class DecTipo {

    private String id;
    private Tipo tipo;
    
	public DecTipo(String id, Tipo tipo) {
		this.id = id;
		this.tipo = tipo;
	}

    public String getId() {
		return id;
	}
    
	public Tipo getTipo() {
		return tipo;
	}
	    
    

}

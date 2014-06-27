package modelo.instrucciones;
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
	  
	@Override
	public String toString() {
		return id + " : " + tipo.getTipoConcreto();
	}
    

}

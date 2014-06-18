package modelo.tipos;



public abstract class Tipo {

    protected Tipos tipo;
    protected String id;
    
    public Tipo(String id, Tipos tipo){
    	this.tipo = tipo;    	
    	this.id = id;
    }

    public Tipos getTipoConcreto() {
        return this.tipo;
    }

    public void setTipoConcreto(Tipos value) {
        this.tipo = value;
    }
    
    public String getId() {
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    

}

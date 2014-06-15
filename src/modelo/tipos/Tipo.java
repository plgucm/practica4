package modelo.tipos;



public abstract class Tipo {

    protected Tipos tipo;
    
    public Tipo(Tipos tipo){
    	this.tipo = tipo;    	
    }


    public Tipos getTipoConcreto() {
        return this.tipo;
    }


    public void setTipoConcreto(Tipos value) {
        this.tipo = value;
    }
    
    

}

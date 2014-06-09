package modelo.tipos;



public abstract class Tipo {

    protected Tipos tipo;
    
    public Tipo(Tipos tipo){
    	this.tipo = tipo;
    }


    Tipos getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }


    void setTipo(Tipos value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

package modelo.operadores;



public abstract class Operador {

    private String tipo;

    public Operador(String tipo){
    	this.tipo = tipo;
    }
    

    public String getTipo() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.tipo;
    }


    public void setTipo(String value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.tipo = value;
    }

}

package modelo.expresiones;



public class ExpresionBoolean extends Expresion {

    private boolean valor;


    public ExpresionBoolean(boolean val) {
    	super(TipoExpresion.BOOLEAN);
    	this.valor = val;
	}


    public boolean isValor() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.valor;
    }


    public void setValor(boolean value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.valor = value;
    }

}

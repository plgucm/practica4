package modelo.expresiones;
import modelo.operadores.OpUnario;



public class ExpresionUnaria extends Expresion {

    private Expresion exp;

    private OpUnario opUnario;


    public ExpresionUnaria(OpUnario op, Expresion exp2) {
    	super(TipoExpresion.UNARIA);
		this.opUnario = op;
		this.exp = exp2;
	}


    public Expresion getExp() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp;
    }


    public void setExp(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp = value;
    }
    
    
    public OpUnario getOpUnario() {
		return opUnario;
	}
    
    public void setOpUnario(OpUnario opUnario) {
		this.opUnario = opUnario;
	}
    

}

package modelo.expresiones;

import modelo.operadores.OpBinario;



public class ExpresionBinaria extends Expresion {

    private Expresion exp0;

    private Expresion exp1;

    private OpBinario opBinario;


    public ExpresionBinaria(Expresion exp02, OpBinario op, Expresion exp12) {
		this.exp0 = exp02;
		this.opBinario = op;
		this.exp1 = exp12;
	}


    public Expresion getExp0() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp0;
    }


	public void setExp0(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp0 = value;
    }


    public Expresion getExp1() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.exp1;
    }


    public void setExp1(Expresion value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.exp1 = value;
    }
    
    public OpBinario getOpBinario() {
		return opBinario;
	}
    
    public void setOpBinario(OpBinario opBinario) {
		this.opBinario = opBinario;
	}

}

package modelo.operadores;

public abstract class Operador {

    protected TipoOperador tipo;

    public Operador(TipoOperador tipo){
    	this.tipo = tipo;
    }

    public TipoOperador getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoOperador value) {
        this.tipo = value;
    }

	public boolean esAritmetico() {		
		return tipo == TipoOperador.MAS ||
		   tipo == TipoOperador.MENOS || 
		   tipo == TipoOperador.POR || 
		   tipo == TipoOperador.DIV ||
		   tipo == TipoOperador.MOD  || 
		   tipo == TipoOperador.TOINT ||
		   tipo == TipoOperador.TODOUBLE;
	}

	public boolean esLogico() {
		return tipo == TipoOperador.AND ||
			   tipo == TipoOperador.OR || 
			   tipo == TipoOperador.NOT;
	}

	public boolean esComparacion() {
		return tipo == TipoOperador.IGUAL ||
			   tipo == TipoOperador.MENOR || 
			   tipo == TipoOperador.MAYOR || 
			   tipo == TipoOperador.MENOROIGUAL ||
			   tipo == TipoOperador.MAYOROIGUAL  || 
			   tipo == TipoOperador.DISTINTO;
	}

}

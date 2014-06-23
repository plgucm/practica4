package maquinap.valor;

public class VInt extends Valor<Integer> {

	public VInt(Integer val) {
		super(val);
	}
	
	public boolean menor(Valor<?> otro){
		if(otro instanceof VInt)
			return val < ((VInt)otro).getValor();
		return false;
	}
	
	public boolean mayor(Valor<?> otro){
		if(otro instanceof VInt)
			return val > ((VInt)otro).getValor();
		return false;
	}
}

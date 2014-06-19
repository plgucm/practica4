package maquinap.valor;

public class Int extends Valor<Integer> {

	public Int(Integer val) {
		super(val);
	}
	
	public boolean menor(Valor<?> otro){
		if(otro instanceof Int)
			return val < ((Int)otro).getValor();
		return false;
	}
	
	public boolean mayor(Valor<?> otro){
		if(otro instanceof Int)
			return val > ((Int)otro).getValor();
		return false;
	}
}

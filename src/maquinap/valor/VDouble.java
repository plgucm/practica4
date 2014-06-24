package maquinap.valor;

public class VDouble extends Valor<Double> {

	public VDouble(Double val) {
		super(val);
	}
	
	public boolean menor(Valor<?> otro){
		if(otro instanceof VDouble)
			return val < ((VDouble)otro).getValor();
		return false;
	}
	
	public boolean mayor(Valor<?> otro){
		if(otro instanceof VDouble)
			return val > ((VDouble)otro).getValor();
		return false;
	}
}

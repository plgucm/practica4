package maquinap.valor;

public abstract class Valor<T> {
	
	protected T val;
	
	public Valor(T val){
		this.val = val;
	}
	
	public T getValor() {
		return val;
	}
	
	public void setValor(T otro){
		val = otro;
	}
	
	@Override
	public String toString() {
		return val.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return val.equals(((Valor<?>) obj).getValor());
	}
	
	public boolean menor(Valor<?> otro){
		throw new UnsupportedOperationException(getClass().getSimpleName()
				+ " operación no implementada.");
	}
	
	public boolean mayor(Valor<?> otro){
		throw new UnsupportedOperationException(getClass().getSimpleName()
				+ " operación no implementada.");
	}
}

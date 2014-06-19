package maquinap.instrucciones;

import maquinap.MaquinaP;

public abstract class Instruccion {

	public void ejecutar(MaquinaP maq) throws Exception {
		throw new UnsupportedOperationException(getClass().getSimpleName()
				+ " no está implementada.");
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}

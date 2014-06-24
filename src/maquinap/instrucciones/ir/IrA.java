package maquinap.instrucciones.ir;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;

public class IrA extends Instruccion {

	private int dir;

	public IrA(int dir) {
		this.dir = dir;
	}

	@Override
	public void ejecutar(MaquinaP maq) {
		maq.setContadorPrograma(dir);
	}
}

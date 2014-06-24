package maquinap.instrucciones.memoria;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;

public class Dup extends Instruccion {

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		
		maq.getPilaEvaluacion().push(maq.getPilaEvaluacion().peek());
		maq.incrementaContadorPrograma();
		
	}

}

package maquinap.instrucciones.apila;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class ApilaDir extends Instruccion {

	private int dir;

	public ApilaDir(int dir) {
		this.dir = dir;
	}

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Valor<?> valor = maq.getMemoriaDatos().get(dir);	
		
		if(valor == null){
			valor = new Int(0);
		}
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.size() == pe.capacity()){
			throw new Exception("M�xima direcci�n de la pila.");
		}		
		
		maq.getPilaEvaluacion().push(valor);
		maq.incrementaContadorPrograma();
	}
}

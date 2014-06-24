package maquinap.instrucciones.desapila;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Valor;

public class DesapilaDir extends Instruccion {

	private int dir;

	public DesapilaDir(int dir) {
		this.dir = dir;
	}

	@Override
	public void ejecutar(MaquinaP maq) {
		Stack<Valor<?>> pila = maq.getPilaEvaluacion();
		
		if(pila.isEmpty())
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " pila de evaluación vacía.");
		
		maq.getMemoriaDatos().put(dir, pila.pop());
		maq.incrementaContadorPrograma();
	}
	
}

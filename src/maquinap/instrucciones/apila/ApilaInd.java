package maquinap.instrucciones.apila;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class ApilaInd extends Instruccion {

	@Override
	public void ejecutar(MaquinaP maq) {
		Stack<Valor<?>> pila = maq.getPilaEvaluacion();

		if (pila.isEmpty())
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " pila de evaluación vacía.");

		Valor<?> cima = pila.pop();
		
		if (cima instanceof Int) {
			
			Valor<?> val = maq.getMemoriaDatos().get(cima.getValor());

			if (val == null){
				val = new Int(0);
			}
			pila.push(val);
			maq.incrementaContadorPrograma();
		} else
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " la cima no es un entero, es " + cima.getClass());
	}
}

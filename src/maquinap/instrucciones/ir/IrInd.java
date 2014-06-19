package maquinap.instrucciones.ir;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class IrInd extends Instruccion {

	@Override
	public void ejecutar(MaquinaP maq) {
		Stack<Valor<?>> pila = maq.getPilaEvaluacion();

		if (pila.isEmpty())
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " la pila de evaluación está vacía.");

		Valor<?> cima = pila.pop();
		
		if (cima instanceof Int)
			maq.setContadorPrograma(((Int) cima).getValor());
		else
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " la cima no es un entero, es " + cima.getClass());
	}
}

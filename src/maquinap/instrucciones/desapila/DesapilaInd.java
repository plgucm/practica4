package maquinap.instrucciones.desapila;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.VInt;
import maquinap.valor.Valor;

public class DesapilaInd extends Instruccion {

	@Override
	public void ejecutar(MaquinaP maq) {
		Stack<Valor<?>> pila = maq.getPilaEvaluacion();

		if (pila.size() < 2)
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " la pila de evaluación debe al menos tener dos valores.");

		Valor<?> cima = pila.pop();
		Valor<?> subCima = pila.pop();
		
		if (subCima instanceof VInt) {
			if(cima == null) {
				cima = new VInt(0);
			}
			
			maq.getMemoriaDatos().put(((VInt)subCima).getValor(), cima);
			maq.incrementaContadorPrograma();
			
		} else
			throw new UnsupportedOperationException(getClass().getSimpleName()
					+ " la sub-cima no es un entero, es " + subCima.getClass());
	}

}

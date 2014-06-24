package maquinap.instrucciones.io;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Valor;

public class Escribe extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception{
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("ESCRIBE -> falta operando");
		}		
		Valor<?> valor = pe.pop();
		System.out.println(valor);
		maq.aumentarContadorPrograma(1);
	}

}

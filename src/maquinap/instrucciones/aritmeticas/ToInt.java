package maquinap.instrucciones.aritmeticas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.VInt;
import maquinap.valor.Valor;

public class ToInt extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("TOINT -> falta operando");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Double)){
			throw new Exception("TOINT -> operando no de tipo double");
		}
		VInt newValue = new VInt(Integer.valueOf(valor1.getValor().toString()));
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

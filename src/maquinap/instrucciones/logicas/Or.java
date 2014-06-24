package maquinap.instrucciones.logicas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Bool;
import maquinap.valor.Valor;

public class Or extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("OR -> falta segundo operando");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Boolean)){
			throw new Exception("OR -> segundo operando no de tipo bool");
		}
		if (pe.isEmpty()){ 
			throw new Exception("OR -> falta primer operando"); 
		}		
		Valor<?> valor2 = pe.pop();
		if (!(valor2.getValor() instanceof Boolean)){
			throw new Exception("OR -> primer operando no de tipo bool");
		}
		Bool newValue = new Bool((Boolean)valor2.getValor()||(Boolean)valor1.getValor());
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

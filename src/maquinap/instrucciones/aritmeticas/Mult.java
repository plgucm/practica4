package maquinap.instrucciones.aritmeticas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class Mult extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("MULT -> faltan operandos");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Integer)){
			throw new Exception("MULT -> segundo operando no de tipo entero");
		}
		if (pe.isEmpty()){ 
			throw new Exception("MULT -> faltan operandos");
		}		
		Valor<?> valor2 = pe.pop();
		if (!(valor2.getValor() instanceof Integer)){
			throw new Exception("MULT -> primer operando no de tipo entero");
		}
		Int newValue = new Int((Integer)valor2.getValor()*(Integer)valor1.getValor());
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

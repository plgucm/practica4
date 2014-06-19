package maquinap.instrucciones.aritmeticas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class Div extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("DIV -> faltan operandos");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Integer)){
			throw new Exception("DIV -> segundo operando no de tipo entero");
		}
		if (pe.isEmpty()){ 
			throw new Exception("DIV -> faltan operandos");
		}		
		Valor<?> valor2 = pe.pop();
		if (!(valor2.getValor() instanceof Integer)){
			throw new Exception("DIV -> primer operando no de tipo entero");
		}
		int op1 = (Integer)valor2.getValor();
		int op2 = (Integer)valor1.getValor();
		int res = op1/op2;
		Int newValue = new Int(res);
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

package maquinap.instrucciones.aritmeticas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.VDouble;
import maquinap.valor.VInt;
import maquinap.valor.Valor;

public class Div extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("DIV -> faltan operandos");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Integer || valor1.getValor() instanceof Double)){
			throw new Exception("DIV -> segundo operando no de tipo entero o double");
		}
		if (pe.isEmpty()){ 
			throw new Exception("DIV -> faltan operandos");
		}		
		Valor<?> valor2 = pe.pop();
		if (!(valor2.getValor() instanceof Integer || valor1.getValor() instanceof Double)){
			throw new Exception("DIV -> primer operando no de tipo entero o double");
		}
		
		double op1 = 0, op2 = 0;
		if (valor2 instanceof VDouble){
			op1 = (Double) valor2.getValor();
		} else if (valor1 instanceof VInt){
			op1 = (Integer) valor2.getValor();
		}
		 		
		Integer res;
		Double res2;
		Valor<?> newValue = null;
		if (valor1 instanceof VDouble){
			op2 = (Double) valor1.getValor();
			res2 = op1 / op2;
			newValue = new VDouble(res2);
		} else if (valor1 instanceof VInt){
			op2 = (Integer) valor1.getValor();
			res = (int) (op1 / op2);
			newValue = new VInt(res);
		}
			
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

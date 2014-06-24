package maquinap.instrucciones.aritmeticas;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.VDouble;
import maquinap.valor.VInt;
import maquinap.valor.Valor;

public class Neg extends Instruccion {
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.isEmpty()){ 
			throw new Exception("NEG -> falta operando");
		}		
		Valor<?> valor1 = pe.pop();
		if (!(valor1.getValor() instanceof Integer || valor1.getValor() instanceof Double)){
			throw new Exception("NEG -> operando no de tipo entero o double");
		}
		
		double op1 = 0;
		if (valor1 instanceof VDouble){
			op1 = (Double) valor1.getValor();
		} else if (valor1 instanceof VInt){
			op1 = (Integer) valor1.getValor();
		}
		 		
		Integer res;
		Double res2;
		Valor<?> newValue = null;
		if (valor1 instanceof VDouble){
			res2 = -op1;
			newValue = new VDouble(res2);
		} else if (valor1 instanceof VInt){
			res = (int) (-op1);
			newValue = new VInt(res);
		}
		
		maq.getPilaEvaluacion().push(newValue);
		maq.aumentarContadorPrograma(1);
	}

}

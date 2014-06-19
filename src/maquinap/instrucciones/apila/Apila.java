package maquinap.instrucciones.apila;

import java.util.Stack;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Valor;


public class Apila extends Instruccion {

	private Valor<?> val;

	public Apila(Valor<?> val) {
		this.val = val;
	}
	
	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		Stack<Valor<?>> pe = maq.getPilaEvaluacion();
		if (pe.size() == pe.capacity()){
			throw new Exception("Máxima dirección de la pila.");
		}		
		maq.getPilaEvaluacion().push(val);
		maq.incrementaContadorPrograma();
	}
	
}

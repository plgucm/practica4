package maquinap.instrucciones.memoria;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class Clona extends Instruccion {
	
	private Integer cantidad;
	
	public Clona(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		int dirCimaPila = (Integer) maq.getPilaEvaluacion().pop().getValor();
		int dirSubCimaPila = (Integer) maq.getPilaEvaluacion().pop().getValor();
		
		int len = dirCimaPila+cantidad;
		while (dirCimaPila<len){
			Valor<?> v = maq.getMemoriaDatos().get(dirCimaPila);
			if(v == null){
				v = new Int(0);
			}
			maq.getMemoriaDatos().put(dirSubCimaPila, v);
			dirCimaPila++;
			dirSubCimaPila++;
		}

		maq.aumentarContadorPrograma(1);
	}

}

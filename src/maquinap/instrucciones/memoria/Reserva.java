package maquinap.instrucciones.memoria;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Int;

public class Reserva extends Instruccion {
	
	private Integer cantidad;
	
	public Reserva(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		boolean reservado = false;
		int tam, dir = 0;
		
		for (Espacio e : maq.getListaDeEspacios()){
			tam = e.getTam();
			dir = e.getDir_com();
			if (tam > cantidad){
				maq.getListaDeEspacios().add(new Espacio(dir, tam, false));	
				e.setTam(tam-cantidad);
				e.setDir_com(dir+cantidad);		
				e.setLibre(false);						
				reservado = true;
				break;
			} else if (tam == cantidad){				
				e.setLibre(false);
				reservado = true;
				break;
			}
		}
		
		if (!reservado){
			throw new Exception("No hay memoria suficiente para reservar esa cantidad.");
		} else {
			maq.getPilaEvaluacion().push(new Int(dir));
		}
		
		maq.aumentarContadorPrograma(1);		
	}

}

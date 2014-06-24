package maquinap.instrucciones.memoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;

public class Libera extends Instruccion {

	private Integer cantidad;

	public Libera(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		int dirPila = (Integer) maq.getPilaEvaluacion().pop().getValor();
		List<Espacio> esp = maq.getListaDeEspacios();

		// Uso copias para no liarla.
		int dirPilaTmp = dirPila;
		int cantidadTmp = this.cantidad;
		Collections.sort(maq.getListaDeEspacios(), comparator);

		for (Espacio espacio : esp) {
			if (espacio.estaEnEsteEspacioDeDirecciones(dirPilaTmp)) {
				if (espacio.superaElTamanio(dirPilaTmp, cantidadTmp)) {
					if (espacio.getDir_com() < dirPilaTmp) {
						// Hay que dividir en dos partes

						int tam = dirPilaTmp - espacio.getDir_com();
						esp.add(new Espacio(espacio.getDir_com(), tam, espacio
								.isLibre()));

						int tam2 = (espacio.getDir_com() + espacio.getTam())
								- dirPilaTmp;
						espacio.setTam(tam2);
						espacio.setDir_com(dirPilaTmp);
						espacio.setLibre(true);

						dirPilaTmp += tam2;
						cantidadTmp -= tam2;

					} else /* Tienen la misma direccion */{
						espacio.setLibre(true);
						dirPilaTmp += espacio.getTam();
						cantidadTmp -= espacio.getTam();

					}

				} else if (espacio.loLiberaExacto(dirPilaTmp, cantidadTmp)) {
					if (espacio.getDir_com() < dirPilaTmp) {
						// Hay que dividir en dos partes

						int tam = dirPilaTmp - espacio.getDir_com();
						esp.add(new Espacio(espacio.getDir_com(), tam, espacio
								.isLibre()));

						int tam2 = (espacio.getDir_com() + espacio.getTam())
								- dirPilaTmp;
						espacio.setTam(tam2);
						espacio.setDir_com(dirPilaTmp);
						espacio.setLibre(true);
						
						dirPilaTmp += tam2;
						cantidadTmp -= tam2;

					} else /* Tienen la misma direccion <-> caso ideal */{
						espacio.setLibre(true);
						dirPilaTmp += espacio.getTam();
						cantidadTmp -= espacio.getTam();
						
					}
					break;

				} else /* No lo libera completamente */{
					// Hay que dividir en tres partes

					int tam1 = dirPilaTmp - espacio.getDir_com();
					esp.add(new Espacio(espacio.getDir_com(), tam1, espacio
							.isLibre()));

					int tam3 = espacio.getDir_com() - dirPilaTmp + cantidadTmp;
					esp.add(new Espacio(dirPilaTmp + cantidadTmp, tam3, espacio
							.isLibre()));

					espacio.setLibre(true);
					espacio.setTam(espacio.getTam() - tam1 - tam3);
					espacio.setDir_com(espacio.getDir_com() + tam1);
					
					dirPilaTmp += espacio.getTam();
					cantidadTmp -= espacio.getTam();

					break;
				}
			}
		}

		/* fusiona y simplifica la lista de espacios. */
		ArrayList<Espacio> espacios = new ArrayList<Espacio>();

		Collections.sort(esp, comparator);
		Iterator<Espacio> it = esp.iterator();
		Espacio eAnt = it.next();
		while (it.hasNext()) {
			Espacio espacio = it.next();
			if (eAnt.isLibre() && espacio.isLibre()) {
				espacio.setDir_com(eAnt.getDir_com());
				espacio.setTam(espacio.getTam() + eAnt.getTam());
				espacios.add(eAnt);
			}
			eAnt = espacio;
		}

		for (Espacio espacio : espacios) {
			esp.remove(espacio);
		}

		maq.aumentarContadorPrograma(1);
	}

	private static final Comparator<Espacio> comparator = new Comparator<Espacio>() {
		@Override
		public int compare(Espacio e1, Espacio e2) {
			return e1.compareTo(e2);
		}
	};

}

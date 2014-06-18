package traductor;

import static traductor.LenguajeP.generaEpilogo;
import static traductor.LenguajeP.generaInicio;
import static traductor.LenguajeP.generaPrellamada;
import static traductor.LenguajeP.generaPrologo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import modelo.instrucciones.Programa;

public class EscribeCodigoEnArchivo {
	
	private FileWriter fw;	
	private Programa prog;
	private Decoracion d;
	
	public EscribeCodigoEnArchivo(Programa p, Decoracion d) {
		this.prog = p;
		this.d = d;
	}
	
	private void abreArchivo(String nombreArchivo){
		try {
			fw = new FileWriter(new File(nombreArchivo), false);
		} catch (IOException e) { }
	}
	
	private void cierraArchivo(){
		try {
			fw.close();
		} catch (IOException e) { }
	}
	
	private void escribeEnArchivo(String codigo){
		try {
			fw.append(codigo);
		} catch (IOException e) { }
	}

	public void escribeCodigo(String nombreArchivo) {
		abreArchivo(nombreArchivo);
		
		
		
		cierraArchivo();
	}

}

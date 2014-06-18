package traductor;

import static traductor.LenguajeP.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import modelo.instrucciones.Programa;

public class EscribeCodigoEnArchivo {
	
	private FileWriter fw;	
	private Programa prog;
	private Decoracion d;
	
	public EscribeCodigoEnArchivo(Programa p, Decoracion d) {
		this.prog = p;
		this.d = d;
	}
	
	private void testeo(){
		escribeEnArchivo("\nEjemplo de inicio:\n");
		escribeEnArchivo(generaInicio(14, 683));
		escribeEnArchivo("\nEjemplo de prellamada:\n");
		escribeEnArchivo(generaPrellamada(new ArrayList<String>(), 697, 216));
		escribeEnArchivo("\nEjemplo de prologo:\n");
		escribeEnArchivo(generaPrologo(2, 1));
		escribeEnArchivo("\nEjemplo de epilogo:\n");
		escribeEnArchivo(generaEpilogo(2, 1));
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
		
		String inicio = generaInicio(
				(Integer)d.getDecoracion(prog.getDecVariables()).get("tam")+
				(Integer)d.getDecoracion(prog).get("finDatos")+1,
				(Integer)d.getDecoracion(prog).get("cinst"));
		System.out.println(inicio);
		
		cierraArchivo();
	}

}

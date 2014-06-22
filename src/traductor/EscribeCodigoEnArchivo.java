package traductor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EscribeCodigoEnArchivo {
	
	private FileWriter fw;	
	private List<String> codigo;
	
	public EscribeCodigoEnArchivo(List<String> codigo) {
		this.codigo = codigo;
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
		imprimeCodigo(codigo);	
		cierraArchivo();
	}	

	public void imprimeCodigo(List<String> codigo){
		for (String s : codigo){
			System.out.println(s);
			escribeEnArchivo(s);
		}
	}

}

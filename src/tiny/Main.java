package tiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import maquinap.MaquinaP;
import modelo.instrucciones.Programa;
import traductor.Chequeo;
import traductor.Decoracion;
import traductor.EscribeCodigoEnArchivo;
import traductor.GeneraCodigo;
import traductor.Vinculador;

public class Main {
	
	static final String nombreArchivoFuente = "input.txt";
	static final String nombreArchivoSalida = "output.txt";
	
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream(nombreArchivoFuente));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);		
		asint.parse();
		System.out.println(" <-------- Análisis completado --------->");
		Programa p = AnalizadorSintacticoTiny.programaRaiz;	

		Vinculador vinculador = new Vinculador();
		Map<Object, Object> vinculos = vinculador.vincula(p);
		System.out.println(" <-------- Vinculación completada --------->");
		
		Chequeo ch = new Chequeo(vinculos);
		ch.chequea(p);
		System.out.println(" <-------- Chequeo completado --------->");
		
		Decoracion d = new Decoracion();
		GeneraCodigo gc = new GeneraCodigo(vinculos, d);
		gc.generaCodigo(p);
		System.out.println(" <-------- Generación de código completado --------->");
		
		EscribeCodigoEnArchivo ea = new EscribeCodigoEnArchivo(gc.getCodigo());
		ea.escribeCodigo(nombreArchivoSalida);
		System.out.println(" <-------- Fin de procesamiento de archivo fuente --------->");
		

		System.out.println(" <-------- Inicio ejecución del programa --------->");
		
//		MaquinaP.main(new String[]{nombreArchivoSalida});

		System.out.println(" <-------- Fin de la ejecución del programa--------->");
		
	}
}

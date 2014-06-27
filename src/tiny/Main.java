package tiny;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import modelo.instrucciones.Programa;
import traductor.Chequeo;
import traductor.Decoracion;
import traductor.EscribeCodigoEnArchivo;
import traductor.GeneraCodigo;
import traductor.Vinculador;

public class Main {
	
	/**
	 * Aquí deberá asignar el ejemplo de entrada.
	 */
	static final String nombreArchivoFuente = "errorChequeoParametros.txt";
	
	
	static final String nombreArchivoSalida = "output.txt";
	
	public static void main(String[] args) throws Exception {
		
		File f = new File("");
		String carpetaCasosDePrueba = 
					f.getAbsolutePath()+File.separator+"casosdeprueba"+File.separator;
		String carpetaSalidaCasosDePrueba = 
					f.getAbsolutePath()+File.separator+"casodepruebagenerado"+File.separator;
		
		Reader input = new InputStreamReader(new FileInputStream(carpetaCasosDePrueba+nombreArchivoFuente));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);		
		asint.parse();
		System.out.println(" <-------- Análisis completado --------->");
		
		Programa p = AnalizadorSintacticoTiny.programaRaiz;	
		Map<Object, Integer> numLineas = AnalizadorSintacticoTiny.cons.getNumeroLinea();

		Vinculador vinculador = new Vinculador();
		Map<Object, Object> vinculos = vinculador.vincula(p);
		System.out.println(" <-------- Vinculación completada --------->");
		
		Chequeo ch = new Chequeo(numLineas, vinculos);
		ch.chequea(p);
		System.out.println(" <-------- Chequeo completado --------->");
		
		Decoracion d = new Decoracion();
		GeneraCodigo gc = new GeneraCodigo(vinculos, d);
		gc.generaCodigo(p);
		System.out.println(" <-------- Generación de código completado --------->");
		
		EscribeCodigoEnArchivo ea = new EscribeCodigoEnArchivo(gc.getCodigo());
		ea.escribeCodigo(carpetaSalidaCasosDePrueba+nombreArchivoSalida);
		System.out.println(" <-------- Fin de procesamiento de archivo fuente --------->");
		

		System.out.println(" <-------- Inicio ejecución del programa --------->");
		
//		MaquinaP.main(new String[]{carpetaSalidaCasosDePrueba+nombreArchivoSalida});

		System.out.println(" <-------- Fin de la ejecución del programa--------->");
		
	}
}

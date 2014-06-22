package tiny;

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
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
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
		ea.escribeCodigo("cod.txt");
		System.out.println(" <-------- Fin --------->");
	}
}

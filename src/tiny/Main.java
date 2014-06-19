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
		System.out.println("Análisis completado. Se supone que el árbol está construído.");
		Programa p = AnalizadorSintacticoTiny.programaRaiz;	
		

		Vinculador vinculador = new Vinculador();
		Map<Object, Object> ts = vinculador.vincula(p).peek();
		
		Chequeo ch = new Chequeo();
		ch.chequea(p);
		Decoracion d = new Decoracion();
		GeneraCodigo gc = new GeneraCodigo(ts, d);
		gc.generaCodigo(p);
		EscribeCodigoEnArchivo ea = new EscribeCodigoEnArchivo(p, d);
		ea.escribeCodigo("cod.txt");
	}
}

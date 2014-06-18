package tiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.Programa;
import traductor.Vinculador;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);		
		asint.parse();
		System.out.println("Análisis completado. Se supone que el árbol está construído.");
		Programa p = AnalizadorSintacticoTiny.programaRaiz;	
		

		System.out.println(p.getDecTipos().getIdentificador());
		
		
		//TablaDeSimbolos ts = new TablaDeSimbolos();
		Vinculador vinculador = new Vinculador();
		vinculador.vincula(p);
		//Map<String, Object> ts = vinculador.getTS().peek();
		//System.out.println(ts.get("LeeValores"));
		
		
		
		
		
		/*
		Chequeo ch = new Chequeo(ts);
		ch.chequea(p);
		Decoracion d = new Decoracion();
		GeneraCodigo gc = new GeneraCodigo(ts, d);
		gc.generaCodigo(p);
		EscribeCodigoEnArchivo ea = new EscribeCodigoEnArchivo(p, d);
		ea.escribeCodigo("cod.txt");
		*/
	}
}

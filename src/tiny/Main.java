package tiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import traductor.ProcesadorSemantico;
import modelo.expresiones.Expresion;
import modelo.instrucciones.Llamada;
import modelo.instrucciones.Programa;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(alex);		
		asint.parse();
		System.out.println("Análisis completado. Se supone que el árbol está construído.");
		Programa p = AnalizadorSintacticoTiny.programaRaiz;		
		
		/*Llamada a = ((Llamada)p.getBloque().getInstrucciones().get(1));
		Expresion e = a.getParams().get(0);		
		System.out.println(e.getTipoExpresion());*/		
		//System.out.println(p.getDecVariables().getDecVariables().getDecVariables().getIdentificador());
		
		ProcesadorSemantico tse = new ProcesadorSemantico();
		tse.vincula(p);
		
	}
}

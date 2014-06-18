package tiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import modelo.instrucciones.Programa;
import traductor.Decoracion;
import traductor.EscribeCodigoEnArchivo;
import traductor.GeneraCodigo;

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
		//System.out.println(p.getDecVariables().getDecVariables().getTipo().getTipoConcreto());
		
		
		
		// System.out.println(p.getDecTipos().getDecTipos().getTipo());
		
		/*System.out.println(
				p.getDecSubprogramas().getDecSubprogramas()
				.getDecSubprogramas().getPrograma().
				getDecSubprogramas().getDecSubprogramas().getIdentificador());*/
		
		/*Vinculador tse = new Vinculador();
		tse.vincula(p);
		
		Chequeo ch = new Chequeo();
		ch.chequea(p);
		*/
		Decoracion d = new Decoracion();
		GeneraCodigo gc = new GeneraCodigo(d);
		gc.generaCodigo(p);
		EscribeCodigoEnArchivo ea = new EscribeCodigoEnArchivo(p, d);
		ea.escribeCodigo("cod.txt");
		
	}
}

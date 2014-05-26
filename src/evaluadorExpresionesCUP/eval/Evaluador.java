package evaluadorExpresionesCUP.eval;

import evaluadorExpresionesCUP.alex.AnalizadorLexicoEval;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public class Evaluador implements Scanner {

	private AnalizadorLexicoEval alex;

	public Evaluador(AnalizadorLexicoEval alex) {
		this.alex = alex;
	}

	@Override
	public Symbol next_token() throws Exception {
		return alex.next_token();
	}

}

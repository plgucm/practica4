package interpreteTiny.eval;

import interpreteTiny.ast.E;
import interpreteTiny.ast.LDs;
import interpreteTiny.ast.S;
import interpreteTiny.ast.TipoLDs;
import interpreteTiny.ast.TipoS;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Eval {
	private Map<String, Double> binds;

	public Eval() {
		binds = new HashMap<String, Double>();
	}

	public double eval(S s) {
		binds(s);
		return eval(s.exp());
	}

	private void binds(S s) {
		binds.clear();
		if (s.tipo() == TipoS.EVALUADONDE)
			mkBinds(s.decs());
	}

	private void mkBinds(LDs decs) {
		if (decs.tipo() == TipoLDs.DCOMPUESTA)
			mkBinds(decs.decs());
		binds.put(decs.id(), eval(decs.exp()));
	}

	private double valorDe(String id) {
		Double v = binds.get(id);
		if (v == null)
			v = 0.0;
		return v;
	}

	private void actualiza(String id, double v) {
		binds.put(id, v);
	}

	private double eval(E exp) {
		switch (exp.tipo()) {
		case REAL:
			return Double.valueOf(exp.val()).doubleValue();
		case ENTERO:
			return Integer.valueOf(exp.val()).intValue();
		case ID:
			return valorDe(exp.id());
		case SUMA:
			return eval(exp.opnd1()) + eval(exp.opnd2());
		case RESTA:
			return eval(exp.opnd1()) - eval(exp.opnd2());
		case MUL:
			return eval(exp.opnd1()) * eval(exp.opnd2());
		case DIV:
			return eval(exp.opnd1()) / eval(exp.opnd2());
		default:
			return -1;
		}
	}

	public static void main(String[] args) throws Exception {
		ConstructorASTTiny cast = new ConstructorASTTiny(
				new FileReader(args[0]));
		Eval eval = new Eval();
		System.out.println(eval.eval(cast.Sp()));
	}
}

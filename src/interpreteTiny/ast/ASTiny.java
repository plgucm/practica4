package interpreteTiny.ast;

public class ASTiny {
	public E suma(E opnd1, E opnd2) {
		return new Suma(opnd1, opnd2);
	}

	public E resta(E opnd1, E opnd2) {
		return new Resta(opnd1, opnd2);
	}

	public E mul(E opnd1, E opnd2) {
		return new Mul(opnd1, opnd2);
	}

	public E div(E opnd1, E opnd2) {
		return new Div(opnd1, opnd2);
	}

	public E entero(String ent) {
		return new Entero(ent);
	}

	public E real(String d) {
		return new Real(d);
	}

	public E id(String id) {
		return new Id(id);
	}

	public LDs cCompuesta(LDs decs, String id, E exp) {
		return new DCompuesta(decs, id, exp);
	}

	public LDs cSimple(String id, E exp) {
		return new DSimple(id, exp);
	}

	public S evalua(E exp) {
		return new Evalua(exp);
	}

	public S evaluaDonde(E exp, LDs decs) {
		return new EvaluaDonde(exp, decs);
	}

}

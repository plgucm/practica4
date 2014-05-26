package interpreteTiny.ast;

public class Mul extends EBin {
	public Mul(E opnd1, E opnd2) {
		super(opnd1, opnd2);
	}

	public TipoE tipo() {
		return TipoE.MUL;
	}
}

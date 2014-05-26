package interpreteTiny.ast;

public abstract class EBin extends E {
	private E opnd1;
	private E opnd2;

	public EBin(E opnd1, E opnd2) {
		this.opnd1 = opnd1;
		this.opnd2 = opnd2;
	}

	public E opnd1() {
		return opnd1;
	}

	public E opnd2() {
		return opnd2;
	}
}

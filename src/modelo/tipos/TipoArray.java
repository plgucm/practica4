package modelo.tipos;



public class TipoArray extends Tipo {

    private int dimension;
    private Tipo tipo;


    public TipoArray(Integer size, Tipo tipo) {
    	super(Tipos.ARRAY);
		this.dimension = size;
		this.tipo = tipo;
	}


    public int getDimension() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.dimension;
    }

    public  void setDimension(int value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.dimension = value;
    }


	public Tipo getTipoInterno() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
    
    

}

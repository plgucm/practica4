package modelo.tipos;
import java.util.List;



public class TipoStruct extends Tipo {

    private List<String> identificadores;

    private List<Tipo> tipo;


    public TipoStruct(List<String> id, List<Tipo> tipo) {
		super(Tipos.STRUCT);
		this.tipo = tipo;
		this.identificadores = id;
	}


	public List<String> getIdentificadores() {
		return identificadores;
	}


	public void setIdentificadores(List<String> identificadores) {
		this.identificadores = identificadores;
	}


	public List<Tipo> getTipos() {
		return tipo;
	}


	public void setTipo(List<Tipo> tipo) {
		this.tipo = tipo;
	}
    
    

}

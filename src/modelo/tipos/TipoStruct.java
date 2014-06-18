package modelo.tipos;
import java.util.List;



public class TipoStruct extends Tipo {

    private List<String> identificadores;

    private List<Tipo> tipo;


    public TipoStruct(String id, List<String> ids, List<Tipo> tipo) {
		super(id,  Tipos.STRUCT);
		this.tipo = tipo;
		this.identificadores = ids;
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

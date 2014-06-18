package modelo.instrucciones;
import java.util.List;



public class DecSubprograma {

    private String identificador;
    private List<Parametro> parametros;
    private Programa programa;


    public DecSubprograma(String id, List<Parametro> params, Programa subprograma) {
		this.identificador = id;
		this.parametros = params;
		this.programa = subprograma;    	
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}


	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}


	public Programa getPrograma() {
		return programa;
	}


	public void setPrograma(Programa programa) {
		this.programa = programa;
	}


	

}

package modelo.instrucciones;
import java.util.List;



public class DecSubprogramas {

    private String identificador;
    private DecSubprogramas decSubprogramas;
    private List<Parametro> parametros;
    private Programa programa;


    public DecSubprogramas(DecSubprogramas ds, String id, List<Parametro> params, Programa subprograma) {
		this.decSubprogramas = ds;
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


	public DecSubprogramas getDecSubprogramas() {
		return decSubprogramas;
	}


	public void setDecSubprogramas(DecSubprogramas decSubprogramas) {
		this.decSubprogramas = decSubprogramas;
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

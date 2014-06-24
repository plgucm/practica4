package modelo.tipos;
import java.util.List;

import modelo.instrucciones.DecTipo;



public class TipoStruct extends Tipo {

    private List<DecTipo> tipos;

    public TipoStruct(List<DecTipo> tipos) {
		super(Tipos.STRUCT);
		this.tipos = tipos;
	}

    
    public List<DecTipo> getTipos() {
		return tipos;
	}

}

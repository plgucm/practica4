package traductor;

import java.util.HashMap;
import java.util.Map;

import modelo.instrucciones.Bloque;
import modelo.instrucciones.DecSubprogramas;
import modelo.instrucciones.DecTipos;
import modelo.instrucciones.DecVariables;
import modelo.instrucciones.Programa;

public class ProcesadorSemantico {
	
	private Map<String, Object> tablaDeSimbolos;

	public void vincula(Programa p) {
		iniciaTS();
		//abreBloqueDeclaraciones();
		//cierraBloqueDeclaraciones();
		vinculaTipos(p.getDecTipos());
		vinculaVariables(p.getDecVariables());
		vinculaDecSubprogramas(p.getDecSubprogramas());
		vinculaBloque(p.getBloque());		
	}

	private void iniciaTS() {
		tablaDeSimbolos = new HashMap<String, Object>();		
	}
	
	private void abreBloqueDeclaraciones() {
		// TODO Auto-generated method stub
		
	}

	private void cierraBloqueDeclaraciones() {
		// TODO Auto-generated method stub
		
	}

	private boolean insertaID(String id, Object declaracion){
		if (tablaDeSimbolos.get(id) != null){ return false; }
		tablaDeSimbolos.put(id, declaracion);
		return false;
	}
	
	private Object declaracionDe(String id){
		return tablaDeSimbolos.get(id);
	}


	private void vinculaTipos(DecTipos decTipos) {
		String id = decTipos.getIdentificador();
		
	}

	private void vinculaVariables(DecVariables decVariables) {
		
		
	}

	private void vinculaDecSubprogramas(DecSubprogramas decSubprogramas) {
		// TODO Auto-generated method stub
		
	}

	private void vinculaBloque(Bloque bloque) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}

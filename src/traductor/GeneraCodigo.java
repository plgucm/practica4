package traductor;

import java.util.HashMap;
import java.util.Map;

import modelo.instrucciones.DecSubprogramas;
import modelo.instrucciones.Programa;

public class GeneraCodigo {
	
	
	private Map<Object, Map<String, Object>> nodosDecorados;	
	private int dir, nivel;
	
	public GeneraCodigo() {
		nodosDecorados = new HashMap<Object, Map<String,Object>>();
		dir = nivel = 0;
	}

	public void generaCodigo(Programa p) {
		asignaEspacio(p);
		codigo(p);		
	}

	private void codigo(Programa p) {
		
	}

	private void asignaEspacio(Programa p) {
		dir = anidamiento(p.getDecSubprogramas());
		insertaInfoEnNodo(p, "finDatos", dir);
		
	}

	private int anidamiento(DecSubprogramas decSubprogramas) {
		
		
		return 0;
	}

	public Map<String, Object> getDecoracion(Object nodo){
		return nodosDecorados.get(nodo);
	}
	
	public boolean insertaInfoEnNodo(Object nodo, String clave, Object valor){
		return getDecoracion(nodo).put(clave, valor) == null;
	}
	
	public Object leeInfoDeNodo(Object nodo, String clave){
		return getDecoracion(nodo).get(clave);
	}

}

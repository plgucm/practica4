package traductor;

import java.util.HashMap;
import java.util.Map;

public class Decoracion {

	private Map<Object, Map<String, Object>> nodosDecorados;	
	
	public Decoracion() {
		nodosDecorados = new HashMap<Object, Map<String,Object>>();
	}
	
	public Map<String, Object> getDecoracion(Object nodo){
		if (nodosDecorados.get(nodo) == null){
			nodosDecorados.put(nodo, new HashMap<String, Object>());
		}
		return nodosDecorados.get(nodo);
	}
	
	public boolean insertaInfoEnNodo(Object nodo, String clave, Object valor){
		return getDecoracion(nodo).put(clave, valor) == null;
	}
	
	public Object leeInfoDeNodo(Object nodo, String clave){
		return getDecoracion(nodo).get(clave);
	}

	
}

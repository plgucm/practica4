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
		System.out.println(dir);
		
	}

	private int anidamiento(DecSubprogramas decSubprogramas) {
		int miAnidamiento = 0;
		int maxAnidamientoHermanos = 0;
		DecSubprogramas dh = decSubprogramas.getDecSubprogramas();	
		Programa dp = decSubprogramas.getPrograma();					
		
		if (dh != null && dp != null){
			System.out.println("Id: "+dh.getIdentificador());	
			maxAnidamientoHermanos = anidamiento(dh);	
			miAnidamiento++; 				
			if (dp.getDecSubprogramas() != null){
				miAnidamiento += anidamiento(dp.getDecSubprogramas());						
			}
		} else {
			if (dh == null){
				miAnidamiento++; 
				if (dp.getDecSubprogramas() != null){
					miAnidamiento += anidamiento(dp.getDecSubprogramas());						
				}
			} else {
				System.out.println("Id: "+dh.getIdentificador());	
				maxAnidamientoHermanos = anidamiento(dh);	
			}						
		}

		return Math.max(miAnidamiento, maxAnidamientoHermanos);
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

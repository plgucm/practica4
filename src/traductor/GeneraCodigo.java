package traductor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.instrucciones.DecSubprogramas;
import modelo.instrucciones.DecTipos;
import modelo.instrucciones.DecVariables;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoStruct;

public class GeneraCodigo {	
	
	private Map<Object, Map<String, Object>> nodosDecorados;	
	private Map<String, Integer> tamaniosTipos; 
	private int dir, nivel;
	
	public GeneraCodigo() {
		nodosDecorados = new HashMap<Object, Map<String,Object>>();
		tamaniosTipos = new HashMap<String, Integer>();
		dir = nivel = 0;
	}

	public void generaCodigo(Programa p) {
		asignaEspacioPrincipal(p);
		codigoProgramaPrincipal(p);		
	}

	private void codigoProgramaPrincipal(Programa p) {
		int cinst = numeroInstruccionesActivacionPrograma(p);
		
	
		
	}

	private int numeroInstruccionesActivacionPrograma(Programa p) {
		
		
		return 0;
	}

	private void asignaEspacio(DecSubprogramas ds) {
		if (ds.getDecSubprogramas() != null){
			// esto es debido a que el árbol no esta correctamente construído.
			asignaEspacio(ds.getDecSubprogramas());
		}
		int dirCopia = dir;
		int nivelCopia = nivel;
		if (ds.getPrograma() != null){
			nivel++;
			asignaEspacio(ds.getPrograma());	
		}
		dir = dirCopia;
		nivel = nivelCopia;	
		
		List<Parametro> params = ds.getParametros();
		if (params != null){
			for (Parametro p : params){
				int tam = 1;
				if (p.isPorValor()){
					tam = tamanioVar(p.getTipo());
				}
				insertaInfoEnNodo(params, "tam", tam);	
		//		System.out.println("TAM de " + p.getIdentificador() + " :" + tam);		
			}
		}
	}

	private void asignaEspacioPrincipal(Programa p) {
		DecSubprogramas ds = p.getDecSubprogramas();
		dir = anidamiento(ds);
		insertaInfoEnNodo(p, "finDatos", dir);
		System.out.println("Anidamiento: " + dir);
		nivel = 0;
		asignaEspacio(p);
	}

	private void asignaEspacio(Programa p) {
		if (p.getDecTipos() != null) asignaEspacio(p.getDecTipos());
		if (p.getDecVariables() != null) asignaEspacio(p.getDecVariables());
		if (p.getDecSubprogramas() != null) asignaEspacio(p.getDecSubprogramas());
		/*if (p.getBloque() != null) asignaEspacio(p.getBloque());		*/
	}

	//private void asignaEspacio(Bloque bloque) { }

	private void asignaEspacio(DecTipos decTipos) {
		int tam = tamanioVar(decTipos.getTipo());
		tamaniosTipos.put(decTipos.getIdentificador(), tam);
		DecTipos dv = decTipos.getDecTipos();
		if (dv != null){ asignaEspacio(dv); }		
	}

	private void asignaEspacio(DecVariables decVariables) {
		insertaInfoEnNodo(decVariables, "nivel", nivel);
		insertaInfoEnNodo(decVariables, "dir", dir);
		int tam = tamanioVar(decVariables.getTipo());
		// System.out.println("TAM de " + decVariables.getIdentificador() + " :" + tam);
		insertaInfoEnNodo(decVariables, "tam", tam);
		dir += tam;
		DecVariables dv = decVariables.getDecVariables();
		if (dv != null){ asignaEspacio(dv); }				
	}

	private int tamanioVar(Tipo tipo) {
		//System.out.println(tipo.getTipoConcreto());
		switch(tipo.getTipoConcreto()){
		case BOOL:
		case INT:
		case POINTER:
		case DOUBLE:
			return 1;
		case IDENT:
			TipoID tipoReal = (TipoID)tipo;
			int tamanio = tamaniosTipos.get(tipoReal.getIdentificador());
			return tamanio;
		case ARRAY:
			TipoArray arr = (TipoArray) tipo;
			int dim = arr.getDimension();
			int tamInterior = tamanioVar(arr.getTipoInterno());
			return dim * tamInterior;			
		case STRUCT:
			TipoStruct struct = (TipoStruct) tipo;
			int tam = 0;
			List<Tipo> tipos = struct.getTipos();
			for (Tipo tip : tipos){
				tam += tamanioVar(tip);
			}
			return tam;
			default: break;
		}		
		
		return 0;
	}

	private int anidamiento(DecSubprogramas decSubprogramas) {
		int miAnidamiento = 0;
		int maxAnidamientoHermanos = 0;
		DecSubprogramas dh = decSubprogramas.getDecSubprogramas();	
		Programa dp = decSubprogramas.getPrograma();					
		
		if (dh != null && dp != null){
			maxAnidamientoHermanos = anidamiento(dh);	
			miAnidamiento++; 				
			if (dp.getDecSubprogramas() != null){
				miAnidamiento += anidamiento(dp.getDecSubprogramas());						
			}
		} else if (dh == null){
			miAnidamiento++; 
			if (dp.getDecSubprogramas() != null){
				miAnidamiento += anidamiento(dp.getDecSubprogramas());						
			}
		} else {
			maxAnidamientoHermanos = anidamiento(dh);	
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

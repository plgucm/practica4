package traductor;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import modelo.expresiones.Expresion;
import modelo.expresiones.ExpresionBinaria;
import modelo.expresiones.ExpresionBoolean;
import modelo.expresiones.ExpresionDesignador;
import modelo.expresiones.ExpresionDouble;
import modelo.expresiones.ExpresionInteger;
import modelo.expresiones.ExpresionUnaria;
import modelo.expresiones.TipoExpresion;
import modelo.instrucciones.Asignacion;
import modelo.instrucciones.Bloque;
import modelo.instrucciones.Bucle;
import modelo.instrucciones.Caso;
import modelo.instrucciones.Condicional;
import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.DecTipo;
import modelo.instrucciones.DecVariable;
import modelo.instrucciones.Delete;
import modelo.instrucciones.Designador;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Llamada;
import modelo.instrucciones.New;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.instrucciones.Read;
import modelo.instrucciones.TiposInstruccion;
import modelo.instrucciones.Write;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoStruct;

public class Vinculador {
	
	private Stack<Map<String, Object>> tablaDeSimbolos;
	private Map<Object, Object> vinculos;

	public Map<Object, Object> vincula(Programa p) {
		iniciaTS();
		abreBloque();
		vinculaTipos(p.getDecTipos());
		vinculaVariables(p.getDecVariables());
		vinculaSubprogramas(p.getDecSubprogramas());
		vincula(p.getBloque());
		vinculaTiposDef(p.getDecTipos());
		vinculaVariablesDef(p.getDecVariables());
		vinculaSubprogramasDef(p.getDecSubprogramas());
		vinculaDef(p.getBloque());
		cierraBloque();
		return vinculos;
	}
	
	/*********************************************************************************************
	 * FUNCIONES GENERALES
	 *********************************************************************************************/
	
	private void iniciaTS() {
		tablaDeSimbolos = new Stack<Map<String, Object>>();	
		vinculos = new HashMap<Object, Object>();	
	}
	
	private void abreBloque() {
		tablaDeSimbolos.push(new HashMap<String, Object>());		
	}

	private void cierraBloque() {
		tablaDeSimbolos.pop();
	}
	
	private void debugTS(String from){
		System.out.println("--- Debug llamado desde " + from);
		System.out.println("Nivel: " + (tablaDeSimbolos.size()-1));
		System.out.println("Ámbito: " + tablaDeSimbolos.peek());
	}

	private boolean insertaID(String id, Object declaracion){
//		debugTS("insertaID");
		Map<String, Object> ts = tablaDeSimbolos.peek();		
		if (ts.get(id) != null){ return false; }
		ts.put(id, declaracion);
		return true;
	}
	
	private Object declaracionDe(String id){
		Map<String, Object> ts = tablaDeSimbolos.peek();
		if (ts.get(id) == null){
			// Si no está en el ámbito actual, miro en los ámbitos superiores.
			ListIterator<Map<String, Object>> it = tablaDeSimbolos.listIterator(tablaDeSimbolos.size()-1);
			while (it.hasPrevious()){
				Object e = it.previous().get(id);
				if (e != null){
					return e;
				}
			}
			return null;
		}
		
		return ts.get(id);
	}
	
	private boolean insertaVinculo(Object nodo, Object vinculo){
		if (vinculos.get(nodo) != null){ return false; }
		vinculos.put(nodo, vinculo);
		return true;
	}	
	
	/*********************************************************************************************
	 * VINCULA PUNTEROS
	 *********************************************************************************************/

	private void vinculaDef(Bloque bloque) {
		List<Instruccion> insts = bloque.getInstrucciones();
		for (Instruccion i : insts){
			vinculaDef(i);
		}
	}

	private void vinculaDef(Instruccion i) {
		if (i == null) return;
		TiposInstruccion tipo = i.getTipoInstruccion();
		switch(tipo){
			case ASIG: {				
				vinculaDef((Asignacion) i);
			};  break;
			case BLOQUE: {			
				vinculaDef((Bloque) i);
			}; break;
			case BUCLE: {
				vinculaDef((Bucle) i);
			}; break;
			case CASOS: {
				vinculaDefCasos((List<Caso>) i);
			}; break;
			case DELETE: {
				vinculaDef((Delete) i);
			}; break;
			case IF: {
				vinculaDef((Condicional) i);
			}; break;
			case LLAMADA: {
				vinculaDef((Llamada) i);
			}; break;
			case NEW: {
				vinculaDef((New) i);
			}; break;
			case READ: {
				vinculaDef((Read) i);
			}; break;
			case WRITE: {
				vinculaDef((Write) i);
			}; break;
			default: break;
		}		
		
	}
	
	private void vinculaDef(Expresion expresion) {
		if (expresion == null) return;
		TipoExpresion te = expresion.getTipoExpresion();
		switch(te){
			case BINARIA: {
				vinculaDef((ExpresionBinaria)expresion);
			} break;
			case BOOLEAN: {
				vinculaDef((ExpresionBoolean)expresion);				
			} break;
			case DESIGNADOR: {
				vinculaDef((ExpresionDesignador)expresion);					
			} break;
			case DOUBLE: {
				vinculaDef((ExpresionDouble)expresion);
			} break;
			case INTEGER: {
				vinculaDef((ExpresionInteger)expresion);
			} break;
			case UNARIA: {
				vinculaDef((ExpresionUnaria)expresion);
			} break;
			default: break;		
		}		
	}

	private void vinculaDef(Designador designador) {
		if (designador == null) return;
		
		Expresion e = designador.getExpresion();
		Designador d = designador.getDesignador();
		String id = designador.getIdentificador();
		
		switch(designador.getTipo()){
			case ARRAY: {
				vinculaDef(d);
				vinculaDef(e);
				break;
			}
			case ID: {
				if (id.equalsIgnoreCase("null")){ break; }
				
				Object vinculo = declaracionDe(id);
				if (vinculo == null){
					throw new UnsupportedOperationException("Identificador no declarado. " + id);			
				}		
				insertaVinculo(designador, vinculo);
				
				break;
			}
			case STRUCT: {	
				vinculaDef(d);
				break;				
			}
			case PUNTERO: {				
				vinculaDef(d);
				break;
			}
		default: break;
		}
	}

	private void vinculaDef(ExpresionUnaria expresion) {
		vinculaDef(expresion.getExp());		
	}

	private void vinculaDef(ExpresionInteger expresion) { }

	private void vinculaDef(ExpresionDouble expresion) { }

	private void vinculaDef(ExpresionDesignador expresion) {
		vinculaDef(expresion.getValor());		
	}

	private void vinculaDef(ExpresionBoolean expresion) { }

	private void vinculaDef(ExpresionBinaria expresion) {
		vinculaDef(expresion.getExp0());
		vinculaDef(expresion.getExp1());
	}

	private void vinculaDef(Write i) {
		vinculaDef(i.getExpresion());		
	}

	private void vinculaDef(Read i) {
		vinculaDef(i.getDesignador());		
	}

	private void vinculaDef(New i) {
		vinculaDef(i.getDesignador());		
	}

	private void vinculaDef(Llamada i) {
		vinculaDefParams(i.getParams());		
	}

	private void vinculaDefParams(List<Expresion> params) {
		for (Expresion e : params){
			vinculaDef(e);
		}		
	}

	private void vinculaDef(Condicional i) {
		vinculaDefCasos(i.getCasos());		
	}

	private void vinculaDefCasos(List<Caso> casos) {
		for (Caso c : casos){
			vinculaDef(c);
		}		
	}

	private void vinculaDef(Delete i) {
		vinculaDef(i.getDesignador());		
	}

	private void vinculaDef(Bucle i) {
		vinculaDefCasos(i.getCasos());		
	}

	private void vinculaDef(Asignacion i) {
		vinculaDef(i.getExpresion());
		vinculaDef(i.getDesignador());
	}

	private void vinculaSubprogramasDef(List<DecSubprograma> decSubprogramas) {
		for (DecSubprograma ds : decSubprogramas){
			vinculaDef(ds);
		}		
	}

	private void vinculaDef(DecSubprograma ds) {
		vinculaDef(ds.getParametros());
		vinculaDef(ds.getPrograma());
		
	}

	private void vinculaDef(List<Parametro> parametros) {
		for (Parametro p : parametros){
			vinculaDef(p);
		}		
	}

	private void vinculaDef(Parametro p) {
		p.getIdentificador();
		p.getTipo();
		
	}

	private void vinculaDef(Programa programa) {
		vinculaTiposDef(programa.getDecTipos());
		vinculaVariablesDef(programa.getDecVariables());
		programa.getDecSubprogramas();
		programa.getBloque();		
	}

	private void vinculaVariablesDef(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable dv : decVariables){
			vinculaDef(dv);
		}		
	}

	private void vinculaDef(DecVariable dv) {
		dv.getIdentificador();
		dv.getTipo();
	}

	private void vinculaTiposDef(List<DecTipo> decTipos) {
		if (decTipos == null){ return; }
		for (DecTipo dt : decTipos){
			vinculaDef(dt);
		}
	}
	
	private void vinculaDef(DecTipo dt) {
		dt.getId();
		dt.getTipo();
		
		
	}

	/*********************************************************************************************
	 *  VINCULA NORMAL
	 *********************************************************************************************/

	private void vinculaTipos(List<DecTipo> decTipos) {
		if (decTipos == null) return;		
		for (DecTipo dt : decTipos){
			vincula(dt);
		}
	}

	private void vincula(DecTipo dt) {
		if (dt == null) return;
		String id = dt.getId();
		vinculaTipo(dt.getTipo());		
		if (!insertaID(id, dt)){			
			throw new UnsupportedOperationException("Identificador duplicado. " + id);			
		}
	}
	
	private void vinculaVariables(List<DecVariable> decVariables) {
		if (decVariables == null) return;
		for (DecVariable dv : decVariables){
			vincula(dv);
		}		
	}

	private void vincula(DecVariable dv) {
		if (dv == null) return;
		String id = dv.getIdentificador();
		vinculaTipo(dv.getTipo());
		if (!insertaID(id, dv)){			
			throw new UnsupportedOperationException("Identificador duplicado. " + id);			
		}	
	}

	private void vinculaSubprogramas(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null) return;
		for (DecSubprograma ds : decSubprogramas){
			String id = ds.getIdentificador();
			
			if (!insertaID(id, decSubprogramas)){			
				throw new UnsupportedOperationException("Identificador duplicado. " + id);			
			}	
			declaracionDe(id);
			abreBloque();
			insertaID(id, decSubprogramas);
			List<Parametro> params = ds.getParametros();
			if (params != null){
				for (Parametro p : params){
					insertaID(p.getIdentificador(), p);	
				}				
			}
			
			Programa p = ds.getPrograma();
			if (p != null){ 
				vinculaTipos(p.getDecTipos());
				vinculaVariables(p.getDecVariables());
				vinculaSubprogramas(p.getDecSubprogramas());		
				vincula(p.getBloque());		
			}
			cierraBloque();		
		}	
		
	}

	private void vinculaTipo(Tipo tipoInterno) {
		// System.out.println(tipoInterno.getTipoConcreto());
		switch(tipoInterno.getTipoConcreto()){
		case ARRAY:	
			TipoArray tipoArray = (TipoArray) tipoInterno;
			vinculaTipo(tipoArray.getTipoInterno());
			break;
		case BOOL:
			break;
		case DOUBLE:
			break;
		case IDENT:
			
			TipoID tipoId = (TipoID) tipoInterno;			
			
			Object dec = declaracionDe(tipoId.getId());

			if (dec == null){
				throw new UnsupportedOperationException("Identificador no declarado. ");				
			} else {
				insertaVinculo(tipoInterno, dec);
				insertaID(tipoId.getId(), tipoId);
			}
			
			break;
		case INT:	
			break;
		case POINTER:			
			break;
		case STRUCT:
			
			TipoStruct tipoStruct = (TipoStruct) tipoInterno;
			Map<String, Object> campos = new HashMap<String, Object>();
			for (DecTipo dt : tipoStruct.getTipos()){
				String id = dt.getId();
				if (campos.get(id) != null){
					throw new UnsupportedOperationException("Campo duplicado. "+id);						
				}
				campos.put(id, dt.getTipo());		
				vinculaTipo(dt.getTipo());
			}	
			
			insertaVinculo(tipoStruct, campos);
			
			break;
		default:
			break;
		}
		
	}

	private void vincula(Bloque bloque) {
		if (bloque == null) return;
		List<Instruccion> insts = bloque.getInstrucciones();
		for (Instruccion i : insts){
			vincula(i);
		}		
	}

	private void vincula(Expresion expresion) {
		if (expresion == null) return;
		TipoExpresion te = expresion.getTipoExpresion();
		switch(te){
			case BINARIA: {
				vincula((ExpresionBinaria)expresion);
			} break;
			case BOOLEAN: {
				vincula((ExpresionBoolean)expresion);				
			} break;
			case DESIGNADOR: {
				vincula((ExpresionDesignador)expresion);					
			} break;
			case DOUBLE: {
				vincula((ExpresionDouble)expresion);
			} break;
			case INTEGER: {
				vincula((ExpresionInteger)expresion);
			} break;
			case UNARIA: {
				vincula((ExpresionUnaria)expresion);
			} break;
			default: break;		
		}		
	}

	private void vincula(ExpresionInteger expresion) { }

	private void vincula(ExpresionDouble expresion) { }

	private void vincula(ExpresionBoolean expresion) { }
	
	private void vincula(ExpresionDesignador expresion) {
		vinculaDesignador(expresion.getValor());
	}

	private void vincula(ExpresionUnaria expresion) {
		vincula(expresion.getExp());
	}

	private void vincula(ExpresionBinaria expresion) {
		vincula(expresion.getExp0());
		vincula(expresion.getExp1());	
	}
	
	/// DESIGNADOR

	private void vinculaDesignador(Designador designador) {
		if (designador == null) return;
		
		Expresion e = designador.getExpresion();
		Designador d = designador.getDesignador();
		String id = designador.getIdentificador();
		
		switch(designador.getTipo()){
			case ARRAY: {
				vinculaDesignador(d);
				vincula(e);
				break;
			}
			case ID: {
				if (id.equalsIgnoreCase("null")){ break; }
				
				Object vinculo = declaracionDe(id);
				if (vinculo == null){
					throw new UnsupportedOperationException("Identificador no declarado. " + id);			
				}		
				insertaVinculo(designador, vinculo);
				
				break;
			}
			case STRUCT: {	
				vinculaDesignador(d);
				break;				
			}
			case PUNTERO: {
				vinculaDesignador(d);
				break;
			}
		default: break;
		}		
	}
	
	@SuppressWarnings("unchecked")
	private void vincula(Instruccion i) {
		if (i == null) return;
		TiposInstruccion tipo = i.getTipoInstruccion();
		switch(tipo){
			case ASIG: {				
				vincula((Asignacion) i);
			};  break;
			case BLOQUE: {			
				vincula((Bloque) i);
			}; break;
			case BUCLE: {
				vincula((Bucle) i);
			}; break;
			case CASOS: {
				vincula((List<Caso>) i);
			}; break;
			case DELETE: {
				vincula((Delete) i);
			}; break;
			case IF: {
				vincula((Condicional) i);
			}; break;
			case LLAMADA: {
				vincula((Llamada) i);
			}; break;
			case NEW: {
				vincula((New) i);
			}; break;
			case READ: {
				vincula((Read) i);
			}; break;
			case WRITE: {
				vincula((Write) i);
			}; break;
			default: break;
		}		
	}

	private void vincula(List<Caso> i) {
		if (i == null) return;
		for (Caso caso : i){
			vincula(caso.getBloque());
			vincula(caso.getExpresion());			
		}		
	}

	private void vincula(Write i) {
		if (i == null) return;
		vincula(i.getExpresion());		
	}

	private void vincula(Read i) {
		if (i == null) return;
		vinculaDesignador(i.getDesignador());
	}

	private void vincula(New i) {
		if (i == null) return;
		vinculaDesignador(i.getDesignador());
	}

	private void vincula(Llamada i) {
		if (i == null) return;
		String id = i.getIdentificador();
		Object vinculo = declaracionDe(id);
		if (vinculo == null){
			throw new UnsupportedOperationException("Identificador no declarado. " + id);			
		}	
		insertaVinculo(i, vinculo);
		
		List<Expresion> l = i.getParams();
		if (l != null){
			for (Expresion e : l){
				vincula(e);
			}
		}
	}

	private void vincula(Condicional i) {
		if (i == null) return;
		vincula(i.getCasos());
	}

	private void vincula(Delete i) {
		if (i == null) return;
		vinculaDesignador(i.getDesignador());
	}

	private void vincula(Bucle i) {
		if (i == null) return;
		vincula(i.getCasos());
	}

	private void vincula(Asignacion i) {
		if (i == null) return;
		vinculaDesignador(i.getDesignador());
		vincula(i.getExpresion());
	}
	

}

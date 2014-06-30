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
import modelo.tipos.TipoPuntero;
import modelo.tipos.TipoStruct;
import modelo.tipos.Tipos;

public class Vinculador {
	
	private Stack<Map<String, Object>> tablaDeSimbolos;
	private Map<Object, Object> vinculos;

	public Map<Object, Object> vincula(Programa p) {
		iniciaTS();
		abreBloque();
		vinculaTipos(p.getDecTipos());
		vinculaVariables(p.getDecVariables());
		vinculaDecSubprogramas(p.getDecSubprogramas());
		
		vinculaTiposDef(p.getDecTipos());
		vinculaVariablesDef(p.getDecVariables());
		vinculaDecSubprogramasDef(p.getDecSubprogramas());
		
		vincula(p.getBloque());
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
	
//	private void debugTS(String from){
//		System.out.println("--- Debug llamado desde " + from);
//		System.out.println("Nivel: " + (tablaDeSimbolos.size()-1));
//		System.out.println("Ã�mbito: " + tablaDeSimbolos.peek());
//	}

	private boolean insertaID(String id, Object declaracion){
//		debugTS("insertaID");
		Map<String, Object> ts = tablaDeSimbolos.peek();		
		if (ts.get(id) != null){ return false; }
		ts.put(id, declaracion);
		return true;
	}
	
	private Object declaracionDe(String id){		
		ListIterator<Map<String, Object>> it = tablaDeSimbolos.listIterator(tablaDeSimbolos.size());
		while (it.hasPrevious()){
			Object e = it.previous().get(id);
			if (e != null){
				return e;
			}
		}
		return null;
	}
	
	private boolean insertaVinculo(Object nodo, Object vinculo){
		if (vinculos.get(nodo) != null){ return false; }
		vinculos.put(nodo, vinculo);
		return true;
	}	
	
	/*********************************************************************************************
	 * VINCULA PUNTEROS
	 *********************************************************************************************/
	
	private void vinculaTiposDef(List<DecTipo> decTipos) {
		if (decTipos == null) return;
		for (DecTipo dt : decTipos){
			vinculaTipoDef(dt.getTipo());
		}		
	}

	private void vinculaDecSubprogramasDef(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null) return;
		for (DecSubprograma dt : decSubprogramas){
			vinculaDef(dt);
		}	
	}

	@SuppressWarnings("unchecked")
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
			case CAMPO_DE_STRUCT: {	
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

	private void vinculaDef(DecSubprograma ds) {
		vinculaDef(ds.getParametros());
		vinculaDef(ds.getPrograma());		
	}

	private void vinculaDef(List<Parametro> parametros) {
		if (parametros == null){ return; }
		for (Parametro p : parametros){
			vinculaTipoDef(p.getTipo());
		}		
	}
	
	private void vinculaDef(Programa programa) {
		vinculaTiposDef(programa.getDecTipos());
		vinculaVariablesDef(programa.getDecVariables());
		vinculaDecSubprogramasDef(programa.getDecSubprogramas());
	}

	private void vinculaVariablesDef(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable dv : decVariables){
			vinculaTipoDef(dv.getTipo());
		}		
	}

	private void vinculaTipoDef(Tipo tipo) {
		switch(tipo.getTipoConcreto()){
		case ARRAY: {
			TipoArray ta = (TipoArray) tipo;
			vinculaTipoDef(ta.getTipoInterno());
			break;
		}
		case STRUCT: {	
			TipoStruct ts = (TipoStruct) tipo;
			List<DecTipo> dts = ts.getTipos();
			for (DecTipo dt : dts){
				vinculaTipoDef(dt.getTipo());
			}
			break;				
		}
		case POINTER: {		
			TipoPuntero tp = (TipoPuntero) tipo;
			if (tp.getTipoPuntero().getTipoConcreto() == Tipos.IDENT){
				vinculaTipo(tp.getTipoPuntero());
			} else {
				vinculaTipoDef(tp.getTipoPuntero());
			}
			break;
		}
		default: break;
		}
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

	private void vinculaDecSubprogramas(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null) return;
		for (DecSubprograma ds : decSubprogramas){
			String id = ds.getIdentificador();
			
			if (!insertaID(id, ds)){			
				throw new UnsupportedOperationException("Identificador duplicado. " + id);			
			}	
			abreBloque();
			
			insertaID(id, ds);
			List<Parametro> params = ds.getParametros();
			if (params != null){
				for (Parametro p : params){
					if (!insertaID(p.getIdentificador(), p)){
						throw new UnsupportedOperationException("Parámetro duplicado. " + id);							
					}
					if (p.isPorValor()){
						vinculaTipo(p.getTipo()); // XXX: En el pdf no sale, pero parece ser necesario.
					} else {
						vinculaTipoDef(p.getTipo());
					}
				}				
			}
			
			Programa p = ds.getPrograma();
			if (p != null){ 
				vinculaTipos(p.getDecTipos());
				vinculaVariables(p.getDecVariables());
				vinculaVariablesDef(p.getDecVariables());
				vinculaDecSubprogramas(p.getDecSubprogramas());		
				vincula(p.getBloque());		
			}
			cierraBloque();		
		}	
		
	}

	private void vinculaTipo(Tipo tipo) {
		switch(tipo.getTipoConcreto()){
		case ARRAY:	
			TipoArray tipoArray = (TipoArray) tipo;
			vinculaTipo(tipoArray.getTipoInterno());
			break;
		case IDENT:
			
			TipoID tipoId = (TipoID) tipo;			
			
			Object dec = declaracionDe(tipoId.getId());
			
			if (dec == null){
				throw new UnsupportedOperationException("Identificador no declarado. "+tipoId.getId());				
			} else {
				insertaVinculo(tipo, dec);
				insertaID(tipoId.getId(), tipoId);
			}
			
			break;
		case POINTER:	
			TipoPuntero tipoPointer = (TipoPuntero) tipo;
			
			if (tipoPointer.getTipoPuntero().getTipoConcreto() != Tipos.IDENT){
				vinculaTipo(tipoPointer.getTipoPuntero());
			}
			
			break;
		case STRUCT:
			
			TipoStruct tipoStruct = (TipoStruct) tipo;
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
		vincula(expresion.getValor());
	}

	private void vincula(ExpresionUnaria expresion) {
		vincula(expresion.getExp());
	}

	private void vincula(ExpresionBinaria expresion) {
		vincula(expresion.getExp0());
		vincula(expresion.getExp1());	
	}
	
	/// DESIGNADOR

	private void vincula(Designador designador) {
		if (designador == null) return;
		
		Expresion e = designador.getExpresion();
		Designador d = designador.getDesignador();
		String id = designador.getIdentificador();
		
		switch(designador.getTipo()){
			case ARRAY: {
				vincula(d);
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
			case CAMPO_DE_STRUCT: {	
				vincula(d);
				break;				
			}
			case PUNTERO: {
				vincula(d);
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
		vincula(i.getDesignador());
	}

	private void vincula(New i) {
		if (i == null) return;
		vincula(i.getDesignador());
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
		vincula(i.getDesignador());
	}

	private void vincula(Bucle i) {
		if (i == null) return;
		vincula(i.getCasos());
	}

	private void vincula(Asignacion i) {
		if (i == null) return;
		vincula(i.getDesignador());
		vincula(i.getExpresion());
	}
	

}

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
	
	private Stack<Map<Object, Object>> pilaDeAmbitos;

	public Stack<Map<Object, Object>> vincula(Programa p) {
		iniciaTS();
		abreBloque();
		vinculaTipos(p.getDecTipos());
		vinculaVariables(p.getDecVariables());
		vinculaSubprogramas(p.getDecSubprogramas());
		vincula(p.getBloque());
		return pilaDeAmbitos;
	}

	private void iniciaTS() {
		pilaDeAmbitos = new Stack<Map<Object, Object>>();	
	}
	
	private void abreBloque() {
		pilaDeAmbitos.push(new HashMap<Object, Object>());		
	}

	private void cierraBloque() {
		pilaDeAmbitos.pop();
	}
	
	public Stack<Map<Object, Object>> getTS(){
		return pilaDeAmbitos;
	}
	
	private void debugTS(String from){
		System.out.println("--- Debug llamado desde " + from);
		System.out.println("Nivel: " + (pilaDeAmbitos.size()-1));
		System.out.println("Ámbito: " + pilaDeAmbitos.peek());
		
	}
	
	private void insertaVinculo(Object key, Object value){
		debugTS("insertaVinculo");
		Map<Object, Object> ts = pilaDeAmbitos.peek();		
		ts.put(key, value);
	}

	private boolean insertaID(String id, Object declaracion){
		debugTS("insertaID");
		Map<Object, Object> ts = pilaDeAmbitos.peek();		
		if (ts.get(id) != null){ return false; }
		ts.put(id, declaracion);
		return true;
	}
	
	private Object declaracionDe(String id){
		Map<Object, Object> ts = pilaDeAmbitos.peek();
		if (ts.get(id) == null){
			// Si no está en el ámbito actual, miro en los ámbitos superiores.
			ListIterator<Map<Object, Object>> it = pilaDeAmbitos.listIterator(pilaDeAmbitos.size()-1);
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

	private void vinculaTipos(List<DecTipo> decTipos) {
		if (decTipos == null) return;		
		for (DecTipo dt : decTipos){
			vincula(dt);
		}
	}

	private void vincula(DecTipo dt) {
		if (dt == null) return;
		vincula(dt.getTipo());
		String id = dt.getId();
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
		vincula(dv.getTipo());
		String id = dv.getIdentificador();
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

	private void vincula(Tipo tipoInterno) {
		// System.out.println(tipoInterno.getTipoConcreto());
		switch(tipoInterno.getTipoConcreto()){
		case ARRAY:	
			TipoArray tipoArray = (TipoArray) tipoInterno;
			vincula(tipoArray.getTipoInterno());
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
			case STRUCT: {
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

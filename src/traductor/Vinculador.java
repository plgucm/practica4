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
import modelo.instrucciones.Casos;
import modelo.instrucciones.Condicional;
import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.DecTipos;
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
	
	private Stack<Map<String, Object>> pilaDeAmbitos;

	public void vincula(Programa p) {
		iniciaTS();
		abreBloque();
		vincula(p.getDecTipos());
		vincula(p.getDecVariables());
		vincula(p.getDecSubprogramas());
		vincula(p.getBloque());
		cierraBloque();
	}
	
	private void iniciaTS() {
		pilaDeAmbitos = new Stack<Map<String, Object>>();	
	}
	
	private void abreBloque() {
		pilaDeAmbitos.push(new HashMap<String, Object>());		
	}

	private void cierraBloque() {
		if (pilaDeAmbitos.size() > 1)
			pilaDeAmbitos.pop();
	}
	
	public Stack<Map<String, Object>> getTS(){
		return pilaDeAmbitos;
	}
	
	private void debugTS(String from){
		/*System.out.println("--- Debug llamado desde " + from);
		System.out.println("Nivel: " + (pilaDeAmbitos.size()-1));
		System.out.println("Ámbito: " + pilaDeAmbitos.peek());
		*/
	}

	private boolean insertaID(String id, Object declaracion){
		debugTS("insertaID");
		Map<String, Object> ts = pilaDeAmbitos.peek();		
		if (ts.get(id) != null){ return false; }
		ts.put(id, declaracion);
		return true;
	}
	
	private Object declaracionDe(String id){
		Map<String, Object> ts = pilaDeAmbitos.peek();
		if (ts.get(id) == null){
			// Si no está en el ámbito actual, miro en los ámbitos superiores.
			ListIterator<Map<String, Object>> it = pilaDeAmbitos.listIterator(pilaDeAmbitos.size()-1);
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

	private void vincula(DecTipos decTipos) {
		if (decTipos == null) return;
		String id = decTipos.getIdentificador();
		
		vincula(decTipos.getTipo());
		
		if (!insertaID(id, decTipos)){			
			throw new UnsupportedOperationException("Identificador duplicado. " + id);			
		}
		
		DecTipos siguiente = decTipos.getDecTipos();
		if (siguiente != null) { 
			vincula(siguiente);	
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
			
			Object dec = declaracionDe(tipoId.getIdentificador());
			if (dec == null){
				throw new UnsupportedOperationException("Identificador no declarado. ");				
			} else {
				insertaID(tipoId.getIdentificador(), tipoId);
			}
			
			break;
		case INT:
			break;
		case POINTER:
			break;
		case STRUCT:
			
			TipoStruct tipoStruct = (TipoStruct) tipoInterno;
			List<String> ids = tipoStruct.getIdentificadores();
			List<Tipo> tipos = tipoStruct.getTipos();
			Map<String, Object> campos = new HashMap<String, Object>();
			for (int i = 0, l = ids.size(); i < l; i++){
				String id = ids.get(i);
				if (campos.get(id) != null){
					throw new UnsupportedOperationException("Campo duplicado. "+id);						
				}
				// System.out.println(tipos.get(i));
				campos.put(id, tipos.get(i));
			}	
			
			break;
		default:
			break;
		}
		
	}

	private void vincula(DecVariable decVariables) {
		if (decVariables == null) return;
		String id = decVariables.getIdentificador();
		if (!insertaID(id, decVariables)){			
			throw new UnsupportedOperationException("Identificador duplicado. " + id);			
		}	
		DecVariable siguiente = decVariables.getDecVariables();
		if (siguiente != null) { 
			vincula(siguiente);	
		}
	}

	private void vincula(DecSubprograma decSubprogramas) {
		if (decSubprogramas == null) return;
		String id = decSubprogramas.getIdentificador();
		if (id == null){ // SUBPROGRAMS			

			//System.out.println("Subprograms:"+id);
			
			DecSubprograma decsubprog = decSubprogramas.getDecSubprogramas();
			if (decsubprog != null){	
				vincula(decsubprog);	
			}
			
		} else { // SUBPROGRAM		

			//System.out.println("Program:"+id);
			
			if (!insertaID(id, decSubprogramas)){			
				throw new UnsupportedOperationException("Identificador duplicado. " + id);			
			}	
			declaracionDe(id);
			abreBloque();
			insertaID(id, decSubprogramas);
			List<Parametro> params = decSubprogramas.getParametros();
			for (Parametro p : params){
				vincula(p);			
			}
			
			Programa prog = decSubprogramas.getPrograma();
			if (prog != null){
				vinculaProg(prog);
			}
			cierraBloque();	
			
			DecSubprograma ds = decSubprogramas.getDecSubprogramas();
			if (ds != null){
				vincula(ds);
			}	
			
		}
	}

	private void vinculaProg(Programa p) {
		vincula(p.getDecTipos());
		vincula(p.getDecVariables());
		vincula(p.getDecSubprogramas());		
		vincula(p.getBloque());		
	}

	private void vincula(Parametro p) {
		if (p == null) return;
		String id = p.getIdentificador();
		if (!insertaID(id, p)){			
			throw new UnsupportedOperationException("Identificador duplicado. " + id);			
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

	private void vincula(ExpresionDesignador expresion) { }

	private void vincula(ExpresionBoolean expresion) { }

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
		
		if (e != null && d != null){
			vincula(d);
			vincula(e);
		} else if (d != null){
			vincula(d);
		} else {
			if (declaracionDe(id) == null){
				throw new UnsupportedOperationException("Identificador no declarado. " + id);			
			}			
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
				vincula((Casos) i);
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
		if (declaracionDe(id) == null){
			throw new UnsupportedOperationException("Identificador no declarado. " + id);			
		}	
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

	private void vincula(Casos i) {
		if (i == null) return;
		vincula(i.getBloque());
		vincula(i.getExpresion());
		Casos cs = i.getCasos();
		if (cs != null){
			vincula(cs);
		}
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

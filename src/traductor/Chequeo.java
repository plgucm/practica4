package traductor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import modelo.tipos.Tipos;

public class Chequeo {

	private Map<Object, Object> vinculos;
	private Map<Object, Tipos> tiposSimples;

	public Chequeo(Map<Object, Object> vinculos) {
		this.vinculos = vinculos;		
		this.tiposSimples = new HashMap<Object, Tipos>();
	}
	
	public Tipos getTipoSimple(Object object){
		return tiposSimples.get(object);
	}
	
	public void insertaTipo(Object obj, Tipos tipo){
		this.tiposSimples.put(obj, tipo);
	}
	
	private boolean compatibles(Tipos tipoA, Tipos tipoB) {
		return tipoA == tipoB;
	}
	
	private boolean esTipoPresentable(Tipos tipoA) {
		return tipoA != Tipos.ARRAY || tipoA != Tipos.POINTER || tipoA != Tipos.STRUCT;
	}

	private boolean esTipoLegible(Tipos tipoA) {
		return tipoA == Tipos.IDENT;
	}

	public void chequea(Programa p) {
		chequeaTipos(p.getDecTipos());
		chequeaVars(p.getDecVariables());
		chequeaSubs(p.getDecSubprogramas());
		
		if (p.getDecTipos() != null){
			for (DecTipo d : p.getDecTipos()){
				simplificaDefTipos(d);
			}
		}
		
		if (p.getDecVariables() != null){
			for (DecVariable d : p.getDecVariables()){
				simplificaDefTipos(d);					
			}
		}
		
		if (p.getDecSubprogramas() != null){
			for (DecSubprograma d : p.getDecSubprogramas()){
				simplificaDefTipos(d);			
			}
		}
		
		chequea(p.getBloque());		
	}

	private void chequeaTipos(List<DecTipo> decTipos) {
		if (decTipos == null){ return; }
		for (DecTipo d : decTipos){
			chequea(d);
		}		
	}

	private void chequea(DecTipo d) {
		chequea(d.getTipo());
	}

	private void chequeaVars(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable d : decVariables){
			chequea(d);
		}		
	}

	private void chequea(DecVariable d) {
		chequea(d.getTipo());
		
	}	
	

	private void chequea(Tipo tipo) {
		switch(tipo.getTipoConcreto()){
		case ARRAY:
			break;
		case BOOL:
			break;
		case DOUBLE:
			break;
		case IDENT:			
			break;
		case INT:
			break;
		case POINTER:
			break;
		case STRUCT:
			break;
		default:
			break;
		}
		
	}

	private void chequeaSubs(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null){ return; }
		for (DecSubprograma d : decSubprogramas){
			chequea(d);
		}		
	}

	private void chequea(DecSubprograma d) {
		chequeaParams(d.getParametros());
		chequea(d.getPrograma());
	}

	private void chequeaParams(List<Parametro> parametros) {
		if (parametros == null){ return; }
		for (Parametro p : parametros){
			chequea(p);
		}
	}

	private void chequea(Parametro p) {
		
		
	}

	private void chequea(Bloque bloque) {
		for (Instruccion i : bloque.getInstrucciones()){
			chequea(i);
		}		
	}

	@SuppressWarnings("unchecked")
	private void chequea(Instruccion i) {
		if (i == null) return;
		TiposInstruccion tipo = i.getTipoInstruccion();
		switch(tipo){
			case ASIG: {				
				chequea((Asignacion) i);
			};  break;
			case BLOQUE: {			
				chequea((Bloque) i);
			}; break;
			case BUCLE: {
				chequea((Bucle) i);
			}; break;
			case CASOS: {
				chequeaCasos((List<Caso>) i);
			}; break;
			case DELETE: {
				chequea((Delete) i);
			}; break;
			case IF: {
				chequea((Condicional) i);
			}; break;
			case LLAMADA: {
				chequea((Llamada) i);
			}; break;
			case NEW: {
				chequea((New) i);
			}; break;
			case READ: {
				chequea((Read) i);
			}; break;
			case WRITE: {
				chequea((Write) i);
			}; break;
			default: break;
		}		
	}

	private void chequea(Write i) {
		chequea(i.getExpresion());
		Tipos tipoA = getTipoSimple(i.getExpresion());
		if (tipoA == null || !esTipoPresentable(tipoA)){
			throw new UnsupportedOperationException("No es posible escribir valores de ese tipo.");			
		}
	}

	private void chequea(Read i) {
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || !esTipoLegible(tipoA)){
			throw new UnsupportedOperationException("No es posible leer valores de ese tipo.");			
		}
	}

	private void chequea(New i) {
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA){
			throw new UnsupportedOperationException("No es de tipo pointer.");			
		}
	}

	private void chequea(Delete i) {
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA){
			throw new UnsupportedOperationException("No es de tipo pointer.");			
		}
	}

	private void chequea(Llamada i) {
		DecSubprograma obj = (DecSubprograma) vinculos.get(i);
		
		DecSubprograma ds;
		if (obj instanceof DecSubprograma) {
			ds = (DecSubprograma) obj;			
		} else {
			throw new UnsupportedOperationException("Se está invocando a un objeto que no es un procedimiento.");			
		}		
		
		List<Expresion> argumentos = i.getParams();
		List<Parametro> parametros = ds.getParametros();
		
		int args = argumentos.size();
		int paramsDS = parametros.size();
		if (args != paramsDS){
			throw new UnsupportedOperationException("Discordancia en número de parámetros.");				
		}
		
		for (int j = 0; j < args; j++){
			Expresion e = argumentos.get(j);
			chequea(e);
			Parametro p = parametros.get(j);
			Tipos tipoArg = getTipoSimple(e);			
			if (!p.isPorValor() && e.getTipoExpresion() != TipoExpresion.DESIGNADOR){
				throw new UnsupportedOperationException("El parámetro i-esimo debe ser un designador.");				
			} else if (!compatibles(tipoArg, p.getTipo().getTipoConcreto())){
//				throw new UnsupportedOperationException(
//						"Tipos incompatibles en parámetro i-esimo. Esperado:"+p.getTipo().getTipoConcreto()
//						+" Recibido: " + tipoArg);				
			}				
		}
		
	}

	private void chequea(Condicional i) {
		chequeaCasos(i.getCasos());
		Tipos tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA){
			throw new UnsupportedOperationException("No es de tipo booleano la condición.");			
		}
	}

	private void chequea(Bucle i) {
		chequeaCasos(i.getCasos());
		Tipos tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA){
			throw new UnsupportedOperationException("No es de tipo booleano la condición.");			
		}
	}

	private void chequea(Asignacion i) {
		chequea(i.getExpresion());
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getExpresion());
		Tipos tipoB = getTipoSimple(i.getDesignador());
		if (tipoA == null || tipoB == null || !compatibles(tipoA, tipoB)){
			throw new UnsupportedOperationException("Incompatibilidad de tipos en asignación.");
		}
	}

	private void chequeaCasos(List<Caso> i) {
		for (Caso c : i){
			chequea(c);
		}		
	}

	private void chequea(Designador designador) {
		if (designador == null) return;
		
		Expresion e = designador.getExpresion();
		Designador d = designador.getDesignador();
		String id = designador.getIdentificador();
		
		switch(designador.getTipo()){
			case ARRAY: {
				chequea(d);
				chequea(e);
				

				insertaTipo(designador, Tipos.ARRAY);
				
				break;
			}
			case ID: {
				if (id.equalsIgnoreCase("null")){ insertaTipo(designador, null); break; }
				
				Object obj = vinculos.get(designador);
				
				if (!(obj instanceof DecVariable) && !(obj instanceof Parametro)){
					throw new UnsupportedOperationException("ID debe ser una variable o un parámetro.");					
				}
				
				if (obj instanceof DecVariable) {
					DecVariable dv = (DecVariable) obj;
					Tipos tipo = getTipoSimple(dv);
					insertaTipo(designador, tipo);					
				} else if (obj instanceof Parametro) {
					Parametro p = (Parametro) obj;
					Tipos tipo = getTipoSimple(p);
					insertaTipo(designador, tipo);
				}
				
				break;
			}
			case STRUCT: {	
				chequea(d);
				Tipos tipoD = getTipoSimple(d);
				
				if (tipoD == null){ insertaTipo(designador, tipoD); break; }

				Object obj = vinculos.get(designador);	
				System.out.println(obj);
				
				break;				
			}
			case PUNTERO: {
				chequea(d);
				
				insertaTipo(designador, Tipos.POINTER);
				
				break;
			}
		default: break;
		}				
	}

	private void chequea(Expresion expresion) {
		if (expresion == null) return;
		TipoExpresion te = expresion.getTipoExpresion();
		switch(te){
			case BINARIA: {
				chequea((ExpresionBinaria)expresion);
			} break;
			case BOOLEAN: {
				chequea((ExpresionBoolean)expresion);				
			} break;
			case DESIGNADOR: {
				chequea((ExpresionDesignador)expresion);					
			} break;
			case DOUBLE: {
				chequea((ExpresionDouble)expresion);
			} break;
			case INTEGER: {
				chequea((ExpresionInteger)expresion);
			} break;
			case UNARIA: {
				chequea((ExpresionUnaria)expresion);
			} break;
			default: break;		
		}				
	}
	
	
	private void chequea(ExpresionInteger expresion) {
		insertaTipo(expresion, Tipos.INT);
	}

	private void chequea(ExpresionDouble expresion) {
		insertaTipo(expresion, Tipos.DOUBLE);
	}

	private void chequea(ExpresionBoolean expresion) { 
		insertaTipo(expresion, Tipos.BOOL);
	}
	
	private void chequea(ExpresionDesignador expresion) {
		chequea(expresion.getValor());
		Tipos tipo = getTipoSimple(expresion.getValor());
		insertaTipo(expresion, tipo);
	}

	private void chequea(ExpresionUnaria expresion) {
		chequea(expresion.getExp());
		Tipos tipo = getTipoSimple(expresion.getExp());
		insertaTipo(expresion, tipo);		
	}

	private void chequea(ExpresionBinaria expresion) {
		Expresion exp0 = expresion.getExp0();
		Expresion exp1 = expresion.getExp1();
		
		chequea(exp0);
		chequea(exp1);	
		Tipos tipoA = getTipoSimple(exp0);
		Tipos tipoB = getTipoSimple(exp1);
		if (tipoA == null && tipoB == null){
			insertaTipo(expresion, null);
		} else {
			if (expresion.getOpBinario().esAritmetico()){
				insertaTipo(expresion, getTipoSimple(exp0));				
			} else if (expresion.getOpBinario().esLogico()){
				insertaTipo(expresion, Tipos.BOOL);	
			} else if (expresion.getOpBinario().esComparacion()){
				insertaTipo(expresion, Tipos.BOOL);					
			} else {
				insertaTipo(expresion, null);
			}
		}
	}
	
	private void simplificaDefTipos(DecSubprograma d) {
		List<Parametro> params = d.getParametros();
		for (Parametro p : params){
			simplificaDefTipo(p);
		}
		d.getPrograma();
	}

	private void simplificaDefTipo(Parametro p) {
		
		
	}

	private void simplificaDefTipos(DecVariable d) {
		
	}

	private void simplificaDefTipos(DecTipo d) {
		
	}

}

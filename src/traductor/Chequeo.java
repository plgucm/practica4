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
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoPuntero;
import modelo.tipos.TipoStruct;
import modelo.tipos.Tipos;

public class Chequeo {

	private Map<Object, Object> vinculos;
	private Map<Object, Tipos> tiposSimples;
	private Map<Object, Integer> numLineas;

	public Chequeo(Map<Object, Integer> numLineas, Map<Object, Object> vinculos) {
		this.vinculos = vinculos;		
		this.numLineas = numLineas;
		this.tiposSimples = new HashMap<Object, Tipos>();
	}
	
	/*************************************************************************************
	 **** FUNCIONES GENERALES
	 *************************************************************************************/


	private String lineaError(Object nodo) {
		return "Error en línea " + numLineas.get(nodo) + ": ";
	}
		
	public Tipos getTipoSimple(Object object){
		return tiposSimples.get(object);
	}
	
	public void insertaTipoSimple(Object obj, Tipos tipo){
		this.tiposSimples.put(obj, tipo);
	}
	
	private boolean compatibles(Tipos tipoA, Tipos tipoB) {
		return tipoA == tipoB;
	}
	
	private boolean esTipoPresentable(Tipos tipoA) {
		return tipoA != Tipos.ARRAY && tipoA != Tipos.POINTER && tipoA != Tipos.STRUCT;
	}

	private boolean esTipoLegible(Tipos tipoA) {
		return tipoA == Tipos.IDENT || tipoA == Tipos.POINTER;
	}

	public void chequea(Programa p) {
		chequeaDecTipos(p.getDecTipos());
		simplificaDefTipos(p.getDecTipos());
		chequeaDecVars(p.getDecVariables());
		simplificaDefVars(p.getDecVariables());
		chequeaDecSubs(p.getDecSubprogramas());	
		simplificaDefSubs(p.getDecSubprogramas());
		chequea(p.getBloque());		
	}
	
	/*************************************************************************************
	 **** SIMPLIFICA TIPOS
	 *************************************************************************************/

	private void simplificaDefSubs(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null){ return; }
		for (DecSubprograma d : decSubprogramas){
			simplificaDefTipos(d);			
		}				
	}

	private void simplificaDefVars(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable d : decVariables){
			simplificaDefTipos(d);					
		}		
	}

	private void simplificaDefTipos(List<DecTipo> decTipos) {
		if (decTipos == null){ return; }
		for (DecTipo d : decTipos){
			simplificaDefTipos(d);
		}	
	}
	
	private void simplificaDefTipos(DecSubprograma d) {
		List<Parametro> params = d.getParametros();
		if (params == null){ return; }
		for (Parametro p : params){
			simplificaDefTipo(p);
		}
		Programa p = d.getPrograma();
		if (p != null){
			simplificaDefTipos(p.getDecTipos());
			simplificaDefVars(p.getDecVariables());
			simplificaDefSubs(p.getDecSubprogramas());
		}
	}

	private void simplificaDefTipo(Parametro p) {		
		insertaTipoSimple(p, tipoSimplificado(p.getTipo()));
	}

	private void simplificaDefTipos(DecVariable d) {
		insertaTipoSimple(d, tipoSimplificado(d.getTipo()));
	}

	private void simplificaDefTipos(DecTipo d) {
		insertaTipoSimple(d, tipoSimplificado(d.getTipo()));
	}
	
	private Tipos tipoSimplificado(Tipo tipo) {
		switch (tipo.getTipoConcreto()) {
		case ARRAY:
			TipoArray ta = (TipoArray) tipo;
			Tipos tSimplArr = tipoSimplificado(ta.getTipoInterno());
			insertaTipoSimple(tipo, tSimplArr);			
			return Tipos.ARRAY;
		case IDENT:
			Object obj = vinculos.get(tipo);
			Tipos tSimplID = null;
			if (obj instanceof Tipo){
				tSimplID = tipoSimplificado((Tipo)obj);	
				insertaTipoSimple(tipo, tSimplID);
			}
			return Tipos.IDENT;
		case STRUCT:
			TipoStruct ts = (TipoStruct) tipo;
			List<DecTipo> dts = ts.getTipos();
			for (DecTipo dt : dts){
				Tipos tSimplStr = tipoSimplificado(dt.getTipo());
				insertaTipoSimple(dt.getTipo(), tSimplStr);
			}
			return Tipos.STRUCT;
		case POINTER:
			TipoPuntero tp = (TipoPuntero) tipo;
			Tipos tSimplPointer = tipoSimplificado(tp.getTipoPuntero());
			insertaTipoSimple(tipo, tSimplPointer);			
			return Tipos.POINTER;
		case BOOL:
			return Tipos.BOOL;
		case INT: 
			return Tipos.INT;
		case DOUBLE:
			return Tipos.DOUBLE;
		case NULL:
			return Tipos.NULL;
		default:
			return null;
		}
	}	
	
	
	

	/*************************************************************************************
	 **** CHEQUEO DECLARACIONES
	 *************************************************************************************/

	private void chequeaDecTipos(List<DecTipo> decTipos) {
		if (decTipos == null){ return; }
		for (DecTipo d : decTipos){
			chequea(d.getTipo());
		}		
	}

	private void chequea(Tipo tipo) {
		switch (tipo.getTipoConcreto()) {
		case ARRAY:
			TipoArray ta = (TipoArray) tipo;
			chequea(ta.getTipoInterno());
			break;
		case IDENT:
//			TipoID tid = (TipoID) tipo;
			Object obj = vinculos.get(tipo);
			if (obj == null){ break; }
			if (!(obj instanceof DecTipo)){
				throw new UnsupportedOperationException("El identificador debería ser uno de tipo.");
			}	
			break;		
		case STRUCT:
			TipoStruct ts = (TipoStruct) tipo;
			List<DecTipo> dts = ts.getTipos();
			for (DecTipo dt : dts){
				chequea(dt.getTipo());
			}
			break;
		case POINTER:
			TipoPuntero tp = (TipoPuntero) tipo;
			chequea(tp.getTipoPuntero());
			break;
		default:
			break;
		}		
	}

	private void chequeaDecVars(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable d : decVariables){
			chequea(d.getTipo());
		}		
	}

	private void chequeaDecSubs(List<DecSubprograma> decSubprogramas) {
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
			chequea(p.getTipo());
		}
	}
	
	
	/*************************************************************************************
	 **** CHEQUEO BLOQUE
	 *************************************************************************************/

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
			throw new UnsupportedOperationException("No es posible leer valores de ese tipo."
													+ " Tipo:" + tipoA +
													" de " + i.getDesignador().getIdentificador());			
		}
	}

	private void chequea(New i) {
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA){
			throw new UnsupportedOperationException("No es de tipo pointer." + " Tipo: " + tipoA);			
		}
	}

	private void chequea(Delete i) {
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA){
			throw new UnsupportedOperationException("No es de tipo pointer."+ " Tipo:"+tipoA);			
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
		
		int args = 0;
		if (argumentos != null){
			args = argumentos.size();	
		}
		
		int paramsDS = 0;
		if (parametros != null){
			paramsDS = parametros.size();
		}
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
				throw new UnsupportedOperationException(
						"Tipos incompatibles en parámetro i-esimo. Esperado:"+p.getTipo().getTipoConcreto()
						+" Recibido: " + tipoArg);				
			}				
		}
		
	}

	private void chequea(Condicional i) {
		chequeaCasos(i.getCasos());
		Tipos tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA){
			throw new UnsupportedOperationException("No es de tipo booleano la condición. " + "Tipo: "+tipoA);			
		}
	}

	private void chequea(Bucle i) {
		chequeaCasos(i.getCasos());
		Tipos tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA){
			throw new UnsupportedOperationException("No es de tipo booleano la condición. " + "Tipo: "+tipoA);				
		}
	}

	private void chequea(Asignacion i) {
		chequea(i.getExpresion());
		Tipos tipoB = getTipoSimple(i.getExpresion());
		chequea(i.getDesignador());
		Tipos tipoA = getTipoSimple(i.getDesignador());
		
		if (tipoA == null || tipoB == null || !compatibles(tipoA, tipoB)){
			throw new UnsupportedOperationException(lineaError(i)+"Incompatibilidad de tipos en asignación. "
					+ "TipoA: " + tipoA + " TipoB: " + tipoB+ " " + i.getDesignador().getIdentificador());
		}
	}

	private void chequeaCasos(List<Caso> i) {
		for (Caso c : i){
			chequea(c);
		}		
	}
	
	private void chequea(Caso c){
		chequea(c.getExpresion());
		chequea(c.getBloque());
	}

	private void chequea(Designador designador) {
		if (designador == null) return;
		
		Expresion e = designador.getExpresion();
		Designador d = designador.getDesignador();
		String id = designador.getIdentificador();
		
//		System.out.println("CD de id:" + id + ", des: " + d + ", exp: " + e + " tipoSimple: " + getTipoSimple(d));	
		
		switch(designador.getTipo()){
			case ARRAY: {
				
				chequea(d);
				chequea(e);
				
				Tipos tipoDes = getTipoSimple(d);
				Tipos tipoExp = getTipoSimple(e);
				
				if (d == null || e == null){
					insertaTipoSimple(designador, null);
				} else if (tipoDes != Tipos.ARRAY){
					throw new UnsupportedOperationException("El designador debería ser de tipo array.");
				} else if (tipoExp != Tipos.INT){
					throw new UnsupportedOperationException("El índice debería ser de tipo entero.");					
				} else {
					insertaTipoSimple(designador, tipoDes);					
				}		
				
								
				break;
			}
			case ID: {
				
				if (id.equalsIgnoreCase("null")){ 
					insertaTipoSimple(designador, Tipos.NULL); 
					break; 
				}
				
				Object obj = vinculos.get(designador);				
				
				if (!(obj instanceof DecVariable) && !(obj instanceof Parametro)){
					throw new UnsupportedOperationException("ID debe ser una variable o un parámetro.");					
				}
				
				Tipos tipoConcreto = null;
				if (obj instanceof DecVariable){
					tipoConcreto = ((DecVariable) obj).getTipo().getTipoConcreto();
				} else {
					Parametro p = ((Parametro) obj);
					if (p.isPorValor()) {
						tipoConcreto = p.getTipo().getTipoConcreto();						
					} else {
						tipoConcreto = Tipos.POINTER;
					}					
				}	
				
				
				insertaTipoSimple(designador, tipoConcreto);			
				
				break;
			}
			case CAMPO_DE_STRUCT: {	
				chequea(d);
				
				Tipos tipoD = getTipoSimple(d);				
				
				System.out.println("el campo " + id + " del struct " + d.getDesignador().getIdentificador() +
									" es de tipo " + tipoD);
				
				
				if (tipoD == null){
					insertaTipoSimple(designador, Tipos.NULL); 
					break; 
				}

				if (tipoD == Tipos.STRUCT){
				
					Object c = vinculos.get(d.getDesignador());
					if (c != null){			
						Tipos tipoSimple = null;
						if (c instanceof Parametro){
							Parametro p = (Parametro) c;
							tipoSimple = getTipoSimple(p.getTipo());
							System.out.println(getTipoSimple(p));				
						} 
						
						
						insertaTipoSimple(designador, tipoSimple);
					} else {
						throw new UnsupportedOperationException("Campo inexistente.");
					}
					
				} else {
					throw new UnsupportedOperationException(lineaError(designador)+
							"El designador debería ser de tipo registro."+tipoD);
				}				
				
				break;				
			}
			case PUNTERO: {
				chequea(d);
				
				Tipos tipo = getTipoSimple(d);	
				if (tipo != Tipos.POINTER){
					throw new UnsupportedOperationException(lineaError(designador)+
							"El designador debería ser de tipo puntero."+
							"\nEl tipo era: " + tipo);
				} else {
					insertaTipoSimple(designador, tipo);
				}
				
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
		insertaTipoSimple(expresion, Tipos.INT);
	}

	private void chequea(ExpresionDouble expresion) {
		insertaTipoSimple(expresion, Tipos.DOUBLE);
	}

	private void chequea(ExpresionBoolean expresion) { 
		insertaTipoSimple(expresion, Tipos.BOOL);
	}
	
	private void chequea(ExpresionDesignador expresion) {
		chequea(expresion.getValor());
//		System.out.println("chequea ExpresionDesignador: "+expresion.getValor());
		Tipos tipo = getTipoSimple(expresion.getValor());
		insertaTipoSimple(expresion, tipo);
	}

	private void chequea(ExpresionUnaria expresion) {
		chequea(expresion.getExp());
		Tipos tipo = getTipoSimple(expresion.getExp());
		insertaTipoSimple(expresion, tipo);		
	}

	private void chequea(ExpresionBinaria expresion) {
		Expresion exp0 = expresion.getExp0();
		Expresion exp1 = expresion.getExp1();
		
		chequea(exp0);
		chequea(exp1);	
		Tipos tipoA = getTipoSimple(exp0);
		Tipos tipoB = getTipoSimple(exp1);
		if (tipoA == null && tipoB == null){
			insertaTipoSimple(expresion, null);
		} else {
			if (expresion.getOpBinario().esAritmetico()){
				insertaTipoSimple(expresion, getTipoSimple(exp0));				
			} else if (expresion.getOpBinario().esLogico()){
				insertaTipoSimple(expresion, Tipos.BOOL);	
			} else if (expresion.getOpBinario().esComparacion()){
				insertaTipoSimple(expresion, Tipos.BOOL);					
			} else {
				insertaTipoSimple(expresion, Tipos.NULL);
			}
		}
	}

}

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
import modelo.tipos.TipoBool;
import modelo.tipos.TipoDouble;
import modelo.tipos.TipoEntero;
import modelo.tipos.TipoID;
import modelo.tipos.TipoPuntero;
import modelo.tipos.TipoStruct;
import modelo.tipos.Tipos;

public class Chequeo {

	private Map<Object, Object> vinculos;
	private Map<Object, Object> tiposSimples;
	private Map<Object, Integer> numLineas;

	public Chequeo(Map<Object, Integer> numLineas, Map<Object, Object> vinculos) {
		this.vinculos = vinculos;		
		this.numLineas = numLineas;
		this.tiposSimples = new HashMap<Object, Object>();
	}
	
	/*************************************************************************************
	 **** FUNCIONES GENERALES
	 *************************************************************************************/


	private String lineaError(Object nodo) {
		return "Error en línea " + numLineas.get(nodo) + ": ";
	}
		
	public Tipo getTipoSimple(Object object){
		return (Tipo) tiposSimples.get(object);
	}
	
	public void insertaTipoSimple(Object obj, Object tipo){
		this.tiposSimples.put(obj, tipo);
	}
	
	private boolean compatibles(Tipos tipoA, Tipos tipoB) {
		return tipoA == tipoB;
	}
	
	private boolean esTipoPresentable(Tipos tipoA) {
		return tipoA != Tipos.ARRAY && tipoA != Tipos.POINTER && tipoA != Tipos.STRUCT;
	}

	private boolean esTipoLegible(Tipos tipoA) {
		return tipoA == Tipos.DOUBLE || tipoA == Tipos.BOOL || tipoA == Tipos.INT;
	}
	
	/*************************************************************************************
	 **** SIMPLIFICA TIPOS
	 *************************************************************************************/

	private void simplificaDefSubs(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null){ return; }
		for (DecSubprograma d : decSubprogramas){
			simplificaDefTiposSub(d);			
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
	
	private void simplificaDefTiposSub(DecSubprograma d) {
		Programa p = d.getPrograma();
		if (p != null){
			simplificaDefTipos(p.getDecTipos());
			simplificaDefVars(p.getDecVariables());
		}
		List<Parametro> params = d.getParametros();		
		if (params != null){ 
			for (Parametro par : params){
				simplificaDefTipo(par);
			}
		}
		if (p != null){
			simplificaDefSubs(p.getDecSubprogramas());
		}
	}

	private void simplificaDefTipo(Parametro p) {		
		Tipo tipo = tipoSimplificado(p.getTipo());
		insertaTipoSimple(p, tipo);
	}

	private void simplificaDefTipos(DecVariable d) {
		insertaTipoSimple(d, tipoSimplificado(d.getTipo()));
	}

	private void simplificaDefTipos(DecTipo d) {
		insertaTipoSimple(d, tipoSimplificado(d.getTipo()));
	}
	
	private Tipo tipoSimplificado(Tipo tipo) {
		switch (tipo.getTipoConcreto()) {
		case ARRAY:
			TipoArray ta = (TipoArray) tipo;
			Tipo tSimplArr = tipoSimplificado(ta.getTipoInterno());
			insertaTipoSimple(ta, tSimplArr);			
			return tipo;
		case IDENT:
			
			Object tID = tipo;	
			
			////System.out.println("ANTES: " + tID + " id: " + ((TipoID)tID).getId());
			while (tID instanceof TipoID){
				Object obj = vinculos.get(tID);
				if (obj instanceof DecTipo){
					tID = ((DecTipo)obj).getTipo();		
					insertaTipoSimple(tipo, (Tipo)tID);	
				} else {
					break;
				}				
			}	
			////System.out.println("DESPUES: " + tID);	
					
			return (Tipo) tID;
		case STRUCT:
			TipoStruct ts = (TipoStruct) tipo;
			List<DecTipo> dts = ts.getTipos();
			for (DecTipo dt : dts){
				Tipo tSimplStr =  tipoSimplificado(dt.getTipo());
				insertaTipoSimple(tipo, tSimplStr);
			}
			return tipo;
		case POINTER:
			TipoPuntero tp = (TipoPuntero) tipo;
			Tipo tSimplPointer = tipoSimplificado(tp.getTipoPuntero());
			insertaTipoSimple(tipo, tSimplPointer);			
			return tipo;
		case BOOL:
			return tipo;
		case INT: 
			return tipo;
		case DOUBLE:
			return tipo;
		case NULL:
		default:
			return null;
		}
	}	
	
	/*************************************************************************************
	 **** CHEQUEO PROGRAMA
	 *************************************************************************************/

	public void chequea(Programa p) {
		
		chequeaDecTipos(p.getDecTipos());
		simplificaDefTipos(p.getDecTipos());	
		
		chequeaDecVars(p.getDecVariables());	
		simplificaDefVars(p.getDecVariables());

		simplificaDefSubs(p.getDecSubprogramas());
		chequeaDecSubs(p.getDecSubprogramas());	
		
		chequea(p.getBloque());		
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

	private void chequeaDecVars(List<DecVariable> decVariables) {
		if (decVariables == null){ return; }
		for (DecVariable d : decVariables){
			chequea(d.getTipo());
		}		
	}

	private void chequeaDecSubs(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null){ return; }
		for (DecSubprograma d : decSubprogramas){
			chequeaParams(d.getParametros());	
			chequea(d.getPrograma());
		}		
	}	
	
	private void chequeaParams(List<Parametro> parametros) {
		if (parametros == null){ return; }
		for (Parametro p : parametros){			
			chequea(p.getTipo());
		}
	}

	private void chequea(Tipo tipo) {
		switch (tipo.getTipoConcreto()) {
		case ARRAY:
			TipoArray ta = (TipoArray) tipo;
			chequea(ta.getTipoInterno());
			break;
		case IDENT:
			Object obj = vinculos.get(tipo);
			if (obj == null){ break; }
			if (!(obj instanceof DecTipo)){
				//throw new UnsupportedOperationException(lineaError(tipo)+"El identificador debería ser uno de tipo.");
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
		Tipo tipoA = getTipoSimple(i.getExpresion());
		if (tipoA == null || !esTipoPresentable(tipoA.getTipoConcreto())){
			//throw new UnsupportedOperationException(lineaError(i)+"No es posible escribir valores de ese tipo.");			
		}
	}

	private void chequea(Read i) {
		chequea(i.getDesignador());
		Tipo tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || !esTipoLegible(tipoA.getTipoConcreto())){
			/*throw new UnsupportedOperationException(lineaError(i)+"No es posible leer valores de ese tipo."
													+ " Tipo:" + tipoA +
													" de " + i.getDesignador().getIdentificador());			*/
		}
	}

	private void chequea(New i) {
		chequea(i.getDesignador());
		Tipo tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA.getTipoConcreto()){
			//throw new UnsupportedOperationException(lineaError(i)+"No es de tipo pointer." + " Tipo: " + tipoA);			
		}
	}

	private void chequea(Delete i) {
		chequea(i.getDesignador());
		Tipo tipoA = getTipoSimple(i.getDesignador());
		if (tipoA == null || Tipos.POINTER != tipoA.getTipoConcreto()){
			//throw new UnsupportedOperationException(lineaError(i)+"No es de tipo pointer."+ " Tipo:"+tipoA);			
		}
	}

	private void chequea(Llamada i) {
		DecSubprograma obj = (DecSubprograma) vinculos.get(i);
		
		DecSubprograma ds;
		if (obj instanceof DecSubprograma) {
			ds = (DecSubprograma) obj;			
		} else {
			throw new UnsupportedOperationException(lineaError(i)+"Se está invocando a un objeto que no es un procedimiento.");			
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
			throw new UnsupportedOperationException(lineaError(i)+"Discordancia en número de parámetros.");				
		}
		
		for (int j = 0; j < args; j++){
			Expresion e = argumentos.get(j);
			chequea(e);
			Parametro p = parametros.get(j);
			Tipo tipoArg = getTipoSimple(e);			
			if (!p.isPorValor() && e.getTipoExpresion() != TipoExpresion.DESIGNADOR){
				throw new UnsupportedOperationException(lineaError(i)+"El parámetro i-esimo debe ser un designador.");				
			} /*else if (!compatibles(tipoArg.getTipoConcreto(), p.getTipo().getTipoConcreto())){
				throw new UnsupportedOperationException(
						lineaError(i)+"Tipos incompatibles en parámetro i-esimo. Esperado:"+p.getTipo().getTipoConcreto()
						+" Recibido: " + tipoArg);				
			}*/		
			
		}
		
	}

	private void chequea(Condicional i) {
		chequeaCasos(i.getCasos());
		Tipo tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA.getTipoConcreto()){
			//throw new UnsupportedOperationException(lineaError(i)+"No es de tipo booleano la condición. " + "Tipo: "+tipoA);			
		}
	}

	private void chequea(Bucle i) {
		chequeaCasos(i.getCasos());
		Tipo tipoA = getTipoSimple(i.getCasos());
		if (tipoA == null || Tipos.BOOL != tipoA.getTipoConcreto()){
			//throw new UnsupportedOperationException(lineaError(i)+"No es de tipo booleano la condición. " + "Tipo: "+tipoA);				
		}
	}

	private void chequea(Asignacion i) {
		chequea(i.getExpresion());
		Tipo tipoB = getTipoSimple(i.getExpresion());
		chequea(i.getDesignador());
		Tipo tipoA = getTipoSimple(i.getDesignador());
		
		if (tipoA == null || tipoB == null || !compatibles(tipoA.getTipoConcreto(), tipoB.getTipoConcreto())){
			/*throw new UnsupportedOperationException(lineaError(i)+"Incompatibilidad de tipos en asignación. "
					+ "TipoA: " + tipoA + " TipoB: " + tipoB+ " " + i.getDesignador().getIdentificador());*/
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
	
		//System.out.println(tiposSimples);
		
		switch(designador.getTipo()){
			case ARRAY: {
				
				chequea(d);
				chequea(e);
				
				Tipo tipoDes = getTipoSimple(d);
				Tipo tipoExp = getTipoSimple(e);
				
				if (d == null || e == null){
					insertaTipoSimple(designador, null);
				} else if (tipoDes.getTipoConcreto() != Tipos.ARRAY){
					throw new UnsupportedOperationException("El designador debería ser de tipo array.");
				} else if (tipoExp.getTipoConcreto() != Tipos.INT){
					throw new UnsupportedOperationException("El índice debería ser de tipo entero.");					
				} else {
					insertaTipoSimple(designador, tipoDes);					
				}	
				
								
				break;
			}
			case ID: {
				
				if (id.equalsIgnoreCase("null")){ 
					insertaTipoSimple(designador, null); 
					break; 
				}
				
				Object obj = vinculos.get(designador);				
				
				if (!(obj instanceof DecVariable) && !(obj instanceof Parametro)){
					throw new UnsupportedOperationException("ID debe ser una variable o un parámetro.");					
				}
				
				Tipo tipoConcreto = null;
				if (obj instanceof DecVariable){
					tipoConcreto =  getTipoSimple(((DecVariable)obj));
				} else {	
					tipoConcreto = getTipoSimple(((Parametro)obj));
				}	
				
				//System.out.println(id + " tiene de tipo " + tipoConcreto);
				insertaTipoSimple(designador, tipoConcreto);			
				
				break;
			}
			case CAMPO_DE_STRUCT: {	
				chequea(d);
				
				Tipo tipoD = getTipoSimple(d);	
				if (tipoD == null){
					insertaTipoSimple(designador, null); 
					break; 
				}

				//System.out.println("<<<<<>>>>>>>>>>");
				//System.out.println(tipoD);
				//System.out.println("<<<<<>>>>>>>>>>");
				
			//	TipoStruct tStruct = (TipoStruct) tipoD;
				
				
				
				/*List<DecTipo> dts = tStruct.getTipos();
				for (DecTipo dt : dts){
					if (dt.getId().equalsIgnoreCase(designador.getIdentificador())){
						//System.out.println("ENCONTRADO::::::::::::::::::");
					}
					
					
				}*/
						
				// buscar: designador.getIdentificador()
				// en: getTipoSimple(d.getDesignador()); que debería ser el struct
				// en: cualquier DecTipo de los que hay.
				
				
				////System.out.println("tipo simple de " + designador + " es " + tStruct);				
				
				
				if (tipoD.getTipoConcreto() == Tipos.POINTER){
				
					Object c = vinculos.get(d.getDesignador());
					if (c != null){			
						Tipo tipoSimple = null;
						if (c instanceof Parametro){
							Parametro p = (Parametro) c;
							
							tipoSimple = getTipoSimple(p.getTipo());
							//System.out.println("CAMPO_DE_STRUCT:"+p.getIdentificador()+" "+tipoSimple);				
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
				
				Tipo tipo = getTipoSimple(d);	
				if (tipo.getTipoConcreto() != Tipos.POINTER){
					/*throw new UnsupportedOperationException(lineaError(designador)+
							"El designador debería ser de tipo puntero."+
							"\nEl tipo era: " + tipo);*/
				} else {
					insertaTipoSimple(designador, tipo);
				}
				
				break;
			}
		default: break;
		}	
		
		// //System.out.println("CD de id:" + id + ", des: " + d + ", exp: " + e + " tipoSimple: " + getTipoSimple(designador));				
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
		insertaTipoSimple(expresion, new TipoEntero());
	}

	private void chequea(ExpresionDouble expresion) {
		insertaTipoSimple(expresion, new TipoDouble());
	}

	private void chequea(ExpresionBoolean expresion) { 
		insertaTipoSimple(expresion, new TipoBool());
	}
	
	private void chequea(ExpresionDesignador expresion) {
		chequea(expresion.getValor());
		Tipo tipo = getTipoSimple(expresion.getValor());
		insertaTipoSimple(expresion, tipo);
	}

	private void chequea(ExpresionUnaria expresion) {
		chequea(expresion.getExp());
		Tipo tipo = getTipoSimple(expresion.getExp());
		insertaTipoSimple(expresion, tipo);		
	}

	private void chequea(ExpresionBinaria expresion) {
		Expresion exp0 = expresion.getExp0();
		Expresion exp1 = expresion.getExp1();
		
		chequea(exp0);
		chequea(exp1);	
		Tipo tipoA = getTipoSimple(exp0);
		Tipo tipoB = getTipoSimple(exp1);
		if (tipoA == null && tipoB == null){
			insertaTipoSimple(expresion, null);
		} else {
			if (expresion.getOpBinario().esAritmetico()){
				insertaTipoSimple(expresion, getTipoSimple(exp0));				
			} else if (expresion.getOpBinario().esLogico()){
				insertaTipoSimple(expresion, new TipoBool());	
			} else if (expresion.getOpBinario().esComparacion()){
				insertaTipoSimple(expresion, new TipoBool());					
			} else {
				insertaTipoSimple(expresion, null);
			}
		}
	}

}

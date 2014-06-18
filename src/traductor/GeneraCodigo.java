package traductor;


import static traductor.LenguajeP.DESAPILA_IND;
import static traductor.LenguajeP.generaInicio;

import java.util.List;

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
import modelo.operadores.OpBinario;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoStruct;

public class GeneraCodigo {	
	
	private int dir, nivel, cinst;
	private Decoracion d;
	private TablaDeSimbolos ts;	
	
	public GeneraCodigo(TablaDeSimbolos ts, Decoracion d) {	
		dir = nivel = 0;
		this.d = d;
		this.ts = ts;
	}

	public void generaCodigo(Programa p) {
		asignaEspacioDesdeRaiz(p);
		codigoProgramaDesdeRaiz(p);	
		System.out.println("Código generado.");
	}

	private void codigoProgramaDesdeRaiz(Programa p) {		
		codigoSubprograma(p.getDecSubprogramas());		
		codigo(p.getBloque());				
		String inicio = generaInicio(
						(Integer)d.getDecoracion(p.getDecVariables()).get("tam")+
						(Integer)d.getDecoracion(p).get("finDatos")+1, cinst);
		d.insertaInfoEnNodo(p, "cod", inicio);		
	}

	private String codigoInstruccion(Instruccion i) {
		if (i == null) return "";
		TiposInstruccion tipo = i.getTipoInstruccion();
		if (tipo == TiposInstruccion.ASIG) {
			return codigo((Asignacion) i);
		} else if (tipo == TiposInstruccion.BLOQUE) {
			return codigo((Bloque) i);
		} else if (tipo == TiposInstruccion.BUCLE) {
			return codigo((Bucle) i);
		} else if (tipo == TiposInstruccion.CASOS) {
			return codigo((Casos) i);
		} else if (tipo == TiposInstruccion.DELETE) {
			return codigo((Delete) i);
		} else if (tipo == TiposInstruccion.IF) {
			return codigo((Condicional) i);
		} else if (tipo == TiposInstruccion.LLAMADA) {
			return codigo((Llamada) i);
		} else if (tipo == TiposInstruccion.NEW) {
			return codigo((New) i);
		} else if (tipo == TiposInstruccion.READ) {
			return codigo((Read) i);
		} else if (tipo == TiposInstruccion.WRITE) {
			return codigo((Write) i);
		}  
		return "";
	}

	private String codigo(Write i) {
		
		
		return null;
	}

	private String codigo(Read i) {
		// TODO Auto-generated method stub
		return null;
	}

	private String codigo(New i) {
		// TODO Auto-generated method stub
		return null;
	}

	private String codigo(Llamada i) {

		String id = i.getIdentificador();
		List<Expresion> exps = i.getParams();
		
		System.out.println(id);
		
		
		
		return null;
	}

	private String codigo(Condicional i) {
		// TODO Auto-generated method stub
		return null;
	}

	private String codigo(Delete i) {
		// TODO Auto-generated method stub
		return null;
	}

	private String codigo(Casos i) {
		// TODO Auto-generated method stub
		return null;
	}

	private String codigo(Bucle i) {

		
		
		return null;
	}

	private String codigo(Bloque i) {
		StringBuilder sb = new StringBuilder();
		List<Instruccion> insts = i.getInstrucciones();
		for (Instruccion in : insts){
			sb.append(codigoInstruccion(in));
		}
		d.insertaInfoEnNodo(i, "cod", sb.toString());
		System.out.println(sb.toString());
		return sb.toString();
	}

	private String codigo(Asignacion i) {
		
		// donde
		String cod = codigo(i.getDesignador());
		
		// que
		String cod2 = codigo(i.getExpresion());
		
		// hazlo
		String cod3 = DESAPILA_IND;
		
		return cod + cod2 + cod3;
	}

	private String codigo(Expresion expresion) {
		if (expresion == null) return "";
		TipoExpresion te = expresion.getTipoExpresion();
		switch(te){
			case BINARIA: {
				codigo((ExpresionBinaria)expresion);
			} break;
			case BOOLEAN: {
				codigo((ExpresionBoolean)expresion);				
			} break;
			case DESIGNADOR: {
				codigo((ExpresionDesignador)expresion);					
			} break;
			case DOUBLE: {
				codigo((ExpresionDouble)expresion);
			} break;
			case INTEGER: {
				codigo((ExpresionInteger)expresion);
			} break;
			case UNARIA: {
				codigo((ExpresionUnaria)expresion);
			} break;
			default: break;		
		}
		return "";		
	}

	private void codigo(ExpresionBinaria expresion) {
		String cod1 = codigo(expresion.getExp0());
		String cod2 = codigo(expresion.getExp1());
		OpBinario tipo = expresion.getOpBinario();
		String tipoOp = tipo.getTipo();
		if (tipoOp.equalsIgnoreCase("+")){
			
		}
		
		
	}
	
	
	
	private void codigo(ExpresionUnaria expresion) {
		// TODO Auto-generated method stub
		
	}

	private void codigo(ExpresionInteger expresion) {
		// TODO Auto-generated method stub
		
	}

	private void codigo(ExpresionDouble expresion) {
		// TODO Auto-generated method stub
		
	}

	private void codigo(ExpresionDesignador expresion) {
		// TODO Auto-generated method stub
		
	}

	private void codigo(ExpresionBoolean expresion) {
		// TODO Auto-generated method stub
		
	}

	private String codigo(Designador designador) {

		
		return null;
	}

	private void codigoSubprograma(DecSubprograma decSubprogramas) {
		
		
		
		
	}

	private void asignaEspacio(DecSubprograma ds) {
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
				d.insertaInfoEnNodo(params, "tam", tam);
		//		System.out.println("TAM de " + p.getIdentificador() + " :" + tam);		
			}
		}
	}

	private void asignaEspacioDesdeRaiz(Programa p) {
		DecSubprograma ds = p.getDecSubprogramas();
		dir = anidamiento(ds);
		d.insertaInfoEnNodo(p, "finDatos", dir);
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
		d.insertaInfoEnNodo(decTipos.getIdentificador(), "tam", tam);
		DecTipos dv = decTipos.getDecTipos();
		if (dv != null){ asignaEspacio(dv); }		
	}

	private void asignaEspacio(DecVariable decVariables) {
		d.insertaInfoEnNodo(decVariables, "nivel", nivel);
		d.insertaInfoEnNodo(decVariables, "dir", dir);
		int tam = tamanioVar(decVariables.getTipo());
		// System.out.println("TAM de " + decVariables.getIdentificador() + " :" + tam);
		d.insertaInfoEnNodo(decVariables, "tam", tam);
		dir += tam;
		DecVariable dv = decVariables.getDecVariables();
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
			int tamanio = (Integer) d.getDecoracion(tipoReal.getIdentificador()).get("tam");
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

	private int anidamiento(DecSubprograma decSubprogramas) {
		int miAnidamiento = 0;
		int maxAnidamientoHermanos = 0;
		DecSubprograma dh = decSubprogramas.getDecSubprogramas();	
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

	

}

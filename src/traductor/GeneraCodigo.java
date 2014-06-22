package traductor;


import static traductor.LenguajeP.*;

import java.util.ArrayList;
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
import modelo.operadores.OpBinario;
import modelo.operadores.OpUnario;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoStruct;

public class GeneraCodigo {	
	
	private Decoracion d;
	private Map<Object, Object> vinculos;
	
	private List<String> codigo;
	private int dir, nivel, cinst;

	private BloqueDeCodigo bloqueRaiz, bloqueActual;
	
	public GeneraCodigo(Map<Object, Object> vinculos, Decoracion d) {	
		this.dir = this.cinst = this.nivel = 0;
		this.d = d;
		this.vinculos = vinculos;
	}
	
	class BloqueDeCodigo {
		
		List<BloqueDeCodigo> bl;
		String codigo;
		
		public BloqueDeCodigo(String codigo) {
			this.codigo = codigo;
			this.bl = null;
		}
		
		public void addBloque(BloqueDeCodigo bloque){
			if (bl == null){
				bl = new ArrayList<BloqueDeCodigo>();
			}
			bl.add(bloque);
		}
		
		public List<String> getCodigo(){
			List<String> codigos = new ArrayList<String>();
			codigos.add(codigo);
			if (bl != null){
				for (BloqueDeCodigo b : bl){
					List<String> codigosAgregados = b.getCodigo();
					for (String cod : codigosAgregados){
						codigos.add(cod);
					}
				}
			}
			return codigos;
		}
		
	}

	public void generaCodigo(Programa p) {
		asignaEspacioDesdeRaiz(p);
		codigoProgramaDesdeRaiz(p);	
		System.out.println("Código generado.");
	}

	private void codigoProgramaDesdeRaiz(Programa p) {							
		String inicio = generaInicio(
						(Integer)d.getDecoracion(p.getDecVariables()).get("tam")+
						(Integer)d.getDecoracion(p).get("finDatos"), cinst);
		bloqueActual = bloqueRaiz = new BloqueDeCodigo(inicio);		
		d.insertaInfoEnNodo(p, "inicio", getCI());		
		aumentaCI(numeroInstrucciones(inicio));	
		codigoSubprograma(p.getDecSubprogramas());		
		codigo(p.getBloque());
		d.insertaInfoEnNodo(p, "fin", getCI());	
	}
	
	private void aumentaCI(int cantidad){
		cinst += cantidad;
	}
	
	private void setCI(int cantidad){
		cinst = cantidad;
	}
	
	private int getCI(){
		return cinst;
	}

	private int numeroInstrucciones(String cod) {
		if (cod != null){
			return cod.split("\n").length;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
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
			return codigo((List<Caso>) i);
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

	private String codigo(List<Caso> i) {
		String cod = "";
		for (Caso c : i){
			cod += codigo(c.getExpresion());
			cod += codigo(c.getBloque());
		}			
		d.insertaInfoEnNodo(i, "cod", cod);	
		return cod;
	}

	private String codigo(Write i) {
		String cod = codigo(i.getExpresion());
		cod += WRITE;
		d.insertaInfoEnNodo(i, "cod", cod);
		return cod;
	}

	private String codigo(Read i) {
		String cod = READ;
		cod += codigo(i.getDesignador());
		cod += DESAPILA_IND;
		d.insertaInfoEnNodo(i, "cod", cod);
		return cod;
	}

	private String codigo(New i) {
		i.getDesignador();
		String cod = "";
		d.insertaInfoEnNodo(i, "cod", cod);
		return cod;
	}

	private String codigo(Llamada i) {

		// TODO
		
		String id = i.getIdentificador();
		List<Expresion> exps = i.getParams();
	
		ArrayList<String> params = new ArrayList<String>();
		for (Expresion exp : exps){
			params.add(codigo(exp));
		}
		
		DecSubprograma ds = (DecSubprograma) vinculos.get(i);
		
		Integer dirSalto = (int) d.getDecoracion(ds).get("inicio");
		
		if (dirSalto == null){
			throw new UnsupportedOperationException("En la llamada no se dónde ir.");
		}
		
		String cod = generaPrellamada(params, cinst+1, dirSalto);
		
		this.d.insertaInfoEnNodo(i, "cod", cod);
		
		return cod;
	}

	private String codigo(Condicional i) {
		
		codigo(i.getCasos());
		
		
		return null;
	}

	private String codigo(Delete i) {
		
		
		return null;
	}

	private String codigo(Bucle i) {
		 codigo(i.getCasos());
		 
		 
		 return null;
	}

	private String codigo(Bloque i) {
		StringBuilder sb = new StringBuilder();
		List<Instruccion> insts = i.getInstrucciones();
		for (Instruccion in : insts){
			sb.append(codigoInstruccion(in));
		}
		d.insertaInfoEnNodo(i, "cod", sb.toString());
//		System.out.println(sb.toString());
		return sb.toString();
	}

	private String codigo(Asignacion i) {
		String cod = codigo(i.getDesignador());
		String cod2 = codigo(i.getExpresion());
		String cod3 = cod + cod2 + DESAPILA_IND;		
		d.insertaInfoEnNodo(i, "cod", cod3);
		return cod3;
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
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + SUMA);
		} else if (tipoOp.equalsIgnoreCase("-")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + RESTA );
		} else if (tipoOp.equalsIgnoreCase("*")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MUL );
		} else if (tipoOp.equalsIgnoreCase("/")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + DIV );
		} else if (tipoOp.equalsIgnoreCase("%")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MOD );
		} else if (tipoOp.equalsIgnoreCase("and")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + AND );
		} else if (tipoOp.equalsIgnoreCase("or")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + OR );
		} else if (tipoOp.equalsIgnoreCase(">")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MAYOR );
		} else if (tipoOp.equalsIgnoreCase("<")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MENOR );
		} else if (tipoOp.equalsIgnoreCase(">=")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MAYOR_IGUAL );
		} else if (tipoOp.equalsIgnoreCase("<=")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + MENOR_IGUAL );
		} else if (tipoOp.equalsIgnoreCase("=")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + IGUAL );
		} else if (tipoOp.equalsIgnoreCase("!=")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + cod2 + DISTINTO );
		} 		
		
	}	
	
	
	private void codigo(ExpresionUnaria expresion) {
		
		String cod1 = codigo(expresion.getExp());
		OpUnario tipo = expresion.getOpUnario();
		String tipoOp = tipo.getTipo();
		if (tipoOp.equalsIgnoreCase("not")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + NOT );
		} else if (tipoOp.equalsIgnoreCase("-")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + NEG );
		} else if (tipoOp.equalsIgnoreCase("toint")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + TOINT );
		} else if (tipoOp.equalsIgnoreCase("todouble")){
			d.insertaInfoEnNodo(expresion, "cod", cod1 + TODOUBLE );
		}
		
	}

	private void codigo(ExpresionInteger expresion) {
		d.insertaInfoEnNodo(expresion, "cod", instrConAlgo(APILA, expresion.getValor()));		
	}

	private void codigo(ExpresionDouble expresion) {
		d.insertaInfoEnNodo(expresion, "cod", instrConAlgo(APILA, expresion.getValor()));
	}

	private void codigo(ExpresionDesignador expresion) {
		Designador designador = expresion.getValor();
		if (designador == null) return;
		codigo(designador);		
	}

	private void codigo(ExpresionBoolean expresion) {
		d.insertaInfoEnNodo(expresion, "cod", instrConAlgo(APILA, expresion.getValor()));
	}

	private String codigo(Designador designador) {		
		// códigos para acceder (saber posición)
		Expresion e = designador.getExpresion();
		Designador des = designador.getDesignador();
		String id = designador.getIdentificador();
		
		switch(designador.getTipo()){
			case ARRAY: {
				
				String codArray = codigo(des);
				codArray += codigo(e);
				codArray += SUMA;			
				
//				System.out.println(codArray);
				this.d.insertaInfoEnNodo(designador, "cod", codArray);				
				
				return codArray;
			}
			case ID: {
				String codID;
				if (id.equalsIgnoreCase("null")){ 
					codID = instrConAlgo(APILA, 0);
				} else {					
					Object obj = vinculos.get(designador);
					Map<String, Object> m = this.d.getDecoracion(obj);
					
//					System.out.println(m);
					
					int dir = (int) m.get("dir");
					int niv = (int) m.get("nivel");
					
					if (niv == 0){
						codID = instrConAlgo(APILA, dir);
					} else {						
						codID = instrConAlgo(APILA_DIR, nivel) +
								instrConAlgo(APILA, dir) +
								SUMA;						
					}				
					
				}
//				System.out.println(codID);
				this.d.insertaInfoEnNodo(designador, "cod", codID);
				return codID;
			}
			case STRUCT: {
				
				// TODO
				
				// String codStruct = codigo(des);

//				if (obj != null){
//					Object m = obj.get(id);
//				}
				

				
				
				//return codStruct;				
			}
			case PUNTERO: {
				
				// TODO

				//String codPuntero = codigo(designador);
				
				
//				return codPuntero;
			}
		default: break;
		}		
		
		return null;
	}

	private String codigoSubprograma(List<DecSubprograma> list) {
		if (list == null){ return ""; }
		String cod = "";
		for (DecSubprograma ds : list){
//			codigoPars(ds.getParametros());
			cod += codigo(ds.getPrograma());
		}
		return cod;
	}
	
	private String codigo(Programa programa) {
		if (programa == null){ return ""; }
		// TODO
		String cod = "";
		cod += codigoSubprograma(programa.getDecSubprogramas());
		cod += codigo(programa.getBloque());
		return cod;
	}
	
	// ASIGNA ESPACIOS

	private void asignaEspacioSubs(List<DecSubprograma> decSubprogramas) {
		for (DecSubprograma ds : decSubprogramas){
			asignaEspacio(ds);
		}
	}

	private void asignaEspacio(DecSubprograma ds) {
		int dirCopia = dir;
		int nivelCopia = nivel;
		if (ds.getPrograma() != null){
			nivel++;
			asignaEspacio(ds.getPrograma());	
		}
		dir = dirCopia;
		nivel = nivelCopia;	
		
		List<Parametro> params = ds.getParametros();
		int dir = 2;
		if (params != null){
			for (Parametro p : params){
				int tam = 1;
				if (p.isPorValor()){
					tam = tamanioVar(p.getTipo());
				}
				d.insertaInfoEnNodo(p, "tam", tam);
				d.insertaInfoEnNodo(p, "dir", dir);
				d.insertaInfoEnNodo(p, "nivel", nivel);

//				System.out.println("TAM, DIR y NIVEL de " + p.getIdentificador() + " : " 
//							+ "("+tam+","+dir+","+nivel+")");					
				dir += tam;
			}
		}
	}

	private void asignaEspacioDesdeRaiz(Programa p) {
		List<DecSubprograma> ds = p.getDecSubprogramas();
		dir = anidamiento(ds);
		d.insertaInfoEnNodo(p, "finDatos", dir);
		System.out.println("Anidamiento: " + dir);
		nivel = 0;
		asignaEspacio(p);
	}

	private void asignaEspacio(Programa p) {
		d.insertaInfoEnNodo(p, "nivel", nivel);
		if (p.getDecTipos() != null) asignaEspacio(p.getDecTipos());
		if (p.getDecVariables() != null) asignaEspacioVars(p.getDecVariables());
		if (p.getDecSubprogramas() != null) asignaEspacioSubs(p.getDecSubprogramas());
	}

	private void asignaEspacio(List<DecTipo> decTipos) {		
		for (DecTipo dec : decTipos){
			int tam = tamanioVar(dec.getTipo());
			d.insertaInfoEnNodo(dec.getId(), "tam", tam);
		}			
	}

	private void asignaEspacioVars(List<DecVariable> decVariables) {		
		int dir = 0;
		int oldDir = this.dir;
		d.insertaInfoEnNodo(decVariables, "dir", this.dir);
		for (DecVariable dv : decVariables){
			int tam = tamanioVar(dv.getTipo());		
			
			if (this.nivel == 0){
				d.insertaInfoEnNodo(dv, "dir", this.dir);
//				System.out.println("TAM, DIR y NIVEL de " + dv.getIdentificador() + " : " + "("+tam+","+this.dir+","+nivel+")");	
			} else {
				d.insertaInfoEnNodo(dv, "dir", dir);	
//				 System.out.println("TAM, DIR y NIVEL de " + dv.getIdentificador() + " : " + "("+tam+","+dir+","+nivel+")");				
			}

			d.insertaInfoEnNodo(dv, "nivel", nivel);	
			d.insertaInfoEnNodo(dv, "tam", tam);
			dir += tam;				
			this.dir += tam;
		}
		d.insertaInfoEnNodo(decVariables, "tam", this.dir-oldDir);
		
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
			int tamanio = (Integer) d.getDecoracion(tipoReal.getId()).get("tam");
			return tamanio;
		case ARRAY:
			TipoArray arr = (TipoArray) tipo;
			int dim = arr.getDimension();
			int tamInterior = tamanioVar(arr.getTipoInterno());
			return dim * tamInterior;			
		case STRUCT:
			TipoStruct struct = (TipoStruct) tipo;
			int tam = 0;
			List<DecTipo> tipos = struct.getTipos();
			for (DecTipo tip : tipos){
				d.insertaInfoEnNodo(tip, "desp", tam);
				tam += tamanioVar(tip.getTipo());
			}
			return tam;
			default: break;
		}		
		
		return 0;
	}

	private int anidamiento(List<DecSubprograma> ds) {
		if (ds == null) return 0;
		int maxAnidamiento = 0;

		for (DecSubprograma d : ds){
			maxAnidamiento = Math.max(maxAnidamiento, anidamiento(d));
		}
		
		return maxAnidamiento;
	}

	private int anidamiento(DecSubprograma d) {
		Programa p = d.getPrograma();
		if (p == null) return 1;
		List<DecSubprograma> ds = p.getDecSubprogramas();
		if (ds == null) return 1;
		return anidamiento(ds)+1;
	}

	public List<String> getCodigo() {
		return bloqueRaiz.getCodigo();
	}

	

}

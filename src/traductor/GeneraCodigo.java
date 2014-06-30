package traductor;


import static traductor.LenguajeP.AND;
import static traductor.LenguajeP.APILA;
import static traductor.LenguajeP.APILA_DIR;
import static traductor.LenguajeP.CLONA;
import static traductor.LenguajeP.DESAPILA_IND;
import static traductor.LenguajeP.DISTINTO;
import static traductor.LenguajeP.DIV;
import static traductor.LenguajeP.DUP;
import static traductor.LenguajeP.IGUAL;
import static traductor.LenguajeP.IR_A;
import static traductor.LenguajeP.IR_F;
import static traductor.LenguajeP.IR_V;
import static traductor.LenguajeP.LIBERA;
import static traductor.LenguajeP.MAYOR;
import static traductor.LenguajeP.MAYOR_IGUAL;
import static traductor.LenguajeP.MENOR;
import static traductor.LenguajeP.MENOR_IGUAL;
import static traductor.LenguajeP.MOD;
import static traductor.LenguajeP.MUL;
import static traductor.LenguajeP.NEG;
import static traductor.LenguajeP.NOT;
import static traductor.LenguajeP.OR;
import static traductor.LenguajeP.PRELLAMADA_FINAL;
import static traductor.LenguajeP.PRELLAMADA_INICIO;
import static traductor.LenguajeP.READ;
import static traductor.LenguajeP.RESERVA;
import static traductor.LenguajeP.RESTA;
import static traductor.LenguajeP.SUMA;
import static traductor.LenguajeP.TODOUBLE;
import static traductor.LenguajeP.TOINT;
import static traductor.LenguajeP.WRITE;
import static traductor.LenguajeP.generaEpilogo;
import static traductor.LenguajeP.generaInicio;
import static traductor.LenguajeP.generaPrologo;
import static traductor.LenguajeP.instrConAlgo;

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
import modelo.operadores.TipoOperador;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoID;
import modelo.tipos.TipoPuntero;
import modelo.tipos.TipoStruct;
import modelo.tipos.Tipos;

public class GeneraCodigo {	

	private Decoracion d;
	private Map<Object, Object> vinculos;

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

		public BloqueDeCodigo() {
			this.codigo = null;
			this.bl = null;	
		}

		public BloqueDeCodigo(String codigo) {
			this.codigo = codigo;
			this.bl = null;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public void addBloque(BloqueDeCodigo bloque){
			if (bl == null){
				bl = new ArrayList<BloqueDeCodigo>();
			}
			if (!bl.contains(bloque) && bloque != this){
				bl.add(bloque);
			} else {				
				throw new UnsupportedOperationException("Bloque ya existente");
			}
		}

		public List<String> getCodigo(){
			List<String> codigos = new ArrayList<String>();
			if (codigo != null){
				codigos.add(codigo);
			}
			if (bl != null){
				for (BloqueDeCodigo b : bl){
					List<String> codigosAgregados = null;
					if (b != null){
						codigosAgregados = b.getCodigo();
					}
					if (codigosAgregados != null){
						for (String cod : codigosAgregados){
							codigos.add(cod);
						}
					}
				}
			}
			return codigos;
		}
		
		public int getLineasDeCodigo(){
			int sum = 0;
			if (codigo != null){
				sum = codigo.split("\n").length;
			}
			if (bl != null){
				for (BloqueDeCodigo b : bl){
					sum += b.getLineasDeCodigo();
				}
			}
			return sum;
		}		

		public boolean estaVacio(){
			return codigo  == null && bl == null;
		}
		
	}

	private void aumentaCI(int cantidad){
		cinst += cantidad;
	}

	private int getCI(){
		return cinst;
	}
	
	public void generaCodigo(Programa p) {
		asignaEspacioDesdeRaiz(p);
		codigoProgramaDesdeRaiz(p);	
	}
	
	/***********************************************************************************
	 * Código programa
	 ***********************************************************************************/

	private void codigoProgramaDesdeRaiz(Programa p) {	
		aumentaCI(3);	
		
		BloqueDeCodigo bd = new BloqueDeCodigo();
		
		codigo(p);
		bd.addBloque(bloqueActual);
		
		String inicio = generaInicio(
				(Integer)d.getDecoracion(p.getDecVariables()).get("tam")+
				(Integer)d.getDecoracion(p).get("finDatos"), getCI());
		bd.setCodigo(inicio);
		
		bloqueRaiz = bd;	
	}
	
	private void codigo(Programa p){
		BloqueDeCodigo bd = new BloqueDeCodigo();
		
		if (p.getDecSubprogramas() != null){
			for (int i = 0; i < p.getDecSubprogramas().size(); i++) {
				DecSubprograma decSubprograma = p.getDecSubprogramas().get(i);
				codigoDecSubprograma(decSubprograma);
				bd.addBloque(bloqueActual);				
			}
		}

		codigoBloque(p.getBloque());
		bd.addBloque(bloqueActual);

		bloqueActual = bd;
	}
	
	/***********************************************************************************
	 * Declaraciones
	 ***********************************************************************************/

	private void codigoDecSubprograma(DecSubprograma decSubprograma) {
		BloqueDeCodigo bd = new BloqueDeCodigo();		
		d.insertaInfoEnNodo(decSubprograma, "dirComienzo", cinst);		
		
		int tamDatos = (int) d.getDecoracion(decSubprograma).get("tam");
		
		BloqueDeCodigo bPro = new BloqueDeCodigo(generaPrologo(nivel, tamDatos));
		bd.addBloque(bPro);
		aumentaCI(bPro.getLineasDeCodigo());
				
		Programa programa = decSubprograma.getPrograma();
		if (programa != null){
			codigo(programa);
			bd.addBloque(bloqueActual);
		}
		
		BloqueDeCodigo bEpi = new BloqueDeCodigo(generaEpilogo(nivel, tamDatos));
		bd.addBloque(bEpi);
		aumentaCI(bEpi.getLineasDeCodigo());		
		
		bloqueActual = bd;
	}

	/***********************************************************************************
	 * Instrucciones
	 ***********************************************************************************/
	
	@SuppressWarnings("unchecked")
	private void codigoInstruccion(Instruccion i) {
		TiposInstruccion tipo = i.getTipoInstruccion();
		if (tipo == TiposInstruccion.ASIG) {
			codigo((Asignacion) i);
		} else if (tipo == TiposInstruccion.BLOQUE) {
			codigoBloque((Bloque) i);
		} else if (tipo == TiposInstruccion.BUCLE) {
			codigo((Bucle) i);
		} else if (tipo == TiposInstruccion.CASOS) {
			codigoCasos((List<Caso>) i);
		} else if (tipo == TiposInstruccion.DELETE) {
			codigo((Delete) i);
		} else if (tipo == TiposInstruccion.IF) {
			codigo((Condicional) i);
		} else if (tipo == TiposInstruccion.LLAMADA) {
			codigo((Llamada) i);
		} else if (tipo == TiposInstruccion.NEW) {
			codigo((New) i);
		} else if (tipo == TiposInstruccion.READ) {
			codigo((Read) i);
		} else if (tipo == TiposInstruccion.WRITE) {
			codigo((Write) i);
		}  
	}

	private void codigoCasos(List<Caso> i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		for (Caso c : i){
			codigo(c.getExpresion());	
			bd.addBloque(bloqueActual);
			codigoBloque(c.getBloque());
			bd.addBloque(bloqueActual);
		}	
		bloqueActual = bd;
	}

	private void codigo(Write i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getExpresion());
		bd.addBloque(bloqueActual);
		BloqueDeCodigo bdWrite = new BloqueDeCodigo(WRITE);
		bd.addBloque(bdWrite);
		aumentaCI(bdWrite.getLineasDeCodigo());
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;		
	}

	private void codigo(Read i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		BloqueDeCodigo bdRead = new BloqueDeCodigo(READ);
		bd.addBloque(bdRead);
		aumentaCI(bdRead.getLineasDeCodigo());
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;
	}

	private void codigo(New i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();
		Object o = vinculos.get(i.getDesignador());
		Integer dec = (Integer) d.getDecoracion(o).get("tam");
		if (dec == null){ dec = 0; }
		String codigo = instrConAlgo(RESERVA, dec)+DESAPILA_IND;
		BloqueDeCodigo bdNew = new BloqueDeCodigo(codigo);
		bd.addBloque(bdNew);
		aumentaCI(bdNew.getLineasDeCodigo());
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;		
	}

	private void codigo(Llamada i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();

		BloqueDeCodigo bdPre = new BloqueDeCodigo(PRELLAMADA_INICIO);
		bd.addBloque(bdPre); 
		aumentaCI(bdPre.getLineasDeCodigo());

		DecSubprograma obj = (DecSubprograma) vinculos.get(i);		
		Integer dirSalto = (Integer) d.getDecoracion(obj).get("inicio");
		if (dirSalto == null){ dirSalto = 0; }

		List<Expresion> exps = i.getParams();	
		List<Parametro> pars = obj.getParametros();
		for (int j = 0, s = exps.size(); j < s; j++){
			bd.addBloque(new BloqueDeCodigo(DUP+instrConAlgo(APILA, j)+SUMA));
			aumentaCI(3);

			codigo(exps.get(j));	
			bd.addBloque(bloqueActual);			

			Parametro p = pars.get(j);
			if (p.isPorValor() && !p.getTipo().getTipoConcreto().equals(Tipos.POINTER)){
				// Por valor y no es puntero. Se clona.
				Integer tam =  (Integer) d.getDecoracion(p).get("tam");
				if (tam == null){ tam = 0; }
				bd.addBloque(new BloqueDeCodigo(instrConAlgo(CLONA, tam)));
			} else {
				// Se pasa un valor entero.
				bd.addBloque(new BloqueDeCodigo(DESAPILA_IND));
			}
			aumentaCI(1);
		
		}		

		BloqueDeCodigo bd1 = new BloqueDeCodigo(PRELLAMADA_FINAL+
				instrConAlgo(APILA, getCI()+1)+
				DESAPILA_IND+
				instrConAlgo(IR_A, dirSalto));	
		bd.addBloque(bd1);
		aumentaCI(bd1.getLineasDeCodigo());
		
		d.insertaInfoEnNodo(i, "fin", getCI());

		bloqueActual = bd;
	}

	private void codigo(Condicional i) {	
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();	

		List<Caso> casos = i.getCasos();
		if (casos != null){
			for (Caso c : casos){
				codigo(c.getExpresion());
				bd.addBloque(bloqueActual);
				codigoBloque(c.getBloque());
				BloqueDeCodigo bd2 = new BloqueDeCodigo(instrConAlgo(IR_F, getCI()+1));
				aumentaCI(1);
				bd.addBloque(bd2);
				bd.addBloque(bloqueActual);
			}
		}

		bloqueActual = bd;
	}

	private void codigo(Bucle i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();

		List<Caso> casos = i.getCasos();
		if (casos != null){
			for (Caso c : casos){
				Integer dirInicioBucle = getCI();
				codigo(c.getExpresion());
				bd.addBloque(bloqueActual);
				codigoBloque(c.getBloque());
				bd.addBloque(bloqueActual);
				BloqueDeCodigo bd2 = new BloqueDeCodigo( instrConAlgo(IR_V, dirInicioBucle));
				aumentaCI(1);
				bd.addBloque(bd2);
			}
		}

		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;
	}

	private void codigo(Delete i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		Object o = vinculos.get(i.getDesignador());
		
		Integer tam = (Integer)d.getDecoracion(o).get("tam");
		if (tam == null){ tam = 0; }
		String cod = instrConAlgo(LIBERA, tam);
		BloqueDeCodigo bdLibera = new BloqueDeCodigo(cod);
		bd.addBloque(bdLibera);
		aumentaCI(bdLibera.getLineasDeCodigo());
		
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;
	}

	private void codigoBloque(Bloque i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo();
		List<Instruccion> insts = i.getInstrucciones();
		for (Instruccion in : insts){
			codigoInstruccion(in);
			bd.addBloque(bloqueActual);
		}
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;
	}

	private void codigo(Asignacion i) {
		d.insertaInfoEnNodo(i, "comienzo", getCI());
		BloqueDeCodigo bd = new BloqueDeCodigo(DESAPILA_IND);	
		aumentaCI(1);
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		codigo(i.getExpresion());
		bd.addBloque(bloqueActual);
		d.insertaInfoEnNodo(i, "fin", getCI());
		bloqueActual = bd;
	}

	private void codigo(Expresion expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		if (expresion == null){
			bloqueActual = bd;
			return;
		}
		TipoExpresion te = expresion.getTipoExpresion();
		if (te == TipoExpresion.BINARIA) {
			codigo((ExpresionBinaria)expresion);
		} else if (te == TipoExpresion.BOOLEAN) {
			codigo((ExpresionBoolean)expresion);
		} else if (te == TipoExpresion.DESIGNADOR) {
			codigo((ExpresionDesignador)expresion);
		} else if (te == TipoExpresion.DOUBLE) {
			codigo((ExpresionDouble)expresion);
		} else if (te == TipoExpresion.INTEGER) {
			codigo((ExpresionInteger)expresion);
		} else if (te == TipoExpresion.UNARIA) {
			codigo((ExpresionUnaria)expresion);
		}
		bd.addBloque(bloqueActual);
		bloqueActual = bd;
	}

	private void codigo(ExpresionBinaria expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(expresion.getExp0());
		bd.addBloque(bloqueActual);
		codigo(expresion.getExp1());
		bd.addBloque(bloqueActual);
		OpBinario tipo = expresion.getOpBinario();
		TipoOperador tipoOp = tipo.getTipo();

		if (tipoOp == TipoOperador.MAS){
			bd.addBloque(new BloqueDeCodigo(SUMA));
		} else if (tipoOp == TipoOperador.MENOS){
			bd.addBloque(new BloqueDeCodigo(RESTA ));
		} else if (tipoOp == TipoOperador.POR){
			bd.addBloque(new BloqueDeCodigo(MUL ));
		} else if (tipoOp == TipoOperador.DIV){
			bd.addBloque(new BloqueDeCodigo(DIV ));
		} else if (tipoOp == TipoOperador.MOD){
			bd.addBloque(new BloqueDeCodigo(MOD ));
		} else if (tipoOp == TipoOperador.AND){
			bd.addBloque(new BloqueDeCodigo(AND ));
		} else if (tipoOp == TipoOperador.OR){
			bd.addBloque(new BloqueDeCodigo(OR ));
		} else if (tipoOp == TipoOperador.MAYOR){
			bd.addBloque(new BloqueDeCodigo(MAYOR ));
		} else if (tipoOp == TipoOperador.MENOR){
			bd.addBloque(new BloqueDeCodigo(MENOR ));
		} else if (tipoOp == TipoOperador.MAYOROIGUAL){
			bd.addBloque(new BloqueDeCodigo(MAYOR_IGUAL ));
		} else if (tipoOp == TipoOperador.MENOROIGUAL){
			bd.addBloque(new BloqueDeCodigo(MENOR_IGUAL ));
		} else if (tipoOp == TipoOperador.IGUAL){
			bd.addBloque(new BloqueDeCodigo(IGUAL ));
		} else if (tipoOp == TipoOperador.DISTINTO){
			bd.addBloque(new BloqueDeCodigo(DISTINTO ));
		} 		

		aumentaCI(1);
		bloqueActual = bd;
	}	


	private void codigo(ExpresionUnaria expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo();		
		codigo(expresion.getExp());
		bd.addBloque(bloqueActual);
		OpUnario tipo = expresion.getOpUnario();
		TipoOperador tipoOp = tipo.getTipo();
		if (tipoOp == TipoOperador.NOT){
			bd.addBloque(new BloqueDeCodigo(NOT ));
		} else if (tipoOp == TipoOperador.MENOS){
			bd.addBloque(new BloqueDeCodigo(NEG ));
		} else if (tipoOp == TipoOperador.TOINT){
			bd.addBloque(new BloqueDeCodigo(TOINT ));
		} else if (tipoOp == TipoOperador.TODOUBLE){
			bd.addBloque(new BloqueDeCodigo(TODOUBLE ));
		}
		aumentaCI(1);
		bloqueActual = bd;		
	}

	private void codigo(ExpresionInteger expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo(instrConAlgo(APILA, expresion.getValor()));		
		aumentaCI(1);
		bloqueActual = bd;	
	}

	private void codigo(ExpresionDouble expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo(instrConAlgo(APILA, expresion.getValor()));		
		aumentaCI(1);
		bloqueActual = bd;	
	}

	private void codigo(ExpresionBoolean expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo(instrConAlgo(APILA, expresion.getValor()));		
		aumentaCI(1);
		bloqueActual = bd;	
	}

	private void codigo(ExpresionDesignador expresion) {
		BloqueDeCodigo bd = new BloqueDeCodigo();	
		Designador designador = expresion.getValor();
		if (designador != null){
			codigo(designador);	
			bd.addBloque(bloqueActual);
		}
		bloqueActual = bd;
	}

	private void codigo(Designador designador) {	
		BloqueDeCodigo bd = new BloqueDeCodigo();
		if (designador == null){
			bloqueActual = bd;
			return;
		}

		Expresion e = designador.getExpresion();
		Designador des = designador.getDesignador();
		String id = designador.getIdentificador();		

		modelo.instrucciones.Designador.Tipo tipo = designador.getTipo();
		switch (tipo) {
		case ARRAY:
			codigo(des);
			bd.addBloque(bloqueActual);
			codigo(e);
			bd.addBloque(bloqueActual);
			bd.addBloque(new BloqueDeCodigo(SUMA));
			aumentaCI(1);	
			break;
		case ID:
			if (id.equalsIgnoreCase("null")){ 
				bd.setCodigo(instrConAlgo(APILA, 0));
				aumentaCI(1);

			} else {			
				
				Object obj = vinculos.get(designador);
							
				if (obj instanceof Parametro){
					// es un parámetro
					Parametro p = (Parametro) obj;
					
					Map<String, Object> dec = this.d.getDecoracion(p);
					Integer dir = (int) dec.get("dir");
					if (dir == null){ dir = 0; }
					Integer niv = (int) dec.get("nivel");
					if (niv == null){ niv = 0; }
					
					if (niv == 0){
						bd.setCodigo(instrConAlgo(APILA, dir));
						aumentaCI(1);
					} else {						
						bd.setCodigo(instrConAlgo(APILA_DIR, nivel) +
									instrConAlgo(APILA, dir) +
									SUMA);
						aumentaCI(3);
					}	
					
					
				} else {
					// es una variable
					DecVariable dv = (DecVariable) obj;
					
					Map<String, Object> dec = d.getDecoracion(dv);
					Integer dir = (Integer) dec.get("dir");
					if (dir == null){ dir = 0; }
					Integer niv = (Integer) dec.get("nivel");
					if (niv == null){ niv = 0; }
					
					if (niv == 0){
						bd.setCodigo(instrConAlgo(APILA, dir));
						aumentaCI(1);
					} else {						
						bd.setCodigo(instrConAlgo(APILA_DIR, niv) +
									instrConAlgo(APILA, dir) +
									SUMA);
						aumentaCI(3);
					}	
				}
				
			}
			break;
		case CAMPO_DE_STRUCT:
			codigo(des);
			bd.addBloque(bloqueActual);
			Integer desp = (Integer) d.getDecoracion(id).get("desp");
			if (desp == null){ desp = 0; }
			BloqueDeCodigo bdStruct = new BloqueDeCodigo( instrConAlgo(APILA,desp)+ SUMA );
			bd.addBloque(bdStruct);
			aumentaCI(bdStruct.getLineasDeCodigo());
			break;
		case PUNTERO:
			codigo(des);
			bd.addBloque(bloqueActual);
			break;
		}	

		bloqueActual = bd;
	}

	/*******************************************************************************
	 *  ASIGNA ESPACIOS
	 *******************************************************************************/

	private void asignaEspacioDesdeRaiz(Programa p) {
		List<DecSubprograma> ds = p.getDecSubprogramas();
		int anida = dir = anidamiento(ds);
		d.insertaInfoEnNodo(p, "finDatos", anida);
		nivel = 0;	
		Integer tam = 0;
		for (int i = 0; i < p.getDecVariables().size(); i++) {
			DecVariable decVariable = p.getDecVariables().get(i);
			asignaEspacio(decVariable);
			tam += (Integer) d.getDecoracion(decVariable).get("tam");
		}
		d.insertaInfoEnNodo(p.getDecVariables(), "tam", tam);
		
		tam = 0;
		for (int j = 0; j < p.getDecSubprogramas().size(); j++) {
			DecSubprograma decSubprograma = p.getDecSubprogramas().get(j);
			asignaEspacio(decSubprograma);
			tam += (Integer) d.getDecoracion(decSubprograma).get("tam");		
		}	
		d.insertaInfoEnNodo(p.getDecSubprogramas(), "tam", tam);	
	}
	
	private void asignaEspacio(DecVariable decVariable) {
		d.insertaInfoEnNodo(decVariable, "nivel", nivel);
		d.insertaInfoEnNodo(decVariable, "dir", dir);
		asignaEspacio(decVariable.getTipo());
		int tam = (int) d.getDecoracion(decVariable.getTipo()).get("tam");
		d.insertaInfoEnNodo(decVariable, "tam", tam);
		dir += tam;	
	}

	private void asignaEspacio(DecSubprograma ds) {
		if (ds == null){ return; }	
		
		int copiaDir = dir;
		int copiaNivel = nivel;
		nivel++;
		d.insertaInfoEnNodo(ds, "nivel", nivel);
		dir = 0;
		
		if (ds.getParametros() != null){
			for (int i = 0; i < ds.getParametros().size(); i++) {
				
				Parametro p = ds.getParametros().get(i);
				d.insertaInfoEnNodo(p, "dir", dir);
				d.insertaInfoEnNodo(p, "nivel", nivel);
				asignaEspacio(p.getTipo());
				int tam = (int) d.getDecoracion(p.getTipo()).get("tam");			
				
				if (!p.isPorValor()){
					dir++;
				} else {
					dir+=tam;
				}
				
				Programa programa = ds.getPrograma();
				if (programa != null){
					if (programa.getDecSubprogramas() != null){
						for (int j = 0; j < programa.getDecSubprogramas().size(); j++) {
							DecSubprograma decSubprograma = programa.getDecSubprogramas().get(j);
							asignaEspacio(decSubprograma);
						}				
					}
				}
				
			}
		}
		
		d.insertaInfoEnNodo(ds, "tam", dir);	
		
		nivel = copiaNivel;
		dir = copiaDir;
	}

	private void asignaEspacio(Tipo tipo) {
		switch(tipo.getTipoConcreto()){
		case POINTER:
			asignaEspacio(((TipoPuntero)tipo).getTipoPuntero());
		case BOOL:
		case INT:
		case DOUBLE:
			d.insertaInfoEnNodo(tipo, "tam", 1);
			return;
		case ARRAY:		
			Integer tamDec = (Integer) d.getDecoracion(tipo).get("tam");
			if (tamDec == null){
				TipoArray arr = (TipoArray) tipo;	
				asignaEspacio(arr.getTipoInterno());				
				Integer tamDec2 = (Integer) d.getDecoracion(arr.getTipoInterno()).get("tam");					
				d.insertaInfoEnNodo(tipo, "tam", tamDec2*arr.getDimension());
			}
			return;	
		case STRUCT:
			Integer tamDec3 = (Integer) d.getDecoracion(tipo).get("tam");
			TipoStruct struct = (TipoStruct) tipo;
			if (tamDec3 == null){
				d.insertaInfoEnNodo(struct, "tam", 0);
				
				for (int i = 0; i < struct.getTipos().size(); i++) {
					DecTipo decTipo = struct.getTipos().get(i);

					Integer tamDec4 = (Integer) d.getDecoracion(tipo).get("tam");					
					d.insertaInfoEnNodo(decTipo, "desp", tamDec4);
					
					asignaEspacio(decTipo.getTipo());
					
					Integer tamDec5 = (Integer) d.getDecoracion(decTipo.getTipo()).get("tam");					
					d.insertaInfoEnNodo(struct, "tam", tamDec4+tamDec5);					
				}	
				
			}
		/* Debería haberse simplificado en chequea.*/
			return;
		case IDENT:
			
			Integer tamDec6 = (Integer) d.getDecoracion(tipo).get("tam");
			TipoID tipoID = (TipoID) tipo;
			if (tamDec6 == null){
				 Object obj = vinculos.get(tipoID);
				
				if (obj instanceof DecTipo) {
					DecTipo dt = (DecTipo) obj;
					asignaEspacio(dt.getTipo());				
					Integer tamDec7 = (Integer) d.getDecoracion(dt.getTipo()).get("tam");
					d.insertaInfoEnNodo(tipo, "tam", tamDec7);
				} else {
					d.insertaInfoEnNodo(tipo, "tam", 0);
				}
				
			}
			
		default: break;
		}		
	}
	
	/*******************************************************************************
	 *  ANIDAMIENTO
	 *******************************************************************************/

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

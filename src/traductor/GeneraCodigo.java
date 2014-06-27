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

		public boolean estaVacio(){
			return codigo  == null && bl == null;
		}
		
	}

	public void generaCodigo(Programa p) {
		asignaEspacioDesdeRaiz(p);
		codigoProgramaDesdeRaiz(p);	
	}

	private void codigoProgramaDesdeRaiz(Programa p) {	

		aumentaCI(3);	

		BloqueDeCodigo bd = new BloqueDeCodigo();

		codigoDecSubprogramas(p.getDecSubprogramas());		
		bd.addBloque(bloqueActual);

		String inicio = generaInicio(
				(Integer)d.getDecoracion(p.getDecVariables()).get("tam")+
				(Integer)d.getDecoracion(p).get("finDatos"), getCI());

		codigoBloque(p.getBloque());
		bd.addBloque(bloqueActual);		

		bd.setCodigo(inicio);

		bloqueRaiz = bd;	
	}

	private void aumentaCI(int cantidad){
		cinst += cantidad;
	}

	private int getCI(){
		return cinst;
	}

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
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getExpresion());
		bd.addBloque(bloqueActual);
		bd.addBloque(new BloqueDeCodigo(WRITE));
		aumentaCI(1);
		bloqueActual = bd;		
	}

	private void codigo(Read i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		bd.addBloque(new BloqueDeCodigo(READ));
		aumentaCI(1);
		//		bd.addBloque(new BloqueDeCodigo(DESAPILA_IND));
		//		aumentaCI(1);
		bloqueActual = bd;
	}

	private void codigo(New i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		Object o = vinculos.get(i.getDesignador());
		Integer dec = 1;
		
//		if (o instanceof Parametro) {
//			Parametro p = (Parametro) o;
//			if (!p.isPorValor()) {
//				dec = tamanioVar(p.getTipo());
//			} 
//			System.out.println(p.getIdentificador());
//		} else {
			dec = (Integer) d.getDecoracion(o).get("tam");
//		}
		
//		System.out.println(dec);
		String codigo = instrConAlgo(RESERVA, dec)+DESAPILA_IND;
		aumentaCI(2);
		bd.addBloque(new BloqueDeCodigo(codigo));
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		bloqueActual = bd;		
	}

	private void codigo(Llamada i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();

		bd.addBloque(new BloqueDeCodigo(PRELLAMADA_INICIO)); 
		aumentaCI(3);

		DecSubprograma obj = (DecSubprograma) vinculos.get(i);		
		Integer dirSalto = (Integer) d.getDecoracion(obj).get("inicio");

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
				if (tam == null){
					tam = 0;
					//					throw new UnsupportedOperationException("tam es null");
				}
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
		aumentaCI(6);

		bloqueActual = bd;
	}

	private void codigo(Condicional i) {	
		BloqueDeCodigo bd = new BloqueDeCodigo();	

		List<Caso> casos = i.getCasos();
		if (casos != null){
			for (Caso c : casos){
				codigo(c.getExpresion());
				bd.addBloque(bloqueActual);
				aumentaCI(1);
				codigoBloque(c.getBloque());
				BloqueDeCodigo bd2 = new BloqueDeCodigo(
						instrConAlgo(IR_F, getCI()+1));
				bd.addBloque(bd2);
				bd.addBloque(bloqueActual);
			}
		}

		bloqueActual = bd;
	}

	private void codigo(Bucle i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();

		List<Caso> casos = i.getCasos();
		if (casos != null){
			for (Caso c : casos){
				Integer dirInicioBucle = getCI();
				codigo(c.getExpresion());
				bd.addBloque(bloqueActual);
				codigoBloque(c.getBloque());
				bd.addBloque(bloqueActual);
				BloqueDeCodigo bd2 = new BloqueDeCodigo(
						instrConAlgo(IR_V, dirInicioBucle));
				aumentaCI(1);
				bd.addBloque(bd2);
			}
		}

		bloqueActual = bd;
	}

	private void codigo(Delete i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		Object o = vinculos.get(i.getDesignador());
		String cod = instrConAlgo(LIBERA, ((Integer)d.getDecoracion(o).get("tam")));
		aumentaCI(1);
		bd.addBloque(new BloqueDeCodigo(cod));
		bloqueActual = bd;
	}

	private void codigoBloque(Bloque i) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		List<Instruccion> insts = i.getInstrucciones();
		for (Instruccion in : insts){
			codigoInstruccion(in);
			bd.addBloque(bloqueActual);
		}
		bloqueActual = bd;
	}

	private void codigo(Asignacion i) {
		BloqueDeCodigo bd = new BloqueDeCodigo(DESAPILA_IND);	
		aumentaCI(1);
		codigo(i.getDesignador());
		bd.addBloque(bloqueActual);
		codigo(i.getExpresion());
		bd.addBloque(bloqueActual);
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
		if (tipo == modelo.instrucciones.Designador.Tipo.ARRAY) {
			codigo(des);
			bd.addBloque(bloqueActual);
			codigo(e);
			bd.addBloque(bloqueActual);
			bd.addBloque(new BloqueDeCodigo(SUMA));
			aumentaCI(1);
			//				codArray = SUMA;	
			
		} else if (tipo == modelo.instrucciones.Designador.Tipo.ID) {
			if (id.equalsIgnoreCase("null")){ 
				bd.setCodigo(instrConAlgo(APILA, 0));
				aumentaCI(1);

			} else {			
				
				// TODO
				
				Object obj = vinculos.get(designador);
				Map<String, Object> m = this.d.getDecoracion(obj);

//				System.out.println(obj);
							
				if (obj instanceof Parametro){
					// es un parámetro
					Parametro p = (Parametro) obj;
					if (p.isPorValor()){
						
						
					} else {
						Map<String, Object> m2 = this.d.getDecoracion(p);
						int dir = (int) m2.get("dir");
						int niv = (int) m2.get("nivel");
						
						if (niv == 0){
							bd.setCodigo(instrConAlgo(APILA, "DIR_"+p.getIdentificador()));
							aumentaCI(1);
						} else {						
							bd.setCodigo(instrConAlgo(APILA_DIR, "NIVEL_"+p.getIdentificador()) +
										instrConAlgo(APILA, "DIR_"+p.getIdentificador()) +
										SUMA);
							aumentaCI(3);
						}	
						
						
					}
					
					
				} else {
					// es una variable
					DecVariable dv = (DecVariable) obj;
					
					int dir = (int) m.get("dir");
					int niv = (int) m.get("nivel");
					
					if (niv == 0){
						bd.setCodigo(instrConAlgo(APILA, "DIR_"+dv.getIdentificador()));
						aumentaCI(1);
					} else {						
						bd.setCodigo(instrConAlgo(APILA_DIR, "NIVEL_"+dv.getIdentificador()) +
									instrConAlgo(APILA, "DIR_"+dv.getIdentificador()) +
									SUMA);
						aumentaCI(3);
					}	
				}
				
			}

		} else if (tipo == modelo.instrucciones.Designador.Tipo.CAMPO_DE_STRUCT) {
			codigo(des);
			bd.addBloque(bloqueActual);
			bd.addBloque(new BloqueDeCodigo(
					instrConAlgo(APILA, (Integer) d.getDecoracion(id).get("desp"))+
					SUMA
					));
			aumentaCI(1);

		} else if (tipo == modelo.instrucciones.Designador.Tipo.PUNTERO) {
			codigo(des);
			bd.addBloque(bloqueActual);
		
		}	

		bloqueActual = bd;
	}

	private void codigoDecSubprogramas(List<DecSubprograma> list) {
		BloqueDeCodigo bd = new BloqueDeCodigo();
		if (list == null){ 
			bloqueActual = bd;
			return; 
		}		

		for (DecSubprograma ds : list){
			d.insertaInfoEnNodo(ds, "inicio", getCI());
			Programa programa = ds.getPrograma();

			Integer tamDatos = (Integer) d.getDecoracion(programa.getDecVariables()).get("tam");
			if (tamDatos == null){ 
				//				throw new UnsupportedOperationException("tamDatos null"); 
				tamDatos = 0;
			}
			Integer nivel = (Integer) d.getDecoracion(programa.getDecVariables()).get("nivel");
			if (nivel == null){ 
				//				throw new UnsupportedOperationException("nivel null"); 
				nivel = 0;
			}

			String prologo = generaPrologo(nivel, tamDatos);
			bd.addBloque(new BloqueDeCodigo(prologo));
			aumentaCI(10); // cantidad del prologo			

			if (programa != null){ 
				codigoDecSubprogramas(programa.getDecSubprogramas());
				bd.addBloque(bloqueActual);
				codigoBloque(programa.getBloque());
				bd.addBloque(bloqueActual);
			}

			String epilogo = generaEpilogo(nivel, tamDatos);
			bd.addBloque(new BloqueDeCodigo(epilogo));
			aumentaCI(14); // cantidad del epílogo
		}


		bloqueActual = bd;		
	}

	// ASIGNA ESPACIOS

	private void asignaEspacioDecSubs(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null){ return; }
		for (DecSubprograma ds : decSubprogramas){
			d.insertaInfoEnNodo(ds, "inicio", getCI());
			asignaEspacio(ds);
		}
	}

	private void asignaEspacio(DecSubprograma ds) {
		if (ds.getPrograma() != null){
			asignaEspacio(ds.getPrograma());	
		}

		List<Parametro> params = ds.getParametros();
		int dir = 0;
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
		dir = anidamiento(ds)+1;
		d.insertaInfoEnNodo(p, "finDatos", dir);
		//		System.out.println("Anidamiento: " + dir);
		nivel = 0;
		asignaEspacio(p);
	}

	private void asignaEspacio(Programa p) {
		int dirCopia = dir;
		int nivelCopia = nivel;
		if (p.getDecTipos() != null) asignaEspacio(p.getDecTipos());
		if (p.getDecVariables() != null) asignaEspacioVars(p.getDecVariables());
		nivel++;
		d.insertaInfoEnNodo(p, "nivel", nivel);
		if (p.getDecSubprogramas() != null) asignaEspacioDecSubs(p.getDecSubprogramas());
		dir = dirCopia;
		nivel = nivelCopia;	
	}

	private void asignaEspacio(List<DecTipo> decTipos) {
		if (decTipos == null){ return; }		
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
//				System.out.println("TAM, DIR y NIVEL de " +
//				dv.getIdentificador() + " : " + "("+tam+","+this.dir+","+nivel+")");	
			} else {
				d.insertaInfoEnNodo(dv, "dir", dir);	
//				System.out.println("TAM, DIR y NIVEL de " + dv.getIdentificador() + " : " + "("+tam+","+dir+","+nivel+")");				
			}

			d.insertaInfoEnNodo(dv, "nivel", nivel);	
			d.insertaInfoEnNodo(dv, "tam", tam);
			dir += tam;				
			this.dir += tam;
		}
		d.insertaInfoEnNodo(decVariables, "tam", this.dir-oldDir-1);
		d.insertaInfoEnNodo(decVariables, "nivel", nivel);

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
			int despl = 0;
			List<DecTipo> tipos = struct.getTipos();
			for (DecTipo tip : tipos){
				d.insertaInfoEnNodo(tip.getId(), "desp", despl);
				despl += tamanioVar(tip.getTipo());
			}
			return despl;
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

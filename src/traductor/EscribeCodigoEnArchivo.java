package traductor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import modelo.instrucciones.Caso;
import modelo.instrucciones.Condicional;
import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.Delete;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Llamada;
import modelo.instrucciones.New;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.instrucciones.Read;
import modelo.instrucciones.TiposInstruccion;
import modelo.instrucciones.Write;

public class EscribeCodigoEnArchivo {
	
	private FileWriter fw;	
	private Programa prog;
	private Decoracion d;
	
	public EscribeCodigoEnArchivo(Programa p, Decoracion d) {
		this.prog = p;
		this.d = d;
	}
	
	private void abreArchivo(String nombreArchivo){
		try {
			fw = new FileWriter(new File(nombreArchivo), false);
		} catch (IOException e) { }
	}
	
	private void cierraArchivo(){
		try {
			fw.close();
		} catch (IOException e) { }
	}
	
	private void escribeEnArchivo(String codigo){
		try {
			fw.append(codigo);
		} catch (IOException e) { }
	}

	public void escribeCodigo(String nombreArchivo) {
		//abreArchivo(nombreArchivo);
		imprimeCodigo(prog);
//		muestraCodigo(prog);		
		//cierraArchivo();
	}
	
	public void muestraCodigo(Programa prog) {
		if (prog == null){ return; }
		muestraCodigoDS(prog.getDecSubprogramas());
		muestraCodigo(prog.getBloque());
	}
	
	private void muestraCodigo(DecSubprograma ds) {
		if (ds == null) { return; }
		muestraCodigoParams(ds.getParametros());
		muestraCodigo(ds.getPrograma());
		
		
		imprimeCodigo(ds);
	}

	private void muestraCodigoParams(List<Parametro> parametros) {
		if (parametros == null) { return; }
		for (Parametro p : parametros){
			muestraCodigo(p);
		}		
	}

	private void muestraCodigo(Parametro p) {
		imprimeCodigo(p);
	}

	private void muestraCodigoDS(List<DecSubprograma> decSubprogramas) {
		if (decSubprogramas == null) { return; }
		for (DecSubprograma ds : decSubprogramas){
			muestraCodigo(ds);
		}		
	}

	private void muestraCodigo(Instruccion i) {
		imprimeCodigo(i);
		
		TiposInstruccion tipo = i.getTipoInstruccion();
		if (tipo == TiposInstruccion.ASIG) {
			muestraCodigo((Asignacion) i);
		} else if (tipo == TiposInstruccion.BLOQUE) {
			muestraCodigo((Bloque) i);
		} else if (tipo == TiposInstruccion.BUCLE) {
			muestraCodigo((Bucle) i);
		} else if (tipo == TiposInstruccion.CASOS) {
			muestraCodigoCasos((List<Caso>) i);
		} else if (tipo == TiposInstruccion.DELETE) {
			muestraCodigo((Delete) i);
		} else if (tipo == TiposInstruccion.IF) {
			muestraCodigo((Condicional) i);
		} else if (tipo == TiposInstruccion.LLAMADA) {
			muestraCodigo((Llamada) i);
		} else if (tipo == TiposInstruccion.NEW) {
			muestraCodigo((New) i);
		} else if (tipo == TiposInstruccion.READ) {
			muestraCodigo((Read) i);
		} else if (tipo == TiposInstruccion.WRITE) {
			muestraCodigo((Write) i);
		}  
	}

	private void muestraCodigo(Bloque bloque) {
		if (bloque == null) return;
		for (Instruccion i : bloque.getInstrucciones()){
			muestraCodigo(i);			
		}		
	}

	private void muestraCodigoCasos(List<Caso> i) {
		if (i == null){ return; }
		for (Caso c : i){
			muestraCodigo(c);
		}
	}

	private void muestraCodigo(Write i) {
		imprimeCodigo(i);
	}

	private void muestraCodigo(Read i) {
		imprimeCodigo(i);
	}

	private void muestraCodigo(New i) {
		imprimeCodigo(i);	
	}

	private void muestraCodigo(Llamada i) {
		muestraCodigoExps(i.getParams());
	}

	private void muestraCodigoExps(List<Expresion> exps) {
		if (exps == null){ return; }
		for (Expresion e : exps){
			muestraCodigo(e);
		}
	}

	private void muestraCodigo(Expresion expresion) {
		if (expresion == null) return;
		TipoExpresion te = expresion.getTipoExpresion();
		switch(te){
			case BINARIA: {
				muestraCodigo((ExpresionBinaria)expresion);
			} break;
			case BOOLEAN: {
				muestraCodigo((ExpresionBoolean)expresion);				
			} break;
			case DESIGNADOR: {
				muestraCodigo((ExpresionDesignador)expresion);					
			} break;
			case DOUBLE: {
				muestraCodigo((ExpresionDouble)expresion);
			} break;
			case INTEGER: {
				muestraCodigo((ExpresionInteger)expresion);
			} break;
			case UNARIA: {
				muestraCodigo((ExpresionUnaria)expresion);
			} break;
			default: break;		
		}	
	}

	private void muestraCodigo(Condicional i) {
		imprimeCodigo(i);	
		
	}

	private void muestraCodigo(ExpresionUnaria expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(ExpresionInteger expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(ExpresionDouble expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(ExpresionDesignador expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(ExpresionBoolean expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(ExpresionBinaria expresion) {
		// TODO Auto-generated method stub
		
	}

	private void muestraCodigo(Delete i) {
		imprimeCodigo(i);	
		
	}

	private void muestraCodigo(List<Caso> i) {
		if (i == null){ return; }
		
		
	}

	private void muestraCodigo(Bucle i) {
		imprimeCodigo(i);	
	}

	private void muestraCodigo(Asignacion i) {
		imprimeCodigo(i);			
	}

	public String imprimeCodigo(Object nodo){
		String cod = (String) d.getDecoracion(nodo).get("cod");
		System.out.println(cod);
		return cod;
	}

}

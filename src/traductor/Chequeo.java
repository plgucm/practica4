package traductor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.instrucciones.Asignacion;
import modelo.instrucciones.Bloque;
import modelo.instrucciones.Bucle;
import modelo.instrucciones.Caso;
import modelo.instrucciones.Condicional;
import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.DecTipo;
import modelo.instrucciones.DecVariable;
import modelo.instrucciones.Delete;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Llamada;
import modelo.instrucciones.New;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.instrucciones.Read;
import modelo.instrucciones.TiposInstruccion;
import modelo.instrucciones.Write;
import modelo.tipos.Tipo;

public class Chequeo {

	private Map<Object, Object> vinculos;
	private Map<Object, Object> tipos;

	public Chequeo(Map<Object, Object> vinculos) {
		this.vinculos = vinculos;		
		this.tipos = new HashMap<Object, Object>();
	}

	public void chequea(Programa p) {
		chequeaTipos(p.getDecTipos());
		chequeaVars(p.getDecVariables());
		chequeaSubs(p.getDecSubprogramas());
		
		for (DecTipo d : p.getDecTipos()){
			simplificaDefTipos(d);
		}
		
		for (DecVariable d : p.getDecVariables()){
			simplificaDefTipos(d);					
		}
		
		for (DecSubprograma d : p.getDecSubprogramas()){
			simplificaDefTipos(d);			
		}
		
		chequea(p.getBloque());		
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

	private void chequeaTipos(List<DecTipo> decTipos) {
		for (DecTipo d : decTipos){
			chequea(d);
		}		
	}

	private void chequea(DecTipo d) {
		chequea(d.getTipo());
	}

	private void chequeaVars(List<DecVariable> decVariables) {
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
		for (DecSubprograma d : decSubprogramas){
			chequea(d);
		}		
	}

	private void chequea(DecSubprograma d) {
		d.getParametros();
		d.getPrograma();
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
		
	}

	private void chequea(Read i) {
		
	}

	private void chequea(New i) {
		
	}

	private void chequea(Llamada i) {
		
	}

	private void chequea(Condicional i) {
		
	}

	private void chequea(Delete i) {
		
	}

	private void chequea(Bucle i) {
		
	}

	private void chequea(Asignacion i) {
		
	}

	private void chequeaCasos(List<Caso> i) {
		
	}

}

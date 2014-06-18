package modelo.constructoras;

import java.util.ArrayList;
import java.util.List;

import modelo.expresiones.Expresion;
import modelo.expresiones.ExpresionBinaria;
import modelo.expresiones.ExpresionBoolean;
import modelo.expresiones.ExpresionDesignador;
import modelo.expresiones.ExpresionDouble;
import modelo.expresiones.ExpresionInteger;
import modelo.expresiones.ExpresionUnaria;
import modelo.instrucciones.Asignacion;
import modelo.instrucciones.Bloque;
import modelo.instrucciones.Bucle;
import modelo.instrucciones.Casos;
import modelo.instrucciones.Condicional;
import modelo.instrucciones.DecSubprogramas;
import modelo.instrucciones.DecTipos;
import modelo.instrucciones.DecVariables;
import modelo.instrucciones.Delete;
import modelo.instrucciones.Designador;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Llamada;
import modelo.instrucciones.New;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.instrucciones.Read;
import modelo.instrucciones.Write;
import modelo.operadores.OpBinario;
import modelo.operadores.OpUnario;
import modelo.tipos.Tipo;
import modelo.tipos.TipoArray;
import modelo.tipos.TipoBool;
import modelo.tipos.TipoDouble;
import modelo.tipos.TipoEntero;
import modelo.tipos.TipoID;
import modelo.tipos.TipoPuntero;
import modelo.tipos.TipoStruct;



public class Constructoras implements IConstructoras {

	@Override
	public Programa creaPrograma(DecTipos dt, DecVariables dv, DecSubprogramas ds, Bloque b) {		
		return new Programa(dt, dv, ds, b);
	}

	@Override
	public Tipo creaBool(String id) {
		return new TipoBool(id);
	}

	@Override
	public Tipo creaInt(String id) {
		return new TipoEntero(id);
	}

	@Override
	public Tipo creaDouble(String id) {
		return new TipoDouble(id);
	}

	@Override
	public Tipo creaID(String id) {
		return new TipoID(id);
	}

	@Override
	public Tipo creaArray(String id, Integer size, Tipo tipo) {
		return new TipoArray(id, size, tipo);
	}

	@Override
	public Tipo creaStruct(String id, DecTipos dectipos) {
		return new TipoStruct(id, ids, dectipos);
	}

	@Override
	public Tipo creaPuntero(String id, Tipo tipo) {
		return new TipoPuntero(id, tipo);
	}

	@Override
	public DecTipos creaDecTipos(List<Tipo> tipos) {
		 return new DecTipos(tipos);
	}

	@Override
	public DecVariables creaDecVariables(DecVariables dv, String id, Tipo tipo) {
		return new DecVariables(dv, id, tipo); 
	}

	@Override
	public DecSubprogramas creaDecSubprogramas(DecSubprogramas ds, String id,
			List<Parametro> params, Programa subprograma) {
		return new DecSubprogramas(ds, id, params, subprograma);
	}

	@Override
	public Parametro creaParametro(boolean modo, String id, Tipo tipo) {
		return new Parametro(modo, id, tipo);
	}

	@Override
	public Instruccion creaAsignacion(Designador ds, Expresion exp) {
		return new Asignacion(ds, exp);
	}

	@Override
	public Instruccion creaBloque(List<Instruccion> insts) {
		return new Bloque(insts);
	}

	@Override
	public Instruccion creaCondicional(Casos casos) {
		return new Condicional(casos);
	}

	@Override
	public Instruccion creaCasos(Casos casos, Expresion exp, Bloque bloque) {
		
		return new Casos(casos, exp, bloque);
	}

	@Override
	public Instruccion creaBucle(Casos casos) {
		
		return new Bucle(casos);
	}

	@Override
	public Instruccion creaLlamada(String id, List<Expresion> params) {
		
		return new Llamada(id, params);
	}

	@Override
	public Instruccion creaRead(Designador ds) {
		
		return new Read(ds);
	}

	@Override
	public Instruccion creaWrite(Expresion exp) {
		
		return new Write(exp);
	}

	@Override
	public Instruccion creaNew(Designador ds) {
		
		return new New(ds);
	}

	@Override
	public Instruccion creaDelete(Designador ds) {
		
		return new Delete(ds);
	}

	@Override
	public Designador creaDesignador(String id) {
		
		return new Designador(id);
	}

	@Override
	public Designador creaDesignador(Designador designador, Expresion exp) {
		
		return new Designador(designador, exp);
	}

	@Override
	public Designador creaDesignador(Designador designador, String id) {
		
		return new Designador(designador, id);
	}

	@Override
	public Expresion creaExpresionBinaria(Expresion exp0, OpBinario op,
			Expresion exp1) {
		
		return new ExpresionBinaria(exp0, op, exp1);
	}

	@Override
	public Expresion creaExpresionUnaria(OpUnario op, Expresion exp) {
		
		return new ExpresionUnaria(op, exp);
	}

	@Override
	public Expresion creaExpresionBoolean(boolean val) {
		
		return new ExpresionBoolean(val);
	}

	@Override
	public Expresion creaExpresionInteger(Integer val) {
		
		return new ExpresionInteger(val);
	}

	@Override
	public Expresion creaExpresionDouble(Double val) {
		return new ExpresionDouble(val);
	}

	@Override
	public Expresion creaExpresionDesignador(Designador ds) {
		return new ExpresionDesignador(ds);
	}

	@Override
	public OpBinario creaOpBinario(String tipo) {
		return new OpBinario(tipo);
	}

	@Override
	public OpUnario creaOpUnario(String tipo) {
		return new OpUnario(tipo);
	}

	public List<Expresion> creaArgumentos(Expresion e) {
		ArrayList<Expresion> exps = new ArrayList<Expresion>();
		exps.add(e);
		return exps;
	}

    

}

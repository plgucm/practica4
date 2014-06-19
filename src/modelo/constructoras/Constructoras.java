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
	public Programa creaPrograma(List<DecTipo> dts, List<DecVariable> dvs,
			List<DecSubprograma> dss, Bloque b) {
		return new Programa(dts, dvs, dss, b);
	}

	@Override
	public Tipo creaBool() {
		return new TipoBool();
	}

	@Override
	public Tipo creaInt() {
		return new TipoEntero();
	}

	@Override
	public Tipo creaDouble() {
		return new TipoDouble();
	}

	@Override
	public Tipo creaID(String id) {
		return new TipoID(id);
	}

	@Override
	public Tipo creaArray(Tipo tipo, Integer size) {
		return new TipoArray(size, tipo);
	}

	@Override
	public Tipo creaStruct(List<DecTipo> dts) {
		return new TipoStruct(dts);
	}

	@Override
	public Tipo creaPuntero(Tipo tipo) {
		return new TipoPuntero(tipo);
	}

	@Override
	public List<DecTipo> uneListaDecTipos(List<DecTipo> dts, DecTipo dt) {
		dts.add(dt);
		return dts;
	}

	@Override
	public List<DecTipo> creaListaDecTipos(DecTipo dt) {
		List<DecTipo> dts = new ArrayList<DecTipo>();
		dts.add(dt);
		return dts;
	}

	@Override
	public DecTipo creaDecTipo(String id, Tipo tipo) {
		return new DecTipo(id, tipo);
	}

	@Override
	public List<DecVariable> uneListaDecVariables(List<DecVariable> dvs,
			DecVariable dv) {
		dvs.add(dv);
		return dvs;
	}

	@Override
	public List<DecVariable> creaListaDecVariables(DecVariable dv) {
		List<DecVariable> dts = new ArrayList<DecVariable>();
		dts.add(dv);
		return dts;
	}

	@Override
	public DecVariable creaDecVariable(String id, Tipo tipo) {
		return new DecVariable(id, tipo);
	}

	@Override
	public List<DecSubprograma> uneListaDecSubprogramas(
			List<DecSubprograma> dss, DecSubprograma ds) {
		dss.add(ds);
		return dss;
	}

	@Override
	public List<DecSubprograma> creaListaDecSubprogramas(DecSubprograma ds) {
		List<DecSubprograma> dts = new ArrayList<DecSubprograma>();
		dts.add(ds);
		return dts;
	}

	@Override
	public DecSubprograma creaDecSubprograma(String id, List<Parametro> params,
			Programa subprograma) {
		return new DecSubprograma(id, params, subprograma);
	}

	@Override
	public List<Parametro> uneListaParametros(List<Parametro> params,
			Parametro p) {
		params.add(p);
		return params;
	}

	@Override
	public List<Parametro> creaListaParametros(Parametro p) {
		List<Parametro> dts = new ArrayList<Parametro>();
		dts.add(p);
		return dts;
	}

	@Override
	public Parametro creaParametroValor(String id, Tipo tipo) {
		return new Parametro(true, id, tipo);
	}

	@Override
	public Parametro creaParametroVariable(String id, Tipo tipo) {
		return new Parametro(false, id, tipo);
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
	public Instruccion creaBloqueVacio() {
		return new Bloque(new ArrayList<Instruccion>());
	}

	@Override
	public Instruccion creaIf(List<Caso> casos) {
		return new Condicional(casos);
	}

	@Override
	public List<Caso> uneListaCasos(List<Caso> casos, Caso caso) {
		casos.add(caso);
		return casos;
	}

	@Override
	public List<Caso> creaListaCasos(Caso caso) {
		List<Caso> casos = new ArrayList<Caso>();
		casos.add(caso);
		return casos;
	}

	@Override
	public Caso creaCaso(Expresion exp, Bloque bloque) {
		return new Caso(exp, bloque);
	}

	@Override
	public Instruccion creaBucle(List<Caso> casos) {
		return new Bucle(casos);
	}

	@Override
	public Instruccion creaLlamadaConArgumentos(String id,
			List<Expresion> params) {
		return new Llamada(id, params);
	}

	@Override
	public Instruccion creaLlamada(String id) {
		return new Llamada(id);
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
	public Expresion creaExpresionBinaria(Expresion exp0, OpBinario op, Expresion exp1) {
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
	public OpBinario creaOperadorIgual() {
		return new OpBinario("=");
	}

	@Override
	public OpBinario creaOperadorDistinto() {
		return new OpBinario("!=");
	}

	@Override
	public OpBinario creaOperadorMenor() {
		return new OpBinario("<");
	}

	@Override
	public OpBinario creaOperadorMayor() {
		return new OpBinario(">");
	}

	@Override
	public OpBinario creaOperadorMenorIgual() {
		return new OpBinario("<=");
	}

	@Override
	public OpBinario creaOperadorMayorIgual() {
		return new OpBinario(">=");
	}

	@Override
	public OpBinario creaOperadorMultiplicacion() {
		return new OpBinario("*");
	}

	@Override
	public OpBinario creaOperadorDivision() {
		return new OpBinario("/");
	}

	@Override
	public OpBinario creaOperadorModulo() {
		return new OpBinario("%");
	}

	@Override
	public OpBinario creaOperadorAnd() {
		return new OpBinario("&");
	}

	@Override
	public OpBinario creaOperadorMas() {
		return new OpBinario("+");
	}

	@Override
	public OpBinario creaOperadorMenos() {
		return new OpBinario("-");
	}

	@Override
	public OpBinario creaOperadorOr() {
		return new OpBinario("or");
	}

	@Override
	public OpUnario creaOperadorMenosUnario() {
		return new OpUnario("-");
	}

	@Override
	public OpUnario creaOperadorNot() {
		return new OpUnario("not");
	}

	@Override
	public OpUnario creaOperadorToInt() {
		return new OpUnario("toint");
	}

	@Override
	public OpUnario creaOperadorToDouble() {
		return new OpUnario("todouble");
	}

	@Override
	public Designador creaDesignadorId(String id) {
		return new Designador(id);
	}

	@Override
	public Designador creaDesignadorArray(Designador designador, Expresion exp) {
		return new Designador(designador, exp);
	}

	@Override
	public Designador creaDesignadorStruct(Designador designador, String id) {
		return new Designador(designador, id);
	}

	@Override
	public Designador creaDesignadorPuntero(Designador designador) {
		return new Designador(designador);
	}

	
	

}

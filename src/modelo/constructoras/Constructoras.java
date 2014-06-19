package modelo.constructoras;

import java.util.List;

import modelo.expresiones.Expresion;
import modelo.instrucciones.Bloque;
import modelo.instrucciones.Caso;
import modelo.instrucciones.DecSubprograma;
import modelo.instrucciones.DecTipo;
import modelo.instrucciones.DecVariable;
import modelo.instrucciones.Designador;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.operadores.OpBinario;
import modelo.operadores.OpUnario;
import modelo.tipos.Tipo;
import modelo.tipos.TipoBool;
import modelo.tipos.TipoDouble;
import modelo.tipos.TipoEntero;
import modelo.tipos.TipoID;


public class Constructoras implements IConstructoras {

	@Override
	public Programa creaPrograma(List<DecTipo> dts, List<DecVariable> dvs,
			List<DecSubprograma> dss, Bloque b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaBool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaInt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaDouble() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaArray(Tipo tipo, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaStruct(List<DecTipo> dts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tipo creaPuntero(Tipo tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecTipo> uneListaDecTipos(List<DecTipo> dts, DecTipo dt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecTipo> creaListaDecTipos(DecTipo dt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DecTipo creaDecTipo(String id, Tipo tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecVariable> uneListaDecVariables(List<DecVariable> dvs,
			DecVariable dv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecVariable> creaListaDecVariables(DecVariable dv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DecVariable creaDecVariable(String id, Tipo tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecSubprograma> uneListaDecSubprogramas(
			List<DecSubprograma> dss, DecSubprograma ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DecSubprograma> creaListaDecSubprogramas(DecSubprograma ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DecSubprograma creaDecSubprograma(String id, List<Parametro> params,
			Programa subprograma) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parametro> uneListaParametros(List<Parametro> params,
			Parametro p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parametro> creaListaParametros(Parametro p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parametro creaParametroValor(String id, Tipo tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parametro creaParametroVariable(String id, Tipo tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaAsignacion(Designador ds, Expresion exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaBloque(List<Instruccion> insts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaBloqueVacio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaIf(List<Caso> casos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion uneListaCasos(List<Caso> casos, Caso caso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaCaso(Expresion exp, Bloque bloque) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaBucle(List<Caso> casos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaLlamadaConArgumentos(String id,
			List<Expresion> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaLlamada(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaRead(Designador ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaWrite(Expresion exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaNew(Designador ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaDelete(Designador ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Designador creaDesignador(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Designador creaDesignador(Designador designador, Expresion exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Designador creaDesignador(Designador designador, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionBinaria(Expresion exp0, OpBinario op,
			Expresion exp1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionUnaria(OpUnario op, Expresion exp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionBoolean(boolean val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionInteger(Integer val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionDouble(Double val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expresion creaExpresionDesignador(Designador ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorIgual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorDistinto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMenor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMayor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMenorIgual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMayorIgual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMultiplicacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorDivision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorModulo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorAnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorMenos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpBinario creaOperadorOr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpUnario creaOperadorMenosUnario() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpUnario creaOperadorNot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpUnario creaOperadorToInt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpUnario creaOperadorToDouble() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Instruccion creaListaCasos(Caso caso) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}

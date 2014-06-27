package modelo.constructoras;

import java.util.List;
import java.util.Map;

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

public interface IConstructoras {
	
	public void ponNumeroLinea(Object obj, Integer numLinea);
	public Map<Object, Integer> getNumeroLinea();
	
    Programa creaPrograma(List<DecTipo> dts, List<DecVariable> dvs, List<DecSubprograma> dss, Bloque b);
    
    Tipo creaBool();
    Tipo creaInt();
    Tipo creaDouble();
    Tipo creaID(String id);
    Tipo creaArray(Tipo tipo, Integer size);
    Tipo creaStruct(List<DecTipo> dts);
    Tipo creaPuntero(Tipo tipo);
   
    List<DecTipo> uneListaDecTipos(List<DecTipo> dts, DecTipo dt);
    List<DecTipo> creaListaDecTipos(DecTipo dt);
    DecTipo creaDecTipo(String id, Tipo tipo);
    
    List<DecVariable> uneListaDecVariables(List<DecVariable> dvs, DecVariable dv);
    List<DecVariable> creaListaDecVariables(DecVariable dv);
    DecVariable creaDecVariable(String id, Tipo tipo);
    
    List<DecSubprograma> uneListaDecSubprogramas(List<DecSubprograma> dss, DecSubprograma ds);
    List<DecSubprograma> creaListaDecSubprogramas(DecSubprograma ds);
    DecSubprograma creaDecSubprograma(String id, List<Parametro> params, Programa subprograma);

    List<Parametro> uneListaParametros(List<Parametro> params, Parametro p);
    List<Parametro> creaListaParametros(Parametro p);

    Parametro creaParametroValor(String id, Tipo tipo);
    Parametro creaParametroVariable(String id, Tipo tipo);

    Instruccion creaAsignacion(Designador ds, Expresion exp);
    Instruccion creaBloque(List<Instruccion> insts);
    Instruccion creaBloqueVacio();
    Instruccion creaIf(List<Caso> casos);
    List<Caso> uneListaCasos(List<Caso> casos, Caso caso);
    List<Caso> creaListaCasos(Caso caso);
    Instruccion creaCaso(Expresion exp, Bloque bloque);
    Instruccion creaBucle(List<Caso> casos);
    Instruccion creaLlamadaConArgumentos(String id, List<Expresion> params);
    Instruccion creaLlamada(String id);
    Instruccion creaRead(Designador ds);
    Instruccion creaWrite(Expresion exp);
    Instruccion creaNew(Designador ds);
    Instruccion creaDelete(Designador ds);

    Designador creaDesignadorId(String id);
    Designador creaDesignadorArray(Designador designador, Expresion exp);  
    Designador creaDesignadorStruct(Designador designador, String id);
    Designador creaDesignadorPuntero(Designador designador);
    
    Expresion creaExpresionBinaria(Expresion exp0, OpBinario op, Expresion exp1);
    Expresion creaExpresionUnaria(OpUnario op, Expresion exp);

    Expresion creaExpresionBoolean(boolean val);
    Expresion creaExpresionInteger(Integer val);
    Expresion creaExpresionDouble(Double val);
    Expresion creaExpresionDesignador(Designador ds);
    
    OpBinario creaOperadorIgual();
    OpBinario creaOperadorDistinto();
    OpBinario creaOperadorMenor();
    OpBinario creaOperadorMayor();
    OpBinario creaOperadorMenorIgual();
    OpBinario creaOperadorMayorIgual();
    OpBinario creaOperadorMultiplicacion();
    OpBinario creaOperadorDivision();
    OpBinario creaOperadorModulo();
    OpBinario creaOperadorAnd();
    OpBinario creaOperadorMas();
    OpBinario creaOperadorMenos();
    OpBinario creaOperadorOr();
    
    OpUnario creaOperadorMenosUnario();
    OpUnario creaOperadorNot();
    OpUnario creaOperadorToInt();
    OpUnario creaOperadorToDouble();


}

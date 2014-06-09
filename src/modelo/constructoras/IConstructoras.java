package modelo.constructoras;

import java.util.List;

import modelo.expresiones.Expresion;
import modelo.instrucciones.Bloque;
import modelo.instrucciones.Casos;
import modelo.instrucciones.DecSubprogramas;
import modelo.instrucciones.DecTipos;
import modelo.instrucciones.DecVariables;
import modelo.instrucciones.Designador;
import modelo.instrucciones.Instruccion;
import modelo.instrucciones.Parametro;
import modelo.instrucciones.Programa;
import modelo.operadores.OpBinario;
import modelo.operadores.OpUnario;
import modelo.operadores.Operador;
import modelo.tipos.Tipo;

public interface IConstructoras {

    Programa creaPrograma(DecTipos dt, DecVariables dv, DecSubprogramas ds, Bloque b);


    Tipo creaBool();


    Tipo creaInt();


    Tipo creaDouble();


    Tipo creaID(String id);


    Tipo creaArray(Integer size, Tipo tipo);


    Tipo creaStruct(List<String> id, List<Tipo> tipo);


    Tipo creaPuntero(Tipo tipo);


    DecTipos creaDecTipos(DecTipos dt, String id, Tipo tipo);


    DecVariables creaDecVariables(DecVariables dv, String id, Tipo tipo);


    DecSubprogramas creaDecSubprogramas(DecSubprogramas ds, String id, List<Parametro> params, Programa subprograma);


    Parametro creaParametro(boolean modo, String id, Tipo tipo);


    Instruccion creaAsignacion(Designador ds, Expresion exp);


    Instruccion creaBloque(List<Instruccion> insts);


    Instruccion creaCondicional(Casos casos);


    Instruccion creaCasos(Casos casos, Expresion exp, Bloque bloque);


    Instruccion creaBucle(Casos casos);


    Instruccion creaLlamada(String id, List<String> params);


    Instruccion creaRead(Designador ds);


    Instruccion creaWrite(Expresion exp);


    Instruccion creaNew(Designador ds);


    Instruccion creaDelete(Designador ds);


    Designador creaDesignador(String id);


    Designador creaDesignador(Designador designador, Expresion exp);    


    Designador creaDesignador(Designador designador, String id);


    Expresion creaExpresionBinaria(Expresion exp0, OpBinario op, Expresion exp1);


    Expresion creaExpresionUnaria(OpUnario op, Expresion exp);


    Expresion creaExpresionBoolean(boolean val);


    Expresion creaExpresionInteger(Integer val);


    Expresion creaExpresionDouble(Double val);


    Expresion creaExpresionDesignador(Designador ds);


    Operador creaOpBinario(String tipo);


    Operador creaOpUnario(String tipo);

}

package evaluadorExpresionesCUP.alex;

import evaluadorExpresionesCUP.errors.GestionErroresEval;

%%
%cup
%line
%class AnalizadorLexicoEval
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresEval errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public void fijaGestionErrores(GestionErroresEval errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
separador = [ \t\r\b\n]
comentario = #[^\n]* 
numeroEntero = [\+,\-]?{parteEntera}
operadorSuma = \+
operadorMultiplicacion = \*
parentesisApertura = \(
parentesisCierre = \)
%%
{separador}               {}
{comentario}              {}
{numeroEntero}            {return ops.unidadEnt();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
[^]                       {errores.errorLexico(fila(),lexema());}  
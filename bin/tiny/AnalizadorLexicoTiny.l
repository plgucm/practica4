package tiny;

%%
%cup
%line
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
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
letra= ([a-z]|[A-Z])
numeroNatural = ({digitoPositivo}{digito}*|0)
parteDecimal =  (\.{digito}*{digitoPositivo}|0)
parteFlotante = ((e|E){numeroNatural})
numeroReal = {numeroNatural}({parteDecimal}{parteFlotante}?|{parteFlotante})
program = (p|P)(r|R)(o|O)(g|G)(r|R)(a|A)(m|M)
types = (t|T)(y|Y)(p|P)(e|E)(s|S)
struct = (s|S)(t|T)(r|R)(u|U)(c|C)(t|T)
pointer = (p|P)(o|O)(i|I)(n|N)(t|T)(e|E)(r|R)
int = (i|I)(n|N)(t|T)
double = (d|D)(o|O)(u|U)(b|B)(l|L)(e|E)
boolean = (b|B)(o|O)(o|O)(l|L)(e|E)(a|A)(n|N)
int = (i|I)(n|N)(t|T) 
variables = (v|V)(a|A)(r|R)(i|I)(a|A)(b|B)(l|L)(e|E)(s|S)
subprograms = (s|S)(u|U)(b|B)(p|P)(r|R)(o|O)(g|G)(r|R)(a|A)(m|M)(s|S)
subprogram = (s|S)(u|U)(b|B)(p|P)(r|R)(o|O)(g|G)(r|R)(a|A)(m|M)
and = (a|A)(n|N)(d|D)
array = (a|A)(r|R)(r|R)(a|A)(y|Y)
or = (o|O)(r|R)
not = (n|N)(o|O)(t|T)
if = (i|I)(f|F)
endif = (e|E)(n|N)(d|D)(i|I)(f|F)
read = (r|R)(e|E)(a|A)(d|D)
write = (w|W)(r|R)(i|I)(t|T)(e|E)
new = (n|N)(e|E)(w|W)
delete = (d|D)(e|E)(l|L)(e|E)(t|T)(e|E)
do = (d|D)(o|O)
enddo = (e|E)(n|N)(d|D)(d|D)(o|O)
toint = (t|T)(o|O)(i|I)(n|N)(t|T)
todouble = (t|T)(o|O)(d|D)(o|O)(u|U)(b|B)(l|L)(e|E)
true = (t|T)(r|R)(u|U)(e|E)
false = (f|F)(a|A)(l|L)(s|S)(e|E)
identificador = {letra}({letra}|{digito})*
mas = \+
menos = \-
por = \*
div = \/
mod = \%
asig = \=
igual = \=\=
distinto = !\=
menor = <
mayor = >
menorOIgual = <\=
mayorOIgual = >\=
pap = \(
pcierre = \)
coma = \,
pycoma = ;
dospuntos = \:
llaveap = \{
llavecierre = \}
cap = \[
ccierre = \]
punto = \.
flecha = \->
amp = \&
separador = [ \t\r\b\n]
comentario = #[^\n]* 
%%
{separador}               {}
{comentario}              {}
{numeroNatural}           {return ops.unidadNat();}
{numeroReal}              {return ops.unidadReal();}
{program}                 {return ops.unidadProgram();}
{types}                   {return ops.unidadTypes();}
{struct}                  {return ops.unidadStruct();}
{array}                   {return ops.unidadArray();}
{pointer}                 {return ops.unidadPointer();}
{variables}               {return ops.unidadVariables();}
{subprograms}             {return ops.unidadSubprograms();}
{subprogram}              {return ops.unidadSubprogram();}
{int}                     {return ops.unidadInt();}
{boolean}                 {return ops.unidadBoolean();}
{double}                  {return ops.unidadDouble();}
{and}                     {return ops.unidadAnd();}
{or}                      {return ops.unidadOr();}
{not}                     {return ops.unidadNot();}
{if}                      {return ops.unidadIf();}
{endif}                   {return ops.unidadEndIf();}
{do}                      {return ops.unidadDo();}
{enddo}                   {return ops.unidadEndDo();}
{read}                    {return ops.unidadRead();}
{write}                   {return ops.unidadWrite();}
{new}                     {return ops.unidadNew();}
{delete}                  {return ops.unidadDelete();}
{toint}                   {return ops.unidadToInt();} 
{todouble}                {return ops.unidadToDouble();}
{true}                    {return ops.unidadTrue();}
{false}                   {return ops.unidadFalse();}
{identificador}           {return ops.unidadId();}
{mas}                     {return ops.unidadMas();}
{menos}                   {return ops.unidadMenos();}
{por}                     {return ops.unidadPor();}
{div}                     {return ops.unidadDiv();}
{mod}                     {return ops.unidadMod();}
{asig}                    {return ops.unidadAsig();}
{igual}                   {return ops.unidadIgual();}
{distinto}                {return ops.unidadDistinto();}
{menor}                   {return ops.unidadMenor();}
{mayor}                   {return ops.unidadMayor();}
{menorOIgual}             {return ops.unidadMenorOIgual();}
{mayorOIgual}             {return ops.unidadMayorOIgual();}
{pap}                     {return ops.unidadPAp();}
{pcierre}                 {return ops.unidadPCierre();}
{coma}                    {return ops.unidadComa();}
{pycoma}                  {return ops.unidadPYComa();}
{dospuntos}               {return ops.unidadDosPuntos();}
{llaveap}                 {return ops.unidadLlaveAp();}
{llavecierre}             {return ops.unidadLlaveCierre();}
{cap}                     {return ops.unidadCAp();}
{ccierre}                 {return ops.unidadCCierre();}
{punto}                   {return ops.unidadPunto();}
{flecha}                  {return ops.unidadFlecha();}
{amp}                     {return ops.unidadAmp();}
[^]                       {errores.errorLexico(fila(),lexema());}  
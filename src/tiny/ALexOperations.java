package tiny;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  
  public UnidadLexica unidadNat() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NUMERO_NATURAL,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NUMERO_REAL,alex.lexema()); 
  }
  public UnidadLexica unidadProgram() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PROGRAM,alex.lexema());     
  }  
  public UnidadLexica unidadTypes() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TYPES,alex.lexema());     
  }  
  public UnidadLexica unidadStruct() {
     return new UnidadLexica(alex.fila(),ClaseLexica.STRUCT,alex.lexema());     
  }  
  public UnidadLexica unidadPointer() {
     return new UnidadLexica(alex.fila(),ClaseLexica.POINTER,alex.lexema());     
  }  
  public UnidadLexica unidadArray() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ARRAY,alex.lexema());     
  }  
  public UnidadLexica unidadVariables() {
     return new UnidadLexica(alex.fila(),ClaseLexica.VARIABLES,alex.lexema());     
  }  
  public UnidadLexica unidadSubprograms() {
     return new UnidadLexica(alex.fila(),ClaseLexica.SUBPROGRAMS,alex.lexema());     
  }  
  public UnidadLexica unidadSubprogram() {
     return new UnidadLexica(alex.fila(),ClaseLexica.SUBPROGRAM,alex.lexema());     
  }  
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),ClaseLexica.AND,alex.lexema());     
  }  
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),ClaseLexica.OR,alex.lexema());     
  }  
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NOT,alex.lexema());     
  }  
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IF,alex.lexema());     
  }  
  public UnidadLexica unidadEndIf() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ENDIF,alex.lexema());     
  }  
  public UnidadLexica unidadDo() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DO,alex.lexema());     
  }  
  public UnidadLexica unidadEndDo() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ENDDO,alex.lexema());     
  }  
  public UnidadLexica unidadRead() {
     return new UnidadLexica(alex.fila(),ClaseLexica.READ,alex.lexema());     
  }  
  public UnidadLexica unidadWrite() {
     return new UnidadLexica(alex.fila(),ClaseLexica.WRITE,alex.lexema());     
  }  
  public UnidadLexica unidadNew() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NEW,alex.lexema());     
  }  
  public UnidadLexica unidadDelete() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DELETE,alex.lexema());     
  }  
  public UnidadLexica unidadToInt() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TOINT,alex.lexema());     
  }  
  public UnidadLexica unidadToDouble() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TODOUBLE,alex.lexema());     
  }  
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ID,alex.lexema());     
  }  
  public UnidadLexica unidadMas() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAS,alex.lexema());     
  }  
  public UnidadLexica unidadMenos() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENOS,alex.lexema());     
  }  
  public UnidadLexica unidadPor() {
     return new UnidadLexica(alex.fila(),ClaseLexica.POR,alex.lexema());     
  }  
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DIV,alex.lexema());     
  }  
  public UnidadLexica unidadMod() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MOD,alex.lexema());     
  }  
  public UnidadLexica unidadAsig() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ASIG,alex.lexema());     
  }  
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IGUAL,alex.lexema());     
  }  
  public UnidadLexica unidadDistinto() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DISTINTO,alex.lexema());     
  }  
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENOR,alex.lexema());     
  }  
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAYOR,alex.lexema());     
  }  
  public UnidadLexica unidadMenorOIgual() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENOROIGUAL,alex.lexema());     
  }  
  public UnidadLexica unidadMayorOIgual() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAYOROIGUAL,alex.lexema());     
  }  
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PAP,alex.lexema());     
  }  
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PCIERRE,alex.lexema());     
  }  
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),ClaseLexica.COMA,alex.lexema());     
  }  
  public UnidadLexica unidadPYComa() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PYCOMA,alex.lexema());     
  }  
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DOSPUNTOS,alex.lexema());     
  }
  public UnidadLexica unidadLlaveAp() {
     return new UnidadLexica(alex.fila(),ClaseLexica.LLAVEAP,alex.lexema());     
  }  
  public UnidadLexica unidadLlaveCierre() {
     return new UnidadLexica(alex.fila(),ClaseLexica.LLAVECIERRE,alex.lexema());     
  }  
  public UnidadLexica unidadCAp() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CAP,alex.lexema());     
  }  
  public UnidadLexica unidadCCierre() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CCIERRE,alex.lexema());     
  }  
  public UnidadLexica unidadInt() {
     return new UnidadLexica(alex.fila(),ClaseLexica.INT,alex.lexema());     
  }  
  public UnidadLexica unidadDouble() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DOUBLE,alex.lexema());     
  }  
  public UnidadLexica unidadBoolean() {
     return new UnidadLexica(alex.fila(),ClaseLexica.BOOLEAN,alex.lexema());     
  }  
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PUNTO,alex.lexema());     
  }  
  public UnidadLexica unidadFlecha() {
     return new UnidadLexica(alex.fila(),ClaseLexica.FLECHA,alex.lexema());     
  }  
  public UnidadLexica unidadAmp() {
     return new UnidadLexica(alex.fila(),ClaseLexica.AMP,alex.lexema());     
  }
  public UnidadLexica unidadTrue() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TRUE,alex.lexema());     
  }
  public UnidadLexica unidadFalse() {
     return new UnidadLexica(alex.fila(),ClaseLexica.FALSE,alex.lexema());     
  }
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),ClaseLexica.EOF,"<EOF>"); 
  }
}

package tiny;

public class GestionErroresTiny {
   public void errorLexico(int fila, String lexema) {
     System.out.println("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
     System.exit(1);
   }  
   public void errorSintactico(UnidadLexica unidadLexica) {
     System.out.println("ERROR fila "+unidadLexica.fila()+": Elemento inexperado "+unidadLexica.value+" ("+unidadLexica.sym+")");
     System.exit(1);
   }
}

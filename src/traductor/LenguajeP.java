package traductor;

import java.util.ArrayList;


public class LenguajeP {
	
	public static String IR_IND = "ir_ind\n";
	public static String IR_A = "ir_a ALGO\n";
	public static String IR_F = "ir_f ALGO\n";
	public static String IR_V = "ir_v ALGO\n";
	
	public static String APILA = "apila ALGO\n";
	public static String DESAPILA = "desapila\n";
	public static String APILA_DIR = "apila_dir ALGO\n";
	public static String DESAPILA_DIR = "desapila_dir ALGO\n";
	public static String APILA_IND = "apila_ind\n";
	public static String DESAPILA_IND = "desapila_ind\n";
	
	public static String READ = "read ALGO\n";
	public static String WRITE = "write ALGO\n";
	
	public static String TRUE = "true";
	public static String FALSE = "false";
	
	public static String SUMA = "suma\n";
	public static String RESTA = "resta\n";
	public static String MUL = "mul\n";
	public static String DIV = "div\n";
	public static String MOD = "mod\n";
	
	public static String AND = "and\n";
	public static String OR = "or\n";
	public static String NOT = "not\n";
	
	public static String CLONA = "clona\n";
	public static String DUP = "dup\n";
	public static String LIBERA = "libera\n";
	public static String RESERVA = "reserva\n";
	
	public static String MAYOR = "mayor\n";
	public static String MAYOR_IGUAL = "mayorigual\n";
	public static String MENOR = "menor\n";
	public static String MENOR_IGUAL = "menorigual\n";
	public static String IGUAL = "igual\n";
	public static String DISTINTO = "distinto\n";

	
	public static String generaInicio(int tamDatos, int dirMain){
		return instrConAlgo(APILA, tamDatos) +
			   instrConAlgo(APILA_DIR, 0) +
			   instrConAlgo(IR_A, dirMain);
	}
	
	public static String generaPrellamada(ArrayList<String> params, int dirRetorno, int dirSalto){
		StringBuilder sb = new StringBuilder();
		
		sb.append(PRELLAMADA_INICIO); 
		for (int i = 0, s = params.size(); i < s; ++i){
			sb.append(DUP);
			sb.append(instrConAlgo(APILA, i));
			sb.append(SUMA);
			// Parametros
			sb.append(params.get(i));
		}		
		
		sb.append(PRELLAMADA_FINAL);
		sb.append(instrConAlgo(APILA, dirRetorno));
		sb.append(DESAPILA_IND);
		sb.append(instrConAlgo(IR_A, dirSalto));
		
		return sb.toString();
	}
	
	public static String generaEpilogo(int nivel, int tamDatos){
		StringBuilder sb = new StringBuilder();
		
		sb.append(instrConAlgo(APILA_DIR, 0));
		sb.append(instrConAlgo(APILA, tamDatos+2));
		sb.append(RESTA);
		sb.append(instrConAlgo(DESAPILA_DIR, 0));
		sb.append(instrConAlgo(APILA_DIR, 0));
		sb.append(instrConAlgo(APILA, 2));
		sb.append(SUMA);
		sb.append(APILA_IND);
		sb.append(instrConAlgo(DESAPILA_DIR, nivel));
		sb.append(instrConAlgo(DESAPILA_DIR, 0));
		sb.append(instrConAlgo(APILA, 1));
		sb.append(SUMA);
		sb.append(APILA_IND);
		sb.append(IR_IND);
		
		return sb.toString();		
	}
	
	public static String generaPrologo(int nivel, int tamDatos){

		StringBuilder sb = new StringBuilder();
		sb.append(instrConAlgo(APILA_DIR, 0));
		sb.append(instrConAlgo(APILA, 2));
		sb.append(SUMA);
		sb.append(instrConAlgo(APILA_DIR, nivel));
		sb.append(DESAPILA_IND);
		sb.append(instrConAlgo(DESAPILA_DIR, nivel));
		sb.append(instrConAlgo(APILA_DIR, 0));
		sb.append(instrConAlgo(APILA, tamDatos+2));
		sb.append(SUMA);
		sb.append(instrConAlgo(DESAPILA_DIR, 0));		
		
		return sb.toString();		
	}
	
	public static String PRELLAMADA_INICIO = 
				instrConAlgo(APILA_DIR, 0) +
				instrConAlgo(APILA, 3) +
				SUMA;	
	
	public static String PRELLAMADA_FINAL =
				instrConAlgo(APILA_DIR, 0) +
				instrConAlgo(APILA, 1) +
				SUMA;
	
	public static String instrConAlgo(String inst, int algo){
		return inst.replace("ALGO", String.valueOf(algo));
	}
	
	public static String instrConAlgo(String inst, double algo){
		return inst.replace("ALGO", String.valueOf(algo));
	}
	
	public static String instrConAlgo(String inst, String algo){
		return inst.replace("ALGO", algo);
	}
	
}

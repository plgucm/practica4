package maquinap.instrucciones.io;

import java.util.Scanner;

import maquinap.MaquinaP;
import maquinap.instrucciones.Instruccion;
import maquinap.valor.Bool;
import maquinap.valor.Int;
import maquinap.valor.Valor;

public class Lee extends Instruccion {
	
	private static Scanner sc;

	@Override
	public void ejecutar(MaquinaP maq) throws Exception {
		System.out.print("> ");
		String value = sc.nextLine();
		if (value.isEmpty())ejecutar(maq);
		else {
		Valor<?> valueMP;
		if (value.equalsIgnoreCase("true")){
			valueMP = new Bool(new Boolean(true));
		} else if (value.equalsIgnoreCase("false")){
			valueMP = new Bool(new Boolean(false));			
		} else {
			
			valueMP = new Int(Integer.valueOf(value));
		}
		maq.getPilaEvaluacion().push(valueMP);
		maq.aumentarContadorPrograma(1);
		}
		
	}
	
	public static void abreEscaner(){
		sc = new Scanner(System.in);
	}
	
	public static void cierraEscaner(){
		sc.close();
	}

}

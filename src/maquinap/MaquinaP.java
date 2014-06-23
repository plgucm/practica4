package maquinap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import maquinap.instrucciones.Instruccion;
import maquinap.instrucciones.apila.Apila;
import maquinap.instrucciones.apila.ApilaDir;
import maquinap.instrucciones.apila.ApilaInd;
import maquinap.instrucciones.aritmeticas.Div;
import maquinap.instrucciones.aritmeticas.Mod;
import maquinap.instrucciones.aritmeticas.Mult;
import maquinap.instrucciones.aritmeticas.Neg;
import maquinap.instrucciones.aritmeticas.Resta;
import maquinap.instrucciones.aritmeticas.Suma;
import maquinap.instrucciones.aritmeticas.ToDouble;
import maquinap.instrucciones.aritmeticas.ToInt;
import maquinap.instrucciones.comparacion.Dist;
import maquinap.instrucciones.comparacion.Igual;
import maquinap.instrucciones.comparacion.Mayor;
import maquinap.instrucciones.comparacion.MayorIgual;
import maquinap.instrucciones.comparacion.Menor;
import maquinap.instrucciones.comparacion.MenorIgual;
import maquinap.instrucciones.desapila.DesapilaDir;
import maquinap.instrucciones.desapila.DesapilaInd;
import maquinap.instrucciones.io.Escribe;
import maquinap.instrucciones.io.Lee;
import maquinap.instrucciones.ir.IrA;
import maquinap.instrucciones.ir.IrF;
import maquinap.instrucciones.ir.IrInd;
import maquinap.instrucciones.ir.IrV;
import maquinap.instrucciones.logicas.And;
import maquinap.instrucciones.logicas.Not;
import maquinap.instrucciones.logicas.Or;
import maquinap.instrucciones.memoria.Clona;
import maquinap.instrucciones.memoria.Dup;
import maquinap.instrucciones.memoria.Espacio;
import maquinap.instrucciones.memoria.Libera;
import maquinap.instrucciones.memoria.Reserva;
import maquinap.valor.Bool;
import maquinap.valor.VInt;
import maquinap.valor.Valor;

public class MaquinaP {

	private final Map<Integer, Valor<?>> memoriaDatos = new HashMap<Integer, Valor<?>>();
	private final List<Espacio> listaDeHuecos = new LinkedList<Espacio>();
	private final Stack<Valor<?>> pilaEvaluacion = new Stack<Valor<?>>();
	private final List<Instruccion> memoriaPrograma = new ArrayList<Instruccion>();
	private int contadorPrograma = 0;

	private static final boolean DEBUG = false;
	private static final int INICIO_MONTON = 1024;
	private static final int TAM_MONTON = 2048;

	public MaquinaP() {
		listaDeHuecos.add(new Espacio(INICIO_MONTON, TAM_MONTON, true));
	}

	public Map<Integer, Valor<?>> getMemoriaDatos() {
		return memoriaDatos;
	}

	public List<Espacio> getListaDeEspacios() {
		return listaDeHuecos;
	}

	public Stack<Valor<?>> getPilaEvaluacion() {
		return pilaEvaluacion;
	}

	public List<Instruccion> getMemoriaPrograma() {
		return memoriaPrograma;
	}

	public void incrementaContadorPrograma() {
		++contadorPrograma;
	}

	public void aumentarContadorPrograma(int cantidad) {
		contadorPrograma += cantidad;
	}

	public int getContadorPrograma() {
		return contadorPrograma;
	}

	// ////////////////

	public static void main(String[] args) {
		new MaquinaP().ejecuta(args[0]);
	}

	private void ejecuta(String archivoDeEntrada) {
		Scanner sc;
		try {
			sc = new Scanner(new File(archivoDeEntrada));
			ArrayList<String> lineaSinBasura = new ArrayList<String>();

			while (sc.hasNextLine()) {
				String[] linea = sc.nextLine().split(" ");

				if (linea.length > 0) {
					String first = linea[0].trim();
					if (!first.startsWith("//") && !first.isEmpty()) {
						boolean tieneArgs = true;
						if (first.endsWith("//")) {
							tieneArgs = false;
							first = first.split("\t")[0].trim();
						}
						lineaSinBasura.add(first);

						if (tieneArgs && linea.length > 1) {
							String second = linea[1].trim();
							if (second.contains("//")) {
								String[] args = second.split("\t");
								if (args.length > 0) {
									String num = args[0].trim();
									if (!num.isEmpty()) {
										lineaSinBasura.add(num);
									}
								}
							} else if (!second.isEmpty())
								lineaSinBasura.add(second);
						}

						Instruccion instruccion = transformaEnInstruccion(lineaSinBasura);
						agregaEnMemoriaDePrograma(instruccion);

						lineaSinBasura.clear();
					}
				}
			}

			sc.close();

			Lee.abreEscaner();
			while (contadorPrograma < memoriaPrograma.size()) {
				Instruccion instr = memoriaPrograma.get(contadorPrograma);
				if (DEBUG) {
					System.out.println("----------------------------------");
					System.out.println("contadorPrograma:" + contadorPrograma
							+ ", instr: "
							+ instr.toString());
					System.out.println("pilaEvaluacion:"
							+ getPilaEvaluacion().toString());
					System.out.println("memoriaDeDatos:"
							+ Arrays.toString(getMemoriaDatos().entrySet()
									.toArray()));
				}
				instr.ejecutar(this);
			}
			Lee.cierraEscaner();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Instruccion transformaEnInstruccion(ArrayList<String> linea)
			throws Exception {
		int size = linea.size();
		if (size == 1) {
			String inst = linea.get(0);

			// ENTRADA / SALIDA

			if (inst.equalsIgnoreCase("LEE")) {
				return new Lee();
			} else if (inst.equalsIgnoreCase("ESCRIBE")) {
				return new Escribe();
			}

			// ARITMETICAS

			else if (inst.equalsIgnoreCase("MUL")) {
				return new Mult();
			} else if (inst.equalsIgnoreCase("SUMA")) {
				return new Suma();
			} else if (inst.equalsIgnoreCase("DIV")) {
				return new Div();
			} else if (inst.equalsIgnoreCase("RESTA")) {
				return new Resta();
			} else if (inst.equalsIgnoreCase("MOD")) {
				return new Mod();
			} else if (inst.equalsIgnoreCase("NEG")) {
				return new Neg();
			} else if (inst.equalsIgnoreCase("TODOUBLE")) {
				return new ToDouble();
			} else if (inst.equalsIgnoreCase("TOINT")) {
				return new ToInt();
			}

			// COMPARACION

			else if (inst.equalsIgnoreCase("IGUAL")) {
				return new Igual();
			} else if (inst.equalsIgnoreCase("DISTINTO")) {
				return new Dist();
			} else if (inst.equalsIgnoreCase("MENORIGUAL")) {
				return new MenorIgual();
			} else if (inst.equalsIgnoreCase("MAYORIGUAL")) {
				return new MayorIgual();
			} else if (inst.equalsIgnoreCase("MENOR")) {
				return new Menor();
			} else if (inst.equalsIgnoreCase("MAYOR")) {
				return new Mayor();
			}

			// LOGICAS

			else if (inst.equalsIgnoreCase("AND")) {
				return new And();
			} else if (inst.equalsIgnoreCase("OR")) {
				return new Or();
			} else if (inst.equalsIgnoreCase("NOT")) {
				return new Not();
			}

			else if (inst.equalsIgnoreCase("DESAPILA_IND")) {
				return new DesapilaInd();
			} else if (inst.equalsIgnoreCase("APILA_IND")) {
				return new ApilaInd();
			} else if (inst.equalsIgnoreCase("IR_IND")) {
				return new IrInd();
			} else if (inst.equalsIgnoreCase("DUP")) {
				return new Dup();
			}

		} else if (size == 2) {
			String inst1 = linea.get(0);
			String inst2 = linea.get(1);

			if (inst1.equalsIgnoreCase("APILA")) {
				if (inst2.equalsIgnoreCase("true"))
					return new Apila(new Bool(true));
				else if (inst2.equalsIgnoreCase("false"))
					return new Apila(new Bool(false));
				return new Apila(new VInt(Integer.valueOf(inst2)));
			} else if (inst1.equalsIgnoreCase("APILA_DIR")) {
				return new ApilaDir(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("DESAPILA_DIR")) {
				return new DesapilaDir(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("RESERVA")) {
				return new Reserva(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("LIBERA")) {
				return new Libera(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("IR_A")) {
				return new IrA(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("IR_V")) {
				return new IrV(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("IR_F")) {
				return new IrF(Integer.valueOf(inst2));
			} else if (inst1.equalsIgnoreCase("CLONA")) {
				return new Clona(Integer.valueOf(inst2));
			}

		}

		throw new Exception("Conversión a instrucción fallida, entrada ~> "
				+ Arrays.toString(linea.toArray()));
	}

	private void agregaEnMemoriaDePrograma(Instruccion instruccion) {
		memoriaPrograma.add(instruccion);
	}

	public void setContadorPrograma(int dir) {
		contadorPrograma = dir;
	}

}

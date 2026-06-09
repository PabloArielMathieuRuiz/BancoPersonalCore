package util;

import java.util.Scanner;

public class InputReader {

	/** Scanner único para toda la clase (evita abrir varios sobre System.in). */
	private static final Scanner scanner = new Scanner(System.in);

	/** Constructor privado para impedir crear instancias de la clase. */
	private InputReader() {
		// Evita instanciación
	}

	/**
	 * Lee un entero desde teclado con validación.
	 * 
	 * @param input
	 * @return
	 */
	public static int readInt(String input) {
		System.out.print(input);
		while (!scanner.hasNextInt()) { // Mientras no sea entero, pide de nuevo
			System.out.print("Valor inválido. Intente de nuevo: ");
			scanner.next(); // Limpia el dato incorrecto
		}
		return scanner.nextInt();
	}

	/**
	 * Lee un número decimal (double) desde teclado con validación.
	 * 
	 * @param input
	 * @return
	 */
	public static double readDouble(String input) {
		System.out.print(input);
		while (!scanner.hasNextDouble()) {
			System.out.print("Valor inválido. Intente de nuevo: ");
			scanner.next();
		}
		return scanner.nextDouble();
	}

	/**
	 * Lee una cadena simple (sin espacios) desde teclado.
	 * 
	 * @param input
	 * @return
	 */
	public static String readString(String input) {
		System.out.print(input);
		return scanner.next();
	}
	
	/**
	 * Lee un entero desde teclado con validación.
	 * 
	 * @param input
	 * @return
	 */
	
	public static float readFloat(String input) {
		System.out.print(input);
		while (!scanner.hasNextFloat()) { // Mientras no sea entero, pide de nuevo
			System.out.print("Valor inválido. Intente de nuevo: ");
			scanner.next(); // Limpia el dato incorrecto
		}
		return scanner.nextFloat();
	}
}

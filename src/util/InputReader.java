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
		int valor = 0;
		boolean valido = false;
		while (!valido) {
			System.out.print(input);
			if (scanner.hasNextInt()) {
				valor = scanner.nextInt();
				scanner.nextLine(); // Consume el salto de línea residual
				valido = true;
			} else {
				System.out.println("Error: Debes introducir un número entero.");
				scanner.nextLine(); // Limpia la entrada errónea completa
			}
		}
		return valor;
	}

	/**
	 * Lee un número decimal (double) desde teclado con validación.
	 * 
	 * @param input
	 * @return
	 */
	public static double readDouble(String input) {
		double valor = 0;
		boolean valido = false;
		while (!valido) {
			System.out.print(input);
			if (scanner.hasNextDouble()) {
				valor = scanner.nextDouble();
				scanner.nextLine(); // Consume el salto de línea residual
				valido = true;
			} else {
				System.out.println("Error: Debes introducir un número decimal.");
				scanner.nextLine(); // Limpia la entrada errónea completa
			}
		}
		return valor;
	}

	/**
	 * Lee una cadena simple (sin espacios) desde teclado.
	 * 
	 * @param input
	 * @return
	 */
	public static String readString(String input) {
		System.out.print(input);
		return scanner.nextLine(); // Lee toda la línea evitando problemas de buffer
	}

	/**
	 * Lee un entero desde teclado con validación.
	 * 
	 * @param input
	 * @return
	 */

	public static float readFloat(String input) {
		float valor = 0;
		boolean valido = false;
		while (!valido) {
			System.out.print(input);
			if (scanner.hasNextFloat()) {
				valor = scanner.nextFloat();
				scanner.nextLine(); // Consume el salto de línea residual
				valido = true;
			} else {
				System.out.println("Error: Debes introducir un número decimal.");
				scanner.nextLine(); // Limpia la entrada errónea completa
			}
		}
		return valor;
	}
}

/**
 * 
 */
package util;

/**
 * Clase que da soporte técnico transversal.
 * 
 * @author Pablo
 *
 */
public class Formateador {
	public static String formatoMoneda(double cantidad) {
		return String.format("%.2f €", cantidad);
	}

	public static String normalizarUsername(String username) {
		String usernameNormalizado = username.trim().toLowerCase();
		return usernameNormalizado;
	}

	public static String normalizarPassword(String password) {
		String passwordNormalizado = password.trim();
		return passwordNormalizado;
	}
}

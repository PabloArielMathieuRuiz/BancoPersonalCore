/**
 * 
 */
package validation;

import excepciones.validacion.IbanInvalidoException;

/**
 * Independiente del Service. Si mañana el proyecto crece, estas clases pueden
 * ir a una librería externa.
 * 
 * @author Pedro
 *
 */
public class Validador {
	public static void validarIban(String iban) {
		// Ejemplo de formato: debe empezar por ES y tener al menos 10 caracteres
		if (iban == null || !iban.toUpperCase().startsWith("ES") || iban.length() < 10) {
			throw new IbanInvalidoException(iban);
		}
	}
}

/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * @author Pablo
 *
 */
public class IbanInvalidoException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public IbanInvalidoException(String iban) {
		super("El formato del IBAN '" + iban + "' no es válido.", "ERR_VAL_002");
	}
}

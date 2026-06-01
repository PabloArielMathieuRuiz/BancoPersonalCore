/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * 
 */
public class FormatoUserNameInvalidoException extends BancoException {

	private static final long serialVersionUID = 1L;

	public FormatoUserNameInvalidoException(String mensaje) {
		super("El nombre de usuario no es valido", "EER_VAL_003");
	}

	/**
	 * 
	 */

}

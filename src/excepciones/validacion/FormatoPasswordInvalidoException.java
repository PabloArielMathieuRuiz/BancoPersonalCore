/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * @author Pablo
 */
public class FormatoPasswordInvalidoException extends BancoException {

	private static final long serialVersionUID = 1L;

	public FormatoPasswordInvalidoException() {
		super("La contraseña no es valida", "EER_VAL_004");
	}

	/**
	 * 
	 */

}

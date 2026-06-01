/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * 
 */
public class FormatoPasswordInvalidoException extends BancoException {

	private static final long serialVersionUID = 1L;

	public FormatoPasswordInvalidoException() {
		super("La contraseña no es valida", "EER_VAL_004");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */

}

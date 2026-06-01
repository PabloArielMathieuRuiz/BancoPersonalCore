/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * @author Pedro
 *
 */
public class DniInvalidoException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public DniInvalidoException(String dni) {
		super("El DNI '" + dni + "' no tiene un formato válido.", "ERR_VAL_001");
	}
}

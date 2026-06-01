/**
 * 
 */

package excepciones.seguridad;

/**
 *  Esta clase se encarga de que al introducir datos que no coincidan con la base de datos
 *  
 *  
 *  Esta clase se lanza cuando el usuario existe pero no la contraseeña 
 *  pero en ningun momento se aclara que es lo que esta incorrecto por motivas de seguridad
 * 
 * @author Pablo
 */
import excepciones.base.BancoException;

public class CredencialesInvalidasException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public CredencialesInvalidasException() {

		super("Usuario o contraseña incorrectos", "Err_AUTH_001");

	}
}

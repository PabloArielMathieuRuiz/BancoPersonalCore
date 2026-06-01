/**
 * 
 */
package excepciones.seguridad;
/**
 * 
 * Esta clase se va ha lanzar cuando se intenta acceder a una funcionalidad
 * protegida sin haber completado el proceso de login
 * 
 * 
 * 
 * 
 * @author Pablo
 */

import excepciones.base.BancoException;

public class SesionNoIniciadaException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public SesionNoIniciadaException() {

		super("La sesion no se ha podido iniciar", "Err_AUTH_003");

	}

}

/**
 * 
 */
package excepciones.seguridad;

/**
 * 
 * Esta clase se lanza en el caso de que el usuario no esta registrado en la 
 * base de datos
 * 
 * @author Pablo
 * 
 *  
 */


import excepciones.base.BancoException;

public class UsuarioNoRegistradoException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public UsuarioNoRegistradoException(String username) {

		super("El usuraio "+ username+" no esta registrado en el sistema", "Err_AUTH_002");

	}

}

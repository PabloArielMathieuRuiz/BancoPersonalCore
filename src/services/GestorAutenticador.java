/**
 * 
 */
package services;

import dao.UsuarioDao;
import excepciones.seguridad.CredencialesInvalidasException;
import excepciones.seguridad.UsuarioNoRegistradoException;
import modelo.Usuario;

/**
 * Interpreta lo que devuelve el DAO, decide si es un error o exito. No imprime,
 * no lee del teclado y no accede a la base de datos
 * 
 * El servicio lanza excepciones nunca las captura
 */
public class GestorAutenticador {

	UsuarioDao usuarioDao;

	public GestorAutenticador() {

		usuarioDao = null;

	}

	public GestorAutenticador(UsuarioDao usuarioDao) {

		usuarioDao = new UsuarioDao();

	}

	/**
	 * Verifica las credenciales de un usuario
	 * 
	 * @param username   Nombre del usuario introducido
	 * @param contraseña Contraseña introducida
	 * @return El usuario autenticado
	 */
	public Usuario autenticar(String username, String contraseña) {

		Usuario user = usuarioDao.buscarPorUsername(username);

		if (user == null) {
			throw new UsuarioNoRegistradoException(username);
		}

		if (!user.getContraseña().equals(contraseña)) {
			
			throw new CredencialesInvalidasException();
		}
		
		return user;
	}
}

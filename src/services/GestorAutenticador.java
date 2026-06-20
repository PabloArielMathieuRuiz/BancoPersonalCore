/**
 * 
 */
package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.UsuarioDao;
import excepciones.negocio.SaldoInsuficienteException;
import excepciones.seguridad.CredencialesInvalidasException;
import excepciones.seguridad.UsuarioNoRegistradoException;
import excepciones.validacion.IbanInvalidoException;
import excepciones.validacion.InputNoMenorQue0Exception;
import modelo.Cuenta;
import modelo.Usuario;
import util.GestorPassword;

/**
 * Interpreta lo que devuelve el DAO, decide si es un error o exito. No imprime,
 * no lee del teclado y no accede a la base de datos
 * 
 * El servicio lanza excepciones nunca las captura
 */
public class GestorAutenticador {

	UsuarioDao usuarioDao;
	private static final Logger logger = LoggerFactory.getLogger(GestorAutenticador.class);

	public GestorAutenticador() {
		usuarioDao = new UsuarioDao();
	}

	public GestorAutenticador(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	/**
	 * Verifica las credenciales de un usuario
	 * 
	 * @param username   Nombre del usuario introducido
	 * @param contraseña Contraseña introducida
	 * @return El usuario autenticado
	 */
	public Usuario autenticar(String username, String password) {

		// normalizar aqui los datos

		String usernameNormalizado = util.Formateador.normalizarUsername(username);
		String passwordNormailizado = util.Formateador.normalizarPassword(password);

		validation.ValidarUsername.validarUsername(usernameNormalizado);
		validation.ValidarPassword.validarPassword(passwordNormailizado);

		logger.info("Validando credenciales");
		logger.info("Intento de login para username: '{}'", username);

		Usuario user = usuarioDao.buscarPorUsername(usernameNormalizado);

		if (user == null) {
			throw new UsuarioNoRegistradoException(username);
		}

		if (!GestorPassword.verificar(passwordNormailizado, user.getContraseña())) {

			throw new CredencialesInvalidasException();
		}

		return user;
	}

	public void auntetificarIngresar(String iban, float cantidad) {

		if (iban == null) {
			throw new IbanInvalidoException(iban);
		}

		if (!validation.ValidarIban.validarIban(iban)) {
			throw new IbanInvalidoException(iban);
		}

		if (cantidad < 0) {
			throw new InputNoMenorQue0Exception();
		}

	}

	public void auntetificarRetirar(String iban, float cantidad, Cuenta cuenta) {

		if (iban == null) {
			throw new IbanInvalidoException(iban);
		}

		if (!validation.ValidarIban.validarIban(iban)) {
			throw new IbanInvalidoException(iban);
		}

		if (cantidad < 0) {
			throw new InputNoMenorQue0Exception();
		}

		if (cuenta.getSaldo() < cantidad) {
			throw new SaldoInsuficienteException(cuenta.getSaldo(), cantidad);
		}

	}

}

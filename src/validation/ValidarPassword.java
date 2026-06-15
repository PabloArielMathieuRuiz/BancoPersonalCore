package validation;

import excepciones.validacion.FormatoPasswordInvalidoException;

public class ValidarPassword {

	public static void validarPassword(String password) {

		String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])\\S{6,30}$";

		if (password == null || !password.matches(regex)) {
			throw new FormatoPasswordInvalidoException();
		}

	}

}
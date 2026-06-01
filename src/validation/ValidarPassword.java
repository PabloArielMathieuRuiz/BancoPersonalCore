package validation;

import excepciones.validacion.FormatoPasswordInvalidoException;

public class ValidarPassword {
	
	public static void validarPassword(String password) {
		
		String regex = "^[A-Za-z][A-Za-z0-9._]{3,19}$";
		
		if (password == null || !password.matches(regex)) {
			throw new FormatoPasswordInvalidoException();
		}
		
	}

}
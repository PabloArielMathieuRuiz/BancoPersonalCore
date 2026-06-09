package validation;

import excepciones.validacion.FormatoUserNameInvalidoException;

public class ValidarUsername {
	
	public static void validarUsername(String username) {
		
		
		String regex = "^[a-z][a-zA-Z0-9._]{3,19}$";
		
		if (username == null || !username.matches(regex)) {
			throw new FormatoUserNameInvalidoException(username);
		}
	}

}
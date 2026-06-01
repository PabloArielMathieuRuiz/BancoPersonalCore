package validation;

import excepciones.validacion.FormatoUserNameInvalidoException;

public class ValidarUsername {
	
	public static void validarUsername(String username) {
		
		String regex = "^(?=.[A-Za-z])(?=.\\d)\\S{6,30}$";
		
		if (username == null || !username.matches(regex)) {
			throw new FormatoUserNameInvalidoException(username);
		}
	}

}
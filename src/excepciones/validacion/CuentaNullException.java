package excepciones.validacion;

import excepciones.base.BancoException;

public class CuentaNullException extends BancoException{
	
	private static final long serialVersionUID = 1L; // <- Añade esto

	public CuentaNullException() {
		super("La cuenta no puede ser nula.", "ERR_VAL_005");
	}

}

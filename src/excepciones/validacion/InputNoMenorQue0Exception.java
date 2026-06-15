package excepciones.validacion;

import excepciones.base.BancoException;

public class InputNoMenorQue0Exception extends BancoException{

	private static final long serialVersionUID = 1L; // <- Añade esto

	public InputNoMenorQue0Exception() {
		
		super("El input no puede ser menor que 0.", "ERR_VAL_006");
	}
}

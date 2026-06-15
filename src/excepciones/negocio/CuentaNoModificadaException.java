/**
 * 
 */
package excepciones.negocio;

import excepciones.base.BancoException;

/**
 * 
 */
public class CuentaNoModificadaException extends BancoException{
	
	private static final long serialVersionUID = 1L; // <- Añade esto

	public CuentaNoModificadaException() {
		super("No se ha podido modificar la cuenta", "ERR_NEG_003");

		
	}

}
	

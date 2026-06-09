/**
 * 
 */
package excepciones.validacion;

import excepciones.base.BancoException;

/**
 * 
 */
public class SueldoInsuficienteException extends BancoException{
	
	private static final long serialVersionUID = 1L; // <- Añade esto

	public SueldoInsuficienteException() {
		
		super("El saldo es menor que la cantidad que se quiere retirar.", "ERR_VAL_007");
	}

}

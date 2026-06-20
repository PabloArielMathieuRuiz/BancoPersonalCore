/**
 * 
 */
package excepciones.negocio;

import excepciones.base.BancoException;

/**
 * @author Pablo
 *
 */

public class LimiteExcedidoException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public LimiteExcedidoException(double limite) {
		super("Se ha superado el límite permitido de " + limite + " €.", "ERR_NEG_002");
	}
}

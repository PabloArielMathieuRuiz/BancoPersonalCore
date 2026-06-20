/**
 * 
 */
package excepciones.negocio;

import excepciones.base.BancoException;

/**
 * @author Pablo
 *
 */
public class SaldoInsuficienteException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public SaldoInsuficienteException(float saldoActual, float solicitado) {
		super("Saldo insuficiente. Intentaste retirar " + solicitado + " € pero solo hay " + saldoActual + " €.",
				"ERR_NEG_001");
	}
}
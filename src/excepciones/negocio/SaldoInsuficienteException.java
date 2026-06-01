/**
 * 
 */
package excepciones.negocio;

import excepciones.base.BancoException;

/**
 * @author Pedro
 *
 */
public class SaldoInsuficienteException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public SaldoInsuficienteException(double saldoActual, double solicitado) {
		super("Saldo insuficiente. Intentaste retirar " + solicitado + " € pero solo hay " + saldoActual + " €.",
				"ERR_NEG_001");
	}
}
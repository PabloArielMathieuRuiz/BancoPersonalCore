/**
 * 
 */
package services;

import dao.CuentaDAO;
import validation.Validador;

/**
 * Lógica pura sin UI
 * 
 * Aquí es donde reside el negocio. Observa cómo lanza excepciones sin saber
 * quién las mostrará.
 * 
 * @author Pedro
 *
 */
public class GestorCuentas {

	private CuentaDAO dao = new CuentaDAO();

	public void transferir(String iban, double cantidad) {
		Validador.validarIban(iban);
		dao.actualizarSaldo(iban, cantidad);
	}
}

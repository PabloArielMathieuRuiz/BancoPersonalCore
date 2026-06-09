/**
 * 
 */
package services;

import dao.CuentaDao;
import validation.Validador;

/**
 * Lógica pura sin UI
 * 
 * Aquí es donde reside el negocio. Observa cómo lanza excepciones sin saber
 * quién las mostrará.
 * 
 * @author Pablo
 *
 */
public class GestorCuentas {

	private CuentaDao dao = new CuentaDao();

	public void transferir(String iban, double cantidad) {
		Validador.validarIban(iban);
		dao.actualizarSaldo(iban, cantidad);
	}
}

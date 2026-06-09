/**
 * 
 */
package services;

import dao.CuentaDao;
import modelo.Cuenta;
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

	private CuentaDao cuentaDao = new CuentaDao();

	public void transferir(String ibanEmisor,String ibanReceptor, float cantidad) {
		Validador.validarIban(ibanEmisor);
		Validador.validarIban(ibanReceptor);
		
		Cuenta cuentaRemitente = cuentaDao.obtenerCuentaPorIban(ibanEmisor);
		Cuenta cuentaRepector = cuentaDao.obtenerCuentaPorIban(ibanReceptor);
		
		
		float saldoEmisor = cuentaRemitente.getSaldo() - cantidad ;
		float saldoReceptor = cuentaRepector.getSaldo() + cantidad ;
		
		

		cuentaDao.actualizarSaldo(ibanEmisor, saldoEmisor);
		cuentaDao.actualizarSaldo(ibanReceptor, saldoReceptor);

		
	}
}

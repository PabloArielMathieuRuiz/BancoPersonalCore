/**
 * 
 */
package services;

import dao.CuentaDao;
import dao.MovimientosDao;
import modelo.Cuenta;
import modelo.TipoMovimiento;
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
	private MovimientosDao movimientosDao = new MovimientosDao();

	public void transferir(String ibanEmisor, String ibanReceptor, float cantidad) {
		Validador.validarIban(ibanEmisor);
		Validador.validarIban(ibanReceptor);

		Cuenta cuentaEmisor = cuentaDao.obtenerCuentaPorIban(ibanEmisor);
		Cuenta cuentaRepector = cuentaDao.obtenerCuentaPorIban(ibanReceptor);

		float saldoEmisor = cuentaEmisor.getSaldo() - cantidad;
		float saldoReceptor = cuentaRepector.getSaldo() + cantidad;

		cuentaDao.actualizarSaldo(ibanEmisor, saldoEmisor);
		cuentaDao.actualizarSaldo(ibanReceptor, saldoReceptor);
		
		movimientosDao.crearMovimientosTransferencia( 
				TipoMovimiento.TRANSFERENCIA_ENVIADA, 
				cantidad, 
				saldoReceptor, 
				"Transferencia Facil", 
				cuentaEmisor.getId(), 
				cuentaRepector.getId()
				);

	}
}

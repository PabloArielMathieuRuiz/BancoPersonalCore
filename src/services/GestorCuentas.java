/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.SQLException;

import dao.CuentaDao;
import dao.MovimientosDao;
import excepciones.persistencia.PersistenceException;
import modelo.Cuenta;
import modelo.TipoMovimiento;
import util.HikariConexion;
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

		try (Connection con = HikariConexion.getConnection()) {

			con.setAutoCommit(false);
			try {
				cuentaDao.actualizarSaldo(con, ibanEmisor, saldoEmisor);
				cuentaDao.actualizarSaldo(con, ibanReceptor, saldoReceptor);
				movimientosDao.crearMovimientosTransferencia(TipoMovimiento.TRANSFERENCIA_ENVIADA, cantidad,
						saldoReceptor, "Transferencia Facil", cuentaEmisor.getId(), cuentaRepector.getId());

				con.commit();
			} catch (Exception e) {
				con.rollback();
				throw new PersistenceException("Error en la transferencia, se han revertido los cambios.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

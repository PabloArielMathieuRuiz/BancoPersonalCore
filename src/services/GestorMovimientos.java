/**
 * 
 */
package services;

import java.sql.Connection;
import java.sql.SQLException;

import dao.CuentaDao;
import dao.MovimientosDao;
import modelo.Cuenta;
import modelo.TipoMovimiento;
import util.HikariConexion;

/**
 * 
 * @author Pablo
 */
public class GestorMovimientos {

	private CuentaDao cuentaDao = new CuentaDao();
	private MovimientosDao movimientosDao = new MovimientosDao();
	private GestorAutenticador gestorAutenticador = new GestorAutenticador();

	public void ingresar(String iban, float cantidad) {

		Cuenta cuenta = cuentaDao.obtenerCuentaPorIban(iban);

		gestorAutenticador.auntetificarIngresar(iban, cantidad);

		float nuevoSaldo = cuenta.getSaldo() + cantidad;

		try (Connection con = HikariConexion.getConnection()) {

			cuentaDao.actualizarSaldo(con, iban, nuevoSaldo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		movimientosDao.crearMovimientos(TipoMovimiento.INGRESO, cantidad, nuevoSaldo, "Ingreso Facil", cuenta.getId());

	}

	public void retirar(String iban, float cantidad) {

		Cuenta cuenta = cuentaDao.obtenerCuentaPorIban(iban);

		gestorAutenticador.auntetificarRetirar(iban, cantidad, cuenta);

		float nuevoSaldo = cuenta.getSaldo() - cantidad;

		try (Connection con = HikariConexion.getConnection()) {

			cuentaDao.actualizarSaldo(con, iban, nuevoSaldo);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

}

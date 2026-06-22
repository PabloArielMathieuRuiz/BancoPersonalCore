/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

//Importaciones para SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excepciones.negocio.CuentaNoModificadaException;
import excepciones.persistencia.PersistenceException;
import util.HikariConexion;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.TipoCuenta;

/**
 * @author Pablo
 *
 */
public class CuentaDao {

	// Logger profesional para esta clase
	private static final Logger logger = LoggerFactory.getLogger(CuentaDao.class);

	public void actualizarSaldo(Connection con, String iban, float nuevoSaldo) {
		String sql = "UPDATE cuenta SET saldo = ? WHERE iban = ?";

		// Uso de try-with-resources (fundamental en JDBC)
		//try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setFloat(1, nuevoSaldo);
			ps.setString(2, iban);
			ps.executeUpdate();

		} catch (SQLException e) {
			// 1. Log profesional (técnico, con detalles y stack trace)
			logger.error("Fallo al actualizar saldo para IBAN {}. Error SQL: {}", iban, e.getMessage());

			// 2. Lanzar excepción de dominio controlada
			throw new PersistenceException("Error al actualizar saldo en base de datos para IBAN: " + iban, e);
		}
	}

	public List<Cuenta> verTodasLasCuentasSinParametro() {

		List<Cuenta> lista = new ArrayList<>();

		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					lista.add(mapearCuenta(rs));

				}

			} catch (Exception e) {
				System.err.println("Error en verTodasLasCuentasSinParametro: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lista;

	}

	public List<Cuenta> verTodasLasCuentas(int id_cliente) {

		List<Cuenta> lista = new ArrayList<>();

		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where id_cliente = ?";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id_cliente);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					lista.add(mapearCuenta(rs));

				}

			} catch (Exception e) {
				System.err.println("Error en verTodasLasCuentas: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lista;

	}

	public Cuenta obtenerCuentaPorIban(String ibanBuscado) {

		Cuenta cuenta = null;

		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where iban = ?";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, ibanBuscado);
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					cuenta = mapearCuenta(rs);

				}

			} catch (Exception e) {
				System.err.println("Error en obtenerCuentaPorIban: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cuenta;

	}

	public void activarCuentaPorIdCuenta(int idCuenta) {

		String sql = "UPDATE cuenta SET activa = 1 WHERE id = ?;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idCuenta);
			ps.executeUpdate();

		} catch (SQLException e) {
			// 1. Log profesional (técnico, con detalles y stack trace)
			logger.error("Fallo al activar cuenta");

			// 2. Lanzar excepción de dominio controlada
			throw new CuentaNoModificadaException();
		}

	}

	public void desactivarCuentaPorIdCuenta(int idCuenta) {

		String sql = "UPDATE cuenta SET activa = 0 WHERE id = ?;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idCuenta);
			ps.executeUpdate();

		} catch (SQLException e) {
			// 1. Log profesional (técnico, con detalles y stack trace)
			logger.error("Fallo al activar cuenta");

			// 2. Lanzar excepción de dominio controlada
			throw new CuentaNoModificadaException();
		}
	}

	public List<Cuenta> contarCuentasActivas() {

		List<Cuenta> listaCuentas = new ArrayList<>();
		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where activa = 1;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					listaCuentas.add(mapearCuenta(rs));

				}

			} catch (Exception e) {
				System.err.println("Error en contarCuentasActivas: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return listaCuentas;
	}

	public float contarSaldoTotal() {

		float saldoTotal = 0.00f;
		String sql = "SELECT sum(saldo) as saldo FROM cuenta;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					saldoTotal = rs.getFloat("saldo");

				}

			} catch (Exception e) {
				System.err.println("Error en contarSaldoTotal: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return saldoTotal;
	}

	public List<Cliente> verCuentasSinAccesoDigital() {

		
		List<Cliente> sinAccesoDigital = new ArrayList<Cliente>();
		String sql = "SELECT c.id, c.nombre, c.apellidos, c.dni\r\n" + "FROM cliente c\r\n"
				+ "LEFT JOIN usuario u ON c.id = u.id_cliente\r\n" + "WHERE u.id_cliente IS NULL;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					Cliente cliente = new Cliente();

					cliente.setIdCliente(rs.getInt("id"));
					cliente.setNombre(rs.getString("nombre"));
					cliente.setApellido(rs.getString("apellidos"));
					cliente.setDNI(rs.getString("dni"));

					sinAccesoDigital.add(cliente);
				}

			} catch (Exception e) {
				System.err.println("Error en contarSaldoTotal: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return sinAccesoDigital;
	}

	private Cuenta mapearCuenta(ResultSet rs) throws SQLException {

		Cuenta cuenta = new Cuenta();

		int idCuenta = rs.getInt("id");
		String iban = rs.getString("iban");
		TipoCuenta tipo = TipoCuenta.valueOf(rs.getString("tipo"));
		float saldo = rs.getFloat("saldo");
		LocalDate fecha_apertura = rs.getObject("fecha_apertura", LocalDate.class);
		boolean activa = rs.getBoolean("activa");
		int idCliente = rs.getInt("id_cliente");

		cuenta = new Cuenta(idCuenta, iban, tipo, saldo, fecha_apertura, activa, idCliente);

		return cuenta;
	}

}

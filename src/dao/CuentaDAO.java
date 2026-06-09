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

import excepciones.persistencia.PersistenceException;
import util.HikariConexion;
import modelo.Cuenta;
import modelo.TipoCuenta;

/**
 * @author Pablo
 *
 */
public class CuentaDao {

	// Logger profesional para esta clase
	private static final Logger logger = LoggerFactory.getLogger(CuentaDao.class);

	public void actualizarSaldo(String iban, double nuevoSaldo) {
		String sql = "UPDATE cuenta SET saldo = saldo + ? WHERE iban = ?";

		// Uso de try-with-resources (fundamental en JDBC)
		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setDouble(1, nuevoSaldo);
			ps.setString(2, iban);
			ps.executeUpdate();

		} catch (SQLException e) {
			// 1. Log profesional (técnico, con detalles y stack trace)
			logger.error("Fallo al actualizar saldo para IBAN {}. Error SQL: {}", iban, e.getMessage());

			// 2. Lanzar excepción de dominio controlada
			throw new PersistenceException("Error al actualizar saldo en base de datos para IBAN: " + iban, e);
		}
	}

	public List<Cuenta> verTodasLasCuentas(int id_cliente) {

		List<Cuenta> lista = new ArrayList<>();

		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_cliente FROM cuenta where id_cliente = ?";

		try (Connection con = HikariConexion.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id_cliente);
			
			try (ResultSet rs = ps.executeQuery()) {


				while (rs.next()) {

					int idCuenta = rs.getInt("id");
					String iban = rs.getString("iban");
					TipoCuenta tipo = TipoCuenta.valueOf(rs.getString("tipo"));
					float saldo = rs.getFloat("saldo");
					LocalDate fecha_apertura = rs.getObject("fecha_apertura", LocalDate.class);
					boolean activa = rs.getBoolean("activa");
					int idCliente = rs.getInt("id_cliente");

					lista.add(new Cuenta(idCuenta, iban, tipo, saldo, fecha_apertura, activa, idCliente));

				}

			} catch (Exception e) {
				System.err.println("Error en listarTodos(): " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lista;

	}
	
	public Cuenta obtenerCuentaPorIban (String ibanBuscado) {
		
		Cuenta cuenta = null;

		String sql = "SELECT id, iban, tipo, saldo, fecha_apertura, activa, id_clientes FROM cuenta where iban = ?";

		try (Connection con = HikariConexion.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				ps.setString(1, ibanBuscado);

				while (rs.next()) {

					int idCuenta = rs.getInt("id");
					String iban = rs.getString("iban");
					TipoCuenta tipo = TipoCuenta.valueOf(rs.getString("tipo"));
					float saldo = rs.getFloat("saldo");
					LocalDate fecha_apertura = rs.getObject("fecha_apertura", LocalDate.class);
					boolean activa = rs.getBoolean("activa");
					int idCliente = rs.getInt("id_cliente");

					cuenta = new Cuenta(idCuenta, iban, tipo, saldo, fecha_apertura, activa, idCliente);

				}

			} catch (Exception e) {
				System.err.println("Error en listarTodos(): " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cuenta;

	}

}

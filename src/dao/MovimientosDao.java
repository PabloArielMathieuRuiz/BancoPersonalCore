/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Movimiento;
import modelo.TipoMovimiento;
import util.HikariConexion;

/**
 * 
 */
public class MovimientosDao {

	public List<Movimiento> verTodosLosMovimientosPorIdCuenta(int idCuentaOrigen) {

		List<Movimiento> lista = new ArrayList<>();

		String sql = "SELECT id, tipo, importe, saldo_resultante, fecha_operacion, descripcion, id_cuenta_origen, id_cuenta_destino FROM movimiento where id_cuenta_origen = ?;";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, idCuentaOrigen);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					int id = rs.getInt("id");
					TipoMovimiento tipo = TipoMovimiento.valueOf(rs.getString("tipo"));
					float importe = rs.getFloat("importe");
					float saldo_resultante = rs.getFloat("saldo_resultante");
					LocalDate fecha_operacion = rs.getObject("fecha_operacion", LocalDate.class);
					;
					String descripcion = rs.getString("descripcion");
					int id_cuenta_origen = rs.getInt("id_cuenta_origen");
					int id_cuenta_destino = rs.getInt("id_cuenta_destino");

					lista.add(new Movimiento(id, tipo, importe, saldo_resultante, fecha_operacion, descripcion,
							id_cuenta_origen, id_cuenta_destino));

				}

			} catch (Exception e) {
				System.err.println("Error en listarTodos(): " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return lista;
	}
	
	
	public void crearMovimientos(TipoMovimiento tipo, float importe, float saldo_resultante, String descripcion, int id_cuenta_origen) {

		String sql = "INSERT INTO movimiento (tipo, importe, saldo_resultante, id_cuenta_origen, id_cuenta_destino, descripcion) VALUES\r\n"
				+ "    (?, ?, ?, ?, NULL, ?);";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, tipo.name());
			ps.setFloat(2, importe);
			ps.setFloat(3, saldo_resultante);
			ps.setInt(4, id_cuenta_origen);
			ps.setString(5, descripcion);

			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void crearMovimientosTransferencia(TipoMovimiento tipo, float importe, float saldo_resultante, String descripcion, int id_cuenta_origen, int id_cuenta_destino) {

		String sql = "INSERT INTO movimiento (tipo, importe, saldo_resultante, id_cuenta_origen, id_cuenta_destino, descripcion) VALUES\r\n"
				+ "    (?, ?, ?, ?, ?, ?);";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, tipo.name());
			ps.setFloat(2, importe);
			ps.setFloat(3, saldo_resultante);
			ps.setInt(4, id_cuenta_origen);
			ps.setInt(5, id_cuenta_destino);
			ps.setString(6, descripcion);

			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

}

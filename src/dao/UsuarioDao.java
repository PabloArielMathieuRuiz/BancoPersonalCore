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

import excepciones.persistencia.ConexionFallidaException;
import excepciones.persistencia.PersistenceException;
import modelo.Rol;
import modelo.Usuario;
import util.HikariConexion;

/**
 * Acceso a datos de la tabla usuario
 * 
 * Cuando no encuentra filas devuelve null. Cuando la base de datos falla, lanza
 * PersistenceException para que las capas superiores lo gestionen
 * 
 * @author Pablo
 */
public class UsuarioDao {

	/**
	 * 
	 * Buscas un usuario por su nombre de usuario
	 * 
	 * @param username es el nombre de usuario a buscar
	 * @return Retorna el usuario encontrado, o null si no existe ninguno con ese
	 *         username
	 * @throws PersistenceException si ocure un error en la base de batos
	 */

	public Usuario buscarPorUsername(String username) {

		String sql = "SELECT id, username, password, rol, id_cliente FROM usuario WHERE username = ?";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, username);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					// Encontrado: Mapeamos la fila al objeto Usuario

					Usuario user = new Usuario();

					user = mapearUsuario(rs);

					return user;

				}
				// No encontrado: devolvemos null. El servicio decidira que hacer
				return null;

			} catch (Exception e) {
				throw new PersistenceException("Error en la persistencia", e);
			}

		} catch (Exception e) {
			e.printStackTrace();

			throw new ConexionFallidaException(e);
		}

	}

	public List<Usuario> verTodosLosUsuario() {

		List<Usuario> lista = new ArrayList<>();

		String sql = "SELECT id, username, password, rol, id_cliente FROM usuario";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					lista.add(mapearUsuario(rs));

				}

			} catch (Exception e) {
				System.err.println("Error en verTodosLosUsuario: " + e.getMessage());
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return lista;

	}

	private Usuario mapearUsuario(ResultSet rs) throws SQLException {

		Usuario usuario = new Usuario();

		int idUsuario = rs.getInt("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		Rol rol = Rol.valueOf(rs.getString("rol"));
		int idCliente = rs.getInt("id_cliente");

		usuario = new Usuario(idUsuario, username, password, rol, idCliente);

		return usuario;
	}

}

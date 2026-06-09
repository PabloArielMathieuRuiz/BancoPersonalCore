/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

		try (Connection con = HikariConexion.getConnection(); 
				PreparedStatement ps = con.prepareStatement(sql)) {
			

			ps.setString(1, username);
			
			try (ResultSet rs = ps.executeQuery()) {

		
				
				if (rs.next()) {
					// Encontrado: Mapeamos la fila al objeto Usuario

					Usuario user = new Usuario();


					user.setIdUsuario(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setContraseña(rs.getString("password"));
					user.setRol(Rol.valueOf(rs.getString("rol")));
					user.setIdCliente(rs.getInt("id_cliente"));

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

		String sql = "SELECT idUsuario, username, password, rol, idCliente FROM cuenta";

		try (Connection con = HikariConexion.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {

				int idUsuario = rs.getInt("idUsuario");
				String username = rs.getString("username");
				String password = rs.getString("password");
				Rol rol = Rol.valueOf(rs.getString("rol"));
				int idCliente = rs.getInt("idCliente");

				lista.add(new Usuario(idUsuario, username, password, rol, idCliente));

			}

		} catch (Exception e) {
			System.err.println("Error en listarTodos(): " + e.getMessage());
		}

		return lista;
	}

}

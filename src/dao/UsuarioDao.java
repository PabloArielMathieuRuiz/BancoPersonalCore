/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

		String sql = "SELECT id, username, contraseña, rol, id_cliente FROM usuario WHERE username = ?";

		try (Connection con = HikariConexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					// Encontrado: Mapeamos la fila al objeto Usuario

					Usuario user = new Usuario();

					// TODO mapeo de los atributos

					user.setIdUsuario(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setContraseña(rs.getString("contraseña"));
					user.setRol(Rol.valueOf(rs.getString("rol")));
					user.setIdCliente(rs.getInt("id:cliente"));

					return user;

				}
				// No encontrado: devolvemos null. El servicio decidira que hacer
				return null;

			} catch (Exception e) {
				throw new PersistenceException("oejiocje", e);
			}

		} catch (Exception e) {

			throw new ConexionFallidaException(e);
		}

	}

}

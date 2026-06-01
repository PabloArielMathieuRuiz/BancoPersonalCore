/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Importaciones para SLF4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excepciones.persistencia.PersistenceException;
import util.HikariConexion;
/**
 * @author Pedro
 *
 */
public class CuentaDAO {

	// Logger profesional para esta clase
	private static final Logger logger = LoggerFactory.getLogger(CuentaDAO.class);

	public void actualizarSaldo(String iban, double nuevoSaldo) {
		String sql = "UPDATE cuenta SET saldo = saldo + ? WHERE iban = ?";
	        
	        // Uso de try-with-resources (fundamental en JDBC)
			try (Connection con = HikariConexion.getConnection(); 
	             PreparedStatement ps = con.prepareStatement(sql)) {
	             
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
}

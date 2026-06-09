/**
 * 
 */
package util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author Pablo
 *
 */
public class HikariConexion {
	private static HikariDataSource ds = null;



	

	static {
		try {
			HikariConfig config = new HikariConfig();

			// Configuración básica para MySQL
			// En el futuro, estos valores se leerán de la clase ConfiguracionApp
			config.setJdbcUrl("jdbc:mysql://localhost:3306/Bancopersonal?serverTimezone=UTC");
			config.setUsername("root");
			config.setPassword("");

			// Pool tuning
			config.setMaximumPoolSize(5);
			config.setMinimumIdle(2);
			config.setConnectionTimeout(30000);
			config.setPoolName("BancoPersonal-Pool");

			ds = new HikariDataSource(config);
			System.out.println("✅ HikariCP POOL iniciado correctamente.");

		} catch (Exception e) {
			throw new RuntimeException("Error fatal al inicializar el pool de conexiones", e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static void closePool() {
		if (ds != null && !ds.isClosed()) {
			ds.close();
			System.out.println("Pool cerrado.");
		}
	}

	// El método printPoolStatus() que tenías es genial para DEBUG.
	// Te recomiendo que les pidas a los alumnos que lo añadan solo si notan
	// bloqueos en la aplicación, para que aprendan a medir el rendimiento.

}

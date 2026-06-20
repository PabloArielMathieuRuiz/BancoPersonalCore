/**
 * 
 */
package excepciones.persistencia;

import excepciones.base.BancoException;
/**
 * Excepción lanzada cuando hay un error en la capa de persistencia (DAO).
 * Envuelve cualquier SQLException técnica para que las capas superiores no
 * tengan que gestionar dependencias de JDBC directamente.
 * 
 * 
 * @author Pablo
 *
 */
public class PersistenceException extends BancoException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	// Constructor recomendado: mensaje + causa original (el SQLException)
	public PersistenceException(String mensaje, Throwable causa) {
		super(mensaje, "ERR_DB_01", causa);
	}

	public PersistenceException(String mensaje) {
		super(mensaje, "ERR_DB_01");
	}
}

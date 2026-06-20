/**
 * 
 */
package excepciones.persistencia;



/**
 * @author Pablo
 *
 */
public class ConexionFallidaException extends PersistenceException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public ConexionFallidaException(Throwable causa) {
		super("No se pudo establecer conexión con la base de datos.", causa);
		// initCause(causa); // Importante para conservar el error original de SQL
	}
}

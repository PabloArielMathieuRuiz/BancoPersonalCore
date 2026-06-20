/**
 * 
 */
package excepciones.persistencia;

/**
 * @author Pablo
 * 
 */
public class TransferenciaFallidaException extends PersistenceException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public TransferenciaFallidaException() {

		super("Hubo un problema al realizar la transferencia");
	}
}

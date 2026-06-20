/**
 * 
 */
package excepciones.persistencia;


/**
 * @author Pablo
 *
 */
public class CuentaNoEncontradaException extends PersistenceException {

	private static final long serialVersionUID = 1L; // <- Añade esto

	public CuentaNoEncontradaException(String iban) {

		super("No se ha encontrado ninguna cuenta con IBAN: " + iban);
	}
}


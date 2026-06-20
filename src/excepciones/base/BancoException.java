/**
 * 
 */
package excepciones.base;

/**
 * Clase base para todas las excepciones del dominio bancario. Obliga a definir
 * un mensaje y un código de error único.
 * 
 * @author Pablo
 *
 */
public class BancoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String codigoError;

	// Constructor estándar
	public BancoException(String mensaje, String codigoError) {
		super(mensaje);
		this.codigoError = codigoError;
	}

	// Constructor por si quieres pasar además la causa original (ej: SQLException)
	public BancoException(String mensaje, String codigoError, Throwable causa) {
		super(mensaje, causa);
		this.codigoError = codigoError;
	}


	public String getCodigoError() {
		return codigoError;
	}
}
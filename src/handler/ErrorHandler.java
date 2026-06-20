/**
 * 
 */
package handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import excepciones.base.BancoException;

/**
 * Este es el componente que centraliza el tratamiento de errores.
 * 
 * Por qué esta implementación es la "definitiva": Centralización del formato:
 * Fíjate que el formato del mensaje [CÓDIGO] Mensaje está definido en un solo
 * lugar (ErrorHandler). Si mañana quieres que los errores aparezcan en color
 * rojo o en un pop-up, solo tocas este archivo.
 * 
 * Abstracción del error: El ErrorHandler no diferencia entre una
 * SaldoInsuficienteException y una ConexionFallidaException a nivel de
 * estructura; ambas son BancoException. Esto permite que el manejador siga
 * funcionando perfectamente incluso si añades 50 excepciones más a tu proyecto
 * mañana.
 * 
 * Seguridad: Hemos separado el mensaje para el usuario (limpio, amigable, con
 * código) del printStackTrace() (técnico, sucio, solo para logs). Esto es una
 * regla de oro en el desarrollo bancario: nunca reveles detalles técnicos de tu
 * base de datos al usuario final.
 * 
 * Preparación para Spring:
 * 
 * En Spring, este método gestionar se dividirá en varios métodos anotados
 * con @ExceptionHandler.
 * 
 * El if (e instanceof BancoException) se convertirá
 * en @ExceptionHandler(BancoException.class).
 * 
 * La interfaz ErrorDisplay será reemplazada por ResponseEntity<Object>, que
 * permite devolver un JSON estructurado con el código de error.
 * 
 * Consejo para la clase: "Si vuestra aplicación crece y el día de mañana tenéis
 * 100 excepciones diferentes, este archivo ErrorHandler no crecerá. Seguirá
 * teniendo este mismo aspecto. Si por el contrario hubierais puesto los
 * try-catch dentro de los servicios, tendríais cientos de líneas de código
 * duplicado".
 * 
 * @author Pablo
 *
 */
public class ErrorHandler {

	// Logger para registrar errores técnicos en logback
	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	public interface ErrorDisplay {
		void mostrarError(String mensaje);
	}

	public static void gestionar(Exception e, ErrorDisplay display) {

		// 1. Gestión de nuestras excepciones bancarias (Jerarquía)
		if (e instanceof BancoException be) {
			// BancoException be = (BancoException) e;

			// Registramos en log como WARNING (es controlado, pero importante)
			logger.warn("Error de dominio [{}]: {}", be.getCodigoError(), be.getMessage());

			// Aquí centralizas: imprimes el código de error y el mensaje
			display.mostrarError("[" + be.getCodigoError() + "] " + be.getMessage());

		}
		// 2. Gestión de excepciones de validación de datos de entrada (Java nativo)
		else if (e instanceof IllegalArgumentException iae) {
			logger.info("Validación fallida: {}", iae.getMessage());
			display.mostrarError("[ERR_INPUT] Datos de entrada incorrectos: " + iae.getMessage());
		}
		// 3. Fallo técnico desconocido
		else {
			// Registramos con nivel ERROR y enviamos el StackTrace al archivo de log
			logger.error("Error crítico inesperado detectado", e);

			// Mensaje genérico para el usuario (nunca revelar detalles técnicos)
			display.mostrarError("[ERR_CRITICO] Ha ocurrido un error técnico inesperado.");
		}
	}
}

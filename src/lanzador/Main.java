/**
* 
*/
package lanzador;

import java.util.Scanner;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import vista.AppJavaFX;
import vista.VistaConsola;

/**
 * @author Pedro
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// FORZAR CARGA DE LOGBACK
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			// configurator.doConfigure("src/logback.xml"); // O la ruta donde lo hayas
			configurator.doConfigure("src/logback/logback.xml");

		} catch (JoranException je) {
			je.printStackTrace();
		}

		// new VistaConsola().iniciar();
		Scanner sc = new Scanner(System.in);

		System.out.println("Seleccione modo de ejecución:");
		System.out.println("1. Consola");
		System.out.println("2. Interfaz gráfica");
		System.out.print("Opción: ");

		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:
			new VistaConsola().iniciar();
			break;
		case 2:
			AppJavaFX.main(args);
			break;
		default:
			System.out.println("Opcion invalida");
		}

		sc.close();

	}

}

/**
 * 
 */
package vista;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Esta clase solo arranca la aplicación gráfica y abre el login. JavaFX se
 * inicia mediante Application y launch(args).
 * 
 * @author Pedro
 *
 */
public class AppJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		GestorVistas gestor = new GestorVistas(primaryStage);
		gestor.mostrarLogin();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

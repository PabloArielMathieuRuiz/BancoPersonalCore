/**
 * 
 */
package vista;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Este gestor centraliza la navegación. Así ninguna vista crea a la otra
 * directamente, lo que reduce el acoplamiento y hace más fácil mantener el
 * proyecto.
 *
 * @author Pedro
 *
 */
public class GestorVistas {
	private final Stage stage;

	public GestorVistas(Stage stage) {
		this.stage = stage;
	}

	public void mostrarLogin() {
		VistaLogin vista = new VistaLogin(this);
		stage.setScene(new Scene(vista.getRoot(), 420, 280));
		stage.setTitle("Login - Banco Personal");
		stage.show();
	}

	public void mostrarMenu() {
		VistaMenu vista = new VistaMenu(this);
		stage.setScene(new Scene(vista.getRoot(), 420, 220));
		stage.setTitle("Menú principal - Banco Personal");
		stage.show();
	}

	public void mostrarTransferencia() {
		VistaTransferencia vista = new VistaTransferencia(this);
		stage.setScene(new Scene(vista.getRoot(), 450, 300));
		stage.setTitle("Transferencia - Banco Personal");
		stage.show();
	}
}

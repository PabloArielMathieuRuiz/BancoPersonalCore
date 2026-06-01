/**
 * 
 */
package vista;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * El menú es solo navegación. Tiene una opción que lleva a la transferencia y
 * otra para salir.
 * 
 * @author Pedro
 *
 */
public class VistaMenu {
	private final VBox root;

	public VistaMenu(GestorVistas gestor) {
		Button btnTransferencia = new Button("Transferir a cuenta");
		btnTransferencia.setOnAction(e -> gestor.mostrarTransferencia());

		Button btnSalir = new Button("Salir");
		btnSalir.setOnAction(e -> System.exit(0));

		root = new VBox(10, new Label("Menú principal"), btnTransferencia, btnSalir);

		root.setPadding(new Insets(15));
	}

	public VBox getRoot() {
		return root;
	}
}

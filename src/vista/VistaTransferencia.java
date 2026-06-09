/**
 * 
 */
package vista;

import controller.ControladorBanco;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * @author Pedro
 *
 *         Cuando la clase extiende Application, JavaFX entiende que esa clase
 *         será una aplicación gráfica. Es como decirle: “esta clase sabe
 *         arrancar una ventana”.
 */
public class VistaTransferencia {
	@SuppressWarnings("unused")
	private final GestorVistas gestor;
	private final ControladorBanco controlador;
	private final VBox root;
	private final TextField txtIban;
	private final TextField txtImporte;
	private final Label lblEstado;

	/**
	 * Este método es el punto de entrada de la interfaz. Aquí se construye la
	 * ventana principal, se crean los controles visuales y se muestra la pantalla.
	 * 
	 * Controles gráficos Dentro de la clase aparecen elementos como:
	 * 
	 * TextField: para escribir IBAN o importe.
	 * 
	 * Button: para pulsar y ejecutar la transferencia.
	 * 
	 * Label: para mostrar mensajes.
	 * 
	 * Alert: para enseñar errores de forma visual.
	 * 
	 * Cada uno cumple una función muy simple, pero juntos forman la pantalla
	 * completa.
	 */
	public VistaTransferencia(GestorVistas gestor) {
		this.gestor = gestor;
		this.controlador = new ControladorBanco(null);

		txtIban = new TextField();
		txtIban.setPromptText("Introduce IBAN");

		txtImporte = new TextField();
		txtImporte.setPromptText("Introduce importe");

		lblEstado = new Label();

		Button btnTransferir = new Button("Transferir");
		btnTransferir.setOnAction(e -> procesarTransferencia());

		Button btnVolver = new Button("Volver al menú");
		btnVolver.setOnAction(e -> gestor.mostrarMenu());

		root = new VBox(10, new Label("Transferencia a cuenta"), txtIban, txtImporte, btnTransferir, btnVolver,
				lblEstado);

		root.setPadding(new Insets(15));
	}

	/**
	 * Este método suele hacer tres cosas:
	 * 
	 * Leer el texto de los campos.
	 * 
	 * Convertir los datos al tipo adecuado.
	 * 
	 * Llamar al controlador.
	 * 
	 * Si algo falla, se muestra un mensaje de error. Si todo va bien, se muestra un
	 * mensaje de éxito.
	 * 
	 */
	private void procesarTransferencia() {
		try {
			String iban = txtIban.getText();
			double importe = Double.parseDouble(txtImporte.getText());
			String resultado = controlador.ejecutarTransferencia(iban, importe);
			lblEstado.setText(resultado);
		} catch (NumberFormatException ex) {
			mostrarError("El importe debe ser numérico.");
		} catch (Exception ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void mostrarError(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public VBox getRoot() {
		return root;
	}

}

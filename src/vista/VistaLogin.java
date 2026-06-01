/**
 * 
 */
package vista;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * La vista de login solo valida credenciales básicas y pide al gestor que
 * muestre el menú. No conoce la implementación del menú ni de la transferencia
 * 
 * @author Pedro
 *
 */
public class VistaLogin {
	private final GestorVistas gestor;
	private final VBox root;
	private final TextField txtUsuario;
	private final PasswordField txtPassword;
	private final Label lblMensaje;

	public VistaLogin(GestorVistas gestor) {
		this.gestor = gestor;

		txtUsuario = new TextField();
		txtUsuario.setPromptText("Usuario");

		txtPassword = new PasswordField();
		txtPassword.setPromptText("Contraseña");

		lblMensaje = new Label();

		Button btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(e -> validarLogin());

		root = new VBox(10, new Label("Acceso al sistema"), txtUsuario, txtPassword, btnEntrar, lblMensaje);

		root.setPadding(new Insets(15));
	}

	private void validarLogin() {
		String usuario = txtUsuario.getText();
		String pass = txtPassword.getText();

		if ("admin".equals(usuario) && "1234".equals(pass)) {
			gestor.mostrarMenu();
		} else {
			lblMensaje.setText("Credenciales incorrectas.");
		}
	}

	public VBox getRoot() {
		return root;
	}
}

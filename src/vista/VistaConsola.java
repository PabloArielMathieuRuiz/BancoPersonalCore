/**
 * 
 */
package vista;

import java.util.Scanner;

import controller.ControladorBanco;
import handler.ErrorHandler;
import modelo.Usuario;

/**
 * Único lugar con input/output
 * 
 * Esta clase es la única que sabe que estamos en una consola.
 * 
 * @author Pedro
 *
 */
public class VistaConsola implements ErrorHandler.ErrorDisplay {

	private ControladorBanco controller;
	private Scanner sc;

	public VistaConsola() {
		controller = new ControladorBanco();
		sc = new Scanner(System.in);
	}

	public void iniciar() {
		mostrarBienvenida();
		mostrarLogin();

	}

	public void mostrarBienvenida() {

		System.out.println("=============================");
		System.out.println("     Banco Personal - v1.0   ");
		System.out.println("=============================");

	}

	public void mostrarLogin() {

		System.out.println("=============================");
		System.out.println("     Acceso al sistema       ");
		System.out.println("=============================");
		
		
		boolean loginCorrecto = false;
		while (!loginCorrecto) {

			System.out.println("(Escribe 'salir' para cerrar)");
			System.out.println("Username: ");
			String username = sc.nextLine().trim();

			if ("salir".equalsIgnoreCase(username)) {
				System.out.println("Hasta pronto.");
				return;

			}
			System.out.print("Contraseña: ");
			String password = sc.nextLine().trim();
			
			try {
				
				Usuario user = controller.validarLogin(username, password);
				loginCorrecto = true;
				System.out.println("Bienvenido, "+ user.getUsername() + " [" + user.getRol() + "]" );
				//mostrarMenu();
				
			} catch (Exception e) {

				ErrorHandler.gestionar(e, this);
				System.out.println(" Intentelo de nuevo.");
			}
			
			
			
		}
		
	}

	
	
	public void mostrarMenu () {
		
		
	}
	

	@Override
	public void mostrarError(String mensaje) {
		System.err.println(">>> " + mensaje);
	}
}
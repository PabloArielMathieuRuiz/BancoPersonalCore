/**
 * 
 */
package vista;

import java.util.List;
import java.util.Scanner;


import controller.ControladorBanco;
import handler.ErrorHandler;
import modelo.Cuenta;
import modelo.Movimiento;
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
		// Pasamos 'this' (esta instancia de VistaConsola) al controlador
		this.controller = new ControladorBanco(this);
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
			String username = sc.nextLine();

			if ("salir".equalsIgnoreCase(username)) {
				System.out.println("Hasta pronto.");
				return;

			}
			System.out.print("Password: ");
			String password = sc.nextLine();


			try {

				Usuario user = controller.validarLogin(username, password);
				loginCorrecto = true;
				System.out.println("Bienvenido, " + user.getUsername() + " [" + user.getRol() + "]");
				mostrarMenu();

			} catch (Exception e) {

				ErrorHandler.gestionar(e, this);
				System.out.println(" Intentelo de nuevo.");
			}

		}

	}

	public void mostrarMenu() {

		System.out.println("=====================================");
		System.out.println("        Menu Principal               ");
		System.out.println("=====================================");

		System.out.println("1. Ver mis cuentas");
		System.out.println("2. Ver movimientos");
		System.out.println("3. Ingrear dinero");
		System.out.println("4. Reintegrar dinero");
		System.out.println("5. Transferir");
		System.out.println("0. Cerrar sesion");
		System.out.print("Introduzca su opcion: ");
		int opcion = sc.nextInt();
		
		controller.ControlarOpcionesUsuario(opcion);
		

	}
	
	public void mostrarListaCuenta(List<Cuenta> lista) {

		if (lista.isEmpty()) {
			// si no hay pedidos atendidos no muestra nada
			System.out.println("No hay cuentas a su nombre.");
			return;
		}

		for (Cuenta cuenta : lista) {

			System.out.println(cuenta);
		}

	}
	
	public void mostrarListaMovimientos(List<Movimiento> lista) {

		if (lista.isEmpty()) {
			// si no hay pedidos atendidos no muestra nada
			System.out.println("No hay  movimientos realizados.");
			return;
		}

		for (Movimiento movimiento : lista) {

			System.out.println(movimiento);
		}

	}
	
	public void ingresarVista () {
		
		String iban = util.InputReader.readString("¿Cual en el iban de la cuenta a ingresar?: ");
		float cantidad = util.InputReader.readFloat("¿Cual es la cantidad a ingresar?: ");
		
		controller.ingresar(iban, cantidad);
		
	}
	
	public void retirarVista () {
		
		String iban = util.InputReader.readString("¿Cual en el iban de la cuenta a retirar?: ");
		float cantidad = util.InputReader.readFloat("¿Cual es la cantidad a retirar?: ");
		
		controller.retirar(iban, cantidad);
		
	}
	
	


	@Override
	public void mostrarError(String mensaje) {
		System.err.println(">>> " + mensaje);
	}
}
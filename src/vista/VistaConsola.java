/**
 * 
 */
package vista;
/**
 * @author Pablo
 */

import java.util.List;
import java.util.Scanner;

import controller.ControladorBanco;
import handler.ErrorHandler;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Movimiento;
import modelo.Rol;
import modelo.Usuario;
import controller.ControladorAdmin;

/**
 * Único lugar con input/output
 * 
 * Esta clase es la única que sabe que estamos en una consola.
 * 
 *
 */
public class VistaConsola implements ErrorHandler.ErrorDisplay {

	private ControladorBanco controller;
	private Scanner sc;
	private ControladorAdmin controladorAdmin;

	public VistaConsola() {
		// Pasamos 'this' (esta instancia de VistaConsola) al controlador
		this.controller = new ControladorBanco(this);
		sc = new Scanner(System.in);
		// Pasamos 'this' (esta instancia de VistaConsola) al controlador
		controladorAdmin = new ControladorAdmin(this);
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
				
				if (user.getRol() == Rol.ADMIN) {
				    mostrarMenuAdmin();
				} else if (user.getRol() == Rol.CLIENTE) {
				    mostrarMenu(); // Menú estándar para clientes
				}
				

			} catch (Exception e) {

				ErrorHandler.gestionar(e, this);
				System.out.println(" Intentelo de nuevo.");
			}

		}

	}

	public void mostrarMenu() {

		int opcion = -1;
		boolean salir = false;
		while (!salir) {
			System.out.println("=====================================");
			System.out.println("        Menu Principal               ");
			System.out.println("=====================================");

			System.out.println("1. Ver mis cuentas");
			System.out.println("2. Ver movimientos");
			System.out.println("3. Ingrear dinero");
			System.out.println("4. Reintegrar dinero");
			System.out.println("5. Transferir");
			System.out.println("0. Cerrar sesion");
			opcion = util.InputReader.readInt("Introduzca su opcion: ");
			if (opcion == 0) {
				salir = true;
				System.out.println("¡Gracias!");
			} else {
				controller.ControlarOpcionesUsuario(opcion);
			}
			System.out.println("¡Gracias por usar nuestro sistema bancario!");
		}
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

	public void ingresarVista() {

		String iban = util.InputReader.readString("¿Cual en el iban de la cuenta a ingresar?: ");
		float cantidad = util.InputReader.readFloat("¿Cual es la cantidad a ingresar?: ");

		controller.ingresar(iban, cantidad);

	}

	public void retirarVista() {

		String iban = util.InputReader.readString("¿Cual en el iban de la cuenta a retirar?: ");
		float cantidad = util.InputReader.readFloat("¿Cual es la cantidad a retirar?: ");

		controller.retirar(iban, cantidad);

	}

	public void transferirVista() {

		String ibanEmisor = util.InputReader.readString("¿Cual en el iban de la cuenta a emisora?: ");

		String ibanReceptor = util.InputReader.readString("¿Cual en el iban de la cuenta a receptora?: ");

		float cantidad = util.InputReader.readFloat("¿Cual es la cantidad a retirar?: ");

		try {
			controller.ejecutarTransferencia(ibanEmisor, ibanReceptor, cantidad);
		} catch (Exception e) {
			ErrorHandler.gestionar(e, this);
		}

	}

	public void mostrarMenuAdmin() {
		int opcion = -1;
		boolean salir = false;

		while (!salir) {
			System.out.println("=====================================");
			System.out.println("        Menu Principal               ");
			System.out.println("=====================================");

			System.out.println("1. resumen global del banco");
			System.out.println("2. listar todos los clientes");
			System.out.println("3. clientes sin acceso digital");
			System.out.println("4. cuentas de un cliente");
			System.out.println("5. movimientos de una cuenta");
			System.out.println("6. activar cuenta");
			System.out.println("7. desactivar cuenta");
			System.out.println("0. Cerrar sesion");

			opcion = util.InputReader.readInt("Introduzca su opcion: ");
			if (opcion == 0) {
				salir = true;
				System.out.println("¡Gracias!");
			} else {
				controladorAdmin.ControlarOpcionesAdmin(opcion);
			}
		}
	}

	public void mostrarResumenGlobal(List<Usuario> listaUsuario, 
			List<Cuenta> usuarioConAccesoDigital,
			List<Cliente> usuarioSinAccesoDigital, 
			List<Cuenta> cuentasActivas, 
			float saldoTotal // hasta aqui son los
							// parametros ya que es
							// muy largo
	) {

		boolean salir = false;

		while (!salir) {
			System.out.println("=====================================");
			System.out.print("clientes registrados: ");
			System.out.println(listaUsuario.size());

			System.out.print("con acceso digital: ");
			System.out.println(usuarioConAccesoDigital.size());

			System.out.print("sin acceso digitals: ");
			System.out.println(usuarioSinAccesoDigital.size());

			System.out.print("cuentas activas: ");
			System.out.println(cuentasActivas.size());

			System.out.print("saldo total en custodia: ");
			System.out.println(saldoTotal);
			System.out.println("=====================================");

			salir = true;

		}

	}

	@Override
	public void mostrarError(String mensaje) {
		System.err.println(">>> " + mensaje);
	}

}
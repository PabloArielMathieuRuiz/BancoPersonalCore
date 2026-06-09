 /**
 * 
 */
package controller;

import services.GestorCuentas;
import vista.VistaConsola;
import services.GestorAutenticador;

import java.util.ArrayList;
import java.util.List;

import dao.CuentaDao;
import dao.MovimientosDao;
import modelo.Cuenta;
import modelo.Movimiento;
import modelo.Usuario;

/**
 * El controlador no imprime, no captura de teclado, solo coordina.
 * 
 * @author Pedro
 *
 */
public class ControladorBanco {
	private GestorCuentas servicio;
	private GestorAutenticador gestorAutenticador;
	private Usuario usuarioActual;
	private CuentaDao cuentaDao;
	private VistaConsola vistaConsola;
	private MovimientosDao movimientoDao;

	public ControladorBanco(VistaConsola vista) {
		servicio = new GestorCuentas();
		gestorAutenticador = new GestorAutenticador();
		usuarioActual = new Usuario();
		cuentaDao = new CuentaDao();
		this.vistaConsola = vista; // este se inicaliza de esta manera para evitar que haya un error de bucle 
		movimientoDao = new MovimientosDao();
	}

	/**
	 * Valida las credenciales de acceso Si la autencicacion tiene exito, guarda el
	 * usuario en sesion y lo devuelve si falla, el swervicio lanza la excepcion
	 * corresopndiente y este metodo no lo captura: la sub directemente a la vista
	 * 
	 * @param username Nombre del usuario
	 * @param contraseña Contraseña del usuario
	 * @return Usuario autenticado
	 */
	public Usuario validarLogin(String username, String contraseña) {

		usuarioActual = gestorAutenticador.autenticar(username, contraseña);

		return usuarioActual;
	}

	/**
	 * public void ejecutarTransferencia(double monto) {
	 * servicio.realizarTransferencia(monto); }
	 */

	/**
	 * public void ejecutarTransferencia(String iban, double monto) {
	 * servicio.transferir(iban, monto); }
	 */
	public String ejecutarTransferencia(String ibanEmisor,String ibanReceptor, float importe) throws Exception {
		// gestor.validarImporte(importe);
		// gestor.validarIban(iban);
		servicio.transferir(ibanEmisor, ibanReceptor, importe);
		return "Transferencia realizada correctamente.";
	}
	
	public void ControlarOpcionesUsuario(int opcion) {
		
		boolean salir = false;

		while (!salir) {


			switch (opcion) {
			case 1:
				mostrarTodasLasCuentas();
				break;
			case 2:
				mostrarTodosLosMovimientos();
				break;
			case 3:
				vistaConsola.ingresarVista();
				break;
			case 4:
				vistaConsola.retirarVista();
				break;
			case 5:
				vistaConsola.transferirVista();
				break;
			case 0:
				salir = true;
				System.out.println("\n¡Gracias por usar nuestro sistema bancario!");
				
				break;
			default:
				System.out.println("\n✗ Opción no válida. Intente de nuevo.");
			}
		}
		
	}
	
	
	public void mostrarTodasLasCuentas() {

		List<Cuenta> lista = new ArrayList<>();

		int id_cuenta = util.InputReader.readInt("¿Cual es su id de cliente?: ");

		lista = cuentaDao.verTodasLasCuentas(id_cuenta);

		vistaConsola.mostrarListaCuenta(lista);

	}
	
	public void mostrarTodosLosMovimientos() {
		

		List<Movimiento> lista = new ArrayList<>();

		int id_cuenta = util.InputReader.readInt("¿Cual se su id de cliente?: ");
		
		lista = movimientoDao.verTodosLosMovimientosPorIdCuenta(id_cuenta);

		vistaConsola.mostrarListaMovimientos(lista);

	}
	
	
	public void ingresar(String iban, float cantidad) {

		Cuenta cuenta = cuentaDao.obtenerCuentaPorIban(iban);

		gestorAutenticador.auntetificarIngresar(iban, cantidad);

		float nuevoSaldo = cuenta.getSaldo() + cantidad;

		cuentaDao.actualizarSaldo(iban, nuevoSaldo);

	}
	
	public void retirar(String iban, float cantidad) {

		Cuenta cuenta = cuentaDao.obtenerCuentaPorIban(iban);

		gestorAutenticador.auntetificarRetirar(iban, cantidad, cuenta);

		float nuevoSaldo = cuenta.getSaldo() - cantidad;

		cuentaDao.actualizarSaldo(iban, nuevoSaldo);
	}
}





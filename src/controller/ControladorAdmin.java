/**
 * 
 */
package controller;

import util.InputReader;
import vista.VistaConsola;

import java.util.List;

import dao.CuentaDao;
import dao.MovimientosDao;
import dao.UsuarioDao;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Movimiento;
import modelo.Usuario;

/**
 * 
 */
public class ControladorAdmin {

	CuentaDao cuentaDao;
	MovimientosDao movimientosDao;
	UsuarioDao usuarioDao;
	VistaConsola vistaConsola;

	public ControladorAdmin(VistaConsola vista) {
		cuentaDao = new CuentaDao();
		movimientosDao = new MovimientosDao();
		usuarioDao = new UsuarioDao();
		this.vistaConsola = vista; // este se inicaliza de esta manera para evitar que haya un error de bucle
	}

	public void ControlarOpcionesAdmin(int opcion) {

		switch (opcion) {
		case 1:
			resumenGlobal();
			break;
		case 2:
			System.out.println("2. listar todos los clientes");
			System.out.println("Esta opcion no se puede usar en este momento intentelo de nuevo mas tarde");
			break;
		case 3:
			System.out.println("3. clientes sin acceso digital");
			System.out.println("Esta opcion no se puede usar en este momento intentelo de nuevo mas tarde");
			break;
		case 4:
			System.out.println("4. cuentas de un cliente");
			System.out.println("Esta opcion no se puede usar en este momento intentelo de nuevo mas tarde");
			break;
		case 5:
			mostrarTodosLosMovimientos();
			break;
		case 6:
			activarCuenta();
			break;
		case 7:
			desactivarCuenta();
			break;
		case 0:
			System.out.println("\n¡Gracias por usar nuestro sistema bancario!");

			break;
		default:
			System.out.println("\n✗ Opción no válida. Intente de nuevo.");

		}
	}

	public void resumenGlobal() {

		List<Usuario> listaUsuario = usuarioDao.verTodosLosUsuario();

		List<Cuenta> usuarioConAccesoDigital = cuentaDao.verTodasLasCuentasSinParametro();
		List<Cliente> usuarioSinAccesoDigital = cuentaDao.verCuentasSinAccesoDigital();

		List<Cuenta> cuentasActivas = cuentaDao.contarCuentasActivas();

		float saldoTotal = cuentaDao.contarSaldoTotal();

		vistaConsola.mostrarResumenGlobal(listaUsuario, usuarioConAccesoDigital, usuarioSinAccesoDigital,
				cuentasActivas, saldoTotal);

	}

	public void activarCuenta() {

		int idCuenta = InputReader.readInt("¿Cual es el id de la cuenta que desea activar?");
		cuentaDao.activarCuentaPorIdCuenta(idCuenta);

	}

	public void desactivarCuenta() {

		int idCuenta = InputReader.readInt("¿Cual es el id de la cuenta que desea desactivar?");
		cuentaDao.desactivarCuentaPorIdCuenta(idCuenta);

	}

	public void mostrarTodosLosMovimientos() {

		List<Movimiento> lista = movimientosDao.verTodosLosMovimientos();
		vistaConsola.mostrarListaMovimientos(lista);

	}

}

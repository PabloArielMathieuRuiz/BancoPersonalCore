/**
 * 
 */
package controller;

import services.GestorCuentas;
import services.GestorAutenticador;
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

	public ControladorBanco() {
		servicio = new GestorCuentas();
		gestorAutenticador = new GestorAutenticador();
		usuarioActual = new Usuario();

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
	public String ejecutarTransferencia(String iban, double importe) throws Exception {
		// gestor.validarImporte(importe);
		// gestor.validarIban(iban);
		servicio.transferir(iban, importe);
		return "Transferencia realizada correctamente.";
	}

}
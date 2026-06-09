/**
 * 
 */
package modelo;

import java.time.LocalDate;

/**
 * 
 */
public class Movimiento {

	private int id;
	private TipoMovimiento tipo;
	private float importe;
	private float saldo_resultante;
	private LocalDate fecha_operacion;
	private String descripcion;
	private int id_cuenta_origen;
	private int id_cuenta_destino;

	public Movimiento(int id, TipoMovimiento tipo, float importe, float saldo_resultante, LocalDate fecha_operacion,
			String descripcion, int id_cuenta_origen, int id_cuenta_destino) {

		this.id = id;
		this.tipo = tipo;
		this.importe = importe;
		this.saldo_resultante = saldo_resultante;
		this.fecha_operacion = fecha_operacion;
		this.descripcion = descripcion;
		this.id_cuenta_origen = id_cuenta_origen;
		this.id_cuenta_destino = id_cuenta_destino;

	}


	public Movimiento() {

		this.id = 0;
		this.tipo = null;
		this.importe = 0.0f;
		this.saldo_resultante = 0.0f;
		this.fecha_operacion = null;
		this.descripcion = "";
		this.id_cuenta_origen = 0;
		this.id_cuenta_destino = 0;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public float getSaldo_resultante() {
		return saldo_resultante;
	}

	public void setSaldo_resultante(float saldo_resultante) {
		this.saldo_resultante = saldo_resultante;
	}

	public LocalDate getFecha_operacion() {
		return fecha_operacion;
	}

	public void setFecha_operacion(LocalDate fecha_operacion) {
		this.fecha_operacion = fecha_operacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId_cuenta_origen() {
		return id_cuenta_origen;
	}

	public void setId_cuenta_origen(int id_cuenta_origen) {
		this.id_cuenta_origen = id_cuenta_origen;
	}

	public int getId_cuenta_destino() {
		return id_cuenta_destino;
	}

	public void setId_cuenta_destino(int id_cuenta_destino) {
		this.id_cuenta_destino = id_cuenta_destino;
	}

	@Override
	public String toString() {
	    return "Usuario {id='" + id 
	    		+ "', tipo=" + tipo 
	    		+ "', importe=" + importe 
	    		+ "', saldo_resultante=" + saldo_resultante 
	    		+ "', fecha_operacion=" + fecha_operacion
	    		+ "', descripcion=" + descripcion 
	    		+ "', id_cuenta_origen=" + id_cuenta_origen
	    		+ "', id_cuenta_destino=" + id_cuenta_destino
	    		+ "}";
	   
	}
}

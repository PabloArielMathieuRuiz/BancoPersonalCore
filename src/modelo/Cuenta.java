/**
 * 
 */
package modelo;

import java.time.LocalDate;

/**
 * 
 */
public class Cuenta {
	
	private int id;
	private String iban;
	private TipoCuenta tipo;
	private float saldo;
	private LocalDate fecha_apertura;
	private boolean activa;
	private int id_cliente;
	
	public Cuenta(int id, String iban, TipoCuenta tipo, float saldo, LocalDate fecha_apertura, boolean activa, int id_cliente) {  
	
		this.id = id ;
		this.iban =  iban;
		this.tipo =  tipo;
		this.saldo = saldo ;
		this.fecha_apertura = fecha_apertura ;
		this.activa =  activa;
		this.id_cliente = id_cliente ;
		
		
	}

	public Cuenta () {
		
		this.id = 0 ;
		this.iban = "Sin nombre" ;
		this.tipo = null;
		this.saldo = 0.0f;
		this.fecha_apertura = null;
		this.activa = false ;
		this.id_cliente = 0;
		
		
		
	}
	
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public TipoCuenta getTipo() {
		return tipo;
	}

	public void setTipo(TipoCuenta tipo) {
		this.tipo = tipo;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public LocalDate getFecha_apertura() {
		return fecha_apertura;
	}

	public void setFecha_apertura(LocalDate fecha_apertura) {
		this.fecha_apertura = fecha_apertura;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	@Override
	public String toString() {
	    return "Usuario {id='" + id 
	    		+ "', iban=" + iban 
	    		+ "', tipo=" + tipo 
	    		+ "', saldo=" + saldo 
	    		+ "', fecha_apertura=" + fecha_apertura
	    		+ "', activa=" + activa 
	    		+ "', id_cliente=" + id_cliente
	    		+ "}";
	   
	}



}
package modelo;

/*
 * Clase que contiene la informacion del usuario
 * 
 * 
 * 
 * */
import java.time.LocalDate;

public class Cliente {

	private int idCliente;
	private String nombre;
	private String apellido;
	private String DNI;
	private String email;
	private String telefono;
	private LocalDate fecha_alta;

	public Cliente(int idCliente, String nombre, String apellido, String DNI, String email, String telefono, LocalDate fecha_alta) {  
		
		this.idCliente = idCliente ;
		this.nombre =  nombre;
		this.apellido =  apellido;
		this.DNI = DNI ;
		this.email = email ;
		this.telefono =  telefono;
		this.fecha_alta = fecha_alta ;
		
		
	}

	public Cliente () {
		
		this.idCliente = 0 ;
		this.nombre = "Sin nombre" ;
		this.apellido = "Sin apellido" ;
		this.DNI = "Sin DNI" ;
		this.email = "Sin email" ;
		this.telefono = "Sin telefono" ;
		this.fecha_alta = null ;
		
		
		
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(LocalDate fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	@Override
	public String toString() {
		return "C{idCliente='" + idCliente + "', nombre=" + nombre + "', apellido=" + apellido + "', DNI=" + DNI
				+ "', email=" + email + "', telefono=" + telefono + "', fecha_alta=" + fecha_alta + "}";

	}

}

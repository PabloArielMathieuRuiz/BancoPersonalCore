package modelo;
/*
 * Clase que contiene las credenciales 
 * del usuario para poder logearse
 * 
 * 
 * */



public class Usuario {

	private int idUsuario;
	private String username;
	private String contraseña;
	private Rol rol;
	private int idCliente;
	
	public Usuario(int idUsuario, String username, String contraseña, Rol rol, int idCliente) {

		this.idUsuario = idUsuario;
		this.username = username;
		this.contraseña = contraseña;
		this.rol = rol;
		this.idCliente = idCliente;

	}

	
	public Usuario  () {
		
		
		this.idUsuario = 0;
		this.username = "Sin nombre";
		this.contraseña = "Sin contraseña";
		this.rol = null;
		this.idCliente = 0;
		
		
	}

	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	
	@Override
	public String toString() {
	    return "Usuario {idUsuario='" + idUsuario 
	    		+ "', username=" + username 
	    		+ "', contraseña=" + contraseña 
	    		+ "', rol=" + rol 
	    		+ "', idCliente=" + idCliente 	
	    		+ "}";
	   
	}
	

	
}

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
	private String password;
	private Rol rol;
	private int idCliente;
	
	public Usuario(int idUsuario, String username, String password, Rol rol, int idCliente) {

		this.idUsuario = idUsuario;
		this.username = username;
		this.password = password;
		this.rol = rol;
		this.idCliente = idCliente;

	}

	
	public Usuario  () {
		
		
		this.idUsuario = 0;
		this.username = "Sin nombre";
		this.password = "Sin contraseña";
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
		return password;
	}
	public void setContraseña(String contraseña) {
		this.password = contraseña;
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
	    		+ "', contraseña=" + password 
	    		+ "', rol=" + rol 
	    		+ "', idCliente=" + idCliente 	
	    		+ "}";
	   
	}
	

	
}

package sistAlarmas;

public class Admin {
	
	private UsuarioRegistrado usuario;

	//Constructor
	public Admin() {}

	//Metodo para establecer un usuario como administrador
	public Admin setUsuarioRegistrado(UsuarioRegistrado usuarioR) throws Exception {
		if (this.usuario != null) throw new Exception("El administrador ya estaba vinculado a un perfil de usuario");
		if (usuarioR == null) throw new Exception("Usuario no valido");
		this.usuario = usuarioR;
		return this;
	}
}

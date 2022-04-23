package sistAlarmas;

// Si ten�is dudas de por qu� esta clase extiende/no extiende a
// UsuarioRegistrado, preguntadle a Xiana, que no escribe aqu�
// la justificaci�n porque est� cambiando de opini�n al respecto
// cada 5 minutos :)
public class Admin {
	
	private UsuarioRegistrado usuario;
	
	public Admin(UsuarioRegistrado usuario) {}
	
	public Admin setUsuarioRegistrado(UsuarioRegistrado usuarioR) throws Exception {
		if (this.usuario != null) throw new Exception("El administrador ya estaba vinculado a un perfil de usuario");
		if (usuarioR == null) throw new Exception("Usuario no valido");
		this.usuario = usuarioR;
		return this;
	}
}

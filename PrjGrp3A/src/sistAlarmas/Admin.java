package sistAlarmas;

// Si ten�is dudas de por qu� esta clase extiende/no extiende a
// UsuarioRegistrado, preguntadle a Xiana, que no escribe aqu�
// la justificaci�n porque est� cambiando de opini�n al respecto
// cada 5 minutos :)
public class Admin {
	
	private UsuarioRegistrado usuario;
	
	public Admin(UsuarioRegistrado usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido");
		this.usuario = usuario;
	}
}

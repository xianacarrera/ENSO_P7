package sistAlarmas;

// Si tenéis dudas de por qué esta clase extiende/no extiende a
// UsuarioRegistrado, preguntadle a Xiana, que no escribe aquí
// la justificación porque está cambiando de opinión al respecto
// cada 5 minutos :)
public class Admin {
	
	private UsuarioRegistrado usuario;
	
	public Admin(UsuarioRegistrado usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido");
		this.usuario = usuario;
	}
	
}

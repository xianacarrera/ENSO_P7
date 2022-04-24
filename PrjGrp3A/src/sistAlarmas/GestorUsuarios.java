package sistAlarmas;

import java.util.HashMap;

public class GestorUsuarios implements ItfGestorUsuarios {
	
	private static GestorUsuarios instancia;
	private final HashMap<String, Usuario> usuarios;

	private GestorUsuarios() {
		usuarios = new HashMap<>();
	}
	
	public static GestorUsuarios getInstancia() {
		if (instancia == null) instancia = new GestorUsuarios();
		return instancia;
	}
	
	public boolean existeUsuario(String idUsuario) {
		if (idUsuario == null) return false;
		if (!usuarios.containsKey(idUsuario)) return false;
		if (usuarios.get(idUsuario) == null) return false;
		return true;
	}
	

}

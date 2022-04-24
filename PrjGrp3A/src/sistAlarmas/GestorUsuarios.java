package sistAlarmas;

import java.util.HashMap;

public class GestorUsuarios implements ItfGestorUsuarios {
	//Declaración de variables
	private static GestorUsuarios instancia;
	private final HashMap<String, Usuario> usuarios;

	//Constructor
	private GestorUsuarios() {
		usuarios = new HashMap<>();
	}

	//Patrón Singleton
	public static GestorUsuarios getInstancia() {
		if (instancia == null) instancia = new GestorUsuarios();
		return instancia;
	}

	//Método para comprobar si existe un usuario
	public boolean existeUsuario(String idUsuario) {
		if (idUsuario == null) return false;
		if (!usuarios.containsKey(idUsuario)) return false;
		if (usuarios.get(idUsuario) == null) return false;
		return true;
	}
	

}

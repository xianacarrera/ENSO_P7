package sistAlarmas;

import java.util.Arrays;
import java.util.HashMap;

public class GestorUsuarios implements ItfGestorUsuarios {
	//Declaraci�n de variables
	private static GestorUsuarios instancia;
	private final HashMap<String, Usuario> usuarios;

	//Constructor
	private GestorUsuarios() {
		usuarios = new HashMap<>();
	}

	//Patr�n Singleton
	public static GestorUsuarios getInstancia() {
		if (instancia == null) instancia = new GestorUsuarios();
		return instancia;
	}

	//M�todo para comprobar si existe un usuario
	public boolean existeUsuario(String idUsuario) {
		if (idUsuario == null) return false;
		if (!usuarios.containsKey(idUsuario)) return false;
		if (usuarios.get(idUsuario) == null) return false;
		return true;
	}
	
	//Sobreescritura del m�todo addCentro de la interfaz ItfGestorCentros
	@Override
	public ItfGestorUsuarios addUsuario(Usuario usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido: es inexistente");
		if (!ItfGestorId.checkIdUsuario(usuario.getIdUsuario())) throw new Exception("El identificador del usuario no es valido");
		if (usuarios.containsKey(usuario.getIdUsuario())) throw new Exception("El usuario ya habia sido registrado");
		
		usuarios.put(usuario.getIdUsuario(), usuario);
		return this;
	}

	//Sobreescritura del m�todo modificarCentro de la interfaz ItfGestorCentros
	@Override
	public ItfGestorUsuarios modificarUsuario(Usuario usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido: es inexistente");
		if (!usuarios.containsKey(usuario.getIdUsuario())) throw new Exception("El usuario no habia sido registrado previamente");
		
		usuarios.put(usuario.getIdUsuario(), usuario);
		return this;
	}

	//Sobreescritura del m�todo borrarCentro de la interfaz ItfGestorCentros
	@Override
	public ItfGestorUsuarios borrarUsuario(String idUsuario) throws Exception {
		// Corroboramos tanto que la key (idCentro) est� en el HashMap como que su valor asociado
		// (el centro) no sea nulo
		Usuario usuario;
		if (idUsuario == null) throw new Exception("Identificador no valido: es inexistente");
		if (!usuarios.containsKey(idUsuario)) throw new Exception("El identificador no se corresponde con ningun usuario registrado");
		if ((usuario = usuarios.get(idUsuario)) == null) throw new Exception("Error fatal: el usuario correspondiente al identificador no existe");
		
		usuarios.remove(idUsuario);
		return this;
	}

	//Sobreescritura del m�todo leerCentro de la interfaz ItfGestorCentros
	@Override
	public Usuario leerUsuario(String idUsuario) throws Exception {
		if (idUsuario == null) throw new Exception("Identificador no valido: es inexistente");
		if (!usuarios.containsKey(idUsuario)) throw new Exception("El identificador no se corresponde con ningun centro registrado");

		return usuarios.get(idUsuario);
	}
	

}

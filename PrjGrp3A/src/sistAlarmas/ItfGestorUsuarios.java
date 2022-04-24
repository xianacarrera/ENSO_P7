package sistAlarmas;

public interface ItfGestorUsuarios {
    
	GestorUsuarios addUsuario(Usuario usuario) throws Exception;
	GestorUsuarios modificarUsuario(Usuario usuario) throws Exception;
	GestorUsuarios borrarUsuario(String idUsuario) throws Exception;
    Usuario leerUsuario(String idUsuario) throws Exception;
    boolean existeUsuario(String idUsuario);
}

package sistAlarmas;

public interface ItfGestorUsuarios {
    
	ItfGestorUsuarios addUsuario(Usuario usuario) throws Exception;
	ItfGestorUsuarios modificarUsuario(Usuario usuario) throws Exception;
	ItfGestorUsuarios borrarUsuario(String idUsuario) throws Exception;
    Usuario leerUsuario(String idUsuario) throws Exception;
    boolean existeUsuario(String idUsuario);
}

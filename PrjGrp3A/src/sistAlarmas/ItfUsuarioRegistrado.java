package sistAlarmas;

public interface ItfUsuarioRegistrado {
	

	ItfUsuarioRegistrado volverAdmin() throws Exception;
	ItfUsuarioRegistrado desactivarAdmin() throws Exception;
	ItfUsuarioRegistrado volverPersonalEquipo() throws Exception;
	ItfUsuarioRegistrado desactivarPersonalEquipo() throws Exception;
	boolean ayudaEnEmergencias();
	boolean esAdmin();
	
}

package sistAlarmas;

import java.util.List;

public interface ItfEquipo {

	ItfEquipo recibirOrden(List<Accion> acciones, Alarma al) throws Exception;
	ItfEquipo gestionarAlarma() throws Exception;
	boolean estaOcupado();
	ItfEquipo addResponsabilidad(String resp) throws Exception;
	ItfEquipo quitarResponsabilidad(String resp) throws Exception;
	ItfEquipo addMiembro(UsuarioRegistrado miembro) throws Exception;
	ItfEquipo quitarMiembro(UsuarioRegistrado miembro) throws Exception;
	ItfEquipo borrarDatosEquipo() throws Exception;
}

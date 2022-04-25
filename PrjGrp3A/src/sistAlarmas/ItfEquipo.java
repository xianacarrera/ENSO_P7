package sistAlarmas;

import java.util.List;

public interface ItfEquipo {

	Equipo recibirOrden(List<Accion> acciones, Alarma al) throws Exception;
	Equipo gestionarAlarma() throws Exception;
	boolean estaOcupado();
	Equipo addResponsabilidad(String resp) throws Exception;
	Equipo quitarResponsabilidad(String resp) throws Exception;
	Equipo addMiembro(UsuarioRegistrado miembro) throws Exception;
	Equipo quitarMiembro(UsuarioRegistrado miembro) throws Exception;
	Equipo borrarDatosEquipo() throws Exception;
}

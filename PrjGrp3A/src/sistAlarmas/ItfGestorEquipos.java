package sistAlarmas;

public interface ItfGestorEquipos {

	Equipo addEquipo(Equipo equipo) throws Exception;
	Equipo modificarEquipo(Equipo equipo) throws Exception;
	Equipo eliminarEquipo(String idEquipo) throws Exception;
	Equipo leerEquipo(String idEquipo) throws Exception;
	Accion addAccion(Accion accion) throws Exception;
	Accion modificarAccion(Accion accion) throws Exception;
}

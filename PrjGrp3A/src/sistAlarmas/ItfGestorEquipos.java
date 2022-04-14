package sistAlarmas;

public interface ItfGestorEquipos {

	Equipo addEquipo(Equipo equipo);
	Equipo modificarEquipo(Equipo equipo);
	Equipo eliminarEquipo(String idEquipo);
	Equipo leerEquipo(String idEquipo);
}

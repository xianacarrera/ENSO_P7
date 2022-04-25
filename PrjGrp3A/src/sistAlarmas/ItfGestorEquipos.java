package sistAlarmas;

import java.util.List;

public interface ItfGestorEquipos {

	Equipo buscarEquipo(Alarma al) throws Exception;
	boolean esEquipoRegistrado(String idEquipo);
	
	GestorEquipos enviarAcciones(Equipo equipo, List<Accion> acciones, Alarma alarma) throws Exception;
	GestorEquipos recibirVerificacion(Equipo equipo, Verificacion verif) throws Exception;
	
	Equipo addEquipo(Equipo equipo) throws Exception;
	Equipo modificarEquipo(Equipo equipo) throws Exception;
	Equipo eliminarEquipo(String idEquipo) throws Exception;
	Equipo leerEquipo(String idEquipo) throws Exception;
	
	Accion addAccion(Accion accion) throws Exception;
	Accion modificarAccion(Accion accion) throws Exception;
	Accion leerAccion(String idAccion) throws Exception;
	
	Verificacion addVerificacion(Verificacion verif) throws Exception;
	Verificacion leerVerif(String idVerif) throws Exception;
	
}

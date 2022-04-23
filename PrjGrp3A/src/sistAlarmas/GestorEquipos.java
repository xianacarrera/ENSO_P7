package sistAlarmas;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GestorEquipos implements ItfGestorEquipos{

	private static GestorEquipos instancia;
	private final HashMap<String, Equipo> equipos;

	private GestorEquipos() {
		equipos = new HashMap<>();
	}
	
	public static GestorEquipos getInstancia() {
		if (instancia == null) instancia = new GestorEquipos();
		return instancia;
	}
	
	public Equipo recibirProtocolos(List<Protocolo> prots, Alarma al) {
		// MÉTODO MUY DADO A COMPLEJIDAD CICLOMÁTICA ALTA POR EL ALGORITMO DE BÚSQUEDA DE EQUIPOS
		
		if (prots == null || prots.isEmpty() || al == null) return null;
		
		// List<Accion> 
		
		List<Equipo> candidatos = equipos.values().stream().collect(Collectors.toList());
		// DEFINIR ALGORITMO
		
		// Si no hay candidatos válidos, se devuelve null
		if (candidatos.isEmpty()) return null;
		
		Equipo elegido = candidatos.get(0);  // Hay que poner esto bien
		
		candidatos.
		
		
		return candidatos.get(0);		
	}
	
	public GestorEquipos enviarAcciones(Equipo equipo, List<Accion> acciones, Alarma alarma) throws Exception {
		for (Accion ac : acciones) ac.setDestinatario(equipo);
		equipo.recibirOrden(acciones, alarma);
		return this;
	}
	
	@Override
	public Equipo addEquipo(Equipo equipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Equipo modificarEquipo(Equipo equipo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Equipo eliminarEquipo(String idEquipo) {
		
		return null;
	}

	@Override
	public Equipo leerEquipo(String idEquipo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

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
	
	public Equipo recibirProtocolo(Protocolo prot, Alarma al) {
		// M�TODO MUY DADO A COMPLEJIDAD CICLOM�TICA ALTA POR EL ALGORITMO DE B�SQUEDA DE EQUIPOS
		
		List<Equipo> candidatos = equipos.values().stream().collect(Collectors.toList());
		// DEFINIR ALGORITMO
		
		// Si no hay candidatos v�lidos, se devuelve null
		if (candidatos.isEmpty()) return null;
		
		Equipo elegido = candidatos.get(0);  // Hay que poner esto bien
		
		
		return candidatos.get(0);		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Equipo leerEquipo(String idEquipo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

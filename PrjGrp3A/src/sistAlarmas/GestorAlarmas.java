package sistAlarmas;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GestorAlarmas implements ItfGestorAlarmas {

	private static GestorAlarmas instancia;
	private static HashMap<String, Alarma> alarmasEnEjecucion;
	private static HashMap<String, Protocolo> protocolos;
	
	
	private GestorAlarmas(){
		alarmasEnEjecucion = new HashMap<>();
		protocolos = new HashMap<>();
	}

	public static GestorAlarmas getInstancia() {
		if (instancia == null) instancia = new GestorAlarmas();
		return instancia;
	}
	
	public Alarma activarAlarma(Centro centro, String zona) {
		Alarma alarma;
		try {
			alarma = new Alarma("id????", , centro, zona);
			//List<Protocolo> protocolos = buscarProtocolo(alarma);
		} catch(Exception eo) {
			return null;
		}
		
		List<Protocolo> prots = buscarProtocolos(alarma);
		
		return alarma;
	}
	
	public Protocolo emitirProtocolo(Protocolo protocolo) {
		
	}
	
	public List<Protocolo> buscarProtocolos(Alarma al){
		if (al == null || al.getTipoAlarma() == null) return null;
		
		List<Protocolo> prots = protocolos.values().stream().collect(Collectors.toList());
		// Podemos hacer un getTipo().equals porque el tipo de un protocolo nunca es null
		prots.stream().filter(prot -> prot.getTipo().equals(al.getTipoAlarma()) && (prot.getLocalizacion() == null || prot.getLocalizacion().equals(al.getCentro().getNombre())));
		return prots;
	}
	
	public Alarma activarAlarma(Sensor sensor) {
		if (sensor == null) return null;
		Alarma alarma = null;
		try {
			// Hacer switch en función del tipo de sensor para conseguir el tipo de alarma
			//alarma = new Alarma("id????", , sensor.getCentro(), sensor.getZona());
			alarma = new Alarma("uwu", ItfGestorAlarmas.tipoSensorToTipoAlarma(sensor.getTipoSensor()), sensor.getCentro(), sensor.getZona());
		} catch (Exception eo) {
			return null;
		}
		
		GestorEquipos.getInstancia().recibirProtocolos(buscarProtocolos(alarma), alarma);
		
		return alarma;
	}
	
	
}

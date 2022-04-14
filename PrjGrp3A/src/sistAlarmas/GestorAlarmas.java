package sistAlarmas;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GestorAlarmas implements ItfGestorAlarmas {

	private static GestorAlarmas instancia;
	private HashMap<String, Alarma> alarmas;
	private HashMap<String, Protocolo> protocolos;
	
	
	private GestorAlarmas(){}

	public static GestorAlarmas getInstancia() {
		if (instancia == null) instancia = new GestorAlarmas();
		return instancia;
	}
	
	public Alarma activarAlarma(Centro centro, String zona) {
		Alarma alarma;
		try {
			alarma = new Alarma("id????", TipoAlarma.MANUAL, centro, zona);
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
		Alarma alarma = null;
		try {
			// Hacer switch en función del tipo de sensor para conseguir el tipo de alarma
			//alarma = new Alarma("id????", , sensor.getCentro(), sensor.getZona());

		} catch (Exception eo) {
			return null;
		}
		return alarma;
	}
	
	
}

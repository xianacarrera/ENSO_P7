package sistAlarmas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GestorAlarmas implements ItfGestorAlarmas {
	// Declaracion de variables
	private static GestorAlarmas instancia;
	private HashMap<String, Alarma> alarmasEnEjecucion;
	private HashMap<String, Alarma> alarmasPendientes;
	private HashMap<String, Protocolo> protocolos;
	
	// Constructor
	private GestorAlarmas(){
		alarmasEnEjecucion = new HashMap<>();
		protocolos = new HashMap<>();
		alarmasPendientes = new HashMap<>();
	}
	// Patron Singleton
	public static GestorAlarmas getInstancia() {
		if (instancia == null) instancia = new GestorAlarmas();
		return instancia;
	}
	//Método que comprueba si una alarma está en ejecución
	public boolean esAlarmaEnEjecucion(String idAlarma) {
		if (idAlarma == null) return false;
		if (!alarmasEnEjecucion.containsKey(idAlarma)) return false;
		if (alarmasEnEjecucion.get(idAlarma) == null) return false;
		return true;
	}

	//Método para activar una alarma: debe tener asignada un centro o una zona
	public Alarma activarAlarma(Centro centro, String zona) throws Exception {
		if (centro == null && zona == null) throw new Exception("Una alarma debe tener o bien un centro o bien una zona");
		if (!GestorCentros.getInstancia().esCentroRegistrado(centro.getIdCentro()))
			throw new Exception("El centro no ha sido registrado en el sistema");
		
		Alarma alarma = new Alarma();
		boolean flag = false;
		do {
			alarma.setIdAlarma(ItfGestorId.generarId("Alarma"));
			try {
				leerAlarma(alarma.getIdAlarma());
			} catch (Exception e) {
				flag = true;
			}
		} while (!flag);
		alarma.setEstadoAlarma(EstadoAlarma.CREADA);
		alarma.setTipoAlarma(TipoAlarma.MANUAL);
		alarma.setCentro(centro);
		alarma.setZona(zona);
		
		Equipo equipoEncargado = GestorEquipos.getInstancia().recibirProtocolos(buscarProtocolos(alarma), alarma);
		if (equipoEncargado == null) alarmasPendientes.put(alarma.getIdAlarma(), alarma);
		else{ 
			alarma.setEstadoAlarma(EstadoAlarma.ENEJECUCION);
			alarmasEnEjecucion.put(alarma.getIdAlarma(), alarma);
		}
		
		return alarma;
	}

	//Método para buscar los protocolos de una alarma
	public List<Protocolo> buscarProtocolos(Alarma al){
		if (al == null || al.getTipoAlarma() == null) return null;
		
		List<Protocolo> prots = protocolos.values().stream().collect(Collectors.toList());
		// Podemos hacer un getTipo().equals porque el tipo de un protocolo nunca es null
		prots = prots.stream().filter(prot -> prot.getTipo().equals(al.getTipoAlarma()) && (prot.getLocalizacion() == null || 
				prot.getLocalizacion().equals(al.getCentro().getNombre()) || prot.getLocalizacion().equals(al.getZona())))
				.collect(Collectors.toList());
		return prots;
	}

	//Método para activar una alarma, esta vez empleando un sensor: debe tener asignado un centro o una zona
	public Alarma activarAlarma(Sensor sensor) throws Exception {
		if (sensor == null) throw new Exception("El sensor no existe");
		if (sensor.getCentro() == null && sensor.getZona() == null) throw new Exception("El sensor no tiene ubicacion (ni centro ni zona)");
		if (!GestorCentros.getInstancia().esCentroRegistrado(sensor.getCentro().getIdCentro()))
			throw new Exception("El centro del sensor no esta registrado en el sistema");
		if (!GestorCentros.getInstancia().leerSensor(sensor.getIdSensor()).equals(sensor)) 
			throw new Exception("El sensor no esta registrado en el sistema");

		Alarma alarma = new Alarma();
		boolean flag = false;
		do {
			alarma.setIdAlarma(ItfGestorId.generarId("Alarma"));
			try {
				leerAlarma(alarma.getIdAlarma());
			} catch (Exception e) {
				flag = true;
			}
		} while (!flag);
		alarma.setEstadoAlarma(EstadoAlarma.CREADA);
		alarma.setTipoAlarma(ItfGestorAlarmas.tipoSensorToTipoAlarma(sensor.getTipoSensor()));
		alarma.setCentro(sensor.getCentro());
		alarma.setZona(sensor.getZona());
		
		Equipo equipoEncargado = GestorEquipos.getInstancia().recibirProtocolos(buscarProtocolos(alarma), alarma);
		if (equipoEncargado == null) alarmasPendientes.put(alarma.getIdAlarma(), alarma);
		else{ 
			alarma.setEstadoAlarma(EstadoAlarma.ENEJECUCION);
			alarmasEnEjecucion.put(alarma.getIdAlarma(), alarma);
		}
		
		return alarma;
	}

	//Método para despachar las alarmas pendientes en caso de que existan
	public List<Alarma> despacharAlarmasPendientes() throws Exception {
		if (alarmasPendientes.isEmpty()) throw new Exception("No hay alarmas pendientes");
		List<Alarma> alarmasDespachadas = new ArrayList<>();
		for (Alarma al : alarmasPendientes.values()) {
			Equipo equipoEncargado = GestorEquipos.getInstancia().recibirProtocolos(buscarProtocolos(al), al);
			if (equipoEncargado != null) {
				alarmasPendientes.remove(al.getIdAlarma());
				al.setEstadoAlarma(EstadoAlarma.ENEJECUCION);
				alarmasEnEjecucion.put(al.getIdAlarma(), al);
				alarmasDespachadas.add(al);
			}
		}
		return alarmasDespachadas;
	}

	//Método para desactivar una alarma
	public Alarma desactivarAlarma(String idAlarma) throws Exception {
		if (!ItfGestorId.checkIdAlarma(idAlarma)) throw new Exception("Identificador no v�lido");
		if (!alarmasEnEjecucion.containsKey(idAlarma)) throw new Exception("La alarma no est� en ejecuci�n");
		
		Alarma al = alarmasEnEjecucion.get(idAlarma);
		alarmasEnEjecucion.remove(idAlarma);
		
		return al;
	}

	//Método para leer una alarma
	public Alarma leerAlarma(String idAlarma) throws Exception {
		if (idAlarma == null) throw new Exception("Identificador no valido: es inexistente");
		Alarma al = null;
		al = alarmasEnEjecucion.get(idAlarma);
		if (al == null) {
			alarmasPendientes.get(idAlarma);
			if (al == null) throw new Exception("El identificador no se corresponde con ninguna alarma en ejecucion o pendiente");
		}
		
		return al;
	}

	//Sobreescritura del método addProtocolo de la clase ItfGestorAlarmas
	@Override
	public GestorAlarmas addProtocolo(Protocolo prot) throws Exception {
		if (prot == null) throw new Exception("Protocolo no valido: es inexistente");
		if (!ItfGestorId.checkIdProtocolo(prot.getIdProtocolo())) throw new Exception("El identificador del protocolo no es valido");
		if (prot.getTipo() == null || prot.getNombre() == null) throw new Exception("Informacion del protocolo incompleta");
		if (protocolos.containsKey(prot.getIdProtocolo())) throw new Exception("El protocolo ya habia sido registrado");
		
		protocolos.put(prot.getIdProtocolo(), prot);
		return this;
	}

	//Sobreescritura del método modificarProtocolo de la clase ItfGestorAlarmas
	@Override
	public GestorAlarmas modificarProtocolo(Protocolo prot) throws Exception {
		if (prot == null) throw new Exception("Protocolo no valido: es inexistente");
		if (!protocolos.containsKey(prot.getIdProtocolo())) throw new Exception("El protocolo no habia sido registrado previamente");
		if (prot.getTipo() == null || prot.getNombre() == null) throw new Exception("Informacion del protocolo incompleta");
		
		protocolos.put(prot.getIdProtocolo(), prot);
		return this;
	}

	//Sobreeescritura del método borrarProtocolo de la clase ItfGestorAlarmas
	@Override
	public GestorAlarmas borrarProtocolo(String idProt) throws Exception {
		// Corroboramos tanto que la key (idCentro) est� en el HashMap como que su valor asociado
		// (el centro) no sea nulo
		Protocolo prot;
		if (idProt == null) throw new Exception("Identificador no valido: es inexistente");
		if (!protocolos.containsKey(idProt)) throw new Exception("El identificador no se corresponde con ningun protocolo registrado");
		if ((prot = protocolos.get(idProt)) == null) throw new Exception("Error fatal: el protocolo correspondiente al identificador no existe");
		
		for (Alarma al : alarmasEnEjecucion.values()) {
			if (buscarProtocolos(al).contains(prot)) throw new Exception("No se puede borrar el protocolo: hay alarmas en ejecucion asociadas a el");
		}
		for (Alarma al : alarmasPendientes.values()) {
			if (buscarProtocolos(al).contains(prot)) throw new Exception("No se puede borrar el protocolo: hay alarmas pendientes asociadas a el");
		}
		
		protocolos.remove(idProt);
		
		return this;
	}

	//Sobreeescritura del método leerProtocolo de la clase ItfGestorAlarmas
	@Override
	public Protocolo leerProtocolo(String idProt) throws Exception {
		if (idProt == null) throw new Exception("Identificador no valido: es inexistente");
		if (!protocolos.containsKey(idProt)) throw new Exception("El identificador no se corresponde con ningun protocolo registrado");

		return protocolos.get(idProt);
	}
	
	/**GETTERS**/
	public HashMap<String, Alarma> getAlarmasEnEjecucion(){
		return this.alarmasEnEjecucion;
	}
	
	public HashMap<String, Alarma> getAlarmasPendientes(){
		return this.alarmasPendientes;
	}
	
	public HashMap<String, Protocolo> getProtocolos(){
		return this.protocolos;
	}
	/**FIN GETTERS**/
}

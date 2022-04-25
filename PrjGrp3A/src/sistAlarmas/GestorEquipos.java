package sistAlarmas;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GestorEquipos implements ItfGestorEquipos{
	// Declaracion de variables
	private static GestorEquipos instancia;
	private HashMap<String, Equipo> equipos;
	private HashMap<String, Accion> acciones;
	private HashMap<String, Verificacion> verificaciones; 

	// Constructor
	private GestorEquipos() {
		equipos = new HashMap<>();
		acciones = new HashMap<>();
		verificaciones = new HashMap<>();
	}

	//Patrón Singleton
	public static GestorEquipos getInstancia() {
		if (instancia == null) instancia = new GestorEquipos();
		return instancia;
	}

	//Método para comprobar si un equipo existe
	@Override
	public boolean esEquipoRegistrado(String idEquipo) {
		if (idEquipo == null) return false;
		if (!equipos.containsKey(idEquipo)) return false;
		if (equipos.get(idEquipo) == null) return false;
		return true;
	}

	//Método para recibir los protocolos para los equipos
	// Metodo de elevada complejidad ciclomatica
	@Override
	public Equipo buscarEquipo(Alarma al) throws Exception {
		
		if (al == null) throw new Exception("Debe indicarse una alarma");
		
		List<Equipo> candidatos = equipos.values().stream().collect(Collectors.toList());
		
		// Si no hay candidatos válidos, se devuelve null
		if (candidatos.isEmpty()) return null;
		
		HashMap<String, Integer> puntuaciones = new HashMap<>();
		for (Equipo cand : candidatos) {
			// Generamos una puntuacion para cada equipo. Se acabara tomando el equipo de mayor puntuacion
			if (cand.estaOcupado() || cand.getMiembros().size() == 0) {
				// El equipo no puede ser escogido
				puntuaciones.put(cand.getIdEquipo(), 0);
				continue;
			}
			List<UsuarioRegistrado> miembros = cand.getMiembros();
			if (miembros.size() > 1 && miembros.size() < 6) {
				// El equipo tiene un tamaño optimo para la gestion de emergencias
				puntuaciones.put(cand.getIdEquipo(), cand.getMiembros().size());
			} else if (miembros.size() >= 6) {
				// El tamaño del equipo entorpece la gestion de emergencias en cierta medida
				puntuaciones.put(cand.getIdEquipo(), Math.min(2, 11 - miembros.size()));
			}
			
			if (cand.getResponsabilidades().size() >= 3 && cand.getResponsabilidades().size() <= 6) {
				// El equipo tiene un numero de responsabilidades optimo
				puntuaciones.put(cand.getIdEquipo(), puntuaciones.get(cand.getIdEquipo()) + 3);
			}
			puntuaciones.put(cand.getIdEquipo(), 1);		// Opcion por defecto
		}  
		
		String idMax = puntuaciones.entrySet().stream().max((entry1, entry2) -> Integer.compare(entry1.getValue(), entry2.getValue())).get().getKey();
		/* Para mayor claridad, se incluye comentado el código sin programación funcional
		int max = 0;
		String idMax = null;
		for (String id : puntuaciones.keySet()) {
			if (puntuaciones.get(id) > max) {
				max = puntuaciones.get(id);
				idMax = id;
			}
		}
		*/
		if (puntuaciones.get(idMax) == 0) return null;
		
		return GestorEquipos.getInstancia().leerEquipo(idMax);		
	}

	//Método para enviar las acciones (Órdenes) a un equipo
	@Override
	public GestorEquipos enviarAcciones(Equipo equipo, List<Accion> acciones, Alarma alarma) throws Exception {
		for (Accion ac : acciones) {
			ac.setDestinatario(equipo);
		}
		equipo.recibirOrden(acciones, alarma);
		return this;
	}

	// Método para recibir las verificaciones
	@Override
	public GestorEquipos recibirVerificacion(Equipo equipo, Verificacion verif) throws Exception {
		// En caso de una aplicación con gui, se imprimiría el mensaje
		String mensFinal = "Alarma gestionada";
		if (mensFinal.equals(verif.getMensaje())){
			GestorAlarmas.getInstancia().desactivarAlarma(verif.getAlarma().getIdAlarma());
			equipo.setAlarmaEnEjecucion(null);
		}
		return this;
	}

	// Sobreescritura del método addEquipo de la interfaz ItfGestorEquipos
	@Override
	public Equipo addEquipo(Equipo equipo) throws Exception {
		if (equipo == null) throw new Exception("Equipo no valido: es inexistente");
		if (!ItfGestorId.checkIdEquipo(equipo.getIdEquipo())) throw new Exception("El identificador del equipo no es valido");
		if (equipo.getMiembros().size() < 1) throw new Exception("El equipo no tiene miembros");
		if (equipos.containsKey(equipo.getIdEquipo())) throw new Exception("El centro ya habia sido registrado");
		
		equipos.put(equipo.getIdEquipo(), equipo);
		return equipo;
	}

	// Sobreescritura del método modificarEquipo
	@Override
	public Equipo modificarEquipo(Equipo equipo) throws Exception {
		if (equipo == null) throw new Exception("Equipo no valido: es inexistente");
		if (!equipos.containsKey(equipo.getIdEquipo())) throw new Exception("El equipo no habia sido registrado previamente");
	
		equipos.put(equipo.getIdEquipo(), equipo);
		return equipo;
	}

	// Sobreescritura del método eliminarEquipo de la interfaz ItfGestorEquipos
	@Override
	public Equipo eliminarEquipo(String idEquipo) throws Exception {
		Equipo equipo;
		if (idEquipo == null) throw new Exception("Identificador no valido: es inexistente");
		if (!equipos.containsKey(idEquipo)) throw new Exception("El identificador no se corresponde con ningun equipo registrado");
		if ((equipo = equipos.get(idEquipo)) == null) throw new Exception("Error fatal: el equipo correspondiente al identificador no existe");
		
		if (equipo.estaOcupado()) throw new Exception("El equipo esta ejecutando tareas; no se puede borrar");
		
		equipo.borrarDatosEquipo();
		return equipo;
	}

	// Sobreescritura del método leerEquipo de la interfaz ItfGestorEquipos
	@Override
	public Equipo leerEquipo(String idEquipo) throws Exception {
		if (idEquipo == null) throw new Exception("Identificador no valido: es inexistente");
		if (!equipos.containsKey(idEquipo)) throw new Exception("El identificador no se corresponde con ningun equipo registrado");

		return equipos.get(idEquipo);
	}

	// Sobreescritura del método addVerificacion de la interfaz ItfGestorEquipos
	@Override
	public Verificacion addVerificacion(Verificacion verif) throws Exception {
		if (verif == null) throw new Exception("Verificacion no valida: es inexistente");
		if (!ItfGestorId.checkIdVerificacion(verif.getIdVerif())) throw new Exception("El identificador de la verificacion no es valido");
		if (verif.getMensaje() == null || verif.getAlarma() == null || verif.getEmisor() == null) 
			throw new Exception("Informacion de verificacion incompleta");
		
		if (verificaciones.containsKey(verif.getIdVerif())) throw new Exception("La verificacion ya habia sido registrada");
		
		verificaciones.put(verif.getIdVerif(), verif);
		return verif;
	}

	// Sobreescritura del mÃ©todo leerVerif de la interfaz ItfGestorEquipos
	@Override
	public Verificacion leerVerif(String idVerif) throws Exception {
		if (idVerif == null) throw new Exception("Identificador no valido: es inexistente");
		if (!verificaciones.containsKey(idVerif)) 
			throw new Exception("El identificador no se corresponde con ninguna verificacion registrada");

		return verificaciones.get(idVerif);
	}

	//Sobreescritura del método addAccion de la interfaz ItfGestorEquipos
	@Override
	public Accion addAccion(Accion accion) throws Exception {
		if (accion == null) throw new Exception("Accion no valida: es inexistente");
		if (!ItfGestorId.checkIdAccion(accion.getIdAccion())) throw new Exception("El identificador de la accion no es valido");
		if (accion.getMensaje() == null) throw new Exception("La accion no tiene mensaje");
		if (acciones.containsKey(accion.getIdAccion())) throw new Exception("La accion ya habia sido registrada");
		
		acciones.put(accion.getIdAccion(), accion);
		return accion;
	}

	//Sobreescritura del método modificarAccion de la interfaz ItfGestorEquipos
	@Override
	public Accion modificarAccion(Accion accion) throws Exception {
		if (accion == null) throw new Exception("Accion no valida: es inexistente");
		if (!acciones.containsKey(accion.getIdAccion())) throw new Exception("La accion no habia sido registrada previamente");
		if (accion.getMensaje() == null) throw new Exception("La accion no tiene mensaje");
		
		acciones.put(accion.getIdAccion(), accion);
		return accion;
	}
	
	// No se permite borrar acciones ni verificaciones

	//Método para leer las acciones a partir de un identificador
	@Override
	public Accion leerAccion(String idAccion) throws Exception {
		if (idAccion == null) throw new Exception("Identificador no valido: es inexistente");
		if (!acciones.containsKey(idAccion)) 
			throw new Exception("El identificador no se corresponde con ninguna accion registrada");

		return acciones.get(idAccion);
	}
}

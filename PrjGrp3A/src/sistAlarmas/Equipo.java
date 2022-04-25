package sistAlarmas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Equipo implements ItfEquipo {
	//Declaracion de variables
	private String idEquipo;
	private List<String> responsabilidades;
	private String transportePrimario;
	private String descripcion;
	
	private Alarma alarmaEnEjecucion;
	private List<Accion> accionesEnEjecucion;
	private List<UsuarioRegistrado> miembros;

	//Constructor
	public Equipo() {
		miembros = new ArrayList<>();
		responsabilidades = new ArrayList<>();
	}

	//M�todo para que un equipo reciba una lista de acciones asociadas a la alarma indicada
	@Override
	public ItfEquipo recibirOrden(List<Accion> acciones, Alarma al) throws Exception {
		if (al == null) throw new Exception("Alarma no indicada");
		if (acciones.isEmpty()) throw new Exception("No hay acciones");
		setAlarmaEnEjecucion(al);
		
		for(Accion ac : acciones) ac.recibir();
		accionesEnEjecucion = acciones;
		
		return this;
	}

	//M�todo para la gesti�n de una alarma
	//Metodo de elevada complejidad ciclomatica
	@Override
	public ItfEquipo gestionarAlarma() throws Exception {
		// Declaraci�n de variables
		GestorEquipos ge = GestorEquipos.getInstancia();
		boolean flag = false;
		
		List<Verificacion> verifs = new ArrayList<>();

		//Para cada accion en ejecuci�n:
		for (Accion accion : accionesEnEjecucion) {
			Verificacion verif = new Verificacion(); //Se crea una nueva verificaci�n

			do {
				verif.setIdVerif(ItfGestorId.generarId("Verificacion")); //Se genera un id para la verificaci�n
				try {
					ge.leerVerif(verif.getIdVerif()); //Se intenta leer el id generado
				} catch (Exception e) {
					flag = true; //Se establece el flag en true para que se trate de generar un id nuevo (si falla)
				}
			} while (!flag);
			verif.setMensaje("Verificacion de accion"); //Se establece el mensaje de la verificaci�n
			verif.setAccion(accion); //Se establece la acción de la verificaci�n
			verif.setAlarma(alarmaEnEjecucion);
			verif.setEmisor(this); //Se establece el emisor de la verificaci�n
			ge.addVerificacion(verif); //Se a�ade la verificaci�n al gestor de equipos
			verifs.add(verif); //Se a�ade la verificaci�n a la lista de verificaciones
		}
		
		Verificacion verifFinal = new Verificacion(); //Se crea una nueva verificaci�n
		flag = false; //Se establece el flag a false
		do {
			verifFinal.setIdVerif(ItfGestorId.generarId("Verificacion")); //Se genera un id para la verificaci�n
			try {
				ge.leerVerif(verifFinal.getIdVerif()); //Se intenta leer el id generado
			} catch (Exception e) {
				flag = true; //Se establece el flag en true para que se trate de generar un id nuevo (si falla)
			}
		} while (!flag);
		verifFinal.setMensaje("Verificacion final; se cierra la gestion de la alarma");
		verifFinal.setAlarma(alarmaEnEjecucion); //Se establece la alarma de la verificaci�n final
		verifFinal.setEmisor(this); //Se establece el emisor de la verificaci�n final
		ge.addVerificacion(verifFinal); //Se a�ade la verificaci�n final al gestor de equipos
		verifs.add(verifFinal); //Se a�ade la verificaci�n final a la lista de verificaciones
		
		accionesEnEjecucion = new ArrayList<>(); //Se limpia la lista de acciones en ejecuci�n
		
		for (Verificacion v : verifs) ge.recibirVerificacion(this, v);  //Se llama a la funci�n recibirVerificacion del gestor de equipos
		this.alarmaEnEjecucion = null; //Se establece la alarma en ejecuci�n a null
		
		return this;
	}

	//M�todo para establecer si un equipo est� ocupado (tiene acciones o una alarma en ejecucion)
	@Override
	public boolean estaOcupado() {
		return !(alarmaEnEjecucion == null || accionesEnEjecucion.isEmpty());
	}

	//M�todo para a�adir una responsabilidad a la lista de responsabilidades
	@Override
	public ItfEquipo addResponsabilidad(String resp) throws Exception {
		if (resp == null) throw new Exception("Responsabilidad no valida: no existe");
		responsabilidades.add(resp);
		return this;
	}

	//M�todo para eliminar una responsabilidad de la lista de responsabilidades
	@Override
	public ItfEquipo quitarResponsabilidad(String resp) throws Exception {
		if (resp == null) throw new Exception("Responsabilidad no valida: no existe");
		if (!responsabilidades.contains(resp)) throw new Exception("El equipo no tenia la responsabilidad indicada");
		responsabilidades.remove(resp);
		return this;
	}

	//M�todo para a�adir un usuario a la lista de miembros de un equipo
	@Override
	public ItfEquipo addMiembro(UsuarioRegistrado miembro) throws Exception {
		if (miembro == null || !miembro.ayudaEnEmergencias()) throw new Exception("El nuevo miembro no es valido");
		miembro.getPersonalEquipo().setEquipo(this);
		this.miembros.add(miembro);
		return this;
	}

	//M�todo para eliminar un usuario de la lista de miembros de un equipo
	@Override
	public ItfEquipo quitarMiembro(UsuarioRegistrado miembro) throws Exception{
		if (miembro == null) throw new Exception("El usuario seleccionado no existe");
		if (!miembros.contains(miembro)) throw new Exception("El usuario seleccionado no forma parte del equipo");
		if (miembros.size() - 1 < 1) throw new Exception("No se puede eliminar el miembro: el equipo no puede quedar vacio");
		
		miembros.remove(miembro);
		miembro.getPersonalEquipo().setEquipo(null);
		return this;
	}

	//M�todo para eliminar los datos de un equipo
	@Override
	public ItfEquipo borrarDatosEquipo() throws Exception {
		Iterator<UsuarioRegistrado> it = miembros.iterator(); 
		while (it.hasNext()) {
			UsuarioRegistrado u = it.next();
			it.remove();		// Eliminamos u de la lista de miembros
			u.getPersonalEquipo().setEquipo(null);			// El usuario deja de tener un equipo
		}
		return this;
	}
	
	//M�todo para establecer las responsabilidades de un equipo
	public Equipo setResponsabilidades(List<String> responsabilidades) throws Exception {
		if (responsabilidades == null) throw new Exception("Lista de responsabilidades no valida: no existe");
		if (responsabilidades.isEmpty()) throw new Exception("Lista de responsabilidades no valida: esta vacia");
		// Comprobamos si alguna de las responsabilidades es null
		if (responsabilidades.stream().anyMatch(s -> s == null)) 
			throw new Exception("Lista de responsabilidades no valida: hay una responsabilidad nula");
		this.responsabilidades = responsabilidades;
		return this;
	}

	//M�todo para establecer los miembros de un equipo
	public Equipo setMiembros(List<UsuarioRegistrado> miembros) throws Exception {
		if (miembros == null) throw new Exception("Lista de miembros no valida: no existe");
		if (miembros.isEmpty()) throw new Exception("Lista de miembros no valida: esta vacia");		// Un equipo debe tener al menos un miembro
		// Todos los miembros del equipo deben estar preparados para ayudar en caso de emergencias
		if (!miembros.stream().allMatch(user -> user.ayudaEnEmergencias()))
			throw new Exception("No todos los usuarios de la lista son personal de equipo");
		for (UsuarioRegistrado user : miembros) user.getPersonalEquipo().setEquipo(this);
		// Al llamar a setEquipo comprobamos en este metodo que el usuario est� disponible y que la capacitacion y el nivel de formacion sean adecuados
		
		this.miembros = miembros;
		return this;
	}

	//M�todo para establecer para un equipo una alarma en ejecuci�n
	public Equipo setAlarmaEnEjecucion(Alarma alarmaEnEjecucion) throws Exception {
		if (alarmaEnEjecucion != null && !GestorAlarmas.getInstancia().esAlarmaEnEjecucion(alarmaEnEjecucion.getIdAlarma()))
			throw new Exception("La alarma indicada no esta siendo ejecutada");
		this.alarmaEnEjecucion = alarmaEnEjecucion;
		return this;
	}
	
	//M�todo para establecer el id del equipo
	public Equipo setIdEquipo(String idEquipo) throws Exception {
		if (this.idEquipo != null) throw new Exception("Este equipo ya tiene un identificador");
		if (!ItfGestorId.checkIdEquipo(idEquipo)) throw new Exception("Identificador de equipo no valido");
		this.idEquipo = idEquipo;
		return this;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setTransportePrimario(String transportePrimario) {
		this.transportePrimario = transportePrimario;
	}

	//**GETTERS**//
	public String getIdEquipo() {
		return idEquipo;
	}
	
	public String getTransportePrimario() {
		return transportePrimario;
	}
	
	public List<UsuarioRegistrado> getMiembros() {
		return miembros;
	}
	
	public Alarma getAlarmaEnEjecucion() {
		return alarmaEnEjecucion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public List<String> getResponsabilidades() {
		return responsabilidades;
	}

	/**FIN GETTERS**/

	/**SOBREESCRITURA DE M�TODOS**/
	@Override
	public int hashCode() {
		return Objects.hash(idEquipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Equipo))
			return false;
		Equipo other = (Equipo) obj;
		return Objects.equals(idEquipo, other.idEquipo);
	}
	/**FIN SOBREESCRITURA DE M�TODOS**/
}

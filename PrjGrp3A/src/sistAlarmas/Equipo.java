package sistAlarmas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Equipo {
	private String idEquipo;
	private List<String> responsabilidades;
	private String transportePrimario;
	private String descripcion;
	
	private Alarma alarmaEnEjecucion;
	private List<Accion> accionesEnEjecucion;
	private List<UsuarioRegistrado> miembros;
	
	public Equipo() {
		miembros = new ArrayList<>();
	}
	
	public Equipo setIdEquipo(String idEquipo) throws Exception {
		if (this.idEquipo != null) throw new Exception("Este equipo ya tiene un identificador");
		if (!ItfGestorId.checkIdEquipo(idEquipo)) throw new Exception("Identificador de equipo no valido");
		this.idEquipo = idEquipo;
		return this;
	}
	
	public Equipo recibirOrden(List<Accion> acciones, Alarma al) throws Exception {
		if (this.estaOcupado()) throw new Exception("Equipo no disponible");
		if (al == null) throw new Exception("Alarma no indicada");
		if (acciones.isEmpty()) throw new Exception("No hay acciones");
		setAlarmaEnEjecucion(al);
		
		for(Accion ac : acciones) ac.recibir();
		accionesEnEjecucion = acciones;
		
		return this;
	}
	
	public Equipo gestionarAlarma() throws Exception {
		GestorEquipos ge = GestorEquipos.getInstancia();
		boolean flag = false;
		
		List<Verificacion> verifs = new ArrayList<>();
		
		for (Accion accion : accionesEnEjecucion) {
			Verificacion verif = new Verificacion();

			do {
				verif.setIdVerif(ItfGestorId.generarId("Verificacion"));
				try {
					ge.leerVerif(verif.getIdVerif());
				} catch (Exception e) {
					flag = true;
				}
			} while (!flag);
			verif.setMensaje("Verificacion de accion");
			verif.setAccion(accion);
			verif.setEmisor(this);
			ge.addVerificacion(verif);
			verifs.add(verif);
		}
		
		Verificacion verifFinal = new Verificacion();
		flag = false;
		do {
			verifFinal.setIdVerif(ItfGestorId.generarId("Verificacion"));
			try {
				ge.leerVerif(verifFinal.getIdVerif());
			} catch (Exception e) {
				flag = true;
			}
		} while (!flag);
		verifFinal.setAlarma(alarmaEnEjecucion);
		verifFinal.setEmisor(this);
		ge.addVerificacion(verifFinal);
		verifs.add(verifFinal);
		
		accionesEnEjecucion.clear();
		
		for (Verificacion v : verifs) ge.recibirVerificacion(this, v);
		this.alarmaEnEjecucion = null;
		
		return this;
	}
	
	public boolean estaOcupado() {
		return alarmaEnEjecucion != null || !accionesEnEjecucion.isEmpty();
	}


	public Equipo setResponsabilidades(List<String> responsabilidades) throws Exception {
		if (responsabilidades == null) throw new Exception("Lista de responsabilidades no valida: no existe");
		if (responsabilidades.isEmpty()) throw new Exception("Lista de responsabilidades no valida: esta vacia");
		if (responsabilidades.stream().anyMatch(s -> s == null)) 
			throw new Exception("Lista de responsabilidades no valida: hay una responsabilidad nula");
		this.responsabilidades = responsabilidades;
		return this;
	}
	
	public Equipo addResponsabilidad(String resp) throws Exception {
		if (resp == null) throw new Exception("Responsabilidad no valida: no existe");
		responsabilidades.add(resp);
		return this;
	}
	
	public Equipo quitarResponsabilidad(String resp) throws Exception {
		if (resp == null) throw new Exception("Responsabilidad no valida: no existe");
		if (!responsabilidades.contains(resp)) throw new Exception("El equipo no tenia la responsabilidad indicada");
		responsabilidades.remove(resp);
		return this;
	}
	

	public Equipo setMiembros(List<UsuarioRegistrado> miembros) throws Exception {
		if (miembros == null) throw new Exception("Lista de miembros no valida: no existe");
		if (miembros.isEmpty()) throw new Exception("Lista de miembros no valida: esta vacia");		// Un equipo debe tener al menos un miembro
		if (!miembros.stream().allMatch(user -> user.ayudaEnEmergencias()))
			throw new Exception("No todos los usuarios de la lista son personal de equipo");
		for (UsuarioRegistrado user : miembros) user.getPersonalEquipo().setEquipo(this);
		// Al llamar a setEquipo comprobamos en este metodo que el usuario esté disponible y que la capacitacion y el nivel de formacion sean adecuados
		
		this.miembros = miembros;
		return this;
	}
	

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public void setTransportePrimario(String transportePrimario) {
		this.transportePrimario = transportePrimario;
	}


	public String getTransportePrimario() {
		return transportePrimario;
	}
	
	public Equipo setAlarmaEnEjecucion(Alarma alarmaEnEjecucion) throws Exception {
		if (alarmaEnEjecucion != null && !GestorAlarmas.getInstancia().esAlarmaEnEjecucion(alarmaEnEjecucion.getIdAlarma()))
			throw new Exception("La alarma indicada no esta siendo ejecutada");
		this.alarmaEnEjecucion = alarmaEnEjecucion;
		return this;
	}
	
	public Equipo addMiembro(UsuarioRegistrado miembro) throws Exception {
		if (miembro == null || !miembro.ayudaEnEmergencias()) throw new Exception("El nuevo miembro no es valido");
		miembro.getPersonalEquipo().setEquipo(this);
		this.miembros.add(miembro);
		return this;
	}
	
	public Equipo quitarMiembro(UsuarioRegistrado miembro) throws Exception{
		if (miembro == null) throw new Exception("El usuario seleccionado no existe");
		if (!miembros.contains(miembro)) throw new Exception("El usuario seleccionado no forma parte del equipo");
		if (miembros.size() - 1 < 1) throw new Exception("No se puede eliminar el miembro: el equipo no puede quedar vacio");
		
		miembros.remove(miembro);
		miembro.getPersonalEquipo().setEquipo(null);
		return this;
	}
	
	public Equipo borrarDatosEquipo() throws Exception {
		for (UsuarioRegistrado u : miembros) {
			miembros.remove(u);
			u.getPersonalEquipo().setEquipo(null);
		}
		return this;
	}
	
	public String getIdEquipo() {
		return idEquipo;
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
}

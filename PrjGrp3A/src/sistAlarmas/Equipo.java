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
	
	public Equipo gestionarAlarma() {
		
		for (Accion accion : accionesEnEjecucion) {
			Verificacion verif = new Verificacion();
			verif.setIdVerif("aaaa");
			verif.setMensaje("Verificacion de accion");
			verif.set
		}
		
		List<Verificacion> verificaciones = accionesEnEjecucion.stream()
				.map(accion -> new Verificacion(accion))
				.map(verif -> verif.setEmisor(this))
				.collect(Collectors.toList());
		
		Verificacion verifFinal = new Verificacion("??", "Alarma gestionada");
		verifFinal.setAlarma(alarmaEnEjecucion);
		verifFinal.setEmisor(this);
		verificaciones.add(verifFinal);
		
		accionesEnEjecucion.clear();
		
		GestorEquipos ge = GestorEquipos.getInstancia();
		verificaciones.forEach(verif -> ge.recibirVerificacion(this, verif));
		
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
	
	public void setAlarmaEnEjecucion(Alarma alarmaEnEjecucion) {
		this.alarmaEnEjecucion = alarmaEnEjecucion;
	}


	
	public Equipo addMiembro(UsuarioRegistrado miembro) {
		if (miembro == null || !miembro.ayudaEnEmergencias()) return null;
		this.miembros.add(miembro);
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

package sistAlarmas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Equipo {
	private final String idEquipo;
	private List<String> responsabilidades;
	private String transportePrimario;
	private String descripcion;
	
	private Alarma alarmaEnEjecucion;
	private List<Accion> accionesEnEjecucion;
	private List<UsuarioRegistrado> miembros;
	
	public Equipo(String idEquipo){
		this.idEquipo = idEquipo;
		miembros = new ArrayList<>();
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
		
		List<Verificacion> verificaciones = accionesEnEjecucion.stream()
				.map(accion -> new Verificacion(accion))
				.map(verif -> verif.setEmisor(this))
				.collect(Collectors.toList());
		
		Verificacion verifFinal = new Verificacion("??", "Alarma gestionada");
		verifFinal.setAlarma(alarmaEnEjecucion);
		verifFinal.setEmisor(this);
		verificaciones.add(verifFinal);
		
		accionesEnEjecucion.clear();
		
		GestorEquipos.getInstancia().recibirVerificacion();
		
		return this;
	}
	
	public boolean estaOcupado() {
		return alarmaEnEjecucion != null || !accionesEnEjecucion.isEmpty();
	}

	public List<String> getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(List<String> responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public String getTransportePrimario() {
		return transportePrimario;
	}

	public void setTransportePrimario(String transportePrimario) {
		this.transportePrimario = transportePrimario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Alarma getAlarmaEnEjecucion() {
		return alarmaEnEjecucion;
	}

	public void setAlarmaEnEjecucion(Alarma alarmaEnEjecucion) {
		this.alarmaEnEjecucion = alarmaEnEjecucion;
	}

	public List<UsuarioRegistrado> getMiembros() {
		return miembros;
	}

	public void setMiembros(List<UsuarioRegistrado> miembros) {
		this.miembros = miembros;
	}

	public String getIdEquipo() {
		return idEquipo;
	}
	
	public Equipo addMiembro(UsuarioRegistrado miembro) {
		if (miembro == null || !miembro.ayudaEnEmergencias()) return null;
		this.miembros.add(miembro);
		return this;
	}
}

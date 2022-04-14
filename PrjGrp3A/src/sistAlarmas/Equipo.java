package sistAlarmas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipo {
	private final String idEquipo;
	private List<String> responsabilidades;
	private String transportePrimario;
	private String descripcion;
	
	private Alarma alarmaEnEjecucion;
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
	
	public boolean estaOcupado() {
		return alarmaEnEjecucion != null;
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

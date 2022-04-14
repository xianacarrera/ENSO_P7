package sistAlarmas;

import java.util.Objects;

public class Protocolo {

	private final String idProtocolo;
	private final String nombre;
	private final TipoAlarma tipo;
	private final String localizacion;
	private String rol;
	private String descripcion;
	
	public Protocolo(String id, String nombre, TipoAlarma tipo, String localizacion, String descripcion) throws Exception{
		// Comprobar id con ItfIdChecker
		if (nombre == null || tipo == null || descripcion == null) throw new Exception("Protocolo no valido");
		// El tipo no puede ser nulo
		
		this.idProtocolo = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.localizacion = localizacion;
		this.descripcion = descripcion;
	}

	public Protocolo(String id, String nombre, TipoAlarma tipo, String descripcion) throws Exception{
		// Comprobar id con ItfIdChecker
		if (nombre == null || tipo == null || descripcion == null) throw new Exception("Protocolo no valido");
		// El tipo no puede ser nulo
		
		this.idProtocolo = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.localizacion = null;
		this.descripcion = descripcion;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(idProtocolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Protocolo))
			return false;
		Protocolo other = (Protocolo) obj;
		return Objects.equals(idProtocolo, other.idProtocolo);
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdProtocolo() {
		return idProtocolo;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoAlarma getTipo() {
		return tipo;
	}

	public String getLocalizacion() {
		return localizacion;
	}

}

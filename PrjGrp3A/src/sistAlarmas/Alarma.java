package sistAlarmas;

import java.util.Date;
import java.util.Objects;

public class Alarma {
	private final String idAlarma;
	private final Date fechaIncidencia;
	private Date fechaCierre;
	private Centro centro;
	private String zona;
	private Float valorActivacion;
	private TipoAlarma tipoAlarma;
	
	public Alarma(String idAlarma, TipoAlarma tipoAlarma) throws Exception {
		if (!ItfIdChecker.checkIdAlarma(idAlarma)) throw new Exception("Identificador de alarma no valido");
		if (tipoAlarma == null) throw new Exception("Tipo de alarma no valido");
		
		this.idAlarma = idAlarma;
		this.fechaIncidencia = new Date(System.currentTimeMillis());
		this.tipoAlarma = tipoAlarma;
	}
	
	public Alarma(String idAlarma, TipoAlarma tipoAlarma, Centro centro, String zona) throws Exception {
		if (!ItfIdChecker.checkIdAlarma(idAlarma)) throw new Exception("Identificador de alarma no valido");
		if (tipoAlarma == null) throw new Exception("Tipo de alarma no valido");
		
		// Comprobamos que el centro est� registrado

		
		this.idAlarma = idAlarma;
		this.fechaIncidencia = new Date(System.currentTimeMillis());
		if (setTipoAlarma(tipoAlarma) == null) throw new Exception("Tipo de alarma no valido");
		if (centro != null)	{
			// Comprobamos que el centro es v�lido y lo guardamos si es as�
			if (setCentro(centro) == null) throw new Exception("Centro no valido");
		}
		setZona(zona);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAlarma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Alarma))
			return false;
		Alarma other = (Alarma) obj;
		return Objects.equals(idAlarma, other.idAlarma);
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public Alarma setFechaCierre(Date fechaCierre) {
		// La fecha de cierre, una vez establecida, es definitiva
		if (fechaCierre == null || this.fechaCierre != null) return null;
		if (fechaCierre.getTime() <= this.fechaIncidencia.getTime()) return null;
		
		this.fechaCierre = fechaCierre;
		return this;
	}
	
	public Centro getCentro() {
		return this.centro;
	}
	
	public Alarma setCentro(Centro centro) {
		if (centro == null) return null;
		// Comprobamos que el centro est� registrado
		if (!centro.equals(GestorCentros.getInstancia().leerCentro(centro.getIdCentro()))) return null;
		this.centro = centro;
		return this;
		
	}

	public String getZona() {
		return zona;
	}

	public Alarma setZona(String zona) {
		this.zona = zona;
		return this;
	}

	public Float getValorActivacion() {
		return valorActivacion;
	}

	public Alarma setValorActivacion(Float valorActivacion) {
		// El valor de activaci�n, una vez establecido, es definitivo
		if (this.valorActivacion != null) return null;
		
		// Comprobamos que el valor de activaci�n est� dentro de los l�mites v�lidos
		this.valorActivacion = valorActivacion;
		return this;
	}
	
	public TipoAlarma getTipoAlarma() {
		return tipoAlarma;
	}
	
	public Alarma setTipoAlarma(TipoAlarma tipoAlarma) {
		if (tipoAlarma == null) return null;
		this.tipoAlarma = tipoAlarma;
		return this;
	}

	public String getIdAlarma() {
		return idAlarma;
	}

	public Date getFechaIncidencia() {
		return fechaIncidencia;
	}
}

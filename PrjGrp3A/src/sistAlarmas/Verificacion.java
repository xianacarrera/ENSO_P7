package sistAlarmas;

import java.util.Objects;

public class Verificacion {
	private final String idVerif;
	private final String mensaje;
	private boolean recibida = false;
	
	private Accion accion;
	private Alarma alarma;
	private Equipo emisor;
	
	public Verificacion(String idVerif, String mensaje) {
		this.idVerif = idVerif;
		this.mensaje = mensaje;
	}
	
	public Verificacion(Accion accion) {
		this.idVerif = "???";
		this.mensaje = accion.getMensaje();
		this.alarma = accion.getAlarma();
		this.accion = accion;
	}

	public boolean isRecibida() {
		return recibida;
	}

	public void setRecibida(boolean recibida) {
		this.recibida = recibida;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Alarma getAlarma() {
		return alarma;
	}

	public void setAlarma(Alarma alarma) {
		this.alarma = alarma;
	}

	public Equipo getEmisor() {
		return emisor;
	}

	public Verificacion setEmisor(Equipo emisor) {
		this.emisor = emisor;
		return this;
	}

	public String getIdVerif() {
		return idVerif;
	}

	public String getMensaje() {
		return mensaje;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idVerif);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Verificacion))
			return false;
		Verificacion other = (Verificacion) obj;
		return Objects.equals(idVerif, other.idVerif);
	}
	
}

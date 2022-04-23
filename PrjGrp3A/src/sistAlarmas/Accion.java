package sistAlarmas;

import java.util.Objects;

public class Accion {
	private final String idAccion;
	private final String mensaje;
	private boolean recibida;
	
	private Alarma alarma;
	private Equipo destinatario;
	
	public Accion(String idAccion, String mensaje) throws Exception {
		if (!ItfIdChecker.checkIdAccion(idAccion)) 
			throw new Exception("Identificador no válido");
		this.idAccion = idAccion;
		this.mensaje = mensaje;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Accion))
			return false;
		Accion other = (Accion) obj;
		return Objects.equals(idAccion, other.idAccion);
	}

	public boolean isRecibida() {
		return recibida;
	}

	public Accion recibir() {
		// Una alarma recibida no puede "desrecibirse"
		if (this.recibida == true) return null;
		this.recibida = true;
		return this;
	}

	public Alarma getAlarma() {
		return alarma;
	}

	public Accion setAlarma(Alarma alarma) {
		// Solo se puede asignar una alarma una vez
		if (alarma == null || this.alarma != null) return null;
		this.alarma = alarma;
		return this;
	}

	public Equipo getDestinatario() {
		return destinatario;
	}

	public Accion setDestinatario(Equipo destinatario) {
		// Solo se puede asignar un destinatario una vez
		if (destinatario == null || this.destinatario != null) return null;
		this.destinatario = destinatario;
		return this;
	}

	public String getIdAccion() {
		return idAccion;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	
}

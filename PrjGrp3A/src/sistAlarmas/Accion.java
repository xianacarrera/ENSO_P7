package sistAlarmas;

import java.util.Objects;

public class Accion {
	private String idAccion;
	private String mensaje;
	private boolean recibida;
	
	private Alarma alarma;
	private Equipo destinatario;
	
	public Accion() {}
	
	public Accion setIdAccion(String idAccion) throws Exception {
		if (this.idAccion != null) throw new Exception("Esta accion ya tiene un identificador");
		if (!ItfGestorId.checkIdAccion(idAccion)) throw new Exception("Identificador de accion no valido");
		this.idAccion = idAccion;
		return this;
	}
	
	public Accion setMensaje(String mensaje) throws Exception {
		if (this.mensaje != null) throw new Exception("Esta accion ya tiene un mensaje");
		if (mensaje == null) throw new Exception("Mensaje no valido");
		this.mensaje = mensaje;
		return this;
	}

	public Accion recibir() throws Exception {
		// Una alarma recibida no puede "desrecibirse"
		if (this.recibida) throw new Exception("La acción ya había sido recibida");
		this.recibida = true;
		return this;
	}

	public Accion setAlarma(Alarma alarma) throws Exception {
		if (this.alarma != null) throw new Exception("Esta accion ya esta relacionada con una alarma");
		if (alarma == null) throw new Exception("Alarma inexistente");
		if (!GestorAlarmas.getInstancia().esAlarmaEnEjecucion(alarma.getIdAlarma())) throw new Exception("La alarma no se esta ejecutando");
		
		this.alarma = alarma;
		return this;
	}
	
	public Accion setDestinatario(Equipo destinatario) throws Exception {
		if (this.destinatario != null) throw new Exception("Esta accion ya tenia un destinatario");
		if (destinatario == null) throw new Exception("Equipo destinario inexistente");
		if (!GestorEquipos.getInstancia().esEquipoRegistrado(destinatario.getIdEquipo())) throw new Exception("El equipo no ha sido registrado");
		
		this.destinatario = destinatario;
		return this;
	}


	public String getIdAccion() {
		return idAccion;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public boolean isRecibida() {
		return recibida;
	}
	
	public Alarma getAlarma() {
		return alarma;
	}

	public Equipo getDestinatario() {
		return destinatario;
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
}

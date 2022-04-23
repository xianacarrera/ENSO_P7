package sistAlarmas;

import java.util.Objects;

public class Verificacion {
	private String idVerif;
	private String mensaje;
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

	public Verificacion recibir() throws Exception {
		if (this.recibida) throw new Exception("La verificacion ya habia sido recibida");
		this.recibida = true;
		return this;
	}

	public Accion getAccion() {
		return accion;
	}

	public Verificacion setAccion(Accion accion) throws Exception {
		if (accion == null) throw new Exception("Accion no valida: es nula");
		// La alarma de esta verificacion debe haber 
		if (accion.getAlarma().equals(this.alarma)) throw new Exception
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
	
	public Verificacion setIdVerif(String idVerif) throws Exception{
		if (!ItfIdChecker.checkIdVerificacion(idVerif)) throw new Exception("Identificador de verificacion no valido");
		this.idVerif = idVerif;
		return this;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public Verificacion setMensaje(String mensaje) {
		if (mensaje == null) throw new Exception("Mensaje de verificacion no valido: es nulo");
		this.mensaje = mensaje;
		return this;
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

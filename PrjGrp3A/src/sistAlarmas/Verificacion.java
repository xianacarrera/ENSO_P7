package sistAlarmas;

import java.util.Objects;

public class Verificacion {
	private String idVerif;
	private String mensaje;
	private boolean recibida = false;
	
	private Accion accion;
	private Alarma alarma;
	private Equipo emisor;
	
	public Verificacion() {}

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
		
		// Si la alarma de esta 
		if (this.alarma != null) {
			if (!this.alarma.equals(accion.getAlarma())) 
				throw new Exception("La alarma asociada a la accion no coincide con la de la verificacion");
		} else {
			if (accion.getAlarma() != null) this.alarma = accion.getAlarma();
		}
		
		this.accion = accion;
		
		return this;
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
		if (!ItfGestorId.checkIdVerificacion(idVerif)) throw new Exception("Identificador de verificacion no valido");
		this.idVerif = idVerif;
		return this;
	}

	public String getMensaje() {
		return mensaje;
	}
	
	public Verificacion setMensaje(String mensaje) throws Exception {
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

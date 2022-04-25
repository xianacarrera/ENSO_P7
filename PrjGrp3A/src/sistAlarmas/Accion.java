package sistAlarmas;

import java.util.Objects;

public class Accion implements ItfAccion {
	//Declaracion de variables
	private String idAccion;
	private String mensaje;
	private boolean recibida;
	private Alarma alarma;
	private Equipo destinatario;

	//Constructor
	public Accion() {}
	
	//Metodo para determinar si la accion ha llegado a su destinatario
	@Override
	public boolean esRecibida() {
		return this.recibida;
	}
	
	//Metodo para establecer una accion como recibida
	@Override
	public ItfAccion recibir() throws Exception {
		// Una alarma recibida no puede "desrecibirse"
		// Las comprobaciones de que el emisor, la alarma, etc. esten bien establecidos se consideran precondiciones
		// del metodo y quedan bajo gestion del programador
		if (this.recibida) throw new Exception("La accion ya habia sido recibida");
		this.recibida = true;
		return this;
	}

	//Metodo para establecer el id de una accion
	public Accion setIdAccion(String idAccion) throws Exception {
		if (this.idAccion != null) throw new Exception("Esta accion ya tiene un identificador");
		if (!ItfGestorId.checkIdAccion(idAccion)) throw new Exception("Identificador de accion no valido");
		this.idAccion = idAccion;
		return this;
	}

	//Metodo para establecer el mensaje de una accion
	public Accion setMensaje(String mensaje) throws Exception {
		// El mensaje de una accion tiene caracter definitivo y no se puede sobreescribir
		if (this.mensaje != null) throw new Exception("Esta accion ya tiene un mensaje");
		if (mensaje == null) throw new Exception("Mensaje no valido");
		this.mensaje = mensaje;
		return this;
	}

	//Metodo para relacionar una accion con una alarma que se esta ejecutando
	public Accion setAlarma(Alarma alarma) throws Exception {
		if (this.alarma != null) throw new Exception("Esta accion ya esta relacionada con una alarma");
		if (alarma == null) throw new Exception("Alarma inexistente");
		// Comprobamos si la alarma esta incluida dentro de las alarmas en ejecucion que guarda el GestorAlarmas
		if (!GestorAlarmas.getInstancia().esAlarmaEnEjecucion(alarma.getIdAlarma())) throw new Exception("La alarma no se esta ejecutando");
		
		this.alarma = alarma;
		return this;
	}

	//Metodo para relacionae una accion con un equipo registrado
	public Accion setDestinatario(Equipo destinatario) throws Exception {
		// Un destinatario solamente se puede establecer una vez
		if (this.destinatario != null) throw new Exception("Esta accion ya tenia un destinatario");
		if (destinatario == null) throw new Exception("Equipo destinario inexistente");
		// El equipo debe haber sido introducido oficialmente en el sistema
		if (!GestorEquipos.getInstancia().esEquipoRegistrado(destinatario.getIdEquipo())) throw new Exception("El equipo no ha sido registrado");
		
		this.destinatario = destinatario;
		return this;
	}

	/** GETTERS **/
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
	/** FIN GETTERS **/

	/** SOBREESCRITURA DE METODOS **/
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
	/** FIN SOBREESCRITURA DE METODOS **/
}

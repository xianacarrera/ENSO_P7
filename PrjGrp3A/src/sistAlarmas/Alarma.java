package sistAlarmas;

import java.util.Date;
import java.util.Objects;

public class Alarma {

	//Declaracion de variables
	private String idAlarma;
	private Date fechaIncidencia;		// Se inicializa autom�ticamente cuando se crea la alarma
	private Date fechaCierre;			// No se puede modificar manualmente, se cambia al transicionar al estado DESACTIVADA
	private Centro centro;
	private String zona;
	private Float valorActivacion;
	private TipoAlarma tipoAlarma;
	private EstadoAlarma estado;

	// Constructor
	public Alarma() {
		this.fechaIncidencia = new Date(System.currentTimeMillis());
	}

	//Metodo para establecer el id de la alarma
	public Alarma setIdAlarma(String idAlarma) throws Exception {
		if (this.idAlarma != null) throw new Exception("Esta alarma ya tiene un identificador");
		if (!ItfGestorId.checkIdAlarma(idAlarma)) throw new Exception("Identificador de alarma no valido");
		this.idAlarma = idAlarma;
		return this;
	}

	//Metodo para establecer el centro vinculado a una alarma. Este ha de estar registrado
	public Alarma setCentro(Centro centro) throws Exception {
		if (this.centro != null) throw new Exception("Esta alarma ya esta vinculada a un centro");
		if (centro != null)
			if (!GestorCentros.getInstancia().esCentroRegistrado(centro.getIdCentro())) throw new Exception("El centro no esta registrado");
		
		this.centro = centro;
		return this;
	}

	//Metodo para establecer la zona vinculada a una alarma.
	public Alarma setZona(String zona) throws Exception {
		if (this.zona != null) throw new Exception("Ya se ha registrado una zona para esta alarma");
		
		this.zona = zona;
		return this;
	}

	//Metodo para establecer el valor de activacion de la alarma siempre que esta tenga tipo y no sea manual
	public Alarma setValorActivacion(Float valorActivacion) throws Exception {
		// El valor de activaci�n, una vez establecido, es definitivo
		if (this.valorActivacion != null) throw new Exception("Ya se ha registrado un valor de activacion para esta alarma");
		if (this.tipoAlarma == null) throw new Exception("Esta alarma aun no tiene un tipo");
		switch (this.tipoAlarma) {
			case MANUAL: throw new Exception("Una alarma manual no puede tener un valor de activacion");
			case INTRUSOS:
				if (!(Float.compare((float) 1.0, valorActivacion) == 0)) 
					throw new Exception("Para una alarma de intrusos, el valor de activacion tiene que ser 1");
				break;
			default:
		}
		
		this.valorActivacion = valorActivacion;
		return this;
	}

	//Metodo para establecer el tipo de alarma
	public Alarma setTipoAlarma(TipoAlarma tipoAlarma) throws Exception {
		if (this.tipoAlarma != null) throw new Exception("Esta alarma ya tiene un tipo");
		if (tipoAlarma == null) throw new Exception("Tipo de alarma no valido: es inexistente");
		this.tipoAlarma = tipoAlarma;
		return this;
	}

	//Metodo para establecer la fecha de cierre de la alarma
	private void setFechaCierre() {
		// La fecha de cierre, una vez establecida, es definitiva
		this.fechaCierre = new Date(System.currentTimeMillis());
	}

	/* Metodo para establecer el estado de la alarma:
	 * El nuevo estado debe ser diferente al estado actual
	 * La transicion debe ser valida
	 * */
	public Alarma setEstadoAlarma(EstadoAlarma estado) throws Exception {
		if (estado == null) throw new Exception("Estado no valido: es inexistente");
		if (estado.equals(this.estado)) throw new Exception("El nuevo estado es igual al estado en el que se encuentra la alarma");
		switch (estado) {
			case CREADA:
				if (this.estado != null) throw new Exception("La transicion al estado CREADA es imposible");
				break;
			case ENEJECUCION:
				if (this.estado != EstadoAlarma.CREADA) throw new Exception("La transicion al estado ENEJECUCION es imposible");
				break;
			case DESACTIVADA:
				if (this.estado != EstadoAlarma.ENEJECUCION) throw new Exception("La transicion al estado DESACTIVADA es imposible");
				setFechaCierre();
				break;
			default:
		}
		this.estado = estado;
		return this;
	}
	

	/** Getters **/
	public String getIdAlarma() {
		return idAlarma;
	}
	
	public TipoAlarma getTipoAlarma() {
		return tipoAlarma;
	}

	public Date getFechaIncidencia() {
		return fechaIncidencia;
	}
	
	public Date getFechaCierre() {
		return fechaCierre;
	}
	
	public String getZona() {
		return zona;
	}
	
	public Float getValorActivacion() {
		return valorActivacion;
	}
	
	public Centro getCentro() {
		return this.centro;
	}
	
	public EstadoAlarma getEstado() {
		return this.estado;
	}
	/** Fin getters **/

	/** Sobreescritura de metodos **/
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
	/** Fin sobreescritura de metodos **/
}

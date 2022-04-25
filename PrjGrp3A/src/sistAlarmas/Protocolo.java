package sistAlarmas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Protocolo implements ItfProtocolo {

	//Declaración de variables
	private String idProtocolo;
	private String nombre;
	private TipoAlarma tipo;
	private String localizacion;
	private String rol;
	private String descripcion;
	
	private List<String> mensajesAcciones;

	//Constructor
	public Protocolo() {
		mensajesAcciones = new ArrayList<>();
	}

	//Mtoédo para añadir un mensaje de acción a un protocolo
	@Override
	public Protocolo addMensajeAccion(String msg) throws Exception {
		if (msg == null) throw new Exception("El mensaje de la accion no puede ser nulo");
		if (mensajesAcciones.contains(msg)) throw new Exception("El protocolo ya habia regitrado este mensaje");
		mensajesAcciones.add(msg);
		return this;
	}

	//Método para eliminar un mensaje de acción de un protocolo
	@Override
	public Protocolo borrarMensajeAccion(String msg) throws Exception {
		if (msg == null) throw new Exception("El mensaje de la accion no puede ser nulo");
		if (!mensajesAcciones.contains(msg)) throw new Exception("El protocolo no tiene regitrado este mensaje");
		
		mensajesAcciones.remove(msg);
		return this;
	}

	//Método para establecer el id de un protocolo
	public Protocolo setIdProtocolo(String idProt) throws Exception {
		if (this.idProtocolo != null) throw new Exception("Este protocolo ya tiene un identificador");
		if (!ItfGestorId.checkIdProtocolo(idProt)) throw new Exception("Identificador de protocolo no valido");
		this.idProtocolo = idProt;
		return this;
	}

	//Método para establecer el nombre de un protocolo
	public Protocolo setNombre(String nombre) throws Exception {
		if (nombre == null) throw new Exception("Nombre no valido: es inexistente");
		if (this.nombre != null) throw new Exception("Este protocolo ya tiene un nombre");
		this.nombre = nombre;
		return this;
	}

	//Método para establecer el tipo de alarma implicada en un protocolo
	public Protocolo setTipoAlarma(TipoAlarma tipo) throws Exception {
		if (tipo == null) throw new Exception("Tipo de alarma no valido: es inexistente");
		if (this.tipo != null) throw new Exception("Este protocolo ya tiene un tipo de alarma asignado"); 
		this.tipo = tipo;
		return this;
	}

	//Método para establecer la localización de un protocolo
	public Protocolo setLocalizacion(String loc) throws Exception {
		if (this.localizacion != null) throw new Exception("Este protocolo ya tiene una localizacion");
		this.localizacion = loc;
		return this;
	}

	//Método para establecer el rol de un protocolo
	public Protocolo setRol(String rol) {
		this.rol = rol;
		return this;
	}

	//Método para establecer la descripcion de un protocolo
	public Protocolo setDescripcion(String desc) {
		this.descripcion = desc;
		return this;
	}

	/**GETTERS**/
	public String getRol() {
		return rol;
	}

	public String getDescripcion() {
		return descripcion;
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
	
	public List<String> getMensajesAcciones(){
		return mensajesAcciones;
	}
	/**fin GETTERS**/

	/**Sobreescritura de métodos**/
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
	/**fin sobreescritura de métodos**/
}

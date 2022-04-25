package sistAlarmas;

public class PersonalEquipo {
	//Declaracion de variables
	private String capacitacion;
	private String nivelFormacion;
	
	private UsuarioRegistrado usuario;
	private Equipo equipo;

	//Constructor
	public PersonalEquipo(){}

	//Método para a�adir un usuario al personal de un equipo de emergencias
	public PersonalEquipo setUsuarioRegistrado(UsuarioRegistrado usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido: no existe");
		if (!GestorUsuarios.getInstancia().existeUsuario(usuario.getIdUsuario())) throw new Exception("No hay datos almacenados del usuario");
		if (usuario.ayudaEnEmergencias()) throw new Exception("El usuario ya es personal de emergencias");
		if (this.usuario != null) throw new Exception("Existe un vinculo previo con un usuario registrado");
		this.usuario = usuario;
		return this;
	}

	//M�todo para establecer el nivel de formaci�n del personal de un equipo de emergencias
	public PersonalEquipo setNivelFormacion(String nivelFormacion) throws Exception {
		if (nivelFormacion == null) throw new Exception("Nivel de formacion no valido: no existe");
		this.nivelFormacion = nivelFormacion;
		return this;
	}

	//M�todo para a�adir la capacitaci�n del personal de un equipo de emergencias
	public PersonalEquipo setCapacitacion(String capacitacion) throws Exception {
		if (capacitacion == null) throw new Exception("Capacitacion no valida: no existe");
		this.capacitacion = capacitacion;
		return this;
	}

	//M�todo para a�adir un usuario a un equipo de emergencias
	public PersonalEquipo setEquipo(Equipo equipo) throws Exception {
		if (equipo != null) {
			if (!estaDisponible()) throw new Exception("El usuario ya pertenece a un equipo");
			// El nivel de formacion del usuario no puede ser insuficiente si quiere pertenecer a un equipo
			if ("Insuficiente".equals(this.nivelFormacion)) throw new Exception("El usuario no tiene el nivel de formacion suficiente");
			if (this.capacitacion == null) throw new Exception("El usuario no esta capacitado");
			if (this.nivelFormacion == null) throw new Exception("El usuario no tiene formacion");
		}

		this.equipo = equipo;
		return this;
	}
	

	public boolean estaDisponible() {
		return this.equipo == null;
	}

	/**GETTERS**/
	public String getCapacitacion() {
		return capacitacion;
	}

	public String getNivelFormacion() {
		return nivelFormacion;
	}
	
	public Equipo getEquipo() {
		return this.equipo;
	}

	/** FIN GETTERS**/
}

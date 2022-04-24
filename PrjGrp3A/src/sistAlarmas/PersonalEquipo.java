package sistAlarmas;

//Si ten�is dudas de por qu� esta clase extiende/no extiende a
//UsuarioRegistrado, preguntadle a Xiana, que no escribe aqu�
//la justificaci�n porque est� cambiando de opini�n al respecto
//cada 5 minutos :)
public class PersonalEquipo {
	//Declaración de variables
	private String capacitacion;
	private String nivelFormacion;
	
	private UsuarioRegistrado usuario;
	private Equipo equipo;

	//Constructor
	public PersonalEquipo(){}

	//Método para añadir un usuario al personal de un equipo de emergencias
	public PersonalEquipo setUsuarioRegistrado(UsuarioRegistrado usuario) throws Exception {
		if (usuario == null) throw new Exception("Usuario no valido: no existe");
		if (!GestorUsuarios.getInstancia().existeUsuario(usuario.getIdUsuario())) throw new Exception("No hay datos almacenados del usuario");
		if (usuario.ayudaEnEmergencias()) throw new Exception("El usuario ya es personal de emergencias");
		if (this.usuario != null) throw new Exception("Existe un vinculo previo con un usuario registrado");
		this.usuario = usuario;
		return this;
	}

	//Método para establecer el nivel de formación del personal de un equipo de emergencias
	public PersonalEquipo setNivelFormacion(String nivelFormacion) throws Exception {
		if (nivelFormacion == null) throw new Exception("Nivel de formacion no valido: no existe");
		this.nivelFormacion = nivelFormacion;
		return this;
	}

	//Método para añadir la capacitación del personal de un equipo de emergencias
	public PersonalEquipo setCapacitacion(String capacitacion) throws Exception {
		if (capacitacion == null) throw new Exception("Capacitacion no valida: no existe");
		this.capacitacion = capacitacion;
		return this;
	}

	//Método para añadir un usuario a un equipo de emergencias
	public PersonalEquipo setEquipo(Equipo equipo) throws Exception {
		if (equipo == null) throw new Exception("El equipo no es valido: no existe");
		if (!estaDisponible()) throw new Exception("El usuario ya pertenece a un equipo");
		if (!GestorEquipos.getInstancia().esEquipoRegistrado(equipo.getIdEquipo())) throw new Exception("El equipo no esta registrado");
		if ("Insuficiente".equals(this.nivelFormacion)) throw new Exception("El usuario no tiene el nivel de formacion suficiente");
		if (this.capacitacion == null) throw new Exception("El usuario no esta capacitado");
		if (this.nivelFormacion == null) throw new Exception("El usuario no tiene formacion");

		this.equipo = equipo;
		return this;
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
	
	public boolean estaDisponible() {
		return this.equipo != null;
	}
	/** FIN GETTERS**/
}

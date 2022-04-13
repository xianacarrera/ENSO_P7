package sistAlarmas;

//Si tenéis dudas de por qué esta clase extiende/no extiende a
//UsuarioRegistrado, preguntadle a Xiana, que no escribe aquí
//la justificación porque está cambiando de opinión al respecto
//cada 5 minutos :)
public class PersonalEquipo {
	
	private String capacitacion;
	private Boolean disponible;
	private String nivelFormacion;
	
	private UsuarioRegistrado usuario;
	
	public PersonalEquipo(UsuarioRegistrado usuario){
		this.usuario = usuario;
	}

	public String getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(String capacitacion) {
		this.capacitacion = capacitacion;
	}

	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}

	public String getNivelFormacion() {
		return nivelFormacion;
	}

	public void setNivelFormacion(String nivelFormacion) {
		this.nivelFormacion = nivelFormacion;
	}
}

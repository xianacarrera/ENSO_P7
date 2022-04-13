package sistAlarmas;

import java.util.Objects;

public class Usuario {
	private String idUsuario;
	//private Centro centroActual;
	
	public Usuario(){}
	
	public Usuario(String idUsuario) {
		this.idUsuario = idUsuario;
		//this.centroActual = centroActual;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario);
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	protected void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
/*
	public Centro getCentroActual() {
		return centroActual;
	}

	public void setCentroActual(Centro centroActual) {
		this.centroActual = centroActual;
	}*/
	
}

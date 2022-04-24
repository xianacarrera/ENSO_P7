package sistAlarmas;

import java.util.Objects;

public class Usuario {
	private String idUsuario;
	private Centro centroActual;
	
	public Usuario(){}
	
	public Usuario setIdUsuario(String idUsuario) throws Exception{
		if (this.idUsuario != null) throw new Exception("Este usuario ya tiene un identificador");
		if (!ItfGestorId.checkIdAccion(idUsuario)) throw new Exception("Identificador de usuario no valido");
		this.idUsuario = idUsuario;
		return this;
	}
	
	public Usuario setCentroActual(Centro centroActual) throws Exception {
		if (centroActual == null) throw new Exception("Centro no valido: no existe");
		if (!GestorCentros.getInstancia().esCentroRegistrado(centroActual.getIdCentro())) throw new Exception("Centro no valido: no esta registrado");

		this.centroActual = centroActual;
		return this;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public Centro getCentroActual() {
		return centroActual;
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
}

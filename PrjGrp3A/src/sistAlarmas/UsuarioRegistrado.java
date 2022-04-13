package sistAlarmas;

public class UsuarioRegistrado extends Usuario {
	private String nombrePropio;
	private String apellido1;
	private String apellido2;
	private String DNI;
	private int telefono;
	private String correo;
	private String zona;
	
	private PersonalEquipo personalEquipo;
	private Admin admin;
	
	protected UsuarioRegistrado() {}

	public String getNombrePropio() {
		return nombrePropio;
	}

	public void setNombrePropio(String nombrePropio) {
		this.nombrePropio = nombrePropio;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}
	
	public boolean ayudaEnEmergencias() {
		return this.personalEquipo != null;
	}
	
	public boolean esAdmin() {
		return this.admin != null;
	}
	
	public UsuarioRegistrado volverAdmin() {
		try {
			this.admin = new Admin(this);
		} catch(Exception eo) {
			return null;
		}
		return this;
	}
	
	public UsuarioRegistrado desactivarAdmin(){
		this.admin = null;
		return this;
	}
	
	public UsuarioRegistrado volverPersonalEquipo() {
		this.personalEquipo = new PersonalEquipo(this);
		return this;
	}
	
	public UsuarioRegistrado desactivarPersonalEquipo() {
		this.personalEquipo = null;
		return this;
	}

	public static class Builder {
		private UsuarioRegistrado user;
		
		public Builder() {
			this.reset();
		}
		
		public Builder reset() {
			user = new UsuarioRegistrado();
			return this;
		}
		
		public Builder fromUsuario(Usuario usuario){
			if (usuario == null) return null;
			if (user == null) this.reset();
			user.setIdUsuario(usuario.getIdUsuario());
			//user.setCentroActual(usuario.getCentroActual());
			return this;
		}
		
		public Builder setIdUsuario(String idUsuario){
			if (user == null) this.reset();
			user.setIdUsuario(idUsuario);
			return this;
		}
		/*
		public Builder setCentroActual(Centro centroActual){
			if (user == null) this.reset();
			user.setCentroActual(centroActual);
			return this;
		}*/
		
		public Builder setNombrePropio(String nombrePropio){
			if (user == null) this.reset();
			user.setNombrePropio(nombrePropio);
			return this;
		}
		
		public Builder setApellido1(String apellido1) {
			if (user == null) this.reset();
			user.setApellido1(apellido1);
			return this;
		}
		
		public Builder setApellido2(String apellido2) {
			if (user == null) this.reset();
			user.setApellido2(apellido2);
			return this;
		}
		
		public Builder setNombre(String nombrePropio, String apellido1, String apellido2) {
			if (user == null) this.reset();
			user.setNombrePropio(nombrePropio);
			user.setApellido1(apellido1);
			user.setApellido2(apellido2);
			return this;
		}
		
		public Builder setDNI(String DNI) {
			if (user == null) this.reset();
			user.setDNI(DNI);
			return this;
		}
		
		public Builder setTelefono(int tlf) {
			if (user == null) this.reset();
			user.setTelefono(tlf);
			return this;
		}
		
		public Builder setCorreo(String correo){
			if (user == null) this.reset();
			user.setCorreo(correo);
			return this;
		}
		
		public Builder setZona(String zona) {
			if (user == null) this.reset();
			user.setZona(zona);
			return this;
		}
		
		public UsuarioRegistrado build(){
			if (user == null || user.getIdUsuario() == null) return null;
			return user;
		}
	}
	
	
	
}

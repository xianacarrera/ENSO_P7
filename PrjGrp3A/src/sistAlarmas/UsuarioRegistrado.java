package sistAlarmas;

public class UsuarioRegistrado extends Usuario implements ItfUsuarioRegistrado {
	//Declaracion de variables
	private String nombrePropio;
	private String apellido1;
	private String apellido2;
	private String DNI;
	private int telefono;
	private String correo;
	private String zona;
	
	private PersonalEquipo personalEquipo;
	private Admin admin;

	//Constructor
	protected UsuarioRegistrado() {}

	//Método para establecer el nomre propio del usuario
	public ItfUsuarioRegistrado setNombrePropio(String nombrePropio) throws Exception {
		if (nombrePropio == null) throw new Exception("Nombre no valido: no existe");
		if (this.nombrePropio != null) throw new Exception("Este usuario ya tiene registrado un nombre");
		
		this.nombrePropio = nombrePropio;
		return this;
	}

	//Método para establecer el primer apellido del usuario
	public ItfUsuarioRegistrado setApellido1(String apellido1) throws Exception {
		if (apellido1 == null) throw new Exception("Primer apellido no valido: no existe");
		if (this.apellido1 != null) throw new Exception("Este usuario ya tiene registrado un primer apellido");
		
		this.apellido1 = apellido1;
		return this;
	}

	//Método para establecer el segundo apellido del usuario
	public ItfUsuarioRegistrado setApellido2(String apellido2) throws Exception {
		if (this.apellido2 != null) throw new Exception("Este usuario ya tiene registrado un segundo apellido");
		this.apellido2 = apellido2;
		return this;
	}

	//Método para establecer el DNI del usuario: comprueba que el DNI sea correcto
	public ItfUsuarioRegistrado setDNI(String DNI) throws Exception {
		if (DNI == null) throw new Exception("DNI no valido: no existe");
		if (this.DNI != null) throw new Exception("Este usuario ya tiene registrado un DNI");
		
		boolean esValido = true;
		String letraSegunResto = "TRWAGMYFPDXBNJZSQVHLCKE";

		if (DNI.length() != 9) esValido = false; //Comprueba que el DNI tenga 9 caracteres
		char ultimaLetra = DNI.charAt(8); //Almacena la letra del DNI
		if (!Character.isLetter(ultimaLetra) || !Character.isUpperCase(ultimaLetra)) esValido = false;
		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(DNI.charAt(i))) esValido = false; //Comprueba que los 8 primeros caracteres sean digitos
		}
		//Comprueba que la letra del DNI sea correcta
		if (letraSegunResto.charAt(Integer.parseInt(DNI.substring(0, 8)) % 23) != ultimaLetra) esValido = false;
		
		if (!esValido) throw new Exception("El DNI no sigue un formato valido");
		this.DNI = DNI;
		return this;
	}

	//Método para establecer el teléfono del usuario
	public ItfUsuarioRegistrado setTelefono(int telefono) throws Exception {
		if (String.valueOf(telefono).length() != 9) throw new Exception("Numero de telefono no valido");
		this.telefono = telefono;
		return this;
	}

	//Método para establecer el correo del usuario: comprueba que el correo sea correcto
	public ItfUsuarioRegistrado setCorreo(String correo) throws Exception {
		boolean esValido = true;
		if (!correo.contains("@") || !correo.contains(".")) esValido = false;
		int i = 0;
		while (esValido && i < correo.length()) {
			char c = correo.charAt(i);
			if (Character.isDigit(c) || Character.isLetter(c) || c == '_' || c == '-' || c == '.') continue;
			esValido = false;
			i++;	
		}
		if (!esValido) throw new Exception("El correo indicado no tiene un formato valido");
		this.correo = correo;
		return this;
	}

	//Método para establecer un usuario como administrador: comprueba que el usuario no sea administrador
	public ItfUsuarioRegistrado volverAdmin() throws Exception {
		if (this.esAdmin()) throw new Exception("Este usuario ya es administrador");
		this.admin = new Admin().setUsuarioRegistrado(this);
		return this;
	}

	//Método para eliminar un usuario como administrador: comprueba que el usuario sea administrador
	public ItfUsuarioRegistrado desactivarAdmin() throws Exception {
		if (!this.esAdmin()) throw new Exception("Este usuario no es un administrador");
		this.admin = null;
		return this;
	}

	//Método para establecer un usuario como personal de equipo: comprueba que el usuario no sea personal de equipo
	public ItfUsuarioRegistrado volverPersonalEquipo() throws Exception {
		if (this.ayudaEnEmergencias()) throw new Exception("Este usuario ya es personal de equipo");
		this.personalEquipo = new PersonalEquipo().setCapacitacion("Predeterminada").setNivelFormacion("Basico");
		return this;
	}

	//Método para eliminar un usuario como personal de equipo: comprueba que el usuario sea personal de equipo
	public ItfUsuarioRegistrado desactivarPersonalEquipo() throws Exception {
		if (!this.ayudaEnEmergencias()) throw new Exception("Este usuario no es personal de equipo");
		this.personalEquipo = null;
		return this;
	}


	/**OTROS SETTERS Y GETTERS**/
	public void setZona(String zona) {
		this.zona = zona;
	}
	
	public boolean ayudaEnEmergencias() {
		return this.personalEquipo != null;
	}
	
	public boolean esAdmin() {
		return this.admin != null;
	}
	
	public PersonalEquipo getPersonalEquipo() {
		return this.personalEquipo;
	}
	
	public String getNombrePropio() {
		return nombrePropio;
	}
	
	public String getApellido1() {
		return apellido1;
	}
	
	public String getApellido2() {
		return apellido2;
	}
	
	public String getDNI() {
		return DNI;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public int getTelefono() {
		return telefono;
	}
	
	public String getZona() {
		return zona;
	}

	/**fin de los setters y getters**/

	public static class Builder {
		private UsuarioRegistrado user;
		
		public Builder() {
			this.reset();
		}
		
		public Builder reset() {
			user = new UsuarioRegistrado();
			return this;
		}
		
		public Builder fromUsuario(Usuario usuario) throws Exception {
			if (usuario == null) throw new Exception("Usuario no valido: no existe");
			if (user == null) this.reset();
			user.setIdUsuario(usuario.getIdUsuario());
			user.setCentroActual(usuario.getCentroActual());
			return this;
		}
		
		public Builder setIdUsuario(String idUsuario) throws Exception {
			if (user == null) this.reset();
			user.setIdUsuario(idUsuario);
			return this;
		}
		
		public Builder setCentroActual(Centro centroActual) throws Exception {
			if (user == null) this.reset();
			user.setCentroActual(centroActual);
			return this;
		}
		
		public Builder setNombrePropio(String nombrePropio) throws Exception {
			if (user == null) this.reset();
			user.setNombrePropio(nombrePropio);
			return this;
		}
		
		public Builder setApellido1(String apellido1) throws Exception {
			if (user == null) this.reset();
			user.setApellido1(apellido1);
			return this;
		}
		
		public Builder setApellido2(String apellido2) throws Exception {
			if (user == null) this.reset();
			user.setApellido2(apellido2);
			return this;
		}
		
		public Builder setNombre(String nombrePropio, String apellido1, String apellido2) throws Exception {
			if (user == null) this.reset();
			user.setNombrePropio(nombrePropio);
			user.setApellido1(apellido1);
			user.setApellido2(apellido2);
			return this;
		}
		
		public Builder setDNI(String DNI) throws Exception {
			if (user == null) this.reset();
			user.setDNI(DNI);
			return this;
		}
		
		public Builder setTelefono(int tlf) throws Exception {
			if (user == null) this.reset();
			user.setTelefono(tlf);
			return this;
		}
		
		public Builder setCorreo(String correo) throws Exception {
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

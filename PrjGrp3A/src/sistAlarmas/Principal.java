package sistAlarmas;

/** Se trata de la clase principal en la que se incluye el main**/
public class Principal {

	public static void main(String[] args) {
		// En primer lugar se crea un par de usuarios y se imprimen sus idenrificadores
		Usuario usuario1 = new Usuario("id1");
		System.out.println(usuario1.getIdUsuario());
		
		Usuario usuario2 = new Usuario("id1");
		System.out.println(usuario1.equals(usuario2));

		// A continuación se define un usuarioRegistrado añadiendo sus datos (dni y telefono ya que son los obligatorios)
		UsuarioRegistrado usuario3 = new UsuarioRegistrado.Builder().fromUsuario(usuario2)
				.setDNI("123456").setTelefono(123456).build();
		System.out.println(usuario3.getIdUsuario() + " " + usuario3.getTelefono());

		//Se convierte el suuario registrado en administrador y se comprueba si se hizo correctamente
		usuario3.volverAdmin();
		System.out.println(usuario3.esAdmin());
		//Se elimina de admnistrador a dicho usuario
		usuario3.desactivarAdmin();
		System.out.println(usuario3.esAdmin());
	}

}

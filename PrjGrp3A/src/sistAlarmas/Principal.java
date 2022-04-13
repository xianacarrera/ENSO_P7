package sistAlarmas;

public class Principal {

	public static void main(String[] args) {
		Usuario usuario1 = new Usuario("id1");
		System.out.println(usuario1.getIdUsuario());
		
		Usuario usuario2 = new Usuario("id1");
		System.out.println(usuario1.equals(usuario2));
		
		UsuarioRegistrado usuario3 = new UsuarioRegistrado.Builder().fromUsuario(usuario2)
				.setDNI("123456").setTelefono(123456).build();
		System.out.println(usuario3.getIdUsuario() + " " + usuario3.getTelefono());
		
		usuario3.volverAdmin();
		System.out.println(usuario3.esAdmin());
		usuario3.desactivarAdmin();
		System.out.println(usuario3.esAdmin());
	}

}

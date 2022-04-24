package sistAlarmas;

/** Se trata de la clase principal en la que se incluye el main**/
public class Principal {

	public static void main(String[] args) {
		// Interfaz ItfGestorId
		String idAccion, idAlarma, idCentro, idEquipo, idProt, idSensor, idUsuario, idVerif;
		try {
			idAccion = ItfGestorId.generarId("Accion");
			System.out.println("¿Id de accion valido? " + ItfGestorId.checkIdAccion(idAccion));
			idAlarma = ItfGestorId.generarId("Alarma");
			System.out.println("¿Id de alarma valido? " + ItfGestorId.checkIdAlarma(idAlarma));
			idCentro = ItfGestorId.generarId("Centro");
			System.out.println("¿Id de centro valido? " + ItfGestorId.checkIdCentro(idCentro));
			idEquipo = ItfGestorId.generarId("Equipo");
			System.out.println("¿Id de equipo valido? " + ItfGestorId.checkIdEquipo(idEquipo));
			idProt = ItfGestorId.generarId("Protocolo");
			System.out.println("¿Id de protocolo valido? " + ItfGestorId.checkIdProtocolo(idProt));
			idSensor = ItfGestorId.generarId("Sensor");
			System.out.println("¿Id de sensor valido? " + ItfGestorId.checkIdSensor(idSensor));
			idUsuario = ItfGestorId.generarId("Usuario");
			System.out.println("¿Id de usuario valido? " + ItfGestorId.checkIdUsuario(idUsuario));
			idVerif = ItfGestorId.generarId("Verificacion");
			System.out.println("¿Id de verificacion valido? " + ItfGestorId.checkIdVerificacion(idVerif));
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfGestorId");
			e.printStackTrace();
		}
		

		// Interfaz UsuarioRegistrado
		try {
			UsuarioRegistrado user = new UsuarioRegistrado.Builder().reset()
					.setIdUsuario(idUsuario).setNombre("Caminha", "Sio", null).setCorreo("carminhasio@gmail.com").build();
			user.volverAdmin();
			user.desactivarAdmin();
			System.out.println("¿El usuario es un administrador? " + user.esAdmin());
			
			user.volverPersonalEquipo();
			System.out.println("¿El usuario es personal de emergencias? " + user.ayudaEnEmergencias());
			user.desactivarPersonalEquipo();
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfUsuarioRegistrado");
			e.printStackTrace();
		}
		
		
		// Interfaz UsuarioRegistrado
		//Usuario usuario = new Usuario();
		//usuario.setIdUsuario(Itf)
		/*
		// En primer lugar se crea un par de usuarios y se imprimen sus idenrificadores
		Usuario usuario1 = new Usuario("id1");
		System.out.println(usuario1.getIdUsuario());
		
		Usuario usuario2 = new Usuario("id1");
		System.out.println(usuario1.equals(usuario2));

		// A continuaciÃ³n se define un usuarioRegistrado aÃ±adiendo sus datos (dni y telefono ya que son los obligatorios)
		UsuarioRegistrado usuario3 = new UsuarioRegistrado.Builder().fromUsuario(usuario2)
				.setDNI("123456").setTelefono(123456).build();
		System.out.println(usuario3.getIdUsuario() + " " + usuario3.getTelefono());

		//Se convierte el suuario registrado en administrador y se comprueba si se hizo correctamente
		usuario3.volverAdmin();
		System.out.println(usuario3.esAdmin());
		//Se elimina de admnistrador a dicho usuario
		usuario3.desactivarAdmin();
		System.out.println(usuario3.esAdmin());*/
	}

}

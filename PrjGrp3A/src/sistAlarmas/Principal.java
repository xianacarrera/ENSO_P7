package sistAlarmas;

import java.util.Date;
import java.util.List;

/** Se trata de la clase principal en la que se incluye el main**/
public class Principal {

	/*
	 * Equipo 3A
	 * 		Adriana Aurora Rodríguez Oreiro
	 *		Belén Valle Cao
	 * 		Hugo Vázquez Docampo
	 * 		Xiana Carrera Alonso
	 * ENSO
	 * Curso 2021/2022 - 24/04/2022
	 * Practica 7
	 */
	
	public static void main(String[] args) throws Exception {
		
		// El main comprueba que todos los metodos de todas las interfaces funcionen llamando a cada uno de ellos
		// al menos una vez
		// Este metodo sirve tambien a modo de guia de funcionamiento (se recomienda leer el flujo de 
		// control a traves de los comentarios)
		
		/* Para facilitar la comprobacion de la correcta ejecucion del programa, se han incluido prints que muestran
		 * el contenido y la traza de las excepciones generadas. De lo contrario, resultaria imposible discernir si
		 * ha tenido lugar un error o no.
		 * 
		 * No se utilizan prints en ningun otro metodo de este sistema.
		 */
		
		/* El orden elegido para probar las interfaces responde a la intencion de optimizar la reutilizacion de variables. Es:
		 * - ItfGestorId
		 * - ItfUsuarioRegistrado
		 * - ItfGestorUsuarios
		 * - ItfGestorCentros
		 * - ItfCentro
		 * - ItfSensor
		 * - ItfGestorAlarmas
		 * - ItfGestorEquipos
		 * - ItfEquipo
		 * - ItfAccion
		 * - ItfVerificacion
		 * - ItfProtocolo
		 * - ItfGestorEstadisticas
		 */
		
		// ************************ Interfaz ItfGestorId
		String idAccion = null, idAlarma = null, idCentro = null, idEquipo = null, 
				idProt = null, idSensor = null, idUsuario = null, idVerif = null;
		try {
			// Para cada clae, generamos un identificador y comprobamos su validez
			// Notese que estos metodos son estaticos y estan implementados en la propia interfaz
			idAccion = ItfGestorId.generarId("ACCION");
			ItfGestorId.checkIdAccion(idAccion);
			
			idAlarma = ItfGestorId.generarId("ALARMA");
			ItfGestorId.checkIdAlarma(idAlarma);
			
			idCentro = ItfGestorId.generarId("CENTRO");
			ItfGestorId.checkIdCentro(idCentro);
			
			idEquipo = ItfGestorId.generarId("EQUIPO");
			ItfGestorId.checkIdEquipo(idEquipo);
			
			idProt = ItfGestorId.generarId("PROTOCOLO");
			ItfGestorId.checkIdProtocolo(idProt);
			
			idSensor = ItfGestorId.generarId("SENSOR");
			ItfGestorId.checkIdSensor(idSensor);
			
			idUsuario = ItfGestorId.generarId("USUARIO");
			ItfGestorId.checkIdUsuario(idUsuario);
			
			idVerif = ItfGestorId.generarId("VERIFICACION");
			ItfGestorId.checkIdVerificacion(idVerif);
			
			// Verificamos que el tipo asociado a idVerif sea Verificacion
			ItfGestorId.getTipo(idVerif);
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfGestorId: "+ e.getMessage());
			e.printStackTrace();
		}

		
		
		// ************************ Interfaz ItfUsuarioRegistrado
		UsuarioRegistrado user = null;
		try {
			// Creamos un nuevo usuario registrado y le damos un id
			user = new UsuarioRegistrado.Builder().reset()
					.setIdUsuario(idUsuario).build();
			// Convertimos el usuario en administrador y acto seguido le quitamos los permisos
			user.volverAdmin();
			user.desactivarAdmin();
			// No deberia ser un admin
			user.esAdmin();
			
			// Hacemos que el usuario sea personal de emergencias
			user.volverPersonalEquipo();
			// La siguiente instruccion deberia devolver true (comprueba si el usuario es personal de equipo)
			user.ayudaEnEmergencias();
			user.desactivarPersonalEquipo();	// El usuario deja de estar capacitado para ayudar en emergencias
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfUsuarioRegistrado: "+ e.getMessage());
			e.printStackTrace();
		}
		
		
		
		// ************************ Interfaz ItfGestorUsuarios
		GestorUsuarios gu = GestorUsuarios.getInstancia();
		Usuario usuario = new Usuario();
		UsuarioRegistrado usuario2 = new UsuarioRegistrado();
		Centro centro = new Centro();
		try {
			usuario.setIdUsuario(ItfGestorId.generarId("USUARIO"));
			// Mandamos una accion aleatoria al usuario
			usuario.recibirAccion(new Accion().setIdAccion(ItfGestorId.generarId("ACCION")));
			// Introducimos el usuario en el sistema
			gu.addUsuario(usuario);
			// Creamos un usuario con el mismo id
			usuario2.setIdUsuario(usuario.getIdUsuario());
			usuario2.setNombrePropio("Marcos");
			// Sobreescribimos los datos de "usuario"
			gu.modificarUsuario(usuario2);
			
			// Leemos los datos del usuario que acabamos de modificar
			// Se puede comprobar que estos son los datos nuevos
			UsuarioRegistrado uTemp = (UsuarioRegistrado) gu.leerUsuario(usuario.getIdUsuario());
			
			// El usuario existe, pero tras borrarlo ya no
			gu.existeUsuario(uTemp.getIdUsuario());
			gu.borrarUsuario(uTemp.getIdUsuario());
			gu.existeUsuario(uTemp.getIdUsuario());
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfSensor: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		
		// ************************ Interfaz ItfGestorCentros
		Sensor sensor = null;
		Sensor sensor2 = null;
		Centro centro2 = new Centro();
		GestorCentros gc = GestorCentros.getInstancia();
		try {
			sensor = new Sensor.Builder().setIdSensor(idSensor).setTipoSensor(TipoSensor.CALOR).build();
			sensor2 = new Sensor.Builder().setIdSensor(idSensor).setTipoSensor(TipoSensor.BAROMETRO).build();

			// Creamos un centro
			centro.setIdCentro(idCentro);
			centro.setNombre("Facultad de matematicas");
			centro.setCampus("Campus sur");
			centro.setServicio("Educativo");
			centro.setCoordenadas(new float[] {(float) 45.15, (float) 48.98});
			centro.setCalle("Rua de Lope Gomez de Marzoa");
			centro.setCodigoPostal(15705);
			centro.setNumero(24);
			centro.setCiudad("Santiago de Compostela");

			// Creamos un segundo centro con el mismo id
			centro2.setIdCentro(idCentro);
			centro2.setNombre("ETSE");
			centro2.setCampus("Campus sur");
			centro2.setServicio("Educativo");
			centro2.setCoordenadas(new float[] {(float) 04.15, (float) -12.98});
			centro2.setCalle("Rua de Lope Gomez de Marzoa");
			centro2.setCodigoPostal(15705);
			centro2.setNumero(45);
			centro2.setCiudad("Santiago de Compostela");
			
			// Anhadimos el centro al sistema
			gc.addCentro(centro);
			gc.modificarCentro(centro2);	// Sobreescribimos sus datos
			gc.leerCentro(idCentro);	    // Comprobamos cual de los dos ha permanecido
			
			// Analogo para los sensores
			gc.addSensor(sensor, centro2.getIdCentro());
			gc.modificarSensor(sensor2);
			gc.leerSensor(idSensor);
			
			gu.addUsuario(usuario2);
			// Movermos el usuario2 al centro2 y acto seguido, lo sacamos del mismo
			gc.cambiarCentroUsuario(usuario2, centro2);
			gc.cambiarCentroUsuario(usuario2, null);
			
			// Tras borrar el sensor del centro, podemos eliminar este ultimo
			gc.eliminarSensor(idSensor);
			gc.borrarCentro(idCentro);
			
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfGestorCentros: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		// ************************ Interfaz ItfCentro
		try {
			gc.addCentro(centro);
			// Tras añadir el centro al sistema, indicamos que un usuario se encuentra en el
			centro.addUsuarioActual(usuario2.getIdUsuario());
			// El usuario sale del centro
			centro.salirUsuarioActual(usuario2.getIdUsuario());
			
			// Probamos el CRUD de los sensores
			centro.addSensor(sensor2);
			centro.modificarSensor(sensor);
			Sensor sTemp = centro.leerSensor(sensor.getIdSensor());
			centro.borrarSensor(sensor.getIdSensor());
			centro.tieneSensor(sTemp.getTipoSensor());
			
			// Anhadimos varios sensores para borrarlos juntos
			centro.addSensor(sensor);
			centro.addSensor(new Sensor.Builder().setIdSensor(ItfGestorId.generarId("SENSOR")).setTipoSensor(TipoSensor.CALOR).build());
			centro.borrarTodosSensores();
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfCentro: " + e.getMessage());
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfSensor
		Sensor sensor3 = null;
		Alarma alarma = null;
		try {
			sensor3 = new Sensor.Builder().setIdSensor(ItfGestorId.generarId("SENSOR")).setTipoSensor(TipoSensor.SISMO).setUmbralActivacion((float) 8.0).build();
			centro.addSensor(sensor3);
			sensor3.analizarEntorno();			// Medimos un valor aleatorio y declaramos una alarma si superamos el umbral
			alarma = sensor3.dispararAlarma();  // Disparamos una alarma directamente
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfSensor: "+ e.getMessage());
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfGestorAlarmas
		GestorAlarmas ga = GestorAlarmas.getInstancia();
		Protocolo prot = null;
		Protocolo prot2 = null;
		Alarma alarmaIncendios = null;
		try  {
			// Comprobamos los metodos estaticos directamente implementados en la interfaz
			ItfGestorAlarmas.esValidoValorAlarma(TipoAlarma.SISMICA, 7);
			ItfGestorAlarmas.esValidoValorSensor(TipoSensor.CALOR, 12);
			ItfGestorAlarmas.tipoSensorToTipoAlarma(TipoSensor.PRESENCIA);
			
			// Corroboramos si la alarma se habia almacenado como una alarma en ejecucion en el GestorAlarmas
			ga.esAlarmaEnEjecucion(alarma.getIdAlarma());

			// Verificamos el CRUD de los protocolos
			prot = new Protocolo();
			prot.setIdProtocolo(idProt);
			prot.setNombre("Protocolo 1");
			prot.setTipoAlarma(TipoAlarma.MANUAL);
			
			prot2 = new Protocolo();
			prot2.setIdProtocolo(idProt);
			prot2.setTipoAlarma(TipoAlarma.INCENDIOS);
			prot2.setNombre("Protocolo 2");
			prot2.setDescripcion("Protocolo estandar");
			
			ga.addProtocolo(prot);
			ga.modificarProtocolo(prot2);
			
			alarmaIncendios = new Alarma();
			alarmaIncendios.setIdAlarma(ItfGestorId.generarId("ALARMA"));
			alarmaIncendios.setTipoAlarma(TipoAlarma.INCENDIOS);
			
			// Probamos a buscar protocolos relacionados con una alarma
			ga.buscarProtocolos(alarmaIncendios);
			
			// Comprobamos las dos versiones de activarAlarma
			Alarma alarmaTemp = ga.activarAlarma(null, "Campus norte");
			Alarma alarmaTemp2 = ga.activarAlarma(sensor3);
			ga.getAlarmasPendientes();			// La alarma estaria pendiente ya que no hay equipos adecuados para atenderla
			ga.despacharAlarmasPendientes();			
			// Con este metodo intentariamos poner en ejecucion las alarmas pendientes. No obsntante, como sigue sin haber equipos,
			// en este caso no habria cambios.

		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfGestorAlarmas: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		
		// ************************ Interfaz ItGestorEquipos
		Equipo eq = new Equipo();
		Equipo eq2 = new Equipo();
		GestorEquipos ge = GestorEquipos.getInstancia();
		try {
			// Preparamos un equipo
			eq.setIdEquipo(ItfGestorId.generarId("EQUIPO"));
			eq.setDescripcion("Equipo de prueba");
			eq.addMiembro((UsuarioRegistrado) new UsuarioRegistrado.Builder().setIdUsuario(ItfGestorId.generarId("USUARIO"))
					.build().volverPersonalEquipo());
			
			// Y otro para sustituirlo
			eq2.setIdEquipo(eq.getIdEquipo());
			eq2.setDescripcion("Equipo de prueba 2");
			eq2.addMiembro((UsuarioRegistrado) new UsuarioRegistrado.Builder().setIdUsuario(ItfGestorId.generarId("USUARIO"))
					.build().volverPersonalEquipo());
			
			// Probamos el CRUD
			ge.addEquipo(eq);
			ge.modificarEquipo(eq2);
			ge.leerEquipo(eq2.getIdEquipo());
			
			ge.esEquipoRegistrado(eq2.getIdEquipo());		 // Deberia serlo
			
			// Generamos una alarma que sera puesta en ejecucion por el equipo eq2 (por la implementacion del algoritmo)
			Alarma al = GestorAlarmas.getInstancia().activarAlarma(null, "Azotea");
			// Podemos comprobar que este es el equipo elegido por el algoritmo para la gestion de la alarma a traves
			// de buscarEquipo, que devuelve el equipo optimo para ello
			ge.buscarEquipo(al);

			// Creamos una lista de acciones vinculadas a la alarma que acabamos de crear
			// Le enviamos las acciones como una orden al equipo
			List<Accion> acciones = List.of(new Accion().setIdAccion(ItfGestorId.generarId("ACCION"))
					.setAlarma(al));
			ge.enviarAcciones(eq, acciones, al);
		
			
			// Probamos a añadir, modificar y leer acciones (no se pueden borrar)
			Accion ac = new Accion().setIdAccion(ItfGestorId.generarId("ACCION")).setMensaje("Accion de prueba 1");
			Accion ac2 = new Accion().setIdAccion(ac.getIdAccion()).setMensaje("Accion de prueba 2");
			
			ge.addAccion(ac);
			ge.modificarAccion(ac2);
			ge.leerAccion(ac2.getIdAccion());
			
			// Tambien podemos añadir y leer verificaciones (no modificarlas ni borrarlas)
			Verificacion ver = new Verificacion().setIdVerif(ItfGestorId.generarId("VERIFICACION")).setMensaje("Verificacion de prueba")
					.setEmisor(eq2).setAlarma(al);
			ge.addVerificacion(ver);
			ge.leerVerif(ver.getIdVerif());
			
			// El equipo envia la verificacion de vuelta al gestor
			ge.recibirVerificacion(eq2, ver);
			
			ge.eliminarEquipo(eq2.getIdEquipo());
			
					
		} catch (Exception e) {
			System.out.println("Error en la interfaz ItfGestorEquipos: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		// ************************ Interfaz ItfEquipo
		Equipo equipo = null;
		try {
			equipo = new Equipo();
			equipo.setIdEquipo(idEquipo);
			equipo.estaOcupado();		// El equipo aun no esta ocupado porque no esta ejecutando alarmas ni acciones
			
			// Probamos la gestion de responsabilidades
			equipo.addResponsabilidad("Vigilar la caldera");
			equipo.quitarResponsabilidad("Vigilar la caldera");
			
			// Probamos a añadir miembros
			usuario2.volverPersonalEquipo();
			equipo.addMiembro(usuario2);
			user.volverPersonalEquipo();
			equipo.addMiembro(user);
			
			ge.addEquipo(equipo);    // Introducimos el equipo en el sistema
			// Activamos una alarma que, por sus caracteristicas, podra ser gestionada por ese equipo
			// (Vease el metodo buscarEquipo() del gestor de equipos para mas detalles)
			Alarma al = GestorAlarmas.getInstancia().activarAlarma(null, "Campo de futbol");
			
			// Creamos una lista con una sola accion de id aleatorio
			List<Accion> listaAc = List.of(new Accion().setIdAccion(ItfGestorId.generarId("ACCION")));
			// Mandamos una lista de acciones relacionadas con una alarma al equipo
			equipo.recibirOrden(listaAc, al);
			// Hacemos que el equipo resuelva la situacion de emergencia (la alarma queda gestionada)
			equipo.gestionarAlarma();
			
			// Quitamos un miembro del equipo (no podriamos quitar ambos porque quedaria vacio)
			equipo.quitarMiembro(user);
			
			// Eliminamos todos los datos del equipo para poder borrarlo (la unica forma de eliminar todos los miembros)
			equipo.borrarDatosEquipo();
			equipo = null;		// Nos deshacemos del equipo

		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfEquipo: " + e.getMessage());
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfAccion
		Accion accion = new Accion();;
		try {		// La accion es recibida y a continuacion se llama al metodo que comprobaria que esto es cierto
			accion.recibir();
			accion.esRecibida();
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfAccion");
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfVerificacion
		
		Verificacion verif = new Verificacion();
		try {			// De forma analoga a ItfAccion, solo comprobamos si la verificacion se ha recibido tras recibirla
			verif.recibir();
			verif.esRecibida();
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfVerificacion: " + e.getMessage());
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfProtocolo
		
		Protocolo protocolo = new Protocolo();
		try {
			// Para probar la interfaz, simplemente anhadimos el mensaje de una supuesta accion y a continuacion lo eliminamos
			protocolo.addMensajeAccion("Ir a un punto de reunion cercano");
			protocolo.borrarMensajeAccion("Ir a un punto de reunion cercano");
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfProcolo: " + e.getMessage());
			e.printStackTrace();		
		}
		
		
		
		// ************************ Interfaz ItfGestorEstadisticas
		
		/*AGREGAR 1º*/
		GestorEstadisticas gest = GestorEstadisticas.getInstancia();
		@SuppressWarnings("deprecation")
		Date inicio = new Date(122, 0, 3);	
		@SuppressWarnings("deprecation")
		Date fin = new Date(122, 0, 30);
		
		/*AGREGAR 2º*/
		@SuppressWarnings("deprecation")
		Date inicio1 = new Date(122, 1, 4);	
		@SuppressWarnings("deprecation")
		Date fin1 = new Date(122, 1, 15);
		String idAlarma1 = ItfGestorId.generarId("ALARMA");
		
		/*AGREGAMOS UN 3º*/
		@SuppressWarnings("deprecation")
		Date inicio2 = new Date(122, 2, 1);	
		@SuppressWarnings("deprecation")
		Date fin2 = new Date(122, 2, 11);
		String idAlarma2 = ItfGestorId.generarId("ALARMA");
		Centro c =new Centro();
		c.setIdCentro(ItfGestorId.generarId("Centro")); //Con definir el identificador para esta prueba es suficiente
		
		try {
			//Probamos agregar:
			gest.agregar(inicio, fin, new Date(), idAlarma, centro.getIdCentro());
			
			//Agregamos otros para mas pruebas:
			gest.agregar(inicio1, fin1, new Date(), idAlarma1, centro.getIdCentro());
			gest.agregar(inicio2, fin2, new Date(), idAlarma2, c.getIdCentro());

			//Recuperar total (Sin filtrar):
			gest.recuperarTotal("Alarma");

			//Calcular media sin filtrar:
			gest.mediaDuracion("Alarma");
			
			//Procedemos a filtrar por centro:
			gest.distribucionTotal(centro.getIdCentro(),"Alarma");

			//Y por ultimo calculamos la distribucion media:
			gest.distribucionMedia(centro.getIdCentro(),"Alarma");

			
		}catch(Exception e) {
			System.out.println("Error en la interfaz ItfGestorEstadisticas: " + e.getMessage());
			e.printStackTrace();
		}
		

	}

}

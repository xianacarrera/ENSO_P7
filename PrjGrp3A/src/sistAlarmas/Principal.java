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
		// Este metodo sirve tambien a modo de guia de funcionamiento
		
		// ************************ Interfaz ItfGestorId
		String idAccion = null, idAlarma = null, idCentro = null, idEquipo = null, 
				idProt = null, idSensor = null, idUsuario = null, idVerif = null;
		try {
			// Para cada clae, generamos un identificador y comprobamos su validez
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
			System.exit(0);
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
			// La siguiente instruccion deberia devolver true
			user.ayudaEnEmergencias();
			user.desactivarPersonalEquipo();
		} catch(Exception e) {
			System.exit(0);
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
			System.exit(0);
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
			Centro cTemp = gc.leerCentro(idCentro);			// Comprobamos cual de los dos ha permanecido
			System.out.println("Nombre del centro modificado: " + cTemp.getNombre());
			
			// Analogo para los sensores
			gc.addSensor(sensor, centro2.getIdCentro());
			gc.modificarSensor(sensor2);
			Sensor sTemp = gc.leerSensor(idSensor);
			System.out.println("Tipo del sensor modificado: " + sTemp.getTipoSensor());
			
			gu.addUsuario(usuario2);
			// Movermos el usuario2 al centro2 y acto seguido, lo sacamos al aire libre
			gc.cambiarCentroUsuario(usuario2, centro2);
			gc.cambiarCentroUsuario(usuario2, null);
			
			// Tras borrar el sensor del centro, podemos eliminar este ultimo
			gc.eliminarSensor(idSensor);
			gc.borrarCentro(idCentro);
			
		} catch(Exception e) {
			System.exit(0);
		}
		
		// Interfaz ItfCentro
		try {
			gc.addCentro(centro);
			centro.addUsuarioActual(usuario2.getIdUsuario());
			centro.salirUsuarioActual(usuario2.getIdUsuario());
			
			centro.addSensor(sensor2);
			centro.modificarSensor(sensor);
			Sensor sTemp = centro.leerSensor(sensor.getIdSensor());
			System.out.println("El sensor del centro es del tipo: " + sTemp.getTipoSensor());
			centro.borrarSensor(sensor.getIdSensor());
			System.out.println("¿Tiene el centro algun sensor del tipo " + sTemp.getTipoSensor() + "? " + centro.tieneSensor(sTemp.getTipoSensor()));
		} catch(Exception e) {
			System.exit(0);
		}
		
		// ************************ Interfaz ItfSensor
		Sensor sensor3 = null;
		Alarma alarma = null;
		try {
			sensor3 = new Sensor.Builder().setIdSensor(ItfGestorId.generarId("SENSOR")).setTipoSensor(TipoSensor.SISMO).setUmbralActivacion((float) 8.0).build();
			centro.addSensor(sensor3);
			sensor3.analizarEntorno();			// Medimos un valor aleatorio y declaramos una alarma si superamos el umbral
			alarma = sensor3.dispararAlarma();  // Disparamos una alarma directamente
			System.out.println("Estado de la alarma: " + alarma.getEstado() + " (la alarma no ha podido ser gestionada)");
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfSensor");
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfGestorAlarmas
		GestorAlarmas ga = GestorAlarmas.getInstancia();
		Protocolo prot = null;
		Protocolo prot2 = null;
		Alarma alarmaIncendios = null;
		try  {
			// Comprobamos los metodos estaticos
			System.out.println("¿Es valido el valor de la alarma? "+ ItfGestorAlarmas.esValidoValorAlarma(TipoAlarma.SISMICA, 7));
			System.out.println("¿Es valido el valor del sensor? " + ItfGestorAlarmas.esValidoValorSensor(TipoSensor.CALOR, 12));
			System.out.println("El tipo de alarma del tipo de sensor " + TipoSensor.PRESENCIA + " es " + ItfGestorAlarmas.tipoSensorToTipoAlarma(TipoSensor.PRESENCIA));
			// Corroboramos si la alarma se habia almacenado como una alarma en ejecucion en el GestorAlarmas
			System.out.println("¿Es la alarma activada previamente una alarma en ejecucion? " + ga.esAlarmaEnEjecucion(alarma.getIdAlarma()));

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
			System.out.println("Descripcion del protocolo: " + ga.leerProtocolo(idProt).getDescripcion());
			
			alarmaIncendios = new Alarma();
			alarmaIncendios.setIdAlarma(ItfGestorId.generarId("ALARMA"));
			alarmaIncendios.setTipoAlarma(TipoAlarma.INCENDIOS);
			// Probamos a buscar protocolos relacionados con una alarma
			Protocolo protIncendios = ga.buscarProtocolos(alarmaIncendios).get(0);
			System.out.println("Un protocolo valido para una alarma de incendios tiene nombre " + protIncendios.getNombre());
			
			// Comprobamos las dos versiones de activarAlarma
			Alarma alarmaTemp = ga.activarAlarma(null, "Campus norte");
			Alarma alarmaTemp2 = ga.activarAlarma(sensor3);
			System.out.println("¿La alarma esta pendiente? " + 
					ga.getAlarmasPendientes().containsValue(alarmaTemp));
			ga.despacharAlarmasPendientes();
			System.out.println("¿La alarma sigue pendiente? " + 
					ga.getAlarmasPendientes().containsValue(alarmaTemp) + " (no habia equipos para atenderla)");

			
		} catch(Exception e) {
			System.exit(0);
		}
		

		
		// ************************ Interfaz ItfEquipo
		Equipo equipo = null;
		try {
			equipo = new Equipo();
			equipo.setIdEquipo(idEquipo);
			System.out.println("¿Esta ocupado el equipo? "+ equipo.estaOcupado());
			// Probamos la gestion de responsabilidades
			equipo.addResponsabilidad("Vigilar la caldera");
			equipo.quitarResponsabilidad("Vigilar la caldera");
			
			// Probamos a añadir miembros
			usuario2.volverPersonalEquipo();
			equipo.addMiembro(usuario2);
			user.volverPersonalEquipo();
			equipo.addMiembro(user);
			
			GestorEquipos.getInstancia().addEquipo(equipo);
			
			Alarma al = GestorAlarmas.getInstancia().activarAlarma(null, "Campo de futbol");
			
			List<Accion> listaAc = List.of(new Accion().setIdAccion(ItfGestorId.generarId("ACCION")));
			// Mandamos una lista de acciones relacionadas con una alarma al equipo
			equipo.recibirOrden(listaAc, al);
			// Hacemos que el equipo resuelva la situacion de emergencia
			equipo.gestionarAlarma();
			
			// Quitamos un miembro del equipo
			equipo.quitarMiembro(user);
			
			// Eliminamos todos los datos del equipo para poder borrarlo
			equipo.borrarDatosEquipo();
			equipo = null;		// Nos deshacemos del equipo

		} catch(Exception e) {
			System.exit(0);
		}
		
		// ************************ Interfaz ItfAccion
		Accion accion = new Accion();;
		try {
			accion.recibir();
			System.out.println("¿Se ha recibido la accion?: " + accion.esRecibida());
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfAccion");
			e.printStackTrace();
		}
		
		// ************************ Interfaz ItfVerificacion
		Verificacion verif = new Verificacion();
		try {
			System.out.println("¿Se ha recibido la verificacion?: " + verif.esRecibida());
			verif.recibir();
		} catch(Exception e) {
			System.out.println("Error en la interfaz ItfVerificacion");
			e.printStackTrace();
		}
		
		Protocolo protocolo = new Protocolo();
		try {
			protocolo.addMensajeAccion("Ir a un punto de reunion cercano");
			protocolo.borrarMensajeAccion("Ir a un punto de reunion cercano");
		} catch(Exception e) {
			System.exit(0);
		}
		
		// ************************ Interfaz ItfGestorEstadisticas
		
		/*AGREGAR 1º*/
		GestorEstadisticas ge = GestorEstadisticas.getInstancia();
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
			//Agregar:
			Estadistica est= ge.agregar(inicio, fin, new Date(), idAlarma, centro.getIdCentro());
			System.out.println("======================================================================");
			System.out.println("Se ha agregado la estadística: " + est.getId());
			System.out.println("-> Datos: ");
			System.out.println("-TIPO: "+est.getTipo());
			System.out.println("-DURACIÓN: "+est.getDuracion());
			System.out.println("-CENTRO: "+est.getCentro());
			System.out.println("-FECHA OCURRENCIA: "+est.getFechaOcurrencia().toString());
			System.out.println("-FECHA INSERCIÓN: "+est.getFechaInsercion().toString());
			System.out.println("======================================================================");
			
			//Agregamos otros para otras pruebas:
			Estadistica est1= ge.agregar(inicio1, fin1, new Date(), idAlarma1, centro.getIdCentro());
			System.out.println("Se ha agregado la estadística: " + est1.getId());
			System.out.println("-> Datos: ");
			System.out.println("-TIPO: "+est1.getTipo());
			System.out.println("-DURACIÓN: "+est1.getDuracion());
			System.out.println("-CENTRO: "+est1.getCentro());
			System.out.println("-FECHA OCURRENCIA: "+est1.getFechaOcurrencia().toString());
			System.out.println("-FECHA INSERCIÓN: "+est1.getFechaInsercion().toString());
			System.out.println("======================================================================");
			
			Estadistica est2= ge.agregar(inicio2, fin2, new Date(), idAlarma2, c.getIdCentro());
			System.out.println("Se ha agregado la estadística: " + est2.getId());
			System.out.println("-> Datos: ");
			System.out.println("-TIPO: "+est2.getTipo());
			System.out.println("-DURACIÓN: "+est2.getDuracion());
			System.out.println("-CENTRO: "+est2.getCentro());
			System.out.println("-FECHA OCURRENCIA: "+est2.getFechaOcurrencia().toString());
			System.out.println("-FECHA INSERCIÓN: "+est2.getFechaInsercion().toString());
			System.out.println("======================================================================");

			//Recuperar total (Sin filtrar):
			int total = ge.recuperarTotal("Alarma");
			System.out.println("Se ha encontrado un total de " + total + " estadísticos asociados a alarmas.");

			//Calcular media sin filtrar:
			float mediaTotal = ge.mediaDuracion("Alarma");
			System.out.println("La media de estadísticos asociados a alarmas es: " + mediaTotal + ".");
			
			//PROCEDEMOS A FILTRAR POR CENTRO:
			int totalConFiltro = ge.distribucionTotal(centro.getIdCentro(),"Alarma");
			System.out.println("Se ha encontrado un total de " + totalConFiltro + " estadísticos asociados a alarmas en el centro "+ centro.getNombre() + " [" +centro.getIdCentro()+"].");

			//Y POR ÚLTIMO LA MEDIA:
			float mediaConFiltro = ge.distribucionMedia(centro.getIdCentro(),"Alarma");
			System.out.println("La media de estadísticos asociados a alarmas en el centro "+ centro.getNombre()+ " [" +centro.getIdCentro()+"] " +"es:" + mediaConFiltro + ".");

			
		}catch(Exception e) {
			System.exit(0);
		}
		

	}

}

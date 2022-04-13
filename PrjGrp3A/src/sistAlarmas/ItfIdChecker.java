package sistAlarmas;

public interface ItfIdChecker {

		// Todos los método estáticos se pueden implementar directamente
		// en la interfaz desde Java 8
	
	static boolean checkIdAlarma(String idAlarma) {
		// Por ejemplo, que empiece por 'A'
		return true;
	}
	static boolean checkIdCentro(String idCentro) {
		return true;
	}
	static boolean checkIdUsuario(String idUsuario) {
		return true;
	}
	static boolean checkIdEquipo(String idEquipo) {
		return true;
	}
	static boolean checkIdAccion(String idAccion) {
		return true;
	}
	static boolean checkIdVerificacion(String idVerif) {
		return true;
	}

}

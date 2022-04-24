package sistAlarmas;

public interface ItfGestorCentros {
	public Centro addCentro(Centro centro) throws Exception;
	public Centro modificarCentro(Centro centro) throws Exception;
	public Centro borrarCentro(String idCentro) throws Exception;
	public Centro leerCentro(String idCentro) throws Exception;
	
	public Centro addSensor(Sensor sensor, String idCentro) throws Exception;
	public Sensor modificarSensor(Sensor sensor) throws Exception;
	public Sensor eliminarSensor(String idSensor) throws Exception;
	public Sensor leerSensor(String idSensor) throws Exception;
	
	public Usuario cambiarCentroUsuario(Usuario usuario, Centro nuevoCentro) throws Exception;
}

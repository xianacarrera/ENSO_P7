package sistAlarmas;

public interface ItfGestorCentros {
	public Centro addCentro(Centro centro) throws Exception;
	public Centro modificarCentro(Centro centro) throws Exception;
	public Centro borrarCentro(String idCentro) throws Exception;
	public Centro leerCentro(String idCentro);
	
	public Sensor addSensor(Sensor sensor, Centro centro);
	public Sensor modificarSensor(Sensor sensor);
	public Sensor eliminarSensor(String idSensor);
	public Sensor leerSensor(String idSensor);
	
	public Usuario cambiarCentroUsuario(Usuario usuario, Centro centro);
}

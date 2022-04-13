package sistAlarmas;

import java.util.HashMap;

public class GestorCentros implements ItfGestorCentros {
	
	private static GestorCentros instancia;
	private final HashMap<String, Centro> centros;

	private GestorCentros() {
		centros = new HashMap<>();
	}
	
	public static GestorCentros getInstancia() {
		if (instancia == null) instancia = new GestorCentros();
		return instancia;
	}
	
	@Override
	public Centro addCentro(Centro centro) {
		if (centro == null) return null; 
		if (centros.containsKey(centro.getIdCentro())) return null;
		
		centros.put(centro.getIdCentro(), centro);
		return centro;
	}

	@Override
	public Centro modificarCentro(Centro centro) {
		if (centro == null) return null;
		if (!centros.containsKey(centro.getIdCentro())) return null;
		
		centros.put(centro.getIdCentro(), centro);
		return centro;
	}

	@Override
	public Centro borrarCentro(String idCentro) {
		// Corroboramos tanto que la key (idCentro) esté en el HashMap como que su valor asociado
		// (el centro) no sea nulo
		Centro centro;
		if ((centro = centros.get(idCentro)) == null) return null;
		
		if (!centro.getAllSensores().isEmpty()) return null;
		
		// Comprobar si hay alguna alarma activa en relación con ese centro
		
		centros.remove(centro);
		
		return centro;
	}

	@Override
	public Centro leerCentro(String idCentro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sensor addSensor(Sensor sensor, Centro centro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sensor modificarSensor(Sensor sensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sensor eliminarSensor(String idSensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sensor leerSensor(String idSensor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario cambiarCentroUsuario(Usuario usuario, Centro centro) {
		// TODO Auto-generated method stub
		return null;
	}

}

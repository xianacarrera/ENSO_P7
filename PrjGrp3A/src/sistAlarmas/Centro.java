package sistAlarmas;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Centro {
	private String idCentro;
	private String nombre;
	private String campus;
	private String servicio;
	private float[] coordenadas;
	private String ciudad;
	private String calle;
	private int numero;
	private int codigoPostal;
	
	private HashMap<String, Sensor> sensores;
	
	protected Centro(){
		sensores = new HashMap<>();
		coordenadas = new float[2];
		coordenadas[0] = coordenadas[1] = (float) 0.0;
	}
	
	private void comprobarValoresSensor(Sensor sensor) throws Exception {
		if (!ItfIdChecker.checkIdSensor(sensor.getIdSensor())) throw new Exception("El identificador del sensor no es valido");
		if (sensor.getTipoSensor() == null) throw new Exception("Sensor no valido: no tiene tipo");
	}
	
	public Centro addSensor(Sensor sensor) throws Exception {
		if (sensor == null) throw new Exception("Sensor no valido: es inexistente");
		if (sensores.containsKey(sensor.getIdSensor())) throw new Exception("El sensor ya habia sido registrado en este centro");
		if (sensor.getFechaInstalacion() != null || sensor.getCentro() != null) throw new Exception("El sensor ya ha sido asignado a un centro");
		comprobarValoresSensor(sensor);
		
		sensor.setCentro(this);
		sensores.put(sensor.getIdSensor(), sensor);
		return this;
	}
	
	public Sensor getSensor(String idSensor) {
		// Que idSensor sea null no produce error. get devolvería null
		// Si no hay tal sensor, tampoco hay error. get devuelve null
		return sensores.get(idSensor);
	}
	
	public Sensor modificarSensor(Sensor sensor) {
		if (sensor == null) return null;
		if (!sensores.containsKey(sensor.getIdSensor())) return null;
		
		sensores.put(sensor.getIdSensor(), sensor);
		return sensor;
	}
	
	public Sensor borrarSensor(String idSensor) {
		Sensor sensor = sensores.get(idSensor);
		sensores.remove(idSensor);   // Si no había mapping o idSensor era null, no hace nada
		return sensor;				 // Devuelve null si el mapping era null o idSensor era null
	}
	
	public Centro borrarTodosSensores() {
		sensores.clear();
		return this;
	}
	
	public List<Sensor> getAllSensores(){
		return sensores.values().stream().collect(Collectors.toList());
	}


	public String getIdCentro() {
		return idCentro;
	}


	protected void setIdCentro(String idCentro) {
		this.idCentro = idCentro;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCampus() {
		return campus;
	}


	public void setCampus(String campus) {
		this.campus = campus;
	}


	public String getServicio() {
		return servicio;
	}


	public void setServicio(String servicio) {
		this.servicio = servicio;
	}


	public float[] getCoordenadas() {
		return coordenadas;
	}


	public Centro setCoordenadas(float[] coordenadas) {
		if (coordenadas.length != 2) return null;
		if (coordenadas[0] < -90 || coordenadas[0] > 90) return null;
		if (coordenadas[1] < -180 || coordenadas[1] > 180) return null;
		this.coordenadas = coordenadas;
		return this;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public int getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public static class Builder{
		private Centro centro;
		
		public Builder() {
			this.reset();
		}
		
		public Builder reset() {
			centro = new Centro();
			return this;
		}
		
		public Builder setCampus(String campus) {
			if (centro == null) this.reset();
			centro.setCalle(campus);
			return this;
		}
		
		public Builder setServicio(String servicio){
			if (centro == null) this.reset();
			centro.setServicio(servicio);
			return this;
		}
		
		public Builder setCoordenadas(float latitud, float longitud) {
			if (centro == null) this.reset();
			float[] coord = {latitud, longitud};
			if (centro.setCoordenadas(coord) == null) return null;
			return this;
		}
		
		public Builder setCoordenadas(float[] coordenadas) {
			if (centro == null) this.reset();
			if (centro.setCoordenadas(coordenadas) == null) return null;
			return this;
		}
		
		public Builder setCiudad(String ciudad) {
			if (centro == null) this.reset();
			centro.setCiudad(ciudad);
			return this;
		}
		
		public Builder setCalle(String calle){
			if (centro == null) this.reset();
			centro.setCalle(calle);
			return this;
		}
		
		public Builder setNumero(int n) {
			if (centro == null) this.reset();
			centro.setNumero(n);
			return this;
		}
		
		public Builder setCodigoPostal(int codigoPostal){
			if (centro == null) this.reset();
			centro.setCodigoPostal(codigoPostal);
			return this;
		}
		
		public Centro build() {
			if (centro == null || centro.getIdCentro() == null) return null;
			return centro;
		}
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(idCentro);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Centro))
			return false;
		Centro other = (Centro) obj;
		return Objects.equals(idCentro, other.idCentro);
	}
	
}

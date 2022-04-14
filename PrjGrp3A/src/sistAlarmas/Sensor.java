package sistAlarmas;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Sensor {
	private String idSensor;
	private final Date fechaInstalacion;
	private String zona;
	private Float umbralActivacion;
	private Float valorActual;
	private TipoSensor tipoSensor;
	
	private Centro centro;
	
	protected Sensor() {
		fechaInstalacion = new Date(System.currentTimeMillis());
	}
	
	
	public String getIdSensor() {
		return idSensor;
	}

	protected void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public Float getUmbralActivacion() {
		return umbralActivacion;
	}

	public void setUmbralActivacion(Float umbralActivacion) {
		this.umbralActivacion = umbralActivacion;
	}

	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public Float getValorActual() {
		return valorActual;
	}

	public void setValorActual(Float valorActual) {
		this.valorActual = valorActual;
	}

	public TipoSensor getTipoSensor() {
		return tipoSensor;
	}

	public void setTipoSensor(TipoSensor tipoSensor) {
		this.tipoSensor = tipoSensor;
	}

	public Date getFechaInstalacion() {
		return fechaInstalacion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSensor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Sensor))
			return false;
		Sensor other = (Sensor) obj;
		return Objects.equals(idSensor, other.idSensor);
	}
	
	public Sensor analizarEntorno(){
		Random rand = new Random();
		switch (tipoSensor) {
			case HUMO:
			case PRESENCIA:
				// Genera un entero en [0, 2), esto es, un 0 ó un 1
				setValorActual((float) rand.nextInt(2));
				break;
			case CALOR:
				// Genera un float en el rango [-5, 45)
				setValorActual(-5 + rand.nextFloat() * 50);
				break;
			case SISMO:
				// Genera un float en el rango [0, 10) (escala de Richter)
				setValorActual(rand.nextFloat() * 10);
				break;
			case BAROMETRO:
				// Genera un float en el rango [960, 1060)
				setValorActual(960 + rand.nextFloat() * 100);
				break;
			default:
				// Genera un float en el rango [-1000, 1000)
				setValorActual(-1000 + rand.nextFloat() * 2000);
		}
		
		if (valorActual > umbralActivacion) dispararAlarma();
		
		return this;
	}
	
	public Alarma dispararAlarma() {
		return GestorAlarmas.getInstancia().activarAlarma(this);
	}



	public static class Builder{
		private Sensor sensor;
		
		public Builder() {
			this.reset();
		}
		
		public Builder reset() {
			sensor = new Sensor();
			return this;
		}
		
		public Builder setIdSensor(String idSensor){
			if (sensor == null) this.reset();
			sensor.setIdSensor(idSensor);
			return this;
		}
		
		public Builder setZona(String zona) {
			if (sensor == null) this.reset();
			sensor.setZona(zona);
			return this;
		}
		
		public Builder setUmbralActivacion(Float umbral) {
			if (sensor == null) this.reset();
			sensor.setUmbralActivacion(umbral);
			return this;
		}
		
		public Builder setCentro(Centro centro){
			if (sensor == null) this.reset();
			sensor.setCentro(centro);
			return this;
		}
		
		
		public Sensor build(){
			if (sensor == null || sensor.getIdSensor() == null) return null;
			return sensor;
		}
	}
}

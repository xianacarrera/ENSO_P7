package sistAlarmas;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Sensor implements ItfSensor {
	//Declaración de variables
	private String idSensor;
	private Date fechaInstalacion;
	private String zona;
	private Float umbralActivacion;
	private Float valorActual;
	private TipoSensor tipoSensor;
	
	private Centro centro;

	//Constructor
	protected Sensor() {
	}

	//Método para establecer la fecha de instalación de un sensor
	private Sensor setFechaInstalacion() {
		this.fechaInstalacion = new Date(System.currentTimeMillis());
		return this;
	}
	
	//Método para obtener el identificador de un sensor
	public String getIdSensor() {
		return idSensor;
	}

	//Método para establecer el identificador de un sensor
	public Sensor setIdSensor(String idSensor) throws Exception {
		if (this.idSensor != null) throw new Exception("Este sensor ya tiene un identificador");
		if (!ItfGestorId.checkIdSensor(idSensor)) throw new Exception("Identificador de sensor no valido");
		this.idSensor = idSensor;
		return this;
	}

	//Método para establecer la zona en la que está instalado un sensor
	public void setZona(String zona) {
		this.zona = zona;
	}

	//Método para obtener el umbral de activación de un sensor
	public Float getUmbralActivacion() {
		return umbralActivacion;
	}

	//Método para establecer el umbral de activación de un sensor
	public Sensor setUmbralActivacion(Float umbralActivacion) throws Exception {
		if (tipoSensor == null) throw new Exception("No se puede asignar un umbral de activacion a un sensor sin tipo");
		boolean esValido = true;
		switch(tipoSensor) {
			case HUMO: 
			case PRESENCIA:
				if (Float.compare(umbralActivacion, (float) 1.0) != 0) esValido = false;
				break;
			case SISMO:
				if (umbralActivacion < 2.0 || umbralActivacion > 10.0) esValido = false;
				break;
			case CALOR:
				if (umbralActivacion < 5 || umbralActivacion > 40) esValido = false;
			default:
		}
		if(!esValido) throw new Exception("El umbral de activacion es incompatible con el tipo de sensor");
		this.umbralActivacion = umbralActivacion;
		return this;
	}

	//Método para obtener el centro donde se encuentra un sensor
	public Centro getCentro() {
		return centro;
	}

	//Método para establecer el centro donde se encuentra un sensor
	public Sensor setCentro(Centro centro) throws Exception {
		if (this.centro != null) throw new Exception("Este sensor ya estaba instalado en un centro");
		if (centro == null) throw new Exception("Sensor no valido: es inexistente");
		if (!GestorCentros.getInstancia().esCentroRegistrado(centro.getIdCentro())) throw new Exception("El centro no esta registrado");

		centro.addSensor(this);
		this.centro = centro;
		setFechaInstalacion();
		return this;
	}

	//Método para obtener el valor actual de un sensor
	public Float getValorActual() {
		return valorActual;
	}

	//Método para establecer el valor actual de un sensor
	public Sensor setValorActual(Float valorActual) throws Exception {
		if (ItfGestorAlarmas.esValidoValorSensor(this.getTipoSensor(), valorActual)) 
			throw new Exception("Valor de sensor incompatible con su tipo");
		if (valorActual > umbralActivacion) dispararAlarma();
		return this;
	}

	//Método para obtener el tipo de sensor
	public TipoSensor getTipoSensor() {
		return tipoSensor;
	}

	//Método para establecer el tipo de sensor
	public void setTipoSensor(TipoSensor tipoSensor) {
		this.tipoSensor = tipoSensor;
	}

	//Método para obtener la fecha de instalación de un sensor
	public Date getFechaInstalacion() {
		return fechaInstalacion;
	}

	//Método para obtener la zona en la que está instalado un sensor
	public String getZona() {
		return zona;
	}

	//Método para analizar el entorno y asignar un valor al sensor
	@Override
	public ItfSensor analizarEntorno() throws Exception {
		Random rand = new Random();
		float nuevoValor;
		switch (tipoSensor) {
			case HUMO:
			case PRESENCIA:
				// Genera un entero en [0, 2), esto es, un 0 � un 1
				nuevoValor = (float) rand.nextInt(2);
				break;
			case CALOR:
				// Genera un float en el rango [-5, 45)
				nuevoValor = -5 + rand.nextFloat() * 50;
				break;
			case SISMO:
				// Genera un float en el rango [0, 10) (escala de Richter)
				nuevoValor = rand.nextFloat() * 10;
				break;
			case BAROMETRO:
				// Genera un float en el rango [960, 1060)
				nuevoValor = 960 + rand.nextFloat() * 100;
				break;
			default:
				// Genera un float en el rango [-1000, 1000)
				nuevoValor = -1000 + rand.nextFloat() * 2000;
		}

		if (ItfGestorAlarmas.esValidoValorSensor(this.getTipoSensor(), nuevoValor)) 
			throw new Exception("Valor de sensor incompatible con su tipo");
		
		this.valorActual = nuevoValor;
		
		if (valorActual > umbralActivacion) dispararAlarma();
		
		return this;
	}

	//Método para disparar una alarma
	@Override
	public Alarma dispararAlarma() throws Exception {
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
		
		public Builder setIdSensor(String idSensor) throws Exception {
			if (sensor == null) this.reset();
			sensor.setIdSensor(idSensor);
			return this;
		}
		
		public Builder setZona(String zona) {
			if (sensor == null) this.reset();
			sensor.setZona(zona);
			return this;
		}
		
		public Builder setTipoSensor(TipoSensor tipo) throws Exception{
			if (sensor == null) this.reset();
			sensor.setTipoSensor(tipo);
			return this;
		}
		
		public Builder setUmbralActivacion(Float umbral) throws Exception {
			if (sensor == null) this.reset();
			sensor.setUmbralActivacion(umbral);
			return this;
		}
		
		public Builder setCentro(Centro centro) throws Exception {
			if (sensor == null) this.reset();
			sensor.setCentro(centro);
			return this;
		}
		
		public Sensor build(){
			if (sensor == null || sensor.getIdSensor() == null) return null;
			return sensor;
		}
	}

	/**SOBREESCRITURA DE MÉTODOS**/
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
	/** FIN SOBREESCRITURA DE MÉTODOS**/
}

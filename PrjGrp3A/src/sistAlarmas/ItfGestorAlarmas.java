package sistAlarmas;

import java.util.List;

public interface ItfGestorAlarmas {

	static boolean esValidoValorAlarma(TipoAlarma tipoAlarma, float valor){
		if (tipoAlarma == null) return false;
		switch(tipoAlarma) {
			case INCENDIOS:   // Sensor de humo
				return esValidoValorSensor(TipoSensor.HUMO, valor);
			case SISMICA:     // Sensor s�smico
				return esValidoValorSensor(TipoSensor.SISMO, valor);
			case INTRUSOS:	  // Sensor de presencia
				return esValidoValorSensor(TipoSensor.PRESENCIA, valor);
			case MANUAL:      // Activaci�n humana. El valor deber�a ser 1
				return Float.compare(valor, 1) == 0;
			case HURACAN:
				return esValidoValorSensor(TipoSensor.BAROMETRO, valor);
			case OTRO:
				return esValidoValorSensor(TipoSensor.OTRO, valor);
		}
		return false;
	}
	
	static boolean esValidoValorSensor(TipoSensor tipoSensor, float valorMedido) {
		if (tipoSensor == null) return false;
		switch (tipoSensor) {
			case HUMO:
			case PRESENCIA:
				// Comprobamos que el valor sea un entero y que sea o bien 0 o bien 1
				int valorEntero = (int) valorMedido;
				if (valorMedido == valorEntero && (valorEntero == 0 || valorEntero == 1)) return true; 
				return false;
			case CALOR:
				// Comprobamos que el valor est� en el rango [-5, 45)
				if (Float.compare(-5, valorMedido) >= 0 && valorMedido < 45) return true;
				return false;
			case BAROMETRO:
				// Comprobamos que el valor est� en el rango [960, 1060)
				if (Float.compare(960, valorMedido) >= 0 && valorMedido < 1060) return true;
				return false;
			case SISMO:
				// Comprobamos que el valor est� en el rango [0, 10)
				if (Float.compare(0, valorMedido) >= 0 && valorMedido < 10) return true;
				return false;
			default:
				// Comprobamos que el valor est� en el rango [-1000, 1000)
				if (Float.compare(-1000, valorMedido) >= 0 && valorMedido < 1000) return true;
		}
		return false;
	}
	
	static TipoAlarma tipoSensorToTipoAlarma(TipoSensor ts) {
		if (ts == null) return null;
		switch (ts) {
			case HUMO: return TipoAlarma.INCENDIOS;
			case CALOR: return TipoAlarma.INCENDIOS;
			case BAROMETRO: return TipoAlarma.HURACAN;
			case SISMO: return TipoAlarma.SISMICA;
			case PRESENCIA: return TipoAlarma.INTRUSOS;
			case OTRO: return TipoAlarma.OTRO;
		}
		return null;
	}
	
	boolean esAlarmaEnEjecucion(String idAlarma);
	List<Protocolo> buscarProtocolos(Alarma al);
	
	Alarma activarAlarma(Sensor sensor) throws Exception;
	Alarma activarAlarma(Centro centro, String zona) throws Exception;
	List<Alarma> despacharAlarmasPendientes() throws Exception;
	Alarma desactivarAlarma(String idAlarma) throws Exception;
	Alarma leerAlarma(String idAlarma) throws Exception;
	
	ItfGestorAlarmas addProtocolo(Protocolo prot) throws Exception;
	ItfGestorAlarmas modificarProtocolo(Protocolo prot) throws Exception;
	ItfGestorAlarmas borrarProtocolo(String idProt) throws Exception;
	Protocolo leerProtocolo(String idProt) throws Exception;
	
}

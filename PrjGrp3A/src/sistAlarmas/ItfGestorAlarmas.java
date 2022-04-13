package sistAlarmas;

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
				if (Float.compare(valorMedido, -5) >= 0 && valorMedido < 45) return true;
				return false;
			case BAROMETRO:
				// Comprobamos que el valor est� en el rango [960, 1060)
				if (Float.compare(valorMedido, 960) >= 0 && valorMedido < 1060) return true;
				return false;
			case SISMO:
				// Comprobamos que el valor est� en el rango [0, 10)
				if (Float.compare(valorMedido, 0) >= 0 && valorMedido < 10) return true;
				return false;
			default:
				// Comprobamos que el valor est� en el rango [-1000, 1000)
				if (Float.compare(valorMedido, -1000) >= 0 && valorMedido < 1000) return true;
		}
		return false;
	}
	
}

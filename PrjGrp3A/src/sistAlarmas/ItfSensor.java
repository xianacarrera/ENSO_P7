package sistAlarmas;

public interface ItfSensor {
	ItfSensor analizarEntorno() throws Exception;
	Alarma dispararAlarma() throws Exception;
}

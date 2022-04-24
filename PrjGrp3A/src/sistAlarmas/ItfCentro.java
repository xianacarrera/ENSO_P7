package sistAlarmas;

public interface ItfCentro {

    ItfCentro addUsuarioActual(String idUsuario) throws Exception;
    String salirUsuarioActual(String idUsuario) throws Exception;
    ItfCentro addSensor(Sensor sensor) throws Exception;
    Sensor leerSensor(String idSensor) throws Exception;
    Sensor modificarSensor(Sensor sensor) throws Exception;
    Sensor borrarSensor(String idSensor) throws Exception;
    ItfCentro borrarTodosSensores();
    boolean tieneSensor(TipoSensor tipo);
}

package sistAlarmas;

import java.util.Arrays;
import java.util.HashMap;

public class GestorCentros implements ItfGestorCentros {
    // Declaración de variables
    private static GestorCentros instancia;
    private final HashMap<String, Centro> centros;

    // Constructor
    private GestorCentros() {
        centros = new HashMap<>();
    }

    // Método para comprobar si un centro está registrado
    public boolean esCentroRegistrado(String idCentro) {
        if (idCentro == null) return false;
        if (!centros.containsKey(idCentro)) return false;
        if (centros.get(idCentro) == null) return false;
        return true;
    }

    // Patrón Singleton
    public static GestorCentros getInstancia() {
        if (instancia == null) instancia = new GestorCentros();
        return instancia;
    }

    //Sobreescritura del método addCentro de la interfaz ItfGestorCentros
    @Override
    public Centro addCentro(Centro centro) throws Exception {
        if (centro == null) throw new Exception("Centro no valido: es inexistente");
        if (!ItfGestorId.checkIdCentro(centro.getIdCentro()))
            throw new Exception("El identificador del centro no es valido");
        comprobarValoresCentro(centro);
        if (centros.containsKey(centro.getIdCentro())) throw new Exception("El centro ya habia sido registrado");

        centros.put(centro.getIdCentro(), centro);
        return centro;
    }

    //Método para comprobar que los valores de un centro son correctos
    private void comprobarValoresCentro(Centro centro) throws Exception {
        if (!ItfGestorId.checkIdCentro(centro.getIdCentro()))
            throw new Exception("El identificador del centro no es valido");
        if (centro.getNombre() == null) throw new Exception("Centro no valido: no tiene nombre");
        if (centro.getCampus() == null) throw new Exception("Centro no valido: no tiene campus");
        if (Arrays.equals(centro.getCoordenadas(), new float[]{(float) 0.0, (float) 0.0}))
            throw new Exception("Centro no valido: sus coordenadas no han sido establecidas");
        if (centro.getCiudad() == null || centro.getCalle() == null)
            // No comprobamos numero y codigo postal porque son ints. Por defecto, valen 0, que es un valor correcto y posible.
            throw new Exception("Centro no valido: su direccion no ha sido establecida");
    }

    //Getter de los centros:

    public HashMap<String, Centro> getCentros() {
        return centros;
    }

    //Sobreescritura del método modificarCentro de la interfaz ItfGestorCentros
    @Override
    public Centro modificarCentro(Centro centro) throws Exception {
        if (centro == null) throw new Exception("Centro no valido: es inexistente");
        if (!centros.containsKey(centro.getIdCentro()))
            throw new Exception("El centro no habia sido registrado previamente");
        comprobarValoresCentro(centro);

        centros.put(centro.getIdCentro(), centro);
        return centro;
    }

    //Sobreescritura del método borrarCentro de la interfaz ItfGestorCentros
    @Override
    public Centro borrarCentro(String idCentro) throws Exception {
        // Corroboramos tanto que la key (idCentro) está en el HashMap como que su valor asociado
        // (el centro) no sea nulo
        Centro centro;
        if (idCentro == null) throw new Exception("Identificador no valido: es inexistente");
        if (!centros.containsKey(idCentro))
            throw new Exception("El identificador no se corresponde con ningun centro registrado");
        if ((centro = centros.get(idCentro)) == null)
            throw new Exception("Error fatal: el centro correspondiente al identificador no existe");

        if (!centro.getListaSensores().isEmpty())
            throw new Exception("No se puede borrar el centro: existen sensores asociados a el");
        GestorAlarmas ga = GestorAlarmas.getInstancia();
        if (ga.getAlarmasEnEjecucion().values().stream().anyMatch(alarma -> centro.equals(alarma.getCentro()))
                || ga.getAlarmasPendientes().values().stream().anyMatch(alarma -> centro.equals(alarma.getCentro())))
            throw new Exception("No se puede borrar el centro: existen alarmas activas relacionadas con el");

        centros.remove(idCentro);

        return centro;
    }

    //Sobreescritura del método leerCentro de la interfaz ItfGestorCentros
    @Override
    public Centro leerCentro(String idCentro) throws Exception {
        if (idCentro == null) throw new Exception("Identificador no valido: es inexistente");
        if (!centros.containsKey(idCentro))
            throw new Exception("El identificador no se corresponde con ningun centro registrado");

        return centros.get(idCentro);
    }

    //Sobreescritura del método addSensor a la interfaz ItfGestorCentros
    @Override
    public Centro addSensor(Sensor sensor, String idCentro) throws Exception {
        Centro centro = leerCentro(idCentro);
        comprobarValoresCentro(centro);
        centro.addSensor(sensor);
        return centro;
    }

    //Sobreescritura del método getCentroDeSensor de la interfaz ItfGestorCentros
    private Centro getCentroDeSensor(String idSensor) throws Exception {
        Centro centro = centros.values().stream()
                .filter(cen -> cen.getListaSensores().stream().anyMatch(sensor -> sensor.getIdSensor().equals(idSensor)))
                .findFirst()
                .orElseThrow(() -> new Exception("Ningun centro tiene registrado el sensor"));
        return centro;
    }

    //Sobreescritura del método modificarSensor de la interfaz ItfGestorCentros
    @Override
    public Sensor modificarSensor(Sensor sensor) throws Exception {
        if (sensor == null) throw new Exception("El sensor pasado como argumento no existe");
        // No tenemos que comprobar que sensor.getIdSensor no sea null, porque no es posible modificarlo así (tampoco tendría repercusiones)
        Centro centro = getCentroDeSensor(sensor.getIdSensor());
        centro.modificarSensor(sensor);
        return null;
    }

    //Sobreescritura del método eliminarSensor de la interfaz ItfGestorCentros
    @Override
    public Sensor eliminarSensor(String idSensor) throws Exception {
        Centro centro = getCentroDeSensor(idSensor);
        return centro.borrarSensor(idSensor);
    }

    //Sobreescritura del método leerSensor de la interfaz ItfGestorCentros
    @Override
    public Sensor leerSensor(String idSensor) throws Exception {
        return getCentroDeSensor(idSensor).leerSensor(idSensor);
    }

    //Sobreescritura del método cambiarCentroUsuario de la interfaz ItfGestorCentros
    @Override
    public Usuario cambiarCentroUsuario(Usuario usuario, Centro nuevoCentro) throws Exception {
        if (usuario == null) throw new Exception("El usuario no existe");
        if (!GestorUsuarios.getInstancia().existeUsuario(usuario.getIdUsuario()))
            throw new Exception("El usuario no está registrado");
        if (nuevoCentro != null) {
	        if (!GestorCentros.getInstancia().esCentroRegistrado(nuevoCentro.getIdCentro()))
	            throw new Exception("El nuevo centro no está registrado");
	        comprobarValoresCentro(nuevoCentro);
        }

        Centro centroAnterior = usuario.getCentroActual();
        if (centroAnterior != null) centroAnterior.salirUsuarioActual(usuario.getIdUsuario());
        usuario.setCentroActual(nuevoCentro);
        if (nuevoCentro != null) nuevoCentro.addUsuarioActual(usuario.getIdUsuario());

        return null;
    }

}

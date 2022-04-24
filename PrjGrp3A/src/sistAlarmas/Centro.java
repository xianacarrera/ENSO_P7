package sistAlarmas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Centro implements ItfCentro {
    //Declaracion de variables
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
    private List<String> usuariosActuales;

    //Constructor
    protected Centro() {
        sensores = new HashMap<>();
        usuariosActuales = new ArrayList<>();
        coordenadas = new float[2];
        coordenadas[0] = coordenadas[1] = (float) 0.0;
    }

    //Método para añadir un usuario registrado al centro
    @Override
    public ItfCentro addUsuarioActual(String idUsuario) throws Exception {
        if (idUsuario == null) throw new Exception("Identificador de usuario no valido: no existe");
        if (usuariosActuales.contains(idUsuario)) throw new Exception("El usuario ya se encuentra en el centro");
        if (!GestorUsuarios.getInstancia().existeUsuario(idUsuario))
            throw new Exception("El usuario no est� registrado");

        usuariosActuales.add(idUsuario);
        return this;
    }

    //Método para eliminar un usuario del centro
    @Override
    public String salirUsuarioActual(String idUsuario) throws Exception {
        if (idUsuario == null) throw new Exception("Identificador no valido: es inexistente");
        if (!usuariosActuales.contains(idUsuario)) throw new Exception("El usuario no se encuentra en el centro");

        usuariosActuales.remove(idUsuario);
        return idUsuario;
    }

    //Método para comprobar los valores de un sensor
    private void comprobarValoresSensor(Sensor sensor) throws Exception {
        if (!ItfGestorId.checkIdSensor(sensor.getIdSensor()))
            throw new Exception("El identificador del sensor no es valido");
        if (sensor.getTipoSensor() == null) throw new Exception("Sensor no valido: no tiene tipo");
    }

    //Método para añadir un sensor a un centro
    @Override
    public ItfCentro addSensor(Sensor sensor) throws Exception {
        if (sensor == null) throw new Exception("Sensor no valido: es inexistente");
        if (sensores.containsKey(sensor.getIdSensor()))
            throw new Exception("El sensor ya habia sido registrado en este centro");
        if (sensor.getFechaInstalacion() != null || sensor.getCentro() != null)
            throw new Exception("El sensor ya ha sido asignado a un centro");
        comprobarValoresSensor(sensor);

        sensores.put(sensor.getIdSensor(), sensor);
        return this;
    }

    //Método para leer los datos de un sensor
    @Override
    public Sensor leerSensor(String idSensor) throws Exception {
        if (idSensor == null) throw new Exception("Identificador no valido: es inexistente");
        if (!sensores.containsKey(idSensor))
            throw new Exception("El identificador no se corresponde con ningun sensor registrado");

        return sensores.get(idSensor);
    }


    //Método para modificar los datos de un sensor registrado
    @Override
    public Sensor modificarSensor(Sensor sensor) throws Exception {
        if (sensor == null) throw new Exception("Sensor no valido: es inexistente");
        if (!sensores.containsKey(sensor.getIdSensor()))
            throw new Exception("El sensor no habia sido registrado previamente");
        comprobarValoresSensor(sensor);

        sensores.put(sensor.getIdSensor(), sensor);
        return sensor;
    }
    
    //Método para eliminar un sensor registrado
    @Override
    public Sensor borrarSensor(String idSensor) throws Exception {
        Sensor sensor;
        if (idSensor == null) throw new Exception("Identificador no valido: es inexistente");
        if (!sensores.containsKey(idSensor))
            throw new Exception("El identificador no se corresponde con ningun sensor registrado");
        if ((sensor = sensores.get(idSensor)) == null)
            throw new Exception("Error fatal: el sensor correspondiente al identificador no existe");

        sensores.remove(idSensor);   // Si no hab�a mapping o idSensor era null, no hace nada
        return sensor;                 // Devuelve null si el mapping era null o idSensor era null
    }

    //Método para eliminar todos los sensores registrados
    @Override
    public ItfCentro borrarTodosSensores() {
        sensores.clear();
        return this;
    }
    
    // M�todo para comprobar si en el centro hay un sensor de un tipo determinado
    @Override
	public boolean tieneSensor(TipoSensor tipo) {
		return sensores.values().stream().anyMatch(sensor -> sensor.getTipoSensor().equals(tipo));
	}

    

    //Método para añadir un identificador a un centro
    public Centro setIdCentro(String idCentro) throws Exception {
        if (this.idCentro != null) throw new Exception("Este centro ya tiene un identificador");
        if (!ItfGestorId.checkIdCentro(idCentro)) throw new Exception("Identificador de centro no valido");
        this.idCentro = idCentro;
        return this;
    }

    
    //Método para establecer el identificador de un centro
    public Centro setNombre(String nombre) throws Exception {
        if (nombre == null) throw new Exception("Nombre no valido: no existe");
        this.nombre = nombre;
        return this;
    }

    //Método para establecer el campus de un centro
    public Centro setCampus(String campus) throws Exception {
        if (campus == null) throw new Exception("Campus no valido: no existe");
        this.campus = campus;
        return this;
    }

    //Método para establecer el servicio
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    //Método para establecer las coordenadas de un centro
    public Centro setCoordenadas(float[] coordenadas) throws Exception {
        if (coordenadas.length != 2) return null;
        if (coordenadas[0] < -90 || coordenadas[0] > 90) throw new Exception("Latitud no valida");
        if (coordenadas[1] < -180 || coordenadas[1] > 180) throw new Exception("Longitud no valida");
        this.coordenadas = coordenadas;
        return this;
    }

    //Método para establecer la ciudad de un centro
    public Centro setCiudad(String ciudad) throws Exception {
        if (ciudad == null) throw new Exception("Ciudad no valida: no existe");
        this.ciudad = ciudad;
        return this;
    }

    //Método para establecer la calle de un centro
    public Centro setCalle(String calle) throws Exception {
        if (calle == null) throw new Exception("Calle no valida: no existe");
        this.calle = calle;
        return this;
    }

    //Método para establecer el número de la dirección de un centro
    public Centro setNumero(int numero) throws Exception {
        if (numero < 0) throw new Exception("El numero de la direccion debe ser un entero positivo");
        this.numero = numero;
        return this;
    }

    //Método para establecer el código postal de un centro
    public Centro setCodigoPostal(int codigoPostal) throws Exception {
        if (String.valueOf(codigoPostal).length() != 5) throw new Exception("Codigo postal no valido");
        if (codigoPostal < 0) throw new Exception("Codigo postal no valido");
        int prov = Integer.parseInt(String.valueOf(codigoPostal).substring(0, 2));
        if (prov < 1 || prov > 52) throw new Exception("Codigo postal no valido");
        this.codigoPostal = codigoPostal;
        return this;
    }

    /** GETTERS **/

    //Métodos para obtener los sensores
    public HashMap<String, Sensor> getSensores() {
        return sensores;
    }
    
    public List<Sensor> getListaSensores() {
        return sensores.values().stream().collect(Collectors.toList());
    }

    
    public float[] getCoordenadas() {
        return coordenadas;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getNumero() {
        return numero;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public String getServicio() {
        return servicio;
    }

    public String getCampus() {
        return campus;
    }

    public String getIdCentro() {
        return idCentro;
    }

    public String getNombre() {
        return nombre;
    }
    /**FIN GETTERS**/

    public static class Builder {
        private Centro centro;

        public Builder() {
            this.reset();
        }

        public Builder reset() {
            centro = new Centro();
            return this;
        }

        public Builder setCampus(String campus) throws Exception {
            if (centro == null) this.reset();
            centro.setCalle(campus);
            return this;
        }

        public Builder setServicio(String servicio) {
            if (centro == null) this.reset();
            centro.setServicio(servicio);
            return this;
        }

        public Builder setCoordenadas(float latitud, float longitud) throws Exception {
            if (centro == null) this.reset();
            float[] coord = {latitud, longitud};
            if (centro.setCoordenadas(coord) == null) return null;
            return this;
        }

        public Builder setCoordenadas(float[] coordenadas) throws Exception {
            if (centro == null) this.reset();
            if (centro.setCoordenadas(coordenadas) == null) return null;
            return this;
        }

        public Builder setCiudad(String ciudad) throws Exception {
            if (centro == null) this.reset();
            centro.setCiudad(ciudad);
            return this;
        }

        public Builder setCalle(String calle) throws Exception {
            if (centro == null) this.reset();
            centro.setCalle(calle);
            return this;
        }

        public Builder setNumero(int n) throws Exception {
            if (centro == null) this.reset();
            centro.setNumero(n);
            return this;
        }

        public Builder setCodigoPostal(int codigoPostal) throws Exception {
            if (centro == null) this.reset();
            centro.setCodigoPostal(codigoPostal);
            return this;
        }

        public Centro build() {
            if (centro == null || centro.getIdCentro() == null) return null;
            return centro;
        }
    }

    /** SOBREESCRITURA DE MÉTODOS **/
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
    /**FIN SOBREESCRITURA DE MÉTODOS**/
}

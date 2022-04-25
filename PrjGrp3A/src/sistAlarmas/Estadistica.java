package sistAlarmas;

import java.util.Date;

public class Estadistica {
    //Declaracion de variables
    private String tipo;
    private Float duracion;
    private Date fechaInsercion, fechaOcurrencia;		
    private String id;
    private String centro;

    //Constructor
    public Estadistica(String tipo, Float duracion, Date fechaInsercion, String id, String centro, Date fechaOcurrencia) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.fechaInsercion = fechaInsercion;
        this.id = id;
        this.centro = centro;
        this.fechaOcurrencia = fechaOcurrencia;
    }

    /**GETTERS**/

    public String getTipo() {
        return tipo;
    }

    public Float getDuracion() {
        return duracion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }
    public String getId() {
        return id;
    }
    public String getCentro() {
        return centro;
    }
    public Date getFechaOcurrencia() {
        return fechaOcurrencia;
    }
    /**FIN GETTERS**/

}
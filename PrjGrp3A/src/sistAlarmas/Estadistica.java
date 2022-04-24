package sistAlarmas;

import java.util.Date;

public class Estadistica {
    //Declaracion de variables
    private String tipo;
    private Float duracion;
    private Date fechaInsercion;
    private String id;
    private String centro;

    //Constructor
    public Estadistica(String tipo, Float duracion, Date fechaInsercion, String id, String centro) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.fechaInsercion = fechaInsercion;
        this.id = id;
        this.centro = centro;
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
    /**FIN GETTERS**/

}
package sistAlarmas;

import java.util.Date;

public class Estadistica {
    private String tipo;
    private Float duracion;
    private Date fechaInsercion;
    private String id;
    private String centro;


    public Estadistica(String tipo, Float duración, Date fechaInsercion, String id, String centro) {
        this.tipo = tipo;
        this.duración = duración;
        this.fechaInsercion = fechaInsercion;
        this.id = id;
        this.centro = centro;
    }

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
}
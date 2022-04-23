import java.util.Date;
import java.util.HashMap;

public class Estadisticas {
    private HashMap<Date, Estadistica> datos;

    public Estadisticas() {
        datos = new HashMap<Date, Estadistica>();
    }


    public void agregar(Date inicio, Date fin, Date fechaInsercion, String id) {
        String tipo = ItfGestorId.getTipo(id);
        Float duracion = Float.valueOf(fin.getTime() - inicio.getTime());
        Estadistica = new Estadistica(tipo, duracion, fechaInsercion);
        datos.put(fechaInsercion, Estadistica);
    }

    public int recuperarTotal(String tipo) {
        int contador = 0;
        for (Estadisticas e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                contador++;
            }
        }
        return contador;
    }

    public float mediaDuracion(String tipo) {
        float total = 0;
        int contador = 0;
        for (Estadisticas e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                total += e.getDuracion();
                contador++;
            }
        }
        return total / contador;
    }

    private boolean isValid
    public int distribucionTotal(String filtro, String tipo) {
        int contador;

        for (Estadisticas e : datos.values()) {
            if (e.getTipo().equals(tipo) ) {
                if(filtro)
                total += e.getDuracion();
                contador++;
            }
        }
        return contador;

    }

    public float distribucionMedia(String filtro, String tipo) {

    }

}
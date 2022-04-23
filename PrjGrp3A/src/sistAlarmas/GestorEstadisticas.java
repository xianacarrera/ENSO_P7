package sistAlarmas;

import java.util.Date;
import java.util.HashMap;

public class GestorEstadisticas implements ItfGestorEstadisticas {

    private static GestorEstadisticas instancia;
    private HashMap<Date, Estadistica> datos;
    final int MAXIMO_ANO = 2023;

    public GestorEstadisticas() {
        datos = new HashMap<Date, Estadistica>();
    }

    public static GestorEstadisticas getInstancia() {
        if (instancia == null) instancia = new GestorEstadisticas();
        return instancia;
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

    /**
     * MÉTODOS AUXILIARES PRIVADOS
     **/

    private int isMonth(String filtro) {
        private ArrayList<String> meses = new ArrayList<String>();
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");
        if (meses.contains(filtro)) {
            return meses.indexOf(filtro);
        }
        return null;
    }

    private int isYear(String filtro) {
        Integer valor = Integer.parseInt(filtro)
        if (valor >= 1 && valor <= MAXIMO_ANO) {
            return valor;
        }
        return null;
    }

    private String isCentro(String filtro) {
        return ItfGestorCentros.getCentros().contains(filtro) ? filtro : null;
    }

    /**
     * ==============================
     **/

    public int distribucionTotal(String filtro, String tipo) {
        int contador;

        for (Estadisticas e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                if (isMonth(filtro) != null || isYear(filtro) != null || isCentro(filtro) != null) {
                    if (e.getFecha().getMonth() == isMonth(filtro) || e.getFecha().getYear() == isYear(filtro) || e.getCentro().equals(filtro)) {
                        contador++;
                    }
                }
            }
            return contador;
        }
    }

    public float distribucionMedia(String filtro, String tipo) {
        float total = 0;
        int contador = 0;
        for (Estadisticas e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                if (isMonth(filtro) != null || isYear(filtro) != null || isCentro(filtro) != null) {
                    if (e.getFecha().getMonth() == isMonth(filtro) || e.getFecha().getYear() == isYear(filtro) || e.getCentro().equals(filtro)) {
                        total += e.getDuracion();
                        contador++;
                    }
                }
            }
            return total / contador;
        }
    }

}
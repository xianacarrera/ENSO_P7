package sistAlarmas;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GestorEstadisticas implements ItfGestorEstadisticas {
    //Declaraci�n de variables
    private static GestorEstadisticas instancia;
    private HashMap<String, Estadistica> datos;
    final int MAXIMO_ANO = 2023;

    //Constructor
    public GestorEstadisticas() {
        datos = new HashMap<String, Estadistica>();
    }

    //Patr�n Singleton
    public static GestorEstadisticas getInstancia() {
        if (instancia == null) instancia = new GestorEstadisticas();
        return instancia;
    }

    //M�todo para a�adir una estad�stica: el tipo se obtendr� a partir del id
    public void agregar(Date inicio, Date fin, Date fechaInsercion, String id, String centro) throws Exception {
        if (id == null) throw new Exception("El id no puede ser nulo");
        String tipo = ItfGestorId.getTipo(id);
        if (tipo == null) throw new Exception("El id no es v�lido -> Tipo no encontrado");
        if (inicio == null || fin == null || fechaInsercion == null) throw new Exception("Alguna fecha es nula");
        if (inicio.after(fin)) throw new Exception("La fecha de inicio no puede ser posterior a la fecha de fin");
        if (centro == null) throw new Exception("El centro no puede ser nulo");
        Float duracion = (float) (fin.getTime() - inicio.getTime());
        Estadistica e = new Estadistica(tipo, duracion, fechaInsercion, id, centro, inicio);
        datos.put(e.getId(), e);
    }

    //M�todo para recuperar el total de valores asociadas a un tipo
    public int recuperarTotal(String tipo) throws Exception {
        int contador = 0;
        if (datos.isEmpty()) throw new Exception("No hay datos");
        for (Estadistica e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                contador++;
            }
        }
        return contador;
    }

    //M�todo para calcular la duraci�n m�dia de un tipo
    public float mediaDuracion(String tipo) throws Exception {
        float total = 0;
        int contador = 0;
        if (datos.isEmpty()) throw new Exception("No hay datos");
        for (Estadistica e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                total += e.getDuracion();
                contador++;
            }
        }
        return total / contador;
    }

    /**
     * M�TODOS AUXILIARES PRIVADOS
     **/

    private Integer isMonth(String filtro) {
        ArrayList<String> meses = new ArrayList<String>();
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

    private Integer isYear(String filtro) {
        int valor = Integer.parseInt(filtro);
        if (valor >= 1 && valor <= MAXIMO_ANO) {
            return valor;
        }
        return null;
    }

    private String isCentro(String filtro) {
        return GestorCentros.getInstancia().getCentros().containsKey(filtro) ? filtro : null;
    }

    /**
     * ==============================
     **/

    //M�todo para calcular la distribuci�n total de un tipo en funci�n de un filtro: mes, a�o o centro
    public int distribucionTotal(String filtro, String tipo) throws Exception {
        int contador = 0;

        for (Estadistica e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                if (isMonth(filtro) != null || isYear(filtro) != null || isCentro(filtro) != null) {
                    if (e.getFechaOcurrencia().getMonth() == isMonth(filtro) || e.getFechaOcurrencia().getYear() == isYear(filtro) || e.getCentro().equals(filtro)) {
                        contador++;
                    }
                } else {
                    throw new Exception("El filtro no es v�lido");
                }
            }
        }
        return contador;
    }

    //M�todo para calcular la distribuci�n media de un tipo en funci�n de un filtro: mes, a�o o centro
    public float distribucionMedia(String filtro, String tipo) throws Exception {
        float total = 0;
        int contador = 0;
        for (Estadistica e : datos.values()) {
            if (e.getTipo().equals(tipo)) {
                if (isMonth(filtro) != null || isYear(filtro) != null || isCentro(filtro) != null) {
                    if (e.getFechaOcurrencia().getMonth() == isMonth(filtro) || e.getFechaOcurrencia().getYear() == isYear(filtro) || e.getCentro().equals(filtro)) {
                        total += e.getDuracion();
                        contador++;
                    }
                } else {
                    throw new Exception("El filtro no es v�lido");
                }
            }
        }
        return total / contador;
    }
}
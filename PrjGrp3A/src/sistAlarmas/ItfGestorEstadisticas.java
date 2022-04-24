package sistAlarmas;

import java.util.Date;

public interface ItfGestorEstadisticas {

    public void agregar(Date inicio, Date fin, Date fechaInsercion, String id);

    public int recuperarTotal(String tipo);

    public float mediaDuracion(String tipo);

    public int distribucionTotal(String filtro, String tipo);

    public float distribucionMedia(String filtro, String tipo);
}

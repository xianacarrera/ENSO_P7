package sistAlarmas;

import java.util.Date;

public interface ItfGestorEstadisticas {

    public void agregar(Date inicio, Date fin, Date fechaInsercion, String id, String centro) throws Exception; //

    public int recuperarTotal(String tipo) throws Exception;

    public float mediaDuracion(String tipo) throws Exception;

    public int distribucionTotal(String filtro, String tipo) throws Exception;

    public float distribucionMedia(String filtro, String tipo) throws Exception;
}

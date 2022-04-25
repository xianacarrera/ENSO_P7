package sistAlarmas;

public interface ItfProtocolo {
	ItfProtocolo addMensajeAccion(String msg) throws Exception;
	ItfProtocolo borrarMensajeAccion(String msg) throws Exception;
}

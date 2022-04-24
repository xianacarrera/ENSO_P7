package sistAlarmas;

public interface ItfProtocolo {
	Protocolo addMensajeAccion(String msg) throws Exception;
	Protocolo borrarMensajeAccion(String msg) throws Exception;
}

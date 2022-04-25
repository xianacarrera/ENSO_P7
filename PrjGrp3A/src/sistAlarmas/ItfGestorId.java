package sistAlarmas;

import java.util.Random;

public interface ItfGestorId {

    // Todos los métodos estáticos se pueden implementar directamente
    // en la interfaz desde Java 8


    /*
     * Las comprobaciones a realizar implementan los requerimientos de informacion
     * establecidos en el documento de analisis, disenho y planificacion. Por ello, se comprobara:
     * -> que el id sea distinto de nulo
     * -> la longitud del id
     * -> su formato
     */

    /**
     * FORMATO ESPERADO: "S-XX...XX"
     **/
    static boolean checkIdSensor(String idSensor) {
        // 1- IDENTIFICADOR NO NULO:
        if (idSensor == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idSensor.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idSensor.charAt(0) != 'S') {
            return false;
        }
        if (idSensor.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "A-XX...XX"
     **/
    static boolean checkIdAlarma(String idAlarma) {
        // 1- IDENTIFICADOR NO NULO:
        if (idAlarma == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idAlarma.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idAlarma.charAt(0) != 'A') {
            return false;
        }
        if (idAlarma.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "C-XX...XX"
     **/
    static boolean checkIdCentro(String idCentro) {
        // 1- IDENTIFICADOR NO NULO:
        if (idCentro == null) {
            return false;
        }
        // 2- LONGITUD (maximo 20):
        if (idCentro.length() > 20) {
            return false;
        }
        // 3- FORMATO:
        if (idCentro.charAt(0) != 'C') {
            return false;
        }
        if (idCentro.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "U-XX...XX"
     **/
    static boolean checkIdUsuario(String idUsuario) {
        // 1- IDENTIFICADOR NO NULO:
        if (idUsuario == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idUsuario.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idUsuario.charAt(0) != 'U') {
            return false;
        }
        if (idUsuario.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "E-XX...XX"
     **/
    static boolean checkIdEquipo(String idEquipo) {
        // 1- IDENTIFICADOR NO NULO:
        if (idEquipo == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idEquipo.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idEquipo.charAt(0) != 'E') {
            return false;
        }
        if (idEquipo.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "AC-XX...XX"
     **/
    // Metodo de elevada complejidad ciclomatica
    static boolean checkIdAccion(String idAccion) {
        // 1- IDENTIFICADOR NO NULO:
        if (idAccion == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idAccion.length() > 15) {
            return false;
        }
        // 3-FORMATO:
        if (idAccion.charAt(0) != 'A') {
            return false;
        }
        if (idAccion.charAt(1) != 'C') {
            return false;
        }
        if (idAccion.charAt(2) != '-') {
            return false;
        }
        return true;
    }


    /**
     * FORMATO ESPERADO: "V-XX...XX"
     **/

    static boolean checkIdVerificacion(String idVerif) {
        // 1- IDENTIFICADOR NO NULO:
        if (idVerif == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idVerif.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idVerif.charAt(0) != 'V') {
            return false;
        }
        if (idVerif.charAt(1) != '-') {
            return false;
        }
        return true;
    }

    /**
     * FORMATO ESPERADO: "P-XX...XX"
     **/

    static boolean checkIdProtocolo(String idProt) {
        // 1- IDENTIFICADOR NO NULO:
        if (idProt == null) {
            return false;
        }
        // 2- LONGITUD (maximo 15):
        if (idProt.length() > 15) {
            return false;
        }
        // 3- FORMATO:
        if (idProt.charAt(0) != 'P') {
            return false;
        }
        if (idProt.charAt(1) != '-') {
        	return false;
        }
        return true;
    }

    // Comprueba la clase a la que pertenece un identificador determinado
    static String getTipo(String id) {
        if (id == null) return null;
        switch (id.charAt(0)) {
            case 'S':
                return "Sensor";
            case 'A':
                if (id.charAt(1) == 'C') {
                    return "Accion";
                }
                return "Alarma";
            case 'C':
                return "Centro";
            case 'U':
                return "Usuario";
            case 'E':
                return "Equipo";
            case 'V':
                return "Verificacion";
            case 'P':
                return "Protocolo";
            default:
                return null;
        }
    }

    /**
     * GENERADOR DE IDENTIFICADORES
     **/

    static String generarId(String tipo) {
        if (tipo == null) return "";
        long num = (long) (Math.random() * Math.pow(10, 12));
        switch (tipo.charAt(0)) {
            case 'S':
                return "S-" + num;
            case 'A':
                if (tipo.charAt(1) == 'C') {
                    return "AC-" + num;
                }
                return "A-" + num;
            case 'C':
                return "C-" + (long) (Math.random() * Math.pow(10, 15));
            case 'U':
                return "U-" + num;
            case 'E':
                return "E-" + num;
            case 'V':
                return "V-" + num;
            case 'P':
                return "P-" + num;
            default:
                return "";
        }
    }

}

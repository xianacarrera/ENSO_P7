package sistAlarmas;

public interface ItfIdChecker {

    // Todos los método estáticos se pueden implementar directamente
    // en la interfaz desde Java 8


    //TODO AÑADIR AL DICCIONARIO LOS FORMATOS

    /*
     * LAS COMPROBACIONES A REALIZAR BUSCARÁN SER SIMILARES A LAS ESTABLECIDAS
     * EN EL DICCIONARIO DE DATOS ASOCIADO A LA PRÁCTICA. POR ELLO, SE COMPROBARÁ:
     * -> ID DISTINTO DE NULL
     * -> LONGITUD DEL IDENTIFICADOR
     * -> FORMATO DEL MISMO
     */

    /**
     * FORMATO ESPERADO: "S-XX...XX"
     **/
    static boolean checkIdSensor(String idSensor) {
        // 1- IDENTIFICADOR NO NULO:
        if (idSensor == null) {
            return false;
        }
        // 2- LONGITUD (máximo 15):
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
        // 2- LONGITUD (máximo 15):
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
        // 2- LONGITUD (máximo 20):
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
        // 2- LONGITUD (máximo 15):
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
        // 2- LONGITUD (máximo 15):
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
    static boolean checkIdAccion(String idAccion) {
        // 1- IDENTIFICADOR NO NULO:
        if (idAccion == null) {
            return false;
        }
        // 2- LONGITUD (máximo 15):
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

    /*TODO AÑADIR AL DICCIONARIO DE DATOS*/

    /**
     * FORMATO ESPERADO: "V-XX...XX"
     **/

    static boolean checkIdVerificacion(String idVerif) {
        // 1- IDENTIFICADOR NO NULO:
        if (idVerif == null) {
            return false;
        }
        // 2- LONGITUD (máximo 15):
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

}

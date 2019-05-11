/*
 * ultimas modificacione:
 * 01 de octubre de 2014: Occibiz Tecnologia - Nestor Marcano
 */
package com.delsur.integradoresSMS;

import com.bds.ws.util.DAOUtil;
import com.delsur.integradoresSMS.excepciones.IntegradorNoConfiguradoException;
import java.lang.reflect.Constructor;
import oracle.jdbc.OracleTypes;

//import com.sitec.pcorp.clases.Utilitarios;
/**
 * Implementa una version simple del patron de diseno "Factory Method".
 */
public class FabricaDeIntegradores extends DAOUtil {

    /**
     * Devuelve un integrador, de los posibles existentes, que este activo.
     * Realiza un balanceo de tal manera que, cada vez que se invoca, devuelve
     * un integrador SMS distinto.
     *
     * Llama al procedimiento almacenado
     * WEB_K_INTEGRADORES_SMS.obtenerIntegradorSMS
     *
     * @param numTelefonico
     * @param mensaje
     *
     * @return IntegradorSMS1
     * @throws com.delsur.integradoresSMS.excepciones.IntegradorNoConfiguradoException
     */
    public IntegradorSMS obtenerIntegrador(String numTelefonico, String mensaje)
            throws IntegradorNoConfiguradoException {
        try {
            // ---------------------------------------------------- Prepara e invoca el procedimiento almacenado
            // -------------------------------------------------------------------------------------------------
            this.conectarJNDI(JNDI_ORACLE_9, "BMP-03-01");
            this.statement = conn.prepareCall("CALL WEB_K_INTEGRADORES_SMS.obtenerIntegradorSMS(?,?,?,?)");

            this.statement.setString(1, numTelefonico);                  // Numero telefonico
            this.statement.registerOutParameter(2, OracleTypes.INTEGER); // ID del integrador
            this.statement.registerOutParameter(3, OracleTypes.VARCHAR); // Nombre del integrador
            this.statement.registerOutParameter(4, OracleTypes.NUMBER);  // Numero de la solicitud
            this.statement.execute();

            int id = this.statement.getInt(2);
            String nombreIntegrador = new String(this.statement.getBytes(3), CHARSET_ORACLE_9);
            int numSolicitud = this.statement.getInt(4);

            String primerCharNombreIntegrador = "" + nombreIntegrador.charAt(0);
            String sigCharsNombreIntegrador = nombreIntegrador.substring(1).toLowerCase();

            // ------------------------------------------- Nombre del integrador con primera letra  en minuscula
            // -------------------------------------------------------------------------------------------------
            nombreIntegrador = new String(primerCharNombreIntegrador.toLowerCase()) + sigCharsNombreIntegrador;

            // ------------------------------------------- Nombre del integrador con primera letra  en mayuscula
            // -------------------------------------------------------------------------------------------------
            String NombreIntegrador = new String(primerCharNombreIntegrador.toUpperCase()) + sigCharsNombreIntegrador;

            // ----------------------------------------------------------- Construye la instancia del integrador
            // -------------------------------------------------------------------------------------------------
            String pathIntegrador = "com.delsur.integradoresSMS." + nombreIntegrador + "." + NombreIntegrador;
            Class<?> integrador = Class.forName(pathIntegrador);
            Class<?>[] constrpartypes = new Class<?>[]{int.class, String.class, String.class, int.class};
            Constructor<?> constr;
            constr = integrador.getConstructor(constrpartypes);
            return (IntegradorSMS) constr.newInstance(new Object[]{id, numTelefonico, mensaje, numSolicitud});
        } catch (Exception e) {
            e.printStackTrace();
            throw new IntegradorNoConfiguradoException(e.getMessage());
        } // --------------------------------------------------------------------------------------- Finalizacion
        // ----------------------------------------------------------------------------------------------------
        finally {
            this.cerrarConexion("BMP-03-01");
        }
    }
}

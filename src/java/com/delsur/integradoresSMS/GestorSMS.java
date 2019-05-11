/*
 * ultimas modificacione:
 * 28 de agosto de 2014: Occibiz Tecnologia - Nestor Marcano
 * 01 de octubre de 2014: Occibiz Tecnologia - Nestor Marcano
 */
package com.delsur.integradoresSMS;

import com.bds.ws.util.DAOUtil;
import com.delsur.integradoresSMS.excepciones.FallaInternaSMSException;
import com.delsur.integradoresSMS.excepciones.IntegradorNoConfiguradoException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//import com.sitec.pcorp.clases.Utilitarios;
public class GestorSMS extends DAOUtil {

    private final int ENVIO_OK = 0;
    private final int SIN_COMUNICACION = -100;
    private final int NO_EXISTE_INTEGRADOR_ACTIVO = -101;
    private final int INTERGRADOR_NO_CONFIGURADO = -102;

    private ExecutorService executorService = null;
    private String primerIntegrador = "";
    private String numeroTelefonico = "";
    private String mensaje = "";
    private int numSolicitud;
    private int respuesta = -1;
    private int idIntegrador;

    public GestorSMS() throws Exception {
        executorService = Executors.newSingleThreadExecutor();
    }

    // -------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------- enviarSMS
    // -------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------
    /**
     * Envia un SMS a traves de uno de los integradores de este servicio
     * (Tedexis, Ogangi, etc), esperando un maximo de 5 segundos por la
     * respuesta.
     *
     * La solicitud consiste de: - Un numero telefonico (al cual se le va a
     * enviar el mensaje). - Una clave dinamica o Token (OTP). - Un texto breve
     * con informacion preestablecida.
     *
     * El metodo maneja internamente el usuario y la contrasena de conexion a
     * cada uno de los servicios de cada integrador (Tedexis, Ogangi, etc).
     *
     * Cada solicitud se envia al siguiente integrador que este activo en la
     * lista de integradores. Al llegar al final de la lista se comienza
     * nuevamente, en forma ciclica, con el primero.
     *
     * Existen tres fallas detectables al enviar el mensaje a los integradores:
     * 1) Que no exista conexion con el integrador. En este caso, se tomara el
     * siguiente integrador activo en la lista y se deja un registro en el log
     * (tabla CSIC_SOL_ENVIOS_SMS) con estatus "F" (Falla de comunicacion). 2)
     * Que el integrador no responda en el tiempo estimado. Se deja un registro
     * en el log (tabla CSIC_SOL_ENVIOS_SMS) con estatus "T" (Timeout). 3) Que
     * el integrador no este bien configurado. Se deja un registro con estatus
     * "I".
     *
     * @author DELSUR - Nelson Roas
     * @author Occibiz Tenologia - Nestor Marcano Se disminuyo el tiempo de
     * espera a 5 segundos. (Modificacion: 28-08-2014)
     */
    // -------------------------------------------------------------------------------------------------------
    public void enviarSMS() throws TimeoutException, IntegradorNoConfiguradoException,
            FallaInternaSMSException {
        Future future = executorService.submit(new Runnable() {
            IntegradorSMS integrador;

            public void run() {
                try {
                    FabricaDeIntegradores fabintegradores = new FabricaDeIntegradores();
                    integrador = fabintegradores.obtenerIntegrador(numeroTelefonico, mensaje);
                    if ("".equals(primerIntegrador)) {
                        primerIntegrador = integrador.getNombre();
                    } else if (integrador.getNombre().equals(primerIntegrador)) {
                        respuesta = NO_EXISTE_INTEGRADOR_ACTIVO; // No existe un integrador activo.
                        return;
                    }
                    idIntegrador = integrador.getId();
                    numSolicitud = integrador.getNumSolicitud();
                    integrador.conectarConServidor();
                    respuesta = integrador.sendSMS();
                } // ---------------------------------------------------------------------------- manejo de errores
                // ----------------------------------------------------------------------------------------------
                // ----------------------------------------------------------------------------------------------
                // -------------------------------------------------------- El integrador esta mal configurado...
                // ----------------------------------------------------------------------------------------------
                catch (IntegradorNoConfiguradoException e) {
                    respuesta = INTERGRADOR_NO_CONFIGURADO; // El integrador no ha sido configurado.
                    e.printStackTrace();
                } // ----------------------------------------------------- No hubo comunicacion con el integrador..
                // ----------------------------------------------------------------------------------------------
                catch (RemoteException e) {
                    try {
                        registrarEstatusSolSMS("F"); /* Rechazo tipo F (falla). No hubo
                         conexion con el integrador. */

                        respuesta = SIN_COMUNICACION; // Falla de comunicacion con el integrador.
                    } catch (FallaInternaSMSException f) {
                        f.printStackTrace();
                    }
                }
                /*catch (FallaInternaSMSException e)
                 {
                 try
                 {
                 registrarEstatusSolSMS("I"); /* Estatus I (falla interna). 
                 respuesta = INTERGRADOR_NO_CONFIGURADO; // Falla de configuracion del integrador.
                 }
                 catch (FallaInternaSMSException f)
                 {
                 f.printStackTrace();
                 }
                 }*/
            }
        });
        try {
            // Object result = future.get(30, TimeUnit.SECONDS);
            Object result = future.get(5, TimeUnit.SECONDS); // (Modificacion: 28-08-2014)

            if (respuesta == ENVIO_OK) {
                registrarEstatusSolSMS("E");
            }

            if (respuesta == NO_EXISTE_INTEGRADOR_ACTIVO) {
                throw new FallaInternaSMSException("No existe ningun integrador Activo");
            } else if (respuesta == SIN_COMUNICACION) {
                enviarSMS(); // Si no hubo comunicacion con el integrador, se intenta con el siguiente.
            }
        } // ---------------------------------------------------------------------------------- Manejo de errores
        // ----------------------------------------------------------------------------------------------------
        catch (TimeoutException ex) {
            registrarEstatusSolSMS("T"); // Rechazo por timeout
            throw ex;
        } catch (InterruptedException e) {
            registrarEstatusSolSMS("I"); // Falla interna
            e.printStackTrace();
            throw new FallaInternaSMSException(e.getMessage());
        } catch (ExecutionException e) {
            registrarEstatusSolSMS("I"); // Falla interna
            e.printStackTrace();
            throw new FallaInternaSMSException(e.getMessage());
        } finally {
            future.cancel(true);
        }
    }

    /**
     * Registra, en la base de datos, el estatus de una solicitud SMS.
     *
     * @param estatus Estatus a registrar: E: Enviado. T: Rechazo por timeout.
     * F: Falla de conexion con el integrador. I: Falla interna.
     *
     * @throws com.delsur.integradoresSMS.excepciones.FallaInternaSMSException
     */
    public void registrarEstatusSolSMS(String estatus) throws FallaInternaSMSException {
        //Connection conn = null;
        if (numSolicitud == 0) {
            return;
        }
        try {
            this.conectarJNDI(JNDI_ORACLE_9, "BMP-03-01");
            this.statement = this.conn.prepareCall("CALL WEB_K_INTEGRADORES_SMS.RegistrarEstatusSolSMS(?,?,?,?)");

            this.statement.setInt(1, idIntegrador);
            this.statement.setString(2, numeroTelefonico);
            this.statement.setInt(3, numSolicitud);
            this.statement.setString(4, estatus);
            this.statement.execute();
        } // ---------------------------------------------------------------------------------- Manejo de errores
        // ----------------------------------------------------------------------------------------------------
        catch (SQLException e) {
            e.printStackTrace();
            throw new FallaInternaSMSException(e.getMessage());
        } // --------------------------------------------------------------------------------------- Finalizacion
        // ----------------------------------------------------------------------------------------------------
        finally {
            this.cerrarConexion("BMP-03-01");
        }
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public int getRespueta() {
        return respuesta;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

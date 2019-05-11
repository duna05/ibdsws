/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.SMSDAO;
import com.bds.ws.dto.RespuestaDTO;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.delsur.integradoresSMS.GestorSMS;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz SMSDAO
 * @author cesar.mujica
 */
@Named("SMSDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class SMSDAOImpl implements SMSDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(SMSDAOImpl.class.getName());

    /**
     * Metodo que se encarga de realizar un envio de SMS
     *
     * @param numeroCelular numero del destinatario
     * @param mensajeTexto texto del mensaje a enviar
     * @param idCanal identificador del canal
     * @return RespuestaDTO objeto con el estatus de la operacion
     */
    @Override
    public RespuestaDTO enviarSMS(String numeroCelular, String mensajeTexto, String idCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            //se instancia esta clase ya que pertenece a un modulo de un tercero
            GestorSMS gestorSMS = new GestorSMS();
            gestorSMS.setNumeroTelefonico(numeroCelular);
            gestorSMS.setMensaje(mensajeTexto);
            gestorSMS.enviarSMS();
            if (gestorSMS.getRespueta() != 0) {
                respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
                respuesta.setCodigoSP(String.valueOf(gestorSMS.getRespueta()));
            }

        } catch (Throwable e) {//se maneja con throwable xq las excepciones que maneja el proveedor no extienden de Exception
            logger.error( new StringBuilder("ERROR DAO EN enviarSMS: ")
                    .append("TLF-").append(numeroCelular)
                    .append("-CH-").append(idCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN enviarSMS: ")
//                    .append("TLF-").append(numeroCelular)
//                    .append("-CH-").append(idCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }
}

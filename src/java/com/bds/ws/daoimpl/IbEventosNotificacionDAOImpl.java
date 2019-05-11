/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbEventosNotificacionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbEventosNotificacionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbEventosNotificacionFacade;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.faneite
 */
@Named("IbEventosNotificacionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)    
public class IbEventosNotificacionDAOImpl extends DAOUtil implements IbEventosNotificacionDAO{
    
    @EJB
    IbEventosNotificacionFacade eventosNotificacionFacade;
    
    private static final Logger logger = Logger.getLogger(IbEventosNotificacionDAOImpl.class.getName());

    /**
     * Metodo que obtiene el listado de eventos
     * @param codigoCanal codigo del canal para su registro en el log
     * @return IbEventosNotificacionDTO con listado
     */
    @Override
    public IbEventosNotificacionDTO listaEventos(String codigoCanal) {
        IbEventosNotificacionDTO eventosNotificacionDTO = new IbEventosNotificacionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        
        try{
            
            if(codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")){
               
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            eventosNotificacionDTO.setEventosNotificacion(eventosNotificacionFacade.findAll());
            eventosNotificacionDTO.setRespuesta(new RespuestaDTO());
            
            if (eventosNotificacionDTO.getEventosNotificacion().isEmpty()){                
                throw new NoResultException();
            }
        
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listaEventos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());            
        
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listaEventos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }finally{
            eventosNotificacionDTO.setRespuesta(respuesta);
        }        
//        if (eventosNotificacionDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listaEventos: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        } 
        return eventosNotificacionDTO;
    }
}

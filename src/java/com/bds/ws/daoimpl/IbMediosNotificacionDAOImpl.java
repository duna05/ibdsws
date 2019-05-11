/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbMediosNotificacionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbMediosNotificacionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbMediosNotificacionFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
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
@Named("IbMediosNotificacionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbMediosNotificacionDAOImpl extends DAOUtil implements IbMediosNotificacionDAO{
    
    @EJB
    IbMediosNotificacionFacade mediosNotificacionFacade;
    
    private static final Logger logger = Logger.getLogger(IbMediosNotificacionDAOImpl.class.getName());

    /**
     * Metodo que obtiene el listado de medios
     * @param codigoCanal codigo del canal para su registro en el log
     * @return IbMediosNotificacionDTO con listado
     */
    @Override
    public IbMediosNotificacionDTO listaMedios(String codigoCanal) {
        
        IbMediosNotificacionDTO mediosNotificacionDTO = new IbMediosNotificacionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        
        try{
            
            if(codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")){                
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            mediosNotificacionDTO.setMediosNotificacion(mediosNotificacionFacade.findAll());
            mediosNotificacionDTO.setRespuesta(new RespuestaDTO());
            
            if (mediosNotificacionDTO.getMediosNotificacion().isEmpty()){                
                throw new NoResultException();
            }
        
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listaMedios: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());       
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listaMedios: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }finally{            
            mediosNotificacionDTO.setRespuesta(respuesta);//revisar el log 
        }
//        if (mediosNotificacionDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listaMedios: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }                    
        return mediosNotificacionDTO;
    }
}

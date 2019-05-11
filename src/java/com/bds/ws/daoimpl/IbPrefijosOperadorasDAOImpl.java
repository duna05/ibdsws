/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPrefijosOperadorasDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbPrefijosOperadorasDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbPrefijosOperadorasFacade;
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
 * Clase que implementa IbPrefijosOperadorasDAO
 * @author juan.faneite
 */
@Named("IbPrefijosOperadorasDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbPrefijosOperadorasDAOImpl extends DAOUtil implements IbPrefijosOperadorasDAO{

    @EJB
    IbPrefijosOperadorasFacade prefijosOperadorasFacade;
    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbPrefijosOperadorasDAOImpl.class.getName());
    
    /**
     * Metodo para obtener el listado de prefijos de operadoras
     * @param nombreCanal nombre del canal
     * @return IbPrefijosOperadorasDTO Listado de prefijos de operadoras
     */
    @Override
    public IbPrefijosOperadorasDTO listaPrefijosOperadoras(String nombreCanal) {
        IbPrefijosOperadorasDTO prefijosOperadorasDTO = new IbPrefijosOperadorasDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        
        try{
            
            if(nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
               respuesta.setCodigo("DAO008");
               throw new DAOException("DAO008"); 
            }
            
            prefijosOperadorasDTO.setPrefijosOperadoras(prefijosOperadorasFacade.findAll());
            prefijosOperadorasDTO.setRespuesta(new RespuestaDTO());
            
            if (prefijosOperadorasDTO.getPrefijosOperadoras().isEmpty()){                
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listaPrefijosOperadoras: ")//.append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
                        
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listaPrefijosOperadoras: ")//.append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }finally{            
            prefijosOperadorasDTO.setRespuesta(respuesta);//revisar el log
        }
//        if (prefijosOperadorasDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN listaPrefijosOperadoras: ")//.append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return prefijosOperadorasDTO;
    }
    
}

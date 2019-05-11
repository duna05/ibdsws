/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPeriodoClaveDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbPeriodoClaveDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbPeriodoClaveFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbPeriodoClaveDAO
 * @author juan.faneite
 */
@Named("IbPeriodoClaveDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbPeriodoClaveDAOImpl extends DAOUtil implements IbPeriodoClaveDAO{

    @EJB
    IbPeriodoClaveFacade periodoClaveFacade;
    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbPeriodoClaveDAOImpl.class.getName());
    
    /**
     * Metodo que obtiene el listado de periodos de clave
     * @param nombreCanal nombre del canal
     * @return IbPeriodoClaveDTO listado de claves
     */
    @Override
    public IbPeriodoClaveDTO listaPeriodoClave(String nombreCanal) {
        IbPeriodoClaveDTO periodoClaveDTO = new IbPeriodoClaveDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            
            if(nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                throw new DAOException("DAO008");
            }
            
            periodoClaveDTO = periodoClaveFacade.obtenerListadoPeriodosClave(nombreCanal);
            
            if (!periodoClaveDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }
            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listaPeriodoClave: ")//.append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            periodoClaveDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (periodoClaveDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listaPeriodoClave: ")//.append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        periodoClaveDTO.setRespuesta(respuestaDTO);
        return periodoClaveDTO;
    }
    
}

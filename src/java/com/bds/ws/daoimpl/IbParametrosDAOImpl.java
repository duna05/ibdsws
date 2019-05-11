/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbParametrosDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbParametrosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbParametrosFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbParametrosDAO
 * @author rony.rodriguez
 */
@Named("IbParametrosDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbParametrosDAOImpl extends DAOUtil implements IbParametrosDAO {

    private static final Logger logger = Logger.getLogger(IbMenuDAOImpl.class.getName());

    @EJB
    private IbParametrosFacade ibParametrosFacade;

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO Contiene el valor de un parametro especifico
     */
    @Override
    public IbParametrosDTO consultaParametro(String codigoParametro, String nombreCanal) {
        IbParametrosDTO IbParametrosDTO = new IbParametrosDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {            
            IbParametrosDTO = ibParametrosFacade.consultaParametro(codigoParametro, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultaParametro: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            IbParametrosDTO.setRespuesta(respuesta);

        }
//        if (IbParametrosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultaParametro: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return IbParametrosDTO;
    }

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO
     */
    @Override
    public IbParametrosDTO consultaParametro(String codigoParametro, BigDecimal idCanal, String nombreCanal) {
        IbParametrosDTO IbParametrosDTO = new IbParametrosDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {

            IbParametrosDTO = ibParametrosFacade.consultaParametro(codigoParametro, idCanal, nombreCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultaParametro: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            IbParametrosDTO.setRespuesta(respuesta);

        }
//        if (IbParametrosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultaParametro: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return IbParametrosDTO;
    }
}

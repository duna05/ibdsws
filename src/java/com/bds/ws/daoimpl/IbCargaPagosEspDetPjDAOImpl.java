/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCargaPagosEspDetPjDAO;
import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbCargaPagosEspDetPjFacade;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author robinson.rodriguez
 */
@Named("IbCargaPagosEspDetPjDAO")
@Stateless
public class IbCargaPagosEspDetPjDAOImpl extends BDSUtil implements IbCargaPagosEspDetPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCargaPagosEspDetPjDAOImpl.class.getName());

    @EJB
    private IbCargaPagosEspDetPjFacade ibCargaPagosEspDetPjFacade;

    @Override
    public IbCargaPagosEspDetPjDTO listarIbCargaPagosEspDetPjDTO(BigDecimal idpago, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaPagosEspDetPjDTO ibCargaPagosEspDetPjDTO = new IbCargaPagosEspDetPjDTO();
        try {
            ibCargaPagosEspDetPjDTO = this.ibCargaPagosEspDetPjFacade.listarIbCargaPagosEspDetPj(idpago, estatusCarga);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaPagosEspDetPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosEspDetPjDTO == null || ibCargaPagosEspDetPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosEspDetPjDTO = new IbCargaPagosEspDetPjDTO();
                ibCargaPagosEspDetPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosEspDetPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibCargaPagosEspDetPjDTO;
    }

    @Override
    public UtilDTO insertarIbCargaPagosEspPj(IbCargaPagosEspDetPj detalle, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            if (detalle != null) {
                respuesta = this.ibCargaPagosEspDetPjFacade.crearPJ(detalle);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR insertarIbCargaPagosEspDetPj");
                }
                resultado.put("ibCargaPagosEspDetPj", detalle);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaPagosEspDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaPagosEspDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;

    }

    @Override
    public UtilDTO modificarEstatusIbCargaPagosEspDetPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            if(ibEstatusPagosPjDTO != null){
                int resultado = this.ibCargaPagosEspDetPjFacade.modificarEstatusDetalle(idPago, ibEstatusPagosPjDTO.getIbEstatusPagosPj().getId());
                if(resultado <=  0){
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosEspDetPj");
                }
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
            }
        } 
        catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR modificarEstatusIbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR modificarEstatusIbCargaPagosEspPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN MODIFICAR modificarEstatusIbCargaPagosEspPj: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;

    }
}

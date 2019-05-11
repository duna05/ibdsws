/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCargaNominaDetallePjDAO;
import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbCargaNominaDetallePjFacade;
import com.bds.ws.model.IbCargaNominaDetallePj;
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
 * @author juan.vasquez
 */
@Named("IbCargaNominaDetallePjDAO")
@Stateless
public class IbCargaNominaDetallePjDaoImpl extends BDSUtil implements IbCargaNominaDetallePjDAO{
    
     /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCargaNominaDetallePjDaoImpl.class.getName());
    
    @EJB
    private IbCargaNominaDetallePjFacade ibCargaNominaDetallePjFacade;

    @Override
    public UtilDTO insertarIbCargaNominaDetallePj(IbCargaNominaDetallePj ibCargaNominaDetallePj, String codigoCanal) {
         RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            if(ibCargaNominaDetallePj != null){
                respuesta = this.ibCargaNominaDetallePjFacade.crearPJ(ibCargaNominaDetallePj);
                if(!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                    throw new Exception("ERROR AL INSERTAR insertarIbCargaNominaDetallePj");
                }
                resultado.put("ibCargaNominaDetallePj", ibCargaNominaDetallePj);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } 
        catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(),e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }
    
    @Override
    public UtilDTO insertarIbCargaNominaDetallePj(IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO, String codigoCanal) {
        return insertarIbCargaNominaDetallePj(ibCargaNominaDetallePjDTO.getIbCargaNominaDetallePj(), codigoCanal);
    }

    @Override
    public UtilDTO modificarEstatusIbCargaNominaDetallePj(BigDecimal idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            if(ibEstatusPagosPjDTO != null){
                int resultado = this. ibCargaNominaDetallePjFacade.modificarEstatusDetalle(idPago, ibEstatusPagosPjDTO.getIbEstatusPagosPj().getId());
                if(resultado <=  0){
                    throw new Exception("ERROR AL MODIFICAR IbCargaNominaDetallePj");
                }
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
            }
        } 
        catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN MODIFICAR IbCargaNominaDetallePj: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    @Override
    public IbCargaNominaDetallePjDTO listarIbCargaNominaDetallePj(BigDecimal idpago, Short estatusCarga, String codigoCanal) {
        IbCargaNominaDetallePjDTO ibCargaNominaDetallePjDTO = new IbCargaNominaDetallePjDTO();
        try {
            ibCargaNominaDetallePjDTO = this.ibCargaNominaDetallePjFacade.listarCargaNominaDetalle(idpago, estatusCarga);            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(),e);            
            if(ibCargaNominaDetallePjDTO==null){
                ibCargaNominaDetallePjDTO = new IbCargaNominaDetallePjDTO();
                ibCargaNominaDetallePjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaNominaDetallePjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
        return ibCargaNominaDetallePjDTO;
    }
    
}

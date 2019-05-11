/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCargaPagosCorpDetPjDAO;
import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbCargaPagosCorpDetPjFacade;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbCargaPagosEspCorpPjDAO")
@Stateless
public class IbCargaPagosCorpDetPjDAOImpl extends BDSUtil implements IbCargaPagosCorpDetPjDAO {

    private static final Logger logger = Logger.getLogger(IbCargaPagosCorpDetPjDAOImpl.class.getName());

    @EJB
    private IbCargaPagosCorpDetPjFacade ibCargaPagosCorpDetPjFacade;

    @Override
    public IbCargaPagosCorpDetPjDTO listarIbCargaPagosCorpDetPjDTO(BigDecimal idpago, Short estatusCarga, String idCanal, String codigoCanal) {
        IbCargaPagosCorpDetPjDTO ibCargaPagosCorpDetPjDTO = new IbCargaPagosCorpDetPjDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {
            List<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjList = this.ibCargaPagosCorpDetPjFacade.listarIbCargaPagosEspDetPj(idpago, estatusCarga);
            ibCargaPagosCorpDetPjDTO.setIbCargaPagosCorpDetPjsList(ibCargaPagosCorpDetPjList);
            
        } catch (Exception e) {

            Logger.getLogger(IbCargaPagosCorpDetPjDAOImpl.class.getName()).log( null, e);

            logger.error( new StringBuilder("ERROR DAO en IbCargaPagosCorpDetPjDAOImpl: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibCargaPagosCorpDetPjDTO == null || ibCargaPagosCorpDetPjDTO.getRespuestaDTO() == null) {
                ibCargaPagosCorpDetPjDTO = new IbCargaPagosCorpDetPjDTO();
                ibCargaPagosCorpDetPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibCargaPagosCorpDetPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
        finally{
         ibCargaPagosCorpDetPjDTO.setRespuestaDTO(respuestaDTO);
         }

        return ibCargaPagosCorpDetPjDTO;
    }

    @Override
    public UtilDTO insertarIbCargaPagosCorpDetPj(IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();

        try {
            if (ibCargaPagosCorpDetPj != null) {

                respuesta = this.ibCargaPagosCorpDetPjFacade.crearPJ(ibCargaPagosCorpDetPj);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception("ERROR AL INSERTAR insertarIbCargaPagosCorpPj");
                }
                resultado.put("IbCargaPagosCorpPj", ibCargaPagosCorpDetPj);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbCargaPagosCorpPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;

    }

    @Override
    public UtilDTO modificarEstatusIbCargaPagosCorpDetPj(Long idPago, IbEstatusPagosPjDTO ibEstatusPagosPjDTO, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        try {
            if (ibEstatusPagosPjDTO != null) {
                int resultado = this.ibCargaPagosCorpDetPjFacade.modificarEstatusDetalle(idPago, ibEstatusPagosPjDTO.getIbEstatusPagosPj().getId());
                if (resultado <= 0) {
                    throw new Exception("ERROR AL MODIFICAR IbCargaPagosCorpDetPj");
                }
                respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                utilDTO.setRespuesta(respuesta);
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR modificarEstatusIbCargaPagosCorpDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR modificarEstatusIbCargaPagosCorpDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN MODIFICAR modificarEstatusIbCargaPagosCorpDetPj: ")
//                    .append("-CH- ").append(codigoCanal)
//                    .append("-DT- ").append(new Date())
//                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbSecuenciaAprobacionPjDAO;
import com.bds.ws.dto.IbSecuenciaAprobacionPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbSecuenciaAprobacionPjFacade;
import com.bds.ws.facade.IbServiciosPjFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbSecuenciaAprobacionPj;
import com.bds.ws.model.IbServiciosPj;
import com.bds.ws.model.IbUsuariosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_FALLIDO_SP;
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
 * @author christian.gutierrez
 */
@Named("IbSecAprobacionPjDAO")
@Stateless
public class IbSecuenciaAprobacionPjDaoImpl extends BDSUtil implements IbSecuenciaAprobacionPjDAO {

    /**
     * Log de sistema.
     */
    
    private static final Logger logger = Logger.getLogger(IbSecuenciaAprobacionPjDaoImpl.class.getName());

    @EJB
   private  IbSecuenciaAprobacionPjFacade ibSecuenciaAprobacionPjFacade;
    @EJB
   private IbServiciosPjFacade ibServiciosPjFacade;
    @EJB
   private IbUsuariosPjFacade ibUsuariosPjFacade;
    

    @Override
    public UtilDTO insertarSecuenciaDeAprobacion(BigDecimal idServicio, Long idTransaccion, BigDecimal idUsuarioPj) {
        RespuestaDTO respuesta = new RespuestaDTO();
            UtilDTO utilDTO = new UtilDTO();
        try {
            IbSecuenciaAprobacionPj ibSecuenciaAprobacionPj = new IbSecuenciaAprobacionPj();
            IbUsuariosPj UsuarioCreacion = this.ibUsuariosPjFacade.consultarUsuarioPjPorId(idUsuarioPj);
            ibSecuenciaAprobacionPj.setCodigoUsuarioCreacion(UsuarioCreacion);
            ibSecuenciaAprobacionPj.setIdUsuarioPj(UsuarioCreacion); 
            
            IbServiciosPj IbServiciosPj = new IbServiciosPj();
                    IbServiciosPj = ibServiciosPjFacade.findById2(idServicio);
            ibSecuenciaAprobacionPj.setIdServicioPj(IbServiciosPj);
            //completar
            ibSecuenciaAprobacionPj.setIdTransaccion(idTransaccion);
            // Fecha de Creación 
            ibSecuenciaAprobacionPj.setFechaHoraCreacion(new Date());
            this.ibSecuenciaAprobacionPjFacade.crearPJ(ibSecuenciaAprobacionPj);

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        return utilDTO;
    }
      
    public UtilDTO buscarAprobacionesMultiples(BigDecimal idServicio,Long idTransaccion){
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultado = new HashMap();
        List<IbSecuenciaAprobacionPj> ibSecuenciaAprobacionPjList = null;
        Integer numAutorizaciones;
        UtilDTO utilDTO = new UtilDTO();
        try {
            ibSecuenciaAprobacionPjList = this.ibSecuenciaAprobacionPjFacade.buscarAprobacionesMultiples(idServicio, idTransaccion);
            if(ibSecuenciaAprobacionPjList!=null){
                resultado.put("ibSecuenciaAprobacionPjList", ibSecuenciaAprobacionPjList);
                numAutorizaciones = ibSecuenciaAprobacionPjList.size();
                if(numAutorizaciones>0){
                   resultado.put("numAutorizaciones", numAutorizaciones);
                   respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                   respuesta.setTextoSP(DESCRIPCION_RESPUESTA_EXITOSO_SP);
                   respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
                   respuesta.setDescripcion(DESCRIPCION_RESPUESTA_EXITOSO_SP);
                }else{
                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA); 
                }
            }else{
            respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDO_SP);
            respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA); 
            }

        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN MODIFICAR IbCargaNominaPj: ")
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuesta);
        utilDTO.setResulados(resultado);
        return utilDTO;
    }
    
    @Override
    public UtilDTO insertarSecuenciaDeAprobacion(IbSecuenciaAprobacionPjDTO ibSecuenciaAprobacionPjDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IbSecuenciaAprobacionPjFacade getIbSecuenciaAprobacionPjFacade() {
        return ibSecuenciaAprobacionPjFacade;
    }

    public void setIbSecuenciaAprobacionPjFacade(IbSecuenciaAprobacionPjFacade ibSecuenciaAprobacionPjFacade) {
        this.ibSecuenciaAprobacionPjFacade = ibSecuenciaAprobacionPjFacade;
    }

    public IbServiciosPjFacade getIbServiciosPjFacade() {
        return ibServiciosPjFacade;
    }

    public void setIbServiciosPjFacade(IbServiciosPjFacade ibServiciosPjFacade) {
        this.ibServiciosPjFacade = ibServiciosPjFacade;
    }

    public IbUsuariosPjFacade getIbUsuariosPjFacade() {
        return ibUsuariosPjFacade;
    }

    public void setIbUsuariosPjFacade(IbUsuariosPjFacade ibUsuariosPjFacade) {
        this.ibUsuariosPjFacade = ibUsuariosPjFacade;
    }
    
    

    
}

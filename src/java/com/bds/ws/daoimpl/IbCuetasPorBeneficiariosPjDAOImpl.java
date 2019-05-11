/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCuetasPorBeneficiariosPjDAO;
import com.bds.ws.dto.IbBeneficiariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbCtasXBeneficiariosPjFacade;
import com.bds.ws.model.IbBeneficiariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author robinson.rodriguez
 */
@Named("IbCuetasPorBeneficiariosPjDAO")
@Stateless
public class IbCuetasPorBeneficiariosPjDAOImpl implements IbCuetasPorBeneficiariosPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCuetasPorBeneficiariosPjDAOImpl.class.getName());

    @EJB
    private IbCtasXBeneficiariosPjFacade ibCtasXBeneficiariosPjFacade;
    
    @Override
    public IbBeneficiariosPjDTO listaIbBeneficiariosPorCtaEmpresaPjDTO(BigDecimal idEmpresa, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, String idCanal, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
           ibBeneficiariosPjDTO.setIbCtasXBeneficiariosPj(ibCtasXBeneficiariosPjFacade.IbBeneficiariosPorCuentaEmpresa(idEmpresa, idCtaDelSur, idCtaPro, estatusCta));
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN IbCtasXBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO IbCtasXBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibBeneficiariosPjDTO;

    }
    
    @Override
    public IbBeneficiariosPjDTO listaIbCuentasPorBeneficiariosPjDTO(IbBeneficiariosPj beneficiario, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta, String idCanal, String codigoCanal) {
        IbBeneficiariosPjDTO ibBeneficiariosPjDTO = new IbBeneficiariosPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
           ibBeneficiariosPjDTO.setIbCtasXBeneficiariosPj(ibCtasXBeneficiariosPjFacade.IbCuentaEmpresaPorBeneficiarios(beneficiario, idCtaDelSur, idCtaPro, estatusCta));
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN IbCtasXBeneficiariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO IbCtasXBeneficiariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibBeneficiariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibBeneficiariosPjDTO;

    }
}



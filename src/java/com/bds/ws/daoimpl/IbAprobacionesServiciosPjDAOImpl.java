/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.IbAprobacionesServiciosPjDAO;
import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.enumerated.EstatusPlantillasEmail;
import com.bds.ws.facade.IbAprobacionesServiciosPjFacade;
import com.bds.ws.facade.IbServiciosPjFacade;
import com.bds.ws.model.IbAprobacionesServiciosPj;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import com.bds.ws.model.IbServiciosPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbAprobacionesServiciosPjDAO")
@Stateless
public class IbAprobacionesServiciosPjDAOImpl extends BDSUtil implements IbAprobacionesServiciosPjDAO {
    @EJB
    private IbAprobacionesServiciosPjFacade ibAprobacionesServiciosPjFacade;
    
    @EJB
    private IbServiciosPjFacade ibServiciosPjFacade;
    
    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;
    
    @EJB
    private EMailDAO emailDAO;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbAprobacionesServiciosPjDAOImpl.class.getName());
    
    /**
     * Lista las aprobaciones por servicio para una empresa
     *
     * Parametros
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return the com.bds.ws.dto.IbAprobacionesServiciosPjDTO
     */
    @Override
    public IbAprobacionesServiciosPjDTO consultarAprobacionesEmpresaServiciosPj(String idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO = new IbAprobacionesServiciosPjDTO();
        try {
            List<IbAprobacionesServiciosPj> ibAprobacionesServiciosPj = ibAprobacionesServiciosPjFacade.consultarAprobacionesEmpresaServiciosPj(new BigDecimal(idEmpresa), idCanal);
            if (!ibAprobacionesServiciosPj.isEmpty()) {
                ibAprobacionesServiciosPjDTO.setIbAprobacionesServiciosPj(ibAprobacionesServiciosPj);
                ibAprobacionesServiciosPjDTO.setRespuesta(respuesta);
            } else {
                respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarAprobacionesEmpresaServiciosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            ibAprobacionesServiciosPjDTO.setRespuesta(respuesta);
        }
        return ibAprobacionesServiciosPjDTO;
    }
    
    /**
     * Inserta la cantidad de aprobadores por servicio para una empresa
     *
     * Parametros
     *
     * @param ibAprobacionesServiciosPjDTO lista de servicios con su cantidad de
     * aprobadores
     * @param idCanal codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return the com.bds.ws.dto.RespuestaDTO
     */
    @Override
    public RespuestaDTO insertarAprobacionesEmpresaServiciosPj(IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IbAprobacionesServiciosPj ibAprobacionServicioPj;
        BigDecimal idEmpresa = BigDecimal.ZERO;
        IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO = null;
        StringBuilder nombreServicios   = new StringBuilder();
        StringBuilder cantidadServicios = new StringBuilder();
        try {
            for (IbAprobacionesServiciosPj ibAprobacionServicioPji : ibAprobacionesServiciosPjDTO.getIbAprobacionesServiciosPj()) {
                IbServiciosPj ibServiciosPj = ibServiciosPjFacade.find(ibAprobacionServicioPji.getIdServicioPj().getId());
                nombreServicios.append(ibServiciosPj.getIdTextos().getMensajeUsuario()).append(",");
                cantidadServicios.append(ibAprobacionServicioPji.getCantAprobadores()).append(",");
                idEmpresa = idEmpresa == BigDecimal.ZERO ? ibAprobacionServicioPji.getIdEmpresa().getId() : idEmpresa;
                if(ibAprobacionServicioPji.getId()== null || ibAprobacionServicioPji.getId().equals(BigDecimal.ZERO)){
                    ibAprobacionServicioPji.setId(BigDecimal.ZERO);
                    ibAprobacionesServiciosPjFacade.crearPJ(ibAprobacionServicioPji);
                }else {
                    ibAprobacionServicioPj = ibAprobacionesServiciosPjFacade.find(ibAprobacionServicioPji.getId());
                    ibAprobacionServicioPj.setCantAprobadores(ibAprobacionServicioPji.getCantAprobadores());
                    ibAprobacionesServiciosPjFacade.actualizar(ibAprobacionServicioPj);
                }
            }
            //SI HUBO EXITO SE ENVIA EL EMAIL A LOS REPRESENTANTES LEGALES DE LA EMPRESA
            //SIEMPRE SE ENVIA EL CODIGO 1 AL SP
            //AJUSTAR CODIGO DE LA PLANTILLA
            String codigoEmpresa = EstatusPlantillasEmail.CODIGO_EMPRESA_PLANTILLA.getDescripcion();
            String codigoPlantilla = EstatusPlantillasEmail.NIVELES_APROBACION_SERVICIO.getDescripcion();
            String parametrosCorreo = "~" + nombreServicios.toString() + "~" + cantidadServicios;
            String parametrosCorreoFinal;
            //SE CONSULTAN LOS REPRESENTANTES LEGALES DE LA EMPRESA
            ibEmpresasUsuariosPjDTO = ibEmpresasUsuariosDAO.consultarEmpresasUsuariosPjRepresentanteLegal(idEmpresa.toString(), idCanal, codigoCanal);
            if(ibEmpresasUsuariosPjDTO.getIbEmpresasUsuariosPj() != null){
                for(IbEmpresasUsuariosPj ibEmpresaUsuarioPj : ibEmpresasUsuariosPjDTO.getIbEmpresasUsuariosPj()){
                    parametrosCorreoFinal = ibEmpresaUsuarioPj.getIdUsuarioPj().getNombre() + parametrosCorreo;
                    emailDAO.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, ibEmpresaUsuarioPj.getEmail(), parametrosCorreoFinal, idCanal, codigoCanal);
                }
            }    
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarAprobacionesEmpresaServiciosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            ibAprobacionesServiciosPjDTO.setRespuesta(respuesta);
        }
        return respuesta;
    }

    @Override
    public IbAprobacionesServiciosPjDTO consultarByEmpresa(BigDecimal cdClienteAbanks, String servicio, String idCanal) {
        return ibAprobacionesServiciosPjFacade.consultarByServicioEmpresa(cdClienteAbanks, servicio ,idCanal);
    }

}

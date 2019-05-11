/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbMenuPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbServiciosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbEmpresasUsuariosPjFacade;
import com.bds.ws.facade.IbModulosPjFacade;
import com.bds.ws.facade.IbServiEmpreUsuariosPjFacade;
import com.bds.ws.facade.IbUrlServiciosFacade;
import com.bds.ws.model.IbServiciosPj;
import com.bds.ws.model.IbUrlServicios;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
@Named("IbMenuPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbMenuPjDAOImpl implements IbMenuPjDAO{
    @EJB
    IbModulosPjFacade ibModulosPjFacade;
    @EJB
    IbEmpresasUsuariosPjFacade ibEmpresasUsuariosPjFacade;
    @EJB
    IbServiEmpreUsuariosPjFacade ibServiciosPerfilesPjFacade;
    @EJB
    IbUrlServiciosFacade ibUrlServiciosFacade;
    
    private static final Logger logger = Logger.getLogger(IbMenuPjDAOImpl.class.getName());
    
    /**
     * *
     * Metodo que retorna el menu (Modulos y Servicios)
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la empresa en IB
     * @param estatus estatus para el modulo A = activo, I = inactivo
     * @param visible visible = 1, no sivible 1
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal the value of codigoCanal
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO consultarModulosServPorEmpresaUsuarioPj(String idUsuario, String idEmpresa, Character estatus, Character visible, String idCanal, String codigoCanal) {
        //IbModulosPjDTO ibModulosPjDTO = new IbModulosPjDTO();
        RespuestaDTO respuesta            = new RespuestaDTO();
        HashMap resultados                = new LinkedHashMap();
        UtilDTO utilDTO                   = new UtilDTO();
        IbServiciosPjDTO ibServiciosPjDTO = null;
        
        try {
            BigDecimal idCanalD   = new BigDecimal(idCanal);

            Map<String, List<IbServiciosPj>> mapsModulos = ibServiciosPerfilesPjFacade.consultarModulosPerfiles(idUsuario, idCanalD.toString(), idEmpresa, estatus, visible);
            if (!mapsModulos.isEmpty()) {
                for (Map.Entry<String, List<IbServiciosPj>> entry : mapsModulos.entrySet()) {
                    ibServiciosPjDTO = new IbServiciosPjDTO();
                    ibServiciosPjDTO.setIbServiciosPj((List<IbServiciosPj>) entry.getValue());
                    resultados.put(entry.getKey(), ibServiciosPjDTO);   
                }
            } else {
                throw new NoResultException();
            }         
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarModulosServPorEmpresaUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarModulosServPorEmpresaUsuarioPj: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
    
    /**
     * Metodo que consulta las urls permitidas para un servicio
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la Empresa en IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return List<String> con las urls permitidas
     */
    @Override
    public List<String> consultarUrlPorServicioUsuario(String idUsuario, String idEmpresa, String idCanal, String codigoCanal) {
        List<String> urls = new ArrayList<>();
        try {
            for (IbUrlServicios ibUrlServicio : ibUrlServiciosFacade.consultarUrlServicio(new BigDecimal(idEmpresa), new BigDecimal(idUsuario))) {
                if(!urls.contains(ibUrlServicio.getIdUrl().getNombre())){
                    urls.add(ibUrlServicio.getIdUrl().getNombre());
                }
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarUrlPorServicioUsuario: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarUrlPorServicioUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
        } 
        return urls;
    } 
}

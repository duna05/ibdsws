/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbServiciosPerfilesPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbServiciosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbModulosFacade;
import com.bds.ws.facade.IbServiciosPerfilesPjFacade;
import com.bds.ws.facade.IbUsuariosPerfilPilotoFacade;
import com.bds.ws.model.IbServiciosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
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
 * Clase que implementa la interfaz IbServiciosPerfilesPjDAO
 *
 * @author luis.perez
 */
@Named("IbServiciosPerfilesPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbServiciosPerfilesPjDAOImpl extends DAOUtil implements IbServiciosPerfilesPjDAO {

    private static final Logger logger = Logger.getLogger(IbServiciosPerfilesPjDAOImpl.class.getName());

    @EJB
    private IbUsuariosPerfilPilotoFacade ibUsuariosPerfilPilotoFacade;

    @EJB
    private IbModulosFacade ibModulosFacade;

    @EJB
    private IbServiciosPerfilesPjFacade ibServiciosPerfilesPjFacade;
    
    /**
     * *
     * Metodo que retorna el menu (Modulos y Servicios)
     *
     * @param idPerfil codigo de perfil en IB
     * @param estatus estatus del servicio y el modulo
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO consultarServiciosPerfilesPj(String idPerfil, Character estatus, String idCanal, String codigoCanal) {
        //IbModulosPjDTO ibModulosPjDTO = new IbModulosPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        HashMap resultados = new LinkedHashMap();
        UtilDTO utilDTO = new UtilDTO();
        IbServiciosPjDTO ibServiciosPjDTO = null;

        try {
            BigDecimal idCanalD = new BigDecimal(idCanal);
            String visible = "1";

            Map<String, List<IbServiciosPj>> mapsModulos = ibServiciosPerfilesPjFacade.consultarServiciosPerfilesPj(idPerfil,estatus.toString(),idCanalD.toString(), visible);
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
                    //.append("USR-").append(idUsuario)
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
}

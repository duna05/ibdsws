/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbServiEmpreUsuariosPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbServiEmpreUsuariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbServiEmpreUsuariosPjFacade;
import com.bds.ws.model.IbServiEmpreUsuariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
@Named("IbServiEmpreUsuariosPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbServiEmpreUsuariosPjDAOImpl extends DAOUtil implements IbServiEmpreUsuariosPjDAO {

    private static final Logger logger = Logger.getLogger(IbServiEmpreUsuariosPjDAOImpl.class.getName());

    @EJB
    private IbServiEmpreUsuariosPjFacade ibServiEmpreUsuariosPjFacade;
    
    /**
     * *
     * Metodo que retorna una lista de IbServiEmpreUsuariosPj
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa id de la empresa en IB
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return List IbServiEmpreUsuariosPjDTO
     *
     */
    @Override
    public IbServiEmpreUsuariosPjDTO consultarIbServiEmpreUsuariosPj(String idUsuario, String idEmpresa, String idCanal, String codigoCanal) {
        IbServiEmpreUsuariosPjDTO ibServiEmpreUsuariosPjDTO = new IbServiEmpreUsuariosPjDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        
        try {
            BigDecimal idCanalD = new BigDecimal(idCanal);
            BigDecimal idUsuarioD = new BigDecimal(idUsuario);
            BigDecimal idEmpresaD = new BigDecimal(idEmpresa);
            List<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPjList = ibServiEmpreUsuariosPjFacade.consultarIbServiEmpreUsuariosPj(idUsuarioD, idEmpresaD, idCanalD);
            if (!ibServiEmpreUsuariosPjList.isEmpty()) {
                ibServiEmpreUsuariosPjDTO.setIbServiEmpreUsuariosPjList(ibServiEmpreUsuariosPjList);
            } else {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarIbServiEmpreUsuariosPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO consultarIbServiEmpreUsuariosPj: ")
                    //.append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            ibServiEmpreUsuariosPjDTO.setRespuestaDTO(respuesta);
        }
        return ibServiEmpreUsuariosPjDTO;
    }
}

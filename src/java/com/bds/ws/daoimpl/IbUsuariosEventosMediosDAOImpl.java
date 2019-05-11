/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbUsuariosEventosMediosDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbUsuariosEventosMediosFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.faneite
 */
@Named("IbUsuariosEventosMediosDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbUsuariosEventosMediosDAOImpl extends DAOUtil implements IbUsuariosEventosMediosDAO{
    
    @EJB
    IbUsuariosEventosMediosFacade eventosMediosFacade;
    
    private static final Logger logger = Logger.getLogger(IbUsuariosEventosMediosDAOImpl.class.getName());

    /**
     * Metodo que sustituye el listado de Eventos y medios asociados a un usuario
     * @param idUsuario
     * @param idEvento
     * @param idMedio
     * @param montoMin
     * @param codigoCanal codigo del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO agregarUsuarioEventosMedios(String idUsuario, String idEvento, String idMedio, String montoMin, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || idEvento == null || idEvento.isEmpty() || idEvento.equalsIgnoreCase("")
                    || idMedio == null || idMedio.isEmpty() || idMedio.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }
            
            int existe = eventosMediosFacade.consultarUsuariosEventosMedios(idUsuario,
                    idEvento, idMedio,
                    codigoCanal);

            if (existe == 0) {
                respuestaDTO = eventosMediosFacade.insertarUsrEventMed(idUsuario,
                        idEvento,
                        idMedio,
                        montoMin, codigoCanal);
            } else {
                respuestaDTO = eventosMediosFacade.actualizarUsuariosEventosMedios(idUsuario,
                        idEvento,
                        idMedio,
                        montoMin,
                        codigoCanal);
            }

            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (DAOException | NoResultException e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarUsuarioEventosMedios: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo("DAO008");

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarUsuarioEventosMedios: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN agregarUsuarioEventosMedios: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuestaDTO;
    }
    
}

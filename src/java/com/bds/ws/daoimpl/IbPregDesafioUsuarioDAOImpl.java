/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPregDesafioUsuarioDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbPregDesafioUsuarioDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbPregDesafioUsuarioFacade;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interface IbPregDesafioUsuarioDAO
 *
 * @author rony.rodriguez
 */
@Named("IbPregDesafioUsuarioDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbPregDesafioUsuarioDAOImpl extends DAOUtil implements IbPregDesafioUsuarioDAO {

    @EJB
    IbPregDesafioUsuarioFacade pregDesafioUsuarioFacade;

    private static final Logger logger = Logger.getLogger(IbPreguntasDesafioDAOImpl.class.getName());

    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    @Override
    public IbPregDesafioUsuarioDTO listadoPreguntasDesafioUsuario(String idUsuario, String nombreCanal) {
        IbPregDesafioUsuarioDTO pregDesafioUsuarioDTO = new IbPregDesafioUsuarioDTO();
        pregDesafioUsuarioDTO.setRespuesta(new RespuestaDTO());
        try {

            if(idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("") ||
               nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                throw new DAOException("DAO008");
            }
            
            pregDesafioUsuarioDTO = pregDesafioUsuarioFacade.listaPreguntasDesafioUsuario(idUsuario, nombreCanal);

            if (!pregDesafioUsuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPreguntasDesafioUsuario: ").append("IDUSR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            pregDesafioUsuarioDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (pregDesafioUsuarioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoPreguntasDesafioUsuario: ")//.append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return pregDesafioUsuarioDTO;
    }

    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con
     * sus respuestas
     *
     * @param listPDDUsuarioRespuestas listado de preguntas y respuestas
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO agregarPDDUsuario(PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        String idUsuario = null;
        try {
            if (!listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios().isEmpty() &&
                nombreCanal != null && !nombreCanal.isEmpty() && !nombreCanal.equalsIgnoreCase("")) {
                idUsuario = listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios().get(0).getIdUsuario().toString();
            } else {
                throw new DAOException("DAO008");
            }
            respuestaDTO = pregDesafioUsuarioFacade.borrarLotePDDUsuario(idUsuario, nombreCanal);
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

            for (PreguntaRespuestaUsuarioDTO pregDesafioUsuario : listPDDUsuarioRespuestas.getPreguntasRespuestasUsuarios()) {
                respuestaDTO = pregDesafioUsuarioFacade.insertarPDDUsuario(pregDesafioUsuario.getIdUsuario().toString()
                        ,pregDesafioUsuario.getIdPregunta().toString(),pregDesafioUsuario.getRespuestaPDD(), nombreCanal);
                if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarPDDUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN agregarPDDUsuario: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuestaDTO;
    }

}

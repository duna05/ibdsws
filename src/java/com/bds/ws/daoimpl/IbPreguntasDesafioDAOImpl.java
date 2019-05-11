/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPreguntasDesafioDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbPreguntasDesafioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbPregDesafioUsuarioFacade;
import com.bds.ws.facade.IbPreguntasDesafioFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * Clase de implementacion de la interface IbPreguntasDesafioDAO
 * @author juan.faneite
 */
@Named("IbPreguntasDesafioDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbPreguntasDesafioDAOImpl extends DAOUtil implements IbPreguntasDesafioDAO{
    
    @EJB
    IbPreguntasDesafioFacade preguntasDesafioFacade;
    
    @EJB
    IbPregDesafioUsuarioFacade pregDesafioUsuarioFacade;
    
    private static final Logger logger = Logger.getLogger(IbPreguntasDesafioDAOImpl.class.getName());
    
    /**
     * Metodo para obtener el listado de preguntas de desafio del banco
     * @param nombreCanal nombre del canal
     * @return IbPreguntasDesafioDTO Listado de las preguntas de desafio
     */
    @Override
    public IbPreguntasDesafioDTO listadoPreguntasDesafioBanco (String nombreCanal){
        IbPreguntasDesafioDTO preguntasDesafioDTO = new IbPreguntasDesafioDTO();
        preguntasDesafioDTO.setRespuesta(new RespuestaDTO());
        try {
            
            if(nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")){
                throw new DAOException("DAO008");
            }
            
            preguntasDesafioDTO = preguntasDesafioFacade.listaPreguntasDesafioBanco(nombreCanal);
            
            if (!preguntasDesafioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }
            
            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoPreguntasDesafioBanco: ")//.append("USR-").append(codUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            preguntasDesafioDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (preguntasDesafioDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoPreguntasDesafioBanco: ")//.append("USR-").append(codUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return preguntasDesafioDTO;
    }
    
    /**
     * Metodo para validar una rafaga de preguntas de desafio
     *
     * @param idUsuario id del usuario
     * @param rafaga cadena con las preguntas y respuestas a validar ej: < codigoPregunta >< separador
     * > < codigoPregunta >< separador >< codigoPregunta >< separador >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO -> 'true' en caso que la respuesta sea corresta 'false'
     * en caso contrario
     */
    @Override
    public UtilDTO validarPreguntaDD(String idUsuario, String rafaga, String separador, String nombreCanal){
        UtilDTO utilDTO = new UtilDTO();
        utilDTO.setRespuesta(new RespuestaDTO());
        try {
            
            if(idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("") ||
               nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("") ||
               rafaga == null || rafaga.isEmpty() || rafaga.equalsIgnoreCase("") ||
               separador == null || separador.isEmpty() || separador.equalsIgnoreCase("")){
                throw new DAOException("DAO008");
            }
            
            utilDTO = pregDesafioUsuarioFacade.validarPreguntaDD(idUsuario, rafaga, separador, nombreCanal);
            
            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                throw new Exception();
            }
            
            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarPreguntaDD: ")//.append("USR-").append(codUsuario)
                    .append("USR-").append(idUsuario).append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarPreguntaDD: ")//.append("USR-").append(codUsuario)
//                    .append("USR-").append(idUsuario).append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }
}

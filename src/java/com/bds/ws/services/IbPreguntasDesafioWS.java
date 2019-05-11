/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbPregDesafioUsuarioDAO;
import com.bds.ws.dao.IbPreguntasDesafioDAO;
import com.bds.ws.dto.IbPregDesafioUsuarioDTO;
import com.bds.ws.dto.IbPreguntasDesafioDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.faneite
 */
@WebService(serviceName = "IbPreguntasDesafioWS")
public class IbPreguntasDesafioWS {
    @EJB
    IbPreguntasDesafioDAO preguntasDesafioDAO;
    
    @EJB
    IbPregDesafioUsuarioDAO pregDesafioUsuarioDAO;


    /**
     * Metodo para obtener el listado de preguntas de desafio del banco
     * @param nombreCanal nombre del canal
     * @return IbPreguntasDesafioDTO listado de las preguntas de desafio
     */
    @WebMethod(operationName = "listadoPreguntasDesafioBanco")
    public IbPreguntasDesafioDTO listadoPreguntasDesafioBanco(@WebParam(name = "nombreCanal") String nombreCanal) {
        return preguntasDesafioDAO.listadoPreguntasDesafioBanco(nombreCanal);
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
    @WebMethod(operationName = "validarPreguntaDD")
    public UtilDTO validarPreguntaDD(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "rafaga") String rafaga, @WebParam(name = "separador") String separador, @WebParam(name = "nombreCanal") String nombreCanal) {
        return preguntasDesafioDAO.validarPreguntaDD(idUsuario, rafaga, separador, nombreCanal);
    }
    
    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    @WebMethod(operationName = "listadoPreguntasDesafioUsuario")
    public IbPregDesafioUsuarioDTO listadoPreguntasDesafioUsuario(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return pregDesafioUsuarioDAO.listadoPreguntasDesafioUsuario(idUsuario, nombreCanal);
    }
    
    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con sus respuestas
     * @param listPDDUsuarioRespuestas
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "agregarPDDUsuario")
    public RespuestaDTO agregarPDDUsuario(@WebParam(name = "listPDDUsuarioRespuestas") PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas, @WebParam(name = "nombreCanal") String nombreCanal) {               
        return pregDesafioUsuarioDAO.agregarPDDUsuario(listPDDUsuarioRespuestas, nombreCanal);
    }
    
}

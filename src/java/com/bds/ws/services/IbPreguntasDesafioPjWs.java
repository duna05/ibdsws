/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbPregDesafioUsuarioPjDAO;
import com.bds.ws.dto.IbPregDesafioUsuarioPjDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbPreguntasDesafioPjWs")
public class IbPreguntasDesafioPjWs {
    @EJB
    IbPregDesafioUsuarioPjDAO ibPregDesafioUsuarioPjDAO;
    
    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con
     * sus respuestas
     *
     * @param listPDDUsuarioRespuestas lista de respuestas a preguntas de seguridad
     * @param idCanal                  id de canal interno en IB
     * @param codigoCanal              codigo de canal en el CORE bancario
     * @return UtilDTO 
     */
    @WebMethod(operationName = "agregarPDDUsuarioPj")
    public UtilDTO agregarPDDUsuarioPj(@WebParam(name = "listPDDUsuarioRespuestas") PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibPregDesafioUsuarioPjDAO.agregarPDDUsuarioPj(listPDDUsuarioRespuestas,idCanal, codigoCanal);
    } 
    
    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente Pj
     *
     * @param idUsuario id del usuario
     * @param codigoCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    @WebMethod(operationName = "listadoPreguntasDesafioUsuarioPj")
    public IbPregDesafioUsuarioPjDTO listadoPreguntasDesafioUsuarioPj(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibPregDesafioUsuarioPjDAO.listadoPreguntasDesafioUsuarioPj(idUsuario, codigoCanal);
    } 

/**
     * Metodo para validar una rafaga de preguntas de desafio de Pj
     *
     * @param idUsuario id del usuario Pj
     * @param rafaga cadena con las preguntas y respuestas a validar ej: 
     * < codigoPregunta >< separador >< respuesta >< separador >< codigoPregunta >< separador >< respuesta >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO -> 'true' en caso que la respuesta sea corresta 'false'
     * en caso contrario
     */
    @WebMethod(operationName = "validarPreguntaDDPj")
    public UtilDTO validarPreguntaDDPj(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "rafaga") String rafaga , @WebParam(name = "separador") String separador , @WebParam(name = "nombreCanal") String nombreCanal) {
        return ibPregDesafioUsuarioPjDAO.validarPreguntaDDPj(idUsuario, rafaga, separador, nombreCanal);
    } 
}

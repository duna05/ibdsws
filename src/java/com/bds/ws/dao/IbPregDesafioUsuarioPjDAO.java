/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbPregDesafioUsuarioPjDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author luis.perez
 */
public interface IbPregDesafioUsuarioPjDAO {
    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con
     * sus respuestas
     *
     * @param listPDDUsuarioRespuestas listado de preguntas y respuestas
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal interno en el CORE
     * @return RespuestaDTO
     */
    public UtilDTO agregarPDDUsuarioPj(PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas,String idCanal, String codigoCanal);
    
    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente Pj
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    public IbPregDesafioUsuarioPjDTO listadoPreguntasDesafioUsuarioPj(String idUsuario, String nombreCanal);
    
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
    public UtilDTO validarPreguntaDDPj(String idUsuario, String rafaga, String separador, String nombreCanal);
}

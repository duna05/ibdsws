/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbPregDesafioUsuarioDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.RespuestaDTO;

/**
 * Interfaz de Preguntas de Desafio del Usuario
 *
 * @author rony.rodriguez
 */
public interface IbPregDesafioUsuarioDAO {

    /**
     * Metodo que devuelve un listado de preguntas de desafio de un cliente
     *
     * @param idUsuario id del usuario
     * @param nombreCanal nombre del canal
     * @return retorna un listado de preguntas de desafio de un cliente
     */
    public IbPregDesafioUsuarioDTO listadoPreguntasDesafioUsuario(String idUsuario, String nombreCanal);
    
    /**
     * Metodo que sustituye las preguntas de desafio viejas por las nuevas con sus respuestas
     * @param listPDDUsuarioRespuestas listado de preguntas y respuestas
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarPDDUsuario(PreguntaRespuestaUsuarioDTO listPDDUsuarioRespuestas, String nombreCanal);

}

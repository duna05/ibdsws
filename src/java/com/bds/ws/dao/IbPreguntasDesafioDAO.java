/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbPreguntasDesafioDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz para preguntas de Desafio
 * @author juan.faneite
 */
public interface IbPreguntasDesafioDAO {
    
    /**
     * Metodo para obtener el listado de preguntas de desafio del banco
     * @param nombreCanal nombre del canal
     * @return IbPreguntasDesafioDTO listado de las preguntas de desafio
     */
    public IbPreguntasDesafioDTO listadoPreguntasDesafioBanco (String nombreCanal);
    
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
    public UtilDTO validarPreguntaDD(String idUsuario, String rafaga, String separador, String nombreCanal);
}

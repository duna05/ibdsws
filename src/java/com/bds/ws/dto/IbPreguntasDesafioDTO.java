/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPreguntasDesafio;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbPreguntasDesafioDTO
 * @author juan.faneite
 */
public class IbPreguntasDesafioDTO implements Serializable{
    private IbPreguntasDesafio preguntaDesafio;
    private List<IbPreguntasDesafio> preguntasDesafio;
    private RespuestaDTO respuesta;

    /**
     * Obtener modelo de IB_PREGUNTAS_DESAFIO
     * @return IbPreguntasDesafio
     */
    public IbPreguntasDesafio getPreguntaDesafio() {
        return preguntaDesafio;
    }

    /**
     * Insertar modelo de IB_PREGUNTAS_DESAFIO
     * @param preguntaDesafio modelo de IB_PREGUNTAS_DESAFIO
     */
    public void setPreguntaDesafio(IbPreguntasDesafio preguntaDesafio) {
        this.preguntaDesafio = preguntaDesafio;
    }

    /**
     * Obtener listado de modelo de IB_PREGUNTAS_DESAFIO
     * @return List < IbPreguntasDesafio >
     */
    public List<IbPreguntasDesafio> getPreguntasDesafio() {
        return preguntasDesafio;
    }

    /**
     * Insertar listado de modelo de IB_PREGUNTAS_DESAFIO
     * @param preguntasDesafio listado de modelo de IB_PREGUNTAS_DESAFIO
     */
    public void setPreguntasDesafio(List<IbPreguntasDesafio> preguntasDesafio) {
        this.preguntasDesafio = preguntasDesafio;
    }
    
    /**
     * retorna la respuesta de la operacion que se realiza.
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}

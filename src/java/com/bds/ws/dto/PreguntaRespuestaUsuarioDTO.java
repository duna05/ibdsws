/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author juan.faneite
 */
public class PreguntaRespuestaUsuarioDTO {
    private BigDecimal idPregunta;
    private BigDecimal idUsuario;
    private String respuestaPDD;
    RespuestaDTO respuesta;
    
    private List<PreguntaRespuestaUsuarioDTO> preguntasRespuestasUsuarios;

    public List<PreguntaRespuestaUsuarioDTO> getPreguntasRespuestasUsuarios() {
        return preguntasRespuestasUsuarios;
    }

    public void setPreguntasRespuestasUsuarios(List<PreguntaRespuestaUsuarioDTO> preguntasRespuestasUsuarios) {
        this.preguntasRespuestasUsuarios = preguntasRespuestasUsuarios;
    }        

    public BigDecimal getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(BigDecimal idPregunta) {
        this.idPregunta = idPregunta;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRespuestaPDD() {
        return respuestaPDD;
    }

    public void setRespuestaPDD(String respuestaPDD) {
        this.respuestaPDD = respuestaPDD;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    
}

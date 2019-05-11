/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPregDesafioUsuarioPj;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author cesar.mujica
 */
public class IbPregDesafioUsuarioPjDTO implements Serializable{
    private IbPregDesafioUsuarioPj preguntaDesafioUsuarioPj;
    private List<IbPregDesafioUsuarioPj> preguntasDesafioUsuarioPj;
    private RespuestaDTO respuesta;

    public IbPregDesafioUsuarioPj getPreguntaDesafioUsuarioPj() {
        return preguntaDesafioUsuarioPj;
    }

    public void setPreguntaDesafioUsuarioPj(IbPregDesafioUsuarioPj preguntaDesafioUsuarioPj) {
        this.preguntaDesafioUsuarioPj = preguntaDesafioUsuarioPj;
    }

    public List<IbPregDesafioUsuarioPj> getPreguntasDesafioUsuarioPj() {
        return preguntasDesafioUsuarioPj;
    }

    public void setPreguntasDesafioUsuarioPj(List<IbPregDesafioUsuarioPj> preguntasDesafioUsuarioPj) {
        this.preguntasDesafioUsuarioPj = preguntasDesafioUsuarioPj;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    
    
}

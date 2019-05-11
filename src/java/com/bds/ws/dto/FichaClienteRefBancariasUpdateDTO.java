/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.util.BDSUtil;
import java.io.Serializable;

/**
 *
 * @author audra.zapata
 */
public class FichaClienteRefBancariasUpdateDTO extends BDSUtil implements Serializable  {    
    
    private RespuestaDTO respuesta;

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
}

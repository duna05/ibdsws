package com.bds.ws.dto;

import com.bds.ws.util.BDSUtil;
import java.io.Serializable;

/**
 *
 * @author audra.zapata
 */
public class FichaClienteRefComercialesUpdateDTO   extends BDSUtil implements Serializable  {
    
    private RespuestaDTO respuesta;

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}

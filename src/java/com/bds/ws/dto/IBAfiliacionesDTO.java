/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbAfiliaciones;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IBAfiliacionesDTO
 * @author juan.faneite
 */
public class IBAfiliacionesDTO implements Serializable {

    private IbAfiliaciones ibAfiliacion;
    private List<IbAfiliaciones> ibAfiliaciones;
    private RespuestaDTO respuesta;

    public IBAfiliacionesDTO() {
    }

    /**
     * Obtiene el modelo de IbAfiliaciones
     *
     * @return IbAfiliaciones Obtiene el modelo de IbAfiliaciones
     */
    public IbAfiliaciones getIbAfiliacion() {
        return ibAfiliacion;
    }

    /**
     * Inserta el modelo de IbAfiliaciones
     *
     * @param ibAfiliacion IbAfiliaciones Obtiene el modelo de IbAfiliaciones
     */
    public void setIbAfiliacion(IbAfiliaciones ibAfiliacion) {
        this.ibAfiliacion = ibAfiliacion;
    }

    /**
     * Obtiene un listado del modelo IbAfiliaciones
     *
     * @return IbAfiliaciones Obtiene un listado del modelo IbAfiliaciones
     */
    public List<IbAfiliaciones> getIbAfiliaciones() {
        return ibAfiliaciones;
    }

    /**
     * Obtiene un listado del modelo IbAfiliaciones
     *
     * @param ibAfiliaciones IbAfiliaciones Obtiene un listado del modelo
     * IbAfiliaciones
     */
    public void setIbAfiliaciones(List<IbAfiliaciones> ibAfiliaciones) {
        this.ibAfiliaciones = ibAfiliaciones;
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

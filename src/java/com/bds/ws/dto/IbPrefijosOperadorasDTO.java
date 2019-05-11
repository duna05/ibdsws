/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbPrefijosOperadoras;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IbPrefijosOperadorasDTO
 * @author juan.faneite
 */
public class IbPrefijosOperadorasDTO implements Serializable{
    private IbPrefijosOperadoras prefijoOperadora;
    private List<IbPrefijosOperadoras> prefijosOperadoras;
    private RespuestaDTO respuesta;

    /**
     * obtiene el modelo de ib_prefijos_operadoras
     * @return IbPrefijosOperadoras
     */
    public IbPrefijosOperadoras getPrefijoOperadora() {
        return prefijoOperadora;
    }

    /**
     * inserta el modelo de ib_prefijos_operadoras
     * @param prefijoOperadora prefijo de una operadora ej: 0414
     */
    public void setPrefijoOperadora(IbPrefijosOperadoras prefijoOperadora) {
        this.prefijoOperadora = prefijoOperadora;
    }

    /**
     * obtiene el listado de modelo de ib_prefijos_operadoras
     * @return List < IbPrefijosOperadoras >
     */
    public List<IbPrefijosOperadoras> getPrefijosOperadoras() {
        return prefijosOperadoras;
    }

    /**
     * inserta listado de modelo de ib_prefijos_operadoras
     * @param prefijosOperadoras listado de modelo de ib_prefijos_operadoras
     */
    public void setPrefijosOperadoras(List<IbPrefijosOperadoras> prefijosOperadoras) {
        this.prefijosOperadoras = prefijosOperadoras;
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

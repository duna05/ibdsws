/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbHistoricoClaves;
import java.util.List;

/**
 *
 * @author wilmer.rondon
 */
public class IbHistoricoClavesDTO {
    private IbHistoricoClaves ibHistoricoClave;
    private List<IbHistoricoClaves> ibHistoricoClaves;
    private RespuestaDTO respuesta;

    public IbHistoricoClaves getIbHistoricoClave() {
        return ibHistoricoClave;
    }

    public void setIbHistoricoClave(IbHistoricoClaves ibHistoricoClave) {
        this.ibHistoricoClave = ibHistoricoClave;
    }

    public List<IbHistoricoClaves> getIbHistoricoClaves() {
        return ibHistoricoClaves;
    }

    public void setIbHistoricoClaves(List<IbHistoricoClaves> ibHistoricoClaves) {
        this.ibHistoricoClaves = ibHistoricoClaves;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    
    
    
    
}

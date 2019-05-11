/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbFideicomisoDetPj;
import java.util.List;

/**
 *
 * @author robinson rodriguez
 */
public class IbFideicomisoDetPjDTO {
    
    private IbFideicomisoDetPj ibFideicomisoDetPj;
    private List<IbFideicomisoDetPj> ibFideicomisoDetPjsList;
    private RespuestaDTO respuestaDTO;
    
    

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }

    /**
     * @return the ibFideicomisoDetPj
     */
    public IbFideicomisoDetPj getIbFideicomisoDetPj() {
        return ibFideicomisoDetPj;
    }

    /**
     * @param ibFideicomisoDetPj the ibFideicomisoDetPj to set
     */
    public void setIbFideicomisoDetPj(IbFideicomisoDetPj ibFideicomisoDetPj) {
        this.ibFideicomisoDetPj = ibFideicomisoDetPj;
    }

    /**
     * @return the ibFideicomisoDetPjsList
     */
    public List<IbFideicomisoDetPj> getIbFideicomisoDetPjsList() {
        return ibFideicomisoDetPjsList;
    }

    /**
     * @param ibFideicomisoDetPjsList the ibFideicomisoDetPjsList to set
     */
    public void setIbFideicomisoDetPjsList(List<IbFideicomisoDetPj> ibFideicomisoDetPjsList) {
        this.ibFideicomisoDetPjsList = ibFideicomisoDetPjsList;
    }
    
}

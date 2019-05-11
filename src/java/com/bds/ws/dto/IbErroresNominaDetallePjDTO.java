/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbErroresNominaDetallePj;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public class IbErroresNominaDetallePjDTO {
    
    private IbErroresNominaDetallePj ibErroresNominaDetallePj;
    private List<IbErroresNominaDetallePj> ibErroresNominaDetallePjList;
    private RespuestaDTO respuestaDTO;

    public IbErroresNominaDetallePj getIbErroresNominaDetallePj() {
        return ibErroresNominaDetallePj;
    }

    public void setIbErroresNominaDetallePj(IbErroresNominaDetallePj ibErroresNominaDetallePj) {
        this.ibErroresNominaDetallePj = ibErroresNominaDetallePj;
    }

    public List<IbErroresNominaDetallePj> getIbErroresNominaDetallePjList() {
        return ibErroresNominaDetallePjList;
    }

    public void setIbErroresNominaDetallePjList(List<IbErroresNominaDetallePj> ibErroresNominaDetallePjList) {
        this.ibErroresNominaDetallePjList = ibErroresNominaDetallePjList;
    }

    public RespuestaDTO getRespuestaDTO() {
        return respuestaDTO;
    }

    public void setRespuestaDTO(RespuestaDTO respuestaDTO) {
        this.respuestaDTO = respuestaDTO;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Errores en la carga masiva
 * @author roger.muñoz
 */
public class IbErroresCargaPjDTO implements Serializable {
    
    private int  numeroLinea;
    private String linea;
    private List<String> error;
    
    private List<IbErroresCargaPjDTO> ibErroresCargaPjDTO;

    public List<IbErroresCargaPjDTO> getIbErroresCargaPjDTO() {
        return ibErroresCargaPjDTO;
    }

    public void setIbErroresCargaPjDTO(List<IbErroresCargaPjDTO> ibErroresCargaPjDTO) {
        this.ibErroresCargaPjDTO = ibErroresCargaPjDTO;
    }
    
    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public List<String> getError() {
        if(error==null) error = new ArrayList();
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}

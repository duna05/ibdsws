/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class FideicomisoCabeceraPjDTO {
    
    private List<FideicomisoPjDTO> fideicomiso;

    public List<FideicomisoPjDTO> getFideicomiso() {
        return fideicomiso;
    }

    public void setFideicomiso(List<FideicomisoPjDTO> fideicomiso) {
        this.fideicomiso = fideicomiso;
    }
    
}

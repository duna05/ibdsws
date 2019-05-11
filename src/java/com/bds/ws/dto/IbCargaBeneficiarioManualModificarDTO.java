/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robinson.rodriguez
 */
public class IbCargaBeneficiarioManualModificarDTO {
    
    private IbBeneficiariosPjDTO beneficiario;
    private List<IbCtasXBeneficiariosPjDTO> listaCuentasEliminar = new ArrayList();
    private List<IbCtasXBeneficiariosPjDTO> listaCuentasModificar = new ArrayList();

    public IbBeneficiariosPjDTO getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(IbBeneficiariosPjDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    public List<IbCtasXBeneficiariosPjDTO> getListaCuentasEliminar() {
        return listaCuentasEliminar;
    }

    public void setListaCuentasEliminar(List<IbCtasXBeneficiariosPjDTO> listaCuentasEliminar) {
        this.listaCuentasEliminar = listaCuentasEliminar;
    }

    public List<IbCtasXBeneficiariosPjDTO> getListaCuentasModificar() {
        return listaCuentasModificar;
    }

    public void setListaCuentasModificar(List<IbCtasXBeneficiariosPjDTO> listaCuentasModificar) {
        this.listaCuentasModificar = listaCuentasModificar;
    }
    
}

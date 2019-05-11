/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.model.IbBeneficiariosP2p;
import com.bds.ws.model.IbUsuariosP2p;
import java.util.List;

/**
 *
 * @author cesar.mujica
 */
public class IbUsuariosP2PDTO {
    private IbUsuariosP2p usuarioP2p;
    private List<IbUsuariosP2p> usuariosP2p;
    private List<IbBeneficiariosP2p> ibBeneficiarioP2P;
    private RespuestaDTO respuesta;

    public IbUsuariosP2PDTO() {
    } 

    public IbUsuariosP2p getUsuarioP2p() {
        return usuarioP2p;
    }

    public void setUsuarioP2p(IbUsuariosP2p usuarioP2p) {
        this.usuarioP2p = usuarioP2p;
    }

    public List<IbUsuariosP2p> getUsuariosP2p() {
        return usuariosP2p;
    }

    public void setUsuariosP2p(List<IbUsuariosP2p> usuariosP2p) {
        this.usuariosP2p = usuariosP2p;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<IbBeneficiariosP2p> getIbBeneficiarioP2P() {
        return ibBeneficiarioP2P;
    }

    public void setIbBeneficiarioP2P(List<IbBeneficiariosP2p> ibBeneficiarioP2P) {
        this.ibBeneficiarioP2P = ibBeneficiarioP2P;
    }
}

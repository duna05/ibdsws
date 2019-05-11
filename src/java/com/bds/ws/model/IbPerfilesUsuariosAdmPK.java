/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author juan.faneite
 */
@Embeddable
public class IbPerfilesUsuariosAdmPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO_ADM")
    private String usuarioAdm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PERFIL_ADM")
    private BigInteger idPerfilAdm;

    public IbPerfilesUsuariosAdmPK() {
    }

    public IbPerfilesUsuariosAdmPK(String usuarioAdm, BigInteger idPerfilAdm) {
        this.usuarioAdm = usuarioAdm;
        this.idPerfilAdm = idPerfilAdm;
    }

    public String getUsuarioAdm() {
        return usuarioAdm;
    }

    public void setUsuarioAdm(String usuarioAdm) {
        this.usuarioAdm = usuarioAdm;
    }

    public BigInteger getIdPerfilAdm() {
        return idPerfilAdm;
    }

    public void setIdPerfilAdm(BigInteger idPerfilAdm) {
        this.idPerfilAdm = idPerfilAdm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioAdm != null ? usuarioAdm.hashCode() : 0);
        hash += (idPerfilAdm != null ? idPerfilAdm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbPerfilesUsuariosAdmPK)) {
            return false;
        }
        IbPerfilesUsuariosAdmPK other = (IbPerfilesUsuariosAdmPK) object;
        if ((this.usuarioAdm == null && other.usuarioAdm != null) || (this.usuarioAdm != null && !this.usuarioAdm.equals(other.usuarioAdm))) {
            return false;
        }
        if ((this.idPerfilAdm == null && other.idPerfilAdm != null) || (this.idPerfilAdm != null && !this.idPerfilAdm.equals(other.idPerfilAdm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPerfilesUsuariosAdmPK[ usuarioAdm=" + usuarioAdm + ", idPerfilAdm=" + idPerfilAdm + " ]";
    }
    
}

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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_PERFILES_USUARIOS_ADM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPerfilesUsuariosAdm.findAll", query = "SELECT i FROM IbPerfilesUsuariosAdm i"),
    @NamedQuery(name = "IbPerfilesUsuariosAdm.findByUsuarioAdm", query = "SELECT i FROM IbPerfilesUsuariosAdm i WHERE i.ibPerfilesUsuariosAdmPK.usuarioAdm = :usuarioAdm"),
    @NamedQuery(name = "IbPerfilesUsuariosAdm.findByIdPerfilAdm", query = "SELECT i FROM IbPerfilesUsuariosAdm i WHERE i.ibPerfilesUsuariosAdmPK.idPerfilAdm = :idPerfilAdm"),
    @NamedQuery(name = "IbPerfilesUsuariosAdm.findByActivo", query = "SELECT i FROM IbPerfilesUsuariosAdm i WHERE i.activo = :activo")})
public class IbPerfilesUsuariosAdm implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IbPerfilesUsuariosAdmPK ibPerfilesUsuariosAdmPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private short activo;
    @JoinColumn(name = "ID_PERFIL_ADM", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private IbPerfilesAdm ibPerfilesAdm;

    public IbPerfilesUsuariosAdm() {
    }

    public IbPerfilesUsuariosAdm(IbPerfilesUsuariosAdmPK ibPerfilesUsuariosAdmPK) {
        this.ibPerfilesUsuariosAdmPK = ibPerfilesUsuariosAdmPK;
    }

    public IbPerfilesUsuariosAdm(IbPerfilesUsuariosAdmPK ibPerfilesUsuariosAdmPK, short activo) {
        this.ibPerfilesUsuariosAdmPK = ibPerfilesUsuariosAdmPK;
        this.activo = activo;
    }

    public IbPerfilesUsuariosAdm(String usuarioAdm, BigInteger idPerfilAdm) {
        this.ibPerfilesUsuariosAdmPK = new IbPerfilesUsuariosAdmPK(usuarioAdm, idPerfilAdm);
    }

    public IbPerfilesUsuariosAdmPK getIbPerfilesUsuariosAdmPK() {
        return ibPerfilesUsuariosAdmPK;
    }

    public void setIbPerfilesUsuariosAdmPK(IbPerfilesUsuariosAdmPK ibPerfilesUsuariosAdmPK) {
        this.ibPerfilesUsuariosAdmPK = ibPerfilesUsuariosAdmPK;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public IbPerfilesAdm getIbPerfilesAdm() {
        return ibPerfilesAdm;
    }

    public void setIbPerfilesAdm(IbPerfilesAdm ibPerfilesAdm) {
        this.ibPerfilesAdm = ibPerfilesAdm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ibPerfilesUsuariosAdmPK != null ? ibPerfilesUsuariosAdmPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbPerfilesUsuariosAdm)) {
            return false;
        }
        IbPerfilesUsuariosAdm other = (IbPerfilesUsuariosAdm) object;
        if ((this.ibPerfilesUsuariosAdmPK == null && other.ibPerfilesUsuariosAdmPK != null) || (this.ibPerfilesUsuariosAdmPK != null && !this.ibPerfilesUsuariosAdmPK.equals(other.ibPerfilesUsuariosAdmPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPerfilesUsuariosAdm[ ibPerfilesUsuariosAdmPK=" + ibPerfilesUsuariosAdmPK + " ]";
    }
    
}

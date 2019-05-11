/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "IB_TRANSACCIONES_PERFILES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbTransaccionesPerfiles.findAll", query = "SELECT i FROM IbTransaccionesPerfiles i"),
    @NamedQuery(name = "IbTransaccionesPerfiles.findById", query = "SELECT i FROM IbTransaccionesPerfiles i WHERE i.id = :id")})
public class IbTransaccionesPerfiles implements Serializable {
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransacciones idTransaccion;
    @JoinColumn(name = "ID_PERFIL_PILOTO", referencedColumnName = "ID")
    @ManyToOne
    private IbPerfilesPiloto idPerfilPiloto;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfiles idPerfil;

    public IbTransaccionesPerfiles() {
    }

    public IbTransaccionesPerfiles(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbTransacciones getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(IbTransacciones idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public IbPerfilesPiloto getIdPerfilPiloto() {
        return idPerfilPiloto;
    }

    public void setIdPerfilPiloto(IbPerfilesPiloto idPerfilPiloto) {
        this.idPerfilPiloto = idPerfilPiloto;
    }

    public IbPerfiles getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(IbPerfiles idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbTransaccionesPerfiles)) {
            return false;
        }
        IbTransaccionesPerfiles other = (IbTransaccionesPerfiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbTransaccionesPerfiles[ id=" + id + " ]";
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
    }

}

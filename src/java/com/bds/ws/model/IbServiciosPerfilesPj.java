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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_SERVICIOS_PERFILES_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbServiciosPerfilesPj.findAll", query = "SELECT i FROM IbServiciosPerfilesPj i"),
    @NamedQuery(name = "IbServiciosPerfilesPj.findById", query = "SELECT i FROM IbServiciosPerfilesPj i WHERE i.id = :id")})
public class IbServiciosPerfilesPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;
    @JoinColumn(name = "ID_PERFIL_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfilesPj idPerfilPj;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;

    public IbServiciosPerfilesPj() {
    }

    public IbServiciosPerfilesPj(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbServiciosPj getIdServicioPj() {
        return idServicioPj;
    }

    public void setIdServicioPj(IbServiciosPj idServicioPj) {
        this.idServicioPj = idServicioPj;
    }

    public IbPerfilesPj getIdPerfilPj() {
        return idPerfilPj;
    }

    public void setIdPerfilPj(IbPerfilesPj idPerfilPj) {
        this.idPerfilPj = idPerfilPj;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
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
        if (!(object instanceof IbServiciosPerfilesPj)) {
            return false;
        }
        IbServiciosPerfilesPj other = (IbServiciosPerfilesPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbServiciosPerfilesPj[ id=" + id + " ]";
    }
    
}

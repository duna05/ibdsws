/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_MONTOS_USUARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbMontosUsuariosPj.findAll", query = "SELECT i FROM IbMontosUsuariosPj i"),
    @NamedQuery(name = "IbMontosUsuariosPj.findById", query = "SELECT i FROM IbMontosUsuariosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbMontosUsuariosPj.findByMontoHasta", query = "SELECT i FROM IbMontosUsuariosPj i WHERE i.montoHasta = :montoHasta"),
    @NamedQuery(name = "IbMontosUsuariosPj.findByFechaCreacion", query = "SELECT i FROM IbMontosUsuariosPj i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbMontosUsuariosPj.findByFechaModificacion", query = "SELECT i FROM IbMontosUsuariosPj i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbMontosUsuariosPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_HASTA")
    private BigDecimal montoHasta;
    @Column(name = "FECHA_CREACION",     insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    private IbUsuariosPj idUsuarioPj;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    private IbEmpresas idEmpresa;
    
    public IbMontosUsuariosPj() {
    }

    public IbMontosUsuariosPj(BigDecimal id) {
        this.id = id;
    }

    public IbMontosUsuariosPj(BigDecimal id, BigDecimal montoHasta, Date fechaCreacion) {
        this.id = id;
        this.montoHasta = montoHasta;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public BigDecimal getMontoHasta() {
        return montoHasta;
    }

    public void setMontoHasta(BigDecimal montoHasta) {
        this.montoHasta = montoHasta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    @XmlTransient
    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }
    
    @XmlTransient
    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        if (!(object instanceof IbMontosUsuariosPj)) {
            return false;
        }
        IbMontosUsuariosPj other = (IbMontosUsuariosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbMontosUsuariosPj[ id=" + id + " ]";
    }
}

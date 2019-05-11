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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_SERVICIOS_EMPRESA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbServiciosEmpresa.findAll", query = "SELECT i FROM IbServiciosEmpresa i"),
    @NamedQuery(name = "IbServiciosEmpresa.findById", query = "SELECT i FROM IbServiciosEmpresa i WHERE i.id = :id"),
    @NamedQuery(name = "IbServiciosEmpresa.findByNroFideicomiso", query = "SELECT i FROM IbServiciosEmpresa i WHERE i.nroFideicomiso = :nroFideicomiso"),
    @NamedQuery(name = "IbServiciosEmpresa.findByCtaFideicomiso", query = "SELECT i FROM IbServiciosEmpresa i WHERE i.ctaFideicomiso = :ctaFideicomiso"),
    @NamedQuery(name = "IbServiciosEmpresa.findByFechaCreacion", query = "SELECT i FROM IbServiciosEmpresa i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbServiciosEmpresa.findByFechaModificacion", query = "SELECT i FROM IbServiciosEmpresa i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbServiciosEmpresa implements Serializable {
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 20)
    @Column(name = "NRO_FIDEICOMISO")
    private String nroFideicomiso;
    @Size(max = 20)
    @Column(name = "CTA_FIDEICOMISO")
    private String ctaFideicomiso;
    @Column(name = "FECHA_CREACION",     insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public IbServiciosEmpresa() {
    }

    public IbServiciosEmpresa(BigDecimal id) {
        this.id = id;
    }

    public IbServiciosEmpresa(BigDecimal id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNroFideicomiso() {
        return nroFideicomiso;
    }

    public void setNroFideicomiso(String nroFideicomiso) {
        this.nroFideicomiso = nroFideicomiso;
    }

    public String getCtaFideicomiso() {
        return ctaFideicomiso;
    }

    public void setCtaFideicomiso(String ctaFideicomiso) {
        this.ctaFideicomiso = ctaFideicomiso;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbServiciosEmpresa)) {
            return false;
        }
        IbServiciosEmpresa other = (IbServiciosEmpresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbServiciosEmpresa[ id=" + id + " ]";
    }

    public IbServiciosPj getIdServicioPj() {
        return idServicioPj;
    }

    public void setIdServicioPj(IbServiciosPj idServicioPj) {
        this.idServicioPj = idServicioPj;
    }

    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
}

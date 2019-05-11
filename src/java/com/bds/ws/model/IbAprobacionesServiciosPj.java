/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_APROBACIONES_SERVICIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAprobacionesServiciosPj.findAll", query = "SELECT i FROM IbAprobacionesServiciosPj i"),
    @NamedQuery(name = "IbAprobacionesServiciosPj.findById", query = "SELECT i FROM IbAprobacionesServiciosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbAprobacionesServiciosPj.findByCantAprobadores", query = "SELECT i FROM IbAprobacionesServiciosPj i WHERE i.cantAprobadores = :cantAprobadores"),
    @NamedQuery(name = "IbAprobacionesServiciosPj.findByFechaCreacion", query = "SELECT i FROM IbAprobacionesServiciosPj i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbAprobacionesServiciosPj.findByFechaModificacion", query = "SELECT i FROM IbAprobacionesServiciosPj i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbAprobacionesServiciosPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANT_APROBADORES")
    private BigInteger cantAprobadores;
    @Column(name = "FECHA_CREACION",     updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;

    public IbAprobacionesServiciosPj() {
    }

    public IbAprobacionesServiciosPj(BigDecimal id) {
        this.id = id;
    }

    public IbAprobacionesServiciosPj(BigDecimal id, BigInteger cantAprobadores, Date fechaCreacion) {
        this.id = id;
        this.cantAprobadores = cantAprobadores;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getCantAprobadores() {
        return cantAprobadores;
    }

    public void setCantAprobadores(BigInteger cantAprobadores) {
        this.cantAprobadores = cantAprobadores;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbAprobacionesServiciosPj)) {
            return false;
        }
        IbAprobacionesServiciosPj other = (IbAprobacionesServiciosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAprobacionesServiciosPj[ id=" + id + " ]";
    }
    
}

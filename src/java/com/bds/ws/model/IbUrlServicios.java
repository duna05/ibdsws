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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_URL_SERVICIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUrlServicios.findAll", query = "SELECT i FROM IbUrlServicios i"),
    @NamedQuery(name = "IbUrlServicios.findById", query = "SELECT i FROM IbUrlServicios i WHERE i.id = :id"),
    @NamedQuery(name = "IbUrlServicios.findByFechaCreacion", query = "SELECT i FROM IbUrlServicios i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbUrlServicios.findByFechaModificacion", query = "SELECT i FROM IbUrlServicios i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbUrlServicios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JoinColumn(name = "ID_URL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUrl idUrl;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;

    public IbUrlServicios() {
    }

    public IbUrlServicios(BigDecimal id) {
        this.id = id;
    }

    public IbUrlServicios(BigDecimal id, Date fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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

    public IbUrl getIdUrl() {
        return idUrl;
    }

    public void setIdUrl(IbUrl idUrl) {
        this.idUrl = idUrl;
    }

    public IbServiciosPj getIdServicioPj() {
        return idServicioPj;
    }

    public void setIdServicioPj(IbServiciosPj idServicioPj) {
        this.idServicioPj = idServicioPj;
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
        if (!(object instanceof IbUrlServicios)) {
            return false;
        }
        IbUrlServicios other = (IbUrlServicios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUrlServicios[ id=" + id + " ]";
    }
    
}

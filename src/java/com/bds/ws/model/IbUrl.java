/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_URL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUrl.findAll", query = "SELECT i FROM IbUrl i"),
    @NamedQuery(name = "IbUrl.findById", query = "SELECT i FROM IbUrl i WHERE i.id = :id"),
    @NamedQuery(name = "IbUrl.findByNombre", query = "SELECT i FROM IbUrl i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbUrl.findByFechaCreacion", query = "SELECT i FROM IbUrl i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbUrl.findByFechaModificacion", query = "SELECT i FROM IbUrl i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbUrl implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUrl")
    private Collection<IbUrlServicios> ibUrlServiciosCollection;

    public IbUrl() {
    }

    public IbUrl(BigDecimal id) {
        this.id = id;
    }

    public IbUrl(BigDecimal id, String nombre, Date fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public Collection<IbUrlServicios> getIbUrlServiciosCollection() {
        return ibUrlServiciosCollection;
    }

    public void setIbUrlServiciosCollection(Collection<IbUrlServicios> ibUrlServiciosCollection) {
        this.ibUrlServiciosCollection = ibUrlServiciosCollection;
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
        if (!(object instanceof IbUrl)) {
            return false;
        }
        IbUrl other = (IbUrl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUrl[ id=" + id + " ]";
    }
    
}

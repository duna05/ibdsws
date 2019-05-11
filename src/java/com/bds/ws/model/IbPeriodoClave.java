/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_PERIODO_CLAVE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPeriodoClave.findAll", query = "SELECT i FROM IbPeriodoClave i ORDER BY i.cantidad ASC"),
    @NamedQuery(name = "IbPeriodoClave.findById", query = "SELECT i FROM IbPeriodoClave i WHERE i.id = :id"),
    @NamedQuery(name = "IbPeriodoClave.findByCantidad", query = "SELECT i FROM IbPeriodoClave i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "IbPeriodoClave.findByDescripcion", query = "SELECT i FROM IbPeriodoClave i WHERE i.descripcion = :descripcion")})
public class IbPeriodoClave implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD")
    private BigInteger cantidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public IbPeriodoClave() {
    }

    public IbPeriodoClave(BigDecimal id) {
        this.id = id;
    }

    public IbPeriodoClave(BigDecimal id, BigInteger cantidad, String descripcion) {
        this.id = id;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof IbPeriodoClave)) {
            return false;
        }
        IbPeriodoClave other = (IbPeriodoClave) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPeriodoClave[ id=" + id + " ]";
    }
    
}

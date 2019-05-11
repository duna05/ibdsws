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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_FECHA_FERIADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbFechaFeriados.findAll", query = "SELECT i FROM IbFechaFeriados i"),
    @NamedQuery(name = "IbFechaFeriados.findById", query = "SELECT i FROM IbFechaFeriados i WHERE i.id = :id"),
    @NamedQuery(name = "IbFechaFeriados.findByFecha", query = "SELECT i FROM IbFechaFeriados i WHERE i.fecha = :fecha")})
public class IbFechaFeriados implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;

    public IbFechaFeriados() {
    }

    public IbFechaFeriados(BigDecimal id) {
        this.id = id;
    }

    public IbFechaFeriados(BigDecimal id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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
        if (!(object instanceof IbFechaFeriados)) {
            return false;
        }
        IbFechaFeriados other = (IbFechaFeriados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbFechaFeriados[ id=" + id + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}

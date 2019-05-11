/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.vasquez
 */
@Entity
@Table(name = "IB_ESTATUS_PAGOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbEstatusPagosPj.findAll", query = "SELECT i FROM IbEstatusPagosPj i"),
    @NamedQuery(name = "IbEstatusPagosPj.findById", query = "SELECT i FROM IbEstatusPagosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbEstatusPagosPj.findByNombre", query = "SELECT i FROM IbEstatusPagosPj i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbEstatusPagosPj.findByDescripcion", query = "SELECT i FROM IbEstatusPagosPj i WHERE i.descripcion = :descripcion")})
public class IbEstatusPagosPj implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatusAutorizacion")
    private Collection<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatusAutorizacion")
    private Collection<IbBeneficiariosPj> ibBeneficiariosPjCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatusAutorizacion")
    private Collection<IbCargaBeneficiariosPj> ibCargaBeneficiariosPjCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatus")
    private Collection<IbCargaNominaPj> ibCargaNominaPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estatus")
    private Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection;

    public IbEstatusPagosPj() {
    }

    public IbEstatusPagosPj(BigDecimal id) {
        this.id = id;
    }

    public IbEstatusPagosPj(BigDecimal id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<IbCargaNominaPj> getIbCargaNominaPjCollection() {
        return ibCargaNominaPjCollection;
    }

    public void setIbCargaNominaPjCollection(Collection<IbCargaNominaPj> ibCargaNominaPjCollection) {
        this.ibCargaNominaPjCollection = ibCargaNominaPjCollection;
    }

    @XmlTransient
    public Collection<IbCargaNominaDetallePj> getIbCargaNominaDetallePjCollection() {
        return ibCargaNominaDetallePjCollection;
    }

    public void setIbCargaNominaDetallePjCollection(Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection) {
        this.ibCargaNominaDetallePjCollection = ibCargaNominaDetallePjCollection;
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
        if (!(object instanceof IbEstatusPagosPj)) {
            return false;
        }
        IbEstatusPagosPj other = (IbEstatusPagosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbEstatusPagosPj[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbCargaBeneficiariosPj> getIbCargaBeneficiariosPjCollection() {
        return ibCargaBeneficiariosPjCollection;
    }

    public void setIbCargaBeneficiariosPjCollection(Collection<IbCargaBeneficiariosPj> ibCargaBeneficiariosPjCollection) {
        this.ibCargaBeneficiariosPjCollection = ibCargaBeneficiariosPjCollection;
    }

    @XmlTransient
    public Collection<IbBeneficiariosPj> getIbBeneficiariosPjCollection() {
        return ibBeneficiariosPjCollection;
    }

    public void setIbBeneficiariosPjCollection(Collection<IbBeneficiariosPj> ibBeneficiariosPjCollection) {
        this.ibBeneficiariosPjCollection = ibBeneficiariosPjCollection;
    }

    @XmlTransient
    public Collection<IbCtasXBeneficiariosPj> getIbCtasXBeneficiariosPjCollection() {
        return ibCtasXBeneficiariosPjCollection;
    }

    public void setIbCtasXBeneficiariosPjCollection(Collection<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjCollection) {
        this.ibCtasXBeneficiariosPjCollection = ibCtasXBeneficiariosPjCollection;
    }
    
}

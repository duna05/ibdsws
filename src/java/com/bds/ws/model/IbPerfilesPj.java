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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_PERFILES_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPerfilesPj.findAll", query = "SELECT i FROM IbPerfilesPj i"),
    @NamedQuery(name = "IbPerfilesPj.findById", query = "SELECT i FROM IbPerfilesPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbPerfilesPj.findByNombre", query = "SELECT i FROM IbPerfilesPj i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbPerfilesPj.findByEstatus", query = "SELECT i FROM IbPerfilesPj i WHERE i.estatus = :estatus")})
public class IbPerfilesPj implements Serializable {
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
    @Column(name = "ESTATUS")
    private Character estatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilAcceso")
    private Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfilPj")
    private Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection;

    public IbPerfilesPj() {
    }

    public IbPerfilesPj(BigDecimal id) {
        this.id = id;
    }

    public IbPerfilesPj(BigDecimal id, String nombre, Character estatus) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
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

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    @XmlTransient
    public Collection<IbEmpresasUsuariosPj> getIbEmpresasUsuariosPjCollection() {
        return ibEmpresasUsuariosPjCollection;
    }

    public void setIbEmpresasUsuariosPjCollection(Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection) {
        this.ibEmpresasUsuariosPjCollection = ibEmpresasUsuariosPjCollection;
    }

    @XmlTransient
    public Collection<IbServiciosPerfilesPj> getIbServiciosPerfilesPjCollection() {
        return ibServiciosPerfilesPjCollection;
    }

    public void setIbServiciosPerfilesPjCollection(Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection) {
        this.ibServiciosPerfilesPjCollection = ibServiciosPerfilesPjCollection;
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
        if (!(object instanceof IbPerfilesPj)) {
            return false;
        }
        IbPerfilesPj other = (IbPerfilesPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPerfilesPj[ id=" + id + " ]";
    }
    
}

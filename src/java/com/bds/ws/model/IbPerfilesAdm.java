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
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_PERFILES_ADM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPerfilesAdm.findAll", query = "SELECT i FROM IbPerfilesAdm i"),
    @NamedQuery(name = "IbPerfilesAdm.findById", query = "SELECT i FROM IbPerfilesAdm i WHERE i.id = :id"),
    @NamedQuery(name = "IbPerfilesAdm.findByNombre", query = "SELECT i FROM IbPerfilesAdm i WHERE i.nombre = :nombre")})
public class IbPerfilesAdm implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Character tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ibPerfilesAdm")
    private Collection<IbPerfilesUsuariosAdm> ibPerfilesUsuariosAdmCollection;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfilAdm")
    private Collection<IbPerfilesModulos> ibPerfilesModulosCollection;

    public IbPerfilesAdm() {
    }

    public IbPerfilesAdm(BigDecimal id) {
        this.id = id;
    }

    public IbPerfilesAdm(BigDecimal id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @XmlTransient
    public Collection<IbPerfilesModulos> getIbPerfilesModulosCollection() {
        return ibPerfilesModulosCollection;
    }

    public void setIbPerfilesModulosCollection(Collection<IbPerfilesModulos> ibPerfilesModulosCollection) {
        this.ibPerfilesModulosCollection = ibPerfilesModulosCollection;
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
        if (!(object instanceof IbPerfilesAdm)) {
            return false;
        }
        IbPerfilesAdm other = (IbPerfilesAdm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPerfilesAdm[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbPerfilesUsuariosAdm> getIbPerfilesUsuariosAdmCollection() {
        return ibPerfilesUsuariosAdmCollection;
    }

    public void setIbPerfilesUsuariosAdmCollection(Collection<IbPerfilesUsuariosAdm> ibPerfilesUsuariosAdmCollection) {
        this.ibPerfilesUsuariosAdmCollection = ibPerfilesUsuariosAdmCollection;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

}

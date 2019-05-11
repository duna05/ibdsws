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
@Table(name = "IB_CANAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCanal.findAll", query = "SELECT i FROM IbCanal i ORDER BY i.nombre ASC"),
    @NamedQuery(name = "IbCanal.findById", query = "SELECT i FROM IbCanal i WHERE i.id = :id"),
    @NamedQuery(name = "IbCanal.findByCodigo", query = "SELECT i FROM IbCanal i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "IbCanal.findByNombre", query = "SELECT i FROM IbCanal i WHERE i.nombre = :nombre")})
public class IbCanal implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbServiciosPj> ibServiciosPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbConexionUsuarioPj> ibConexionUsuarioPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbLogsPj> ibLogsPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection;
    @OneToMany(mappedBy = "idCanal")
    private Collection<IbModulosPj> ibModulosPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbTransaccionesPerfiles> ibTransaccionesPerfilesCollection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CODIGO")
    private String codigo;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbLogs> ibLogsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbParametros> ibParametrosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbModulos> ibModulosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCanal")
    private Collection<IbUsuariosCanales> ibUsuariosCanalesCollection;

    public IbCanal() {
    }

    public IbCanal(BigDecimal id) {
        this.id = id;
    }

    public IbCanal(BigDecimal id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
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
    public Collection<IbLogs> getIbLogsCollection() {
        return ibLogsCollection;
    }

    public void setIbLogsCollection(Collection<IbLogs> ibLogsCollection) {
        this.ibLogsCollection = ibLogsCollection;
    }

    @XmlTransient
    public Collection<IbParametros> getIbParametrosCollection() {
        return ibParametrosCollection;
    }

    public void setIbParametrosCollection(Collection<IbParametros> ibParametrosCollection) {
        this.ibParametrosCollection = ibParametrosCollection;
    }

    @XmlTransient
    public Collection<IbModulos> getIbModulosCollection() {
        return ibModulosCollection;
    }

    public void setIbModulosCollection(Collection<IbModulos> ibModulosCollection) {
        this.ibModulosCollection = ibModulosCollection;
    }

    @XmlTransient
    public Collection<IbUsuariosCanales> getIbUsuariosCanalesCollection() {
        return ibUsuariosCanalesCollection;
    }

    public void setIbUsuariosCanalesCollection(Collection<IbUsuariosCanales> ibUsuariosCanalesCollection) {
        this.ibUsuariosCanalesCollection = ibUsuariosCanalesCollection;
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
        if (!(object instanceof IbCanal)) {
            return false;
        }
        IbCanal other = (IbCanal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCanal[ id=" + id + " ]";
    }
    
    public String getCodigo() {
        return codigo;
}

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<IbTransaccionesPerfiles> getIbTransaccionesPerfilesCollection() {
        return ibTransaccionesPerfilesCollection;
    }

    public void setIbTransaccionesPerfilesCollection(Collection<IbTransaccionesPerfiles> ibTransaccionesPerfilesCollection) {
        this.ibTransaccionesPerfilesCollection = ibTransaccionesPerfilesCollection;
    }

    @XmlTransient
    public Collection<IbModulosPj> getIbModulosPjCollection() {
        return ibModulosPjCollection;
    }

    public void setIbModulosPjCollection(Collection<IbModulosPj> ibModulosPjCollection) {
        this.ibModulosPjCollection = ibModulosPjCollection;
    }

    @XmlTransient
    public Collection<IbServiciosPerfilesPj> getIbServiciosPerfilesPjCollection() {
        return ibServiciosPerfilesPjCollection;
    }

    public void setIbServiciosPerfilesPjCollection(Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection) {
        this.ibServiciosPerfilesPjCollection = ibServiciosPerfilesPjCollection;
    }

    @XmlTransient
    public Collection<IbLogsPj> getIbLogsPjCollection() {
        return ibLogsPjCollection;
    }

    public void setIbLogsPjCollection(Collection<IbLogsPj> ibLogsPjCollection) {
        this.ibLogsPjCollection = ibLogsPjCollection;
    }

    @XmlTransient
    public Collection<IbConexionUsuarioPj> getIbConexionUsuarioPjCollection() {
        return ibConexionUsuarioPjCollection;
    }

    public void setIbConexionUsuarioPjCollection(Collection<IbConexionUsuarioPj> ibConexionUsuarioPjCollection) {
        this.ibConexionUsuarioPjCollection = ibConexionUsuarioPjCollection;
    }

    @XmlTransient
    public Collection<IbServiciosPj> getIbServiciosPjCollection() {
        return ibServiciosPjCollection;
    }

    public void setIbServiciosPjCollection(Collection<IbServiciosPj> ibServiciosPjCollection) {
        this.ibServiciosPjCollection = ibServiciosPjCollection;
    }
    }

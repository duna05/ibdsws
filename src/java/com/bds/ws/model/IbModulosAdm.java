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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "IB_MODULOS_ADM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbModulosAdm.findAll", query = "SELECT i FROM IbModulosAdm i"),
    @NamedQuery(name = "IbModulosAdm.findById", query = "SELECT i FROM IbModulosAdm i WHERE i.id = :id"),
    @NamedQuery(name = "IbModulosAdm.findByNombre", query = "SELECT i FROM IbModulosAdm i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbModulosAdm.findByCodigo", query = "SELECT i FROM IbModulosAdm i WHERE i.codigo = :codigo")})
public class IbModulosAdm implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "NIVEL")
    private String nivel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "POSICION")
    private String posicion;
    @Size(max = 200)
    @Column(name = "URL")
    private String url;
    @OneToMany(mappedBy = "idPadre")
    private Collection<IbModulosAdm> ibModulosAdmCollection;
    @JoinColumn(name = "ID_PADRE", referencedColumnName = "ID")
    @ManyToOne
    private IbModulosAdm idPadre;

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
    @Size(min = 1, max = 50)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(mappedBy = "idModuloAdm")
    private Collection<IbAuditbk> ibAuditbkCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModuloAdm")
    private Collection<IbPerfilesModulos> ibPerfilesModulosCollection;

    public IbModulosAdm() {
    }

    public IbModulosAdm(BigDecimal id) {
        this.id = id;
    }

    public IbModulosAdm(BigDecimal id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<IbAuditbk> getIbAuditbkCollection() {
        return ibAuditbkCollection;
    }

    public void setIbAuditbkCollection(Collection<IbAuditbk> ibAuditbkCollection) {
        this.ibAuditbkCollection = ibAuditbkCollection;
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
        if (!(object instanceof IbModulosAdm)) {
            return false;
        }
        IbModulosAdm other = (IbModulosAdm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbModulosAdm[ id=" + id + " ]";
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public Collection<IbModulosAdm> getIbModulosAdmCollection() {
        return ibModulosAdmCollection;
    }

    public void setIbModulosAdmCollection(Collection<IbModulosAdm> ibModulosAdmCollection) {
        this.ibModulosAdmCollection = ibModulosAdmCollection;
    }

    public IbModulosAdm getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(IbModulosAdm idPadre) {
        this.idPadre = idPadre;
    }

}

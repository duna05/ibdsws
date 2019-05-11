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
@Table(name = "IB_AFILIACIONES_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAfiliacionesPj.findAll", query = "SELECT i FROM IbAfiliacionesPj i"),
    @NamedQuery(name = "IbAfiliacionesPj.findById", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbAfiliacionesPj.findByTipoCarga", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "IbAfiliacionesPj.findByTipo", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "IbAfiliacionesPj.findByTipoDoc", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbAfiliacionesPj.findByDocumento", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbAfiliacionesPj.findByNombre", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbAfiliacionesPj.findByNumeroCuenta", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "IbAfiliacionesPj.findByEmail", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.email = :email"),
    @NamedQuery(name = "IbAfiliacionesPj.findByCelular", query = "SELECT i FROM IbAfiliacionesPj i WHERE i.celular = :celular")})
public class IbAfiliacionesPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_CARGA")
    private Character tipoCarga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Character tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DOCUMENTO")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "CELULAR")
    private String celular;
    @OneToMany(mappedBy = "idAfiliacionPj")
    private Collection<IbLogsPj> ibLogsPjCollection;

    public IbAfiliacionesPj() {
    }

    public IbAfiliacionesPj(BigDecimal id) {
        this.id = id;
    }

    public IbAfiliacionesPj(BigDecimal id, Character tipoCarga, Character tipo, Character tipoDoc, String documento, String nombre, String numeroCuenta, String email, String celular) {
        this.id = id;
        this.tipoCarga = tipoCarga;
        this.tipo = tipo;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.email = email;
        this.celular = celular;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(Character tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Character getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Character tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @XmlTransient
    public Collection<IbLogsPj> getIbLogsPjCollection() {
        return ibLogsPjCollection;
    }

    public void setIbLogsPjCollection(Collection<IbLogsPj> ibLogsPjCollection) {
        this.ibLogsPjCollection = ibLogsPjCollection;
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
        if (!(object instanceof IbAfiliacionesPj)) {
            return false;
        }
        IbAfiliacionesPj other = (IbAfiliacionesPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAfiliacionesPj[ id=" + id + " ]";
    }
    
}

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
@Table(name = "IB_EMPRESAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbEmpresas.findAll", query = "SELECT i FROM IbEmpresas i"),
    @NamedQuery(name = "IbEmpresas.findById", query = "SELECT i FROM IbEmpresas i WHERE i.id = :id"),
    @NamedQuery(name = "IbEmpresas.findByTipoRif", query = "SELECT i FROM IbEmpresas i WHERE i.tipoRif = :tipoRif"),
    @NamedQuery(name = "IbEmpresas.findByNroRif", query = "SELECT i FROM IbEmpresas i WHERE i.nroRif = :nroRif"),
    @NamedQuery(name = "IbEmpresas.findByNroCta", query = "SELECT i FROM IbEmpresas i WHERE i.nroCta = :nroCta"),
    @NamedQuery(name = "IbEmpresas.findByCodCliente", query = "SELECT i FROM IbEmpresas i WHERE i.codCliente = :codCliente"),
    @NamedQuery(name = "IbEmpresas.findByNombre", query = "SELECT i FROM IbEmpresas i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbEmpresas.findByTipoAcceso", query = "SELECT i FROM IbEmpresas i WHERE i.tipoAcceso = :tipoAcceso"),
    @NamedQuery(name = "IbEmpresas.findByEstatus", query = "SELECT i FROM IbEmpresas i WHERE i.estatus = :estatus"),
    @NamedQuery(name = "IbEmpresas.findByFechaCreacion", query = "SELECT i FROM IbEmpresas i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbEmpresas.findByFechaModificacion", query = "SELECT i FROM IbEmpresas i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbEmpresas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa")
    private Collection<IbLogsPj> ibLogsPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa")
    private Collection<IbServiciosEmpresa> ibServiciosEmpresaCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_RIF")
    private Character tipoRif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_RIF")
    private String nroRif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CTA")
    private String nroCta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CLIENTE")
    private long codCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_ACCESO")
    private Character tipoAcceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @Column(name = "FECHA_CREACION",     insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpresa")
    private Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection;
    @Column(name = "CODIGO_FIDEICOMISO")
    private String codigoFideicomiso;
    @Column(name = "NRO_CTA_DEBITO_FIDEICOMISO")
    private String nroCtaDebitoFideicomiso;
    @Column(name = "COD_USUARIO_ABANKS_MODIFICA")
    private String codUsuarioAbanksModifica;
    @Column(name = "COD_EMPRESA_ORDENANTE", insertable = false, updatable = false)
    private Integer codEmpresaOrdenante;
    @Column(name = "CLIENTE_ESPE_DOMICILIACION", insertable = false, updatable = false)
    private Integer clienteEspDomiciliacion;
    
    public IbEmpresas() {
    }

    public IbEmpresas(BigDecimal id) {
        this.id = id;
    }

    public IbEmpresas(BigDecimal id, Character tipoRif, String nroRif, String nroCta, long codCliente, String nombre, Character tipoAcceso, Character estatus, Date fechaCreacion) {
        this.id = id;
        this.tipoRif = tipoRif;
        this.nroRif = nroRif;
        this.nroCta = nroCta;
        this.codCliente = codCliente;
        this.nombre = nombre;
        this.tipoAcceso = tipoAcceso;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getTipoRif() {
        return tipoRif;
    }

    public void setTipoRif(Character tipoRif) {
        this.tipoRif = tipoRif;
    }

    public String getNroRif() {
        return nroRif;
    }

    public void setNroRif(String nroRif) {
        this.nroRif = nroRif;
    }

    public String getNroCta() {
        return nroCta;
    }

    public void setNroCta(String nroCta) {
        this.nroCta = nroCta;
    }

    public long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(long codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(Character tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
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

    public String getCodigoFideicomiso() {
        return codigoFideicomiso;
    }

    public void setCodigoFideicomiso(String codigoFideicomiso) {
        this.codigoFideicomiso = codigoFideicomiso;
    }
    
    @XmlTransient
    public Collection<IbEmpresasUsuariosPj> getIbEmpresasUsuariosPjCollection() {
        return ibEmpresasUsuariosPjCollection;
    }

    public void setIbEmpresasUsuariosPjCollection(Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection) {
        this.ibEmpresasUsuariosPjCollection = ibEmpresasUsuariosPjCollection;
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
        if (!(object instanceof IbEmpresas)) {
            return false;
        }
        IbEmpresas other = (IbEmpresas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbEmpresas[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbServiciosEmpresa> getIbServiciosEmpresaCollection() {
        return ibServiciosEmpresaCollection;
    }

    public void setIbServiciosEmpresaCollection(Collection<IbServiciosEmpresa> ibServiciosEmpresaCollection) {
        this.ibServiciosEmpresaCollection = ibServiciosEmpresaCollection;
    }

    @XmlTransient
    public Collection<IbLogsPj> getIbLogsPjCollection() {
        return ibLogsPjCollection;
    }

    public void setIbLogsPjCollection(Collection<IbLogsPj> ibLogsPjCollection) {
        this.ibLogsPjCollection = ibLogsPjCollection;
    }

    public String getNroCtaDebitoFideicomiso() {
        return nroCtaDebitoFideicomiso;
    }

    public void setNroCtaDebitoFideicomiso(String nroCtaDebitoFideicomiso) {
        this.nroCtaDebitoFideicomiso = nroCtaDebitoFideicomiso;
    }

    public String getCodUsuarioAbanksModifica() {
        return codUsuarioAbanksModifica;
    }

    public void setCodUsuarioAbanksModifica(String codUsuarioAbanksModifica) {
        this.codUsuarioAbanksModifica = codUsuarioAbanksModifica;
    }

    public Integer getCodEmpresaOrdenante() {
        return codEmpresaOrdenante;
    }

    public void setCodEmpresaOrdenante(Integer codEmpresaOrdenante) {
        this.codEmpresaOrdenante = codEmpresaOrdenante;
    }

    public Integer getClienteEspDomiciliacion() {
        return clienteEspDomiciliacion;
    }

    public void setClienteEspDomiciliacion(Integer clienteEspDomiciliacion) {
        this.clienteEspDomiciliacion = clienteEspDomiciliacion;
    }
}

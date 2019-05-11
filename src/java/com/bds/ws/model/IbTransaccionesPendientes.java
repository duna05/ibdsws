/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_TRANSACCIONES_PENDIENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbTransaccionesPendientes.findAll", query = "SELECT i FROM IbTransaccionesPendientes i"),
    @NamedQuery(name = "IbTransaccionesPendientes.findById", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.id = :id"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByTipo", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByEstatus", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.estatus = :estatus"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByLote", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.lote = :lote"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByFechaHoraRegistro", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.fechaHoraRegistro = :fechaHoraRegistro"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByFechaHoraValor", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.fechaHoraValor = :fechaHoraValor"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByMonto", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.monto = :monto"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByAprobacionesRequeridas", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.aprobacionesRequeridas = :aprobacionesRequeridas"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByTipoDoc", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByDocumento", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByCuentaOrigen", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.cuentaOrigen = :cuentaOrigen"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByCuentaDestino", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.cuentaDestino = :cuentaDestino"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByDescripcion", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByTipoIdBeneficiario", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.tipoIdBeneficiario = :tipoIdBeneficiario"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByIdBeneficiario", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.idBeneficiario = :idBeneficiario"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByNombreBeneficiario", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.nombreBeneficiario = :nombreBeneficiario"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByEmailBeneficiario", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.emailBeneficiario = :emailBeneficiario"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByTipoCarga", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "IbTransaccionesPendientes.findByFechaEjecucion", query = "SELECT i FROM IbTransaccionesPendientes i WHERE i.fechaEjecucion = :fechaEjecucion")})
public class IbTransaccionesPendientes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Character tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @Column(name = "LOTE")
    private BigInteger lote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_VALOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "APROBACIONES_REQUERIDAS")
    private BigInteger aprobacionesRequeridas;
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
    @Size(min = 1, max = 20)
    @Column(name = "CUENTA_ORIGEN")
    private String cuentaOrigen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CUENTA_DESTINO")
    private String cuentaDestino;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TIPO_ID_BENEFICIARIO")
    private Character tipoIdBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BENEFICIARIO")
    private BigInteger idBeneficiario;
    @Size(max = 255)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Size(max = 150)
    @Column(name = "EMAIL_BENEFICIARIO")
    private String emailBeneficiario;
    @Column(name = "TIPO_CARGA")
    private Character tipoCarga;
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccionPendiente")
    private Collection<IbAprobacionesTransacciones> ibAprobacionesTransaccionesCollection;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccionPendiente")
    private Collection<IbTransaccionesBeneficiarios> ibTransaccionesBeneficiariosCollection;

    public IbTransaccionesPendientes() {
    }

    public IbTransaccionesPendientes(BigDecimal id) {
        this.id = id;
    }

    public IbTransaccionesPendientes(BigDecimal id, Character tipo, Character estatus, Date fechaHoraRegistro, Date fechaHoraValor, BigDecimal monto, BigInteger aprobacionesRequeridas, Character tipoDoc, String documento, String cuentaOrigen, String cuentaDestino, BigInteger idBeneficiario) {
        this.id = id;
        this.tipo = tipo;
        this.estatus = estatus;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.fechaHoraValor = fechaHoraValor;
        this.monto = monto;
        this.aprobacionesRequeridas = aprobacionesRequeridas;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.idBeneficiario = idBeneficiario;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    public BigInteger getLote() {
        return lote;
    }

    public void setLote(BigInteger lote) {
        this.lote = lote;
    }

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Date getFechaHoraValor() {
        return fechaHoraValor;
    }

    public void setFechaHoraValor(Date fechaHoraValor) {
        this.fechaHoraValor = fechaHoraValor;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigInteger getAprobacionesRequeridas() {
        return aprobacionesRequeridas;
    }

    public void setAprobacionesRequeridas(BigInteger aprobacionesRequeridas) {
        this.aprobacionesRequeridas = aprobacionesRequeridas;
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

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Character getTipoIdBeneficiario() {
        return tipoIdBeneficiario;
    }

    public void setTipoIdBeneficiario(Character tipoIdBeneficiario) {
        this.tipoIdBeneficiario = tipoIdBeneficiario;
    }

    public BigInteger getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(BigInteger idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    public void setEmailBeneficiario(String emailBeneficiario) {
        this.emailBeneficiario = emailBeneficiario;
    }

    public Character getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(Character tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    @XmlTransient
    public Collection<IbAprobacionesTransacciones> getIbAprobacionesTransaccionesCollection() {
        return ibAprobacionesTransaccionesCollection;
    }

    public void setIbAprobacionesTransaccionesCollection(Collection<IbAprobacionesTransacciones> ibAprobacionesTransaccionesCollection) {
        this.ibAprobacionesTransaccionesCollection = ibAprobacionesTransaccionesCollection;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public Collection<IbTransaccionesBeneficiarios> getIbTransaccionesBeneficiariosCollection() {
        return ibTransaccionesBeneficiariosCollection;
    }

    public void setIbTransaccionesBeneficiariosCollection(Collection<IbTransaccionesBeneficiarios> ibTransaccionesBeneficiariosCollection) {
        this.ibTransaccionesBeneficiariosCollection = ibTransaccionesBeneficiariosCollection;
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
        if (!(object instanceof IbTransaccionesPendientes)) {
            return false;
        }
        IbTransaccionesPendientes other = (IbTransaccionesPendientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbTransaccionesPendientes[ id=" + id + " ]";
    }

}

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
@Table(name = "IB_TRANSACCIONES_BENEFICIARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findAll", query = "SELECT i FROM IbTransaccionesBeneficiarios i"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findById", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.id = :id"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByTipoCarga", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.tipoCarga = :tipoCarga"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByTipo", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByNombre", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByTipoDoc", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByDocumento", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByNumeroCuenta", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByEmail", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.email = :email"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByCelular", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.celular = :celular"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByMonto", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.monto = :monto"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByReferencia", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByConcepto", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.concepto = :concepto"),
    @NamedQuery(name = "IbTransaccionesBeneficiarios.findByReversada", query = "SELECT i FROM IbTransaccionesBeneficiarios i WHERE i.reversada = :reversada")})
public class IbTransaccionesBeneficiarios implements Serializable {

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
    @Size(min = 1, max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Size(max = 20)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Size(max = 150)
    @Column(name = "CONCEPTO")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REVERSADA")
    private Character reversada;
    @JoinColumn(name = "ID_TRANSACCION_PENDIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransaccionesPendientes idTransaccionPendiente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccionBeneficiario")
    private Collection<IbDetallePagoProveedor> ibDetallePagoProveedorCollection;

    public IbTransaccionesBeneficiarios() {
    }

    public IbTransaccionesBeneficiarios(BigDecimal id) {
        this.id = id;
    }

    public IbTransaccionesBeneficiarios(BigDecimal id, Character tipoCarga, Character tipo, String nombre, Character tipoDoc, String documento, String numeroCuenta, String email, String celular, BigDecimal monto, Character reversada) {
        this.id = id;
        this.tipoCarga = tipoCarga;
        this.tipo = tipo;
        this.nombre = nombre;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.numeroCuenta = numeroCuenta;
        this.email = email;
        this.celular = celular;
        this.monto = monto;
        this.reversada = reversada;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Character getReversada() {
        return reversada;
    }

    public void setReversada(Character reversada) {
        this.reversada = reversada;
    }

    public IbTransaccionesPendientes getIdTransaccionPendiente() {
        return idTransaccionPendiente;
    }

    public void setIdTransaccionPendiente(IbTransaccionesPendientes idTransaccionPendiente) {
        this.idTransaccionPendiente = idTransaccionPendiente;
    }

    @XmlTransient
    public Collection<IbDetallePagoProveedor> getIbDetallePagoProveedorCollection() {
        return ibDetallePagoProveedorCollection;
    }

    public void setIbDetallePagoProveedorCollection(Collection<IbDetallePagoProveedor> ibDetallePagoProveedorCollection) {
        this.ibDetallePagoProveedorCollection = ibDetallePagoProveedorCollection;
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
        if (!(object instanceof IbTransaccionesBeneficiarios)) {
            return false;
        }
        IbTransaccionesBeneficiarios other = (IbTransaccionesBeneficiarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbTransaccionesBeneficiarios[ id=" + id + " ]";
    }

}

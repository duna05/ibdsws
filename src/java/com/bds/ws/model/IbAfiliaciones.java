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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "IB_AFILIACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAfiliaciones.findAll", query = "SELECT i FROM IbAfiliaciones i"),
    @NamedQuery(name = "IbAfiliaciones.findById", query = "SELECT i FROM IbAfiliaciones i WHERE i.id = :id"),
    @NamedQuery(name = "IbAfiliaciones.findByAlias", query = "SELECT i FROM IbAfiliaciones i WHERE i.alias = :alias"),
    @NamedQuery(name = "IbAfiliaciones.findByNumeroCuenta", query = "SELECT i FROM IbAfiliaciones i WHERE i.numeroCuenta = :numeroCuenta"),
    @NamedQuery(name = "IbAfiliaciones.findByNombreBeneficiario", query = "SELECT i FROM IbAfiliaciones i WHERE i.nombreBeneficiario = :nombreBeneficiario"),
    @NamedQuery(name = "IbAfiliaciones.findByTipoDoc", query = "SELECT i FROM IbAfiliaciones i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbAfiliaciones.findByDocumento", query = "SELECT i FROM IbAfiliaciones i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbAfiliaciones.findByEmail", query = "SELECT i FROM IbAfiliaciones i WHERE i.email = :email"),
    @NamedQuery(name = "IbAfiliaciones.findByMontoMaximo", query = "SELECT i FROM IbAfiliaciones i WHERE i.montoMaximo = :montoMaximo"),
    @NamedQuery(name = "IbAfiliaciones.findByCodUsuario", query = "SELECT i FROM IbAfiliaciones i WHERE i.codUsuario = :codUsuario"),
    @NamedQuery(name = "IbAfiliaciones.findByFechaRegistro", query = "SELECT i FROM IbAfiliaciones i WHERE i.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "IbAfiliaciones.findByFechaModificacion", query = "SELECT i FROM IbAfiliaciones i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbAfiliaciones implements Serializable {
    @Column(name = "ESTATUS")
    private Character estatus;
    @Size(max = 60)
    @Column(name = "NOMBRE_BANCO")
    private String nombreBanco;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 50)
    @Column(name = "ALIAS")
    private String alias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DOCUMENTO")
    private String documento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Size(min = 1, max = 75)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_MAXIMO")
    private BigDecimal montoMaximo;
    @Size(max = 25)
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;    
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private IbUsuarios idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRANSACCION")
    private BigDecimal idTransaccion;
    @XmlTransient
    @OneToMany(mappedBy = "idAfiliacion", fetch = FetchType.EAGER)
    private Collection<IbLogs> ibLogsCollection;

    public IbAfiliaciones() {
    }

    public IbAfiliaciones(BigDecimal id) {
        this.id = id;
    }

    public IbAfiliaciones(BigDecimal id, String numeroCuenta, String nombreBeneficiario, Character tipoDoc, String documento, String email, BigDecimal montoMaximo, Date fechaRegistro, Date fechaModificacion) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.nombreBeneficiario = nombreBeneficiario;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.email = email;
        this.montoMaximo = montoMaximo;
        this.fechaRegistro = fechaRegistro;
        this.fechaModificacion = fechaModificacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Collection<IbLogs> getIbLogsCollection() {
        return ibLogsCollection;
    }

    public void setIbLogsCollection(Collection<IbLogs> ibLogsCollection) {
        this.ibLogsCollection = ibLogsCollection;
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
        if (!(object instanceof IbAfiliaciones)) {
            return false;
        }
        IbAfiliaciones other = (IbAfiliaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAfiliaciones[ id=" + id + " ]";
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }


    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }
    
}

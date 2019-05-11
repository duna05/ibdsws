/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "IB_LOGS")
@XmlRootElement
@NamedQueries({ 
    @NamedQuery(name = "IbLogs.findAll", query = "SELECT i FROM IbLogs i"),
    @NamedQuery(name = "IbLogs.findById", query = "SELECT i FROM IbLogs i WHERE i.id = :id"),
    @NamedQuery(name = "IbLogs.findByFechaHora", query = "SELECT i FROM IbLogs i WHERE i.fechaHora = :fechaHora"),
    @NamedQuery(name = "IbLogs.findByCuentaOrigen", query = "SELECT i FROM IbLogs i WHERE i.cuentaOrigen = :cuentaOrigen"),
    @NamedQuery(name = "IbLogs.findByCuentaDestino", query = "SELECT i FROM IbLogs i WHERE i.cuentaDestino = :cuentaDestino"),
    @NamedQuery(name = "IbLogs.findByMonto", query = "SELECT i FROM IbLogs i WHERE i.monto = :monto"),
    @NamedQuery(name = "IbLogs.findByReferencia", query = "SELECT i FROM IbLogs i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "IbLogs.findByDescripcion", query = "SELECT i FROM IbLogs i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "IbLogs.findByIp", query = "SELECT i FROM IbLogs i WHERE i.ip = :ip"),
    @NamedQuery(name = "IbLogs.findByUserAgent", query = "SELECT i FROM IbLogs i WHERE i.userAgent = :userAgent"),
    @NamedQuery(name = "IbLogs.findByErrorOperacion", query = "SELECT i FROM IbLogs i WHERE i.errorOperacion = :errorOperacion"),
    @NamedQuery(name = "IbLogs.findByNombreBeneficiario", query = "SELECT i FROM IbLogs i WHERE i.nombreBeneficiario = :nombreBeneficiario"),
    @NamedQuery(name = "IbLogs.findByTipoDoc", query = "SELECT i FROM IbLogs i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbLogs.findByDocumento", query = "SELECT i FROM IbLogs i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbLogs.findByFechaHoraTx", query = "SELECT i FROM IbLogs i WHERE i.fechaHoraTx = :fechaHoraTx"),
    @NamedQuery(name = "IbLogs.findByFechaHoraJob", query = "SELECT i FROM IbLogs i WHERE i.fechaHoraJob = :fechaHoraJob")})
public class IbLogs implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRANSACCION")
    private BigDecimal idTransaccion;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @Size(max = 20)
    @Column(name = "CUENTA_ORIGEN")
    private String cuentaOrigen;
    @Size(max = 20)
    @Column(name = "CUENTA_DESTINO")
    private String cuentaDestino;
    @Column(name = "MONTO")
    private BigDecimal monto;
    @Size(max = 45)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Size(max = 255)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "IP")
    private String ip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USER_AGENT")
    private String userAgent;
    @Column(name = "ERROR_OPERACION")
    private BigInteger errorOperacion;
    @Size(max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Size(max = 15) 
    @Column(name = "DOCUMENTO")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_TX")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraTx;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_JOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraJob;
    @XmlTransient
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;
    @XmlTransient
    @JoinColumn(name = "ID_AFILIACION", referencedColumnName = "ID")
    @ManyToOne
    private IbAfiliaciones idAfiliacion;

    public IbLogs() {
    }

    public IbLogs(BigDecimal id) {
        this.id = id;
    }

    public IbLogs(BigDecimal id, Date fechaHora, String ip, String userAgent, Date fechaHoraTx, Date fechaHoraJob) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.ip = ip;
        this.userAgent = userAgent;
        this.fechaHoraTx = fechaHoraTx;
        this.fechaHoraJob = fechaHoraJob;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public BigInteger getErrorOperacion() {
        return errorOperacion;
    }

    public void setErrorOperacion(BigInteger errorOperacion) {
        this.errorOperacion = errorOperacion;
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

    public Date getFechaHoraTx() {
        return fechaHoraTx;
    }

    public void setFechaHoraTx(Date fechaHoraTx) {
        this.fechaHoraTx = fechaHoraTx;
    }

    public Date getFechaHoraJob() {
        return fechaHoraJob;
    }

    public void setFechaHoraJob(Date fechaHoraJob) {
        this.fechaHoraJob = fechaHoraJob;
    }

    @XmlTransient
    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
    }

    @XmlTransient
    public IbAfiliaciones getIdAfiliacion() {
        return idAfiliacion;
    }

    public void setIdAfiliacion(IbAfiliaciones idAfiliacion) {
        this.idAfiliacion = idAfiliacion;
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
        if (!(object instanceof IbLogs)) {
            return false;
        }
        IbLogs other = (IbLogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbLogs[ id=" + id + " ]";
    }

    public BigDecimal getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(BigDecimal idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

}

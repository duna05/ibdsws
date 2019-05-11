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
@Table(name = "IB_LOGS_PJ")
@XmlRootElement
public class IbLogsPj implements Serializable {
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
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;
    @JoinColumn(name = "ID_AFILIACION_PJ", referencedColumnName = "ID")
    @ManyToOne
    private IbAfiliacionesPj idAfiliacionPj;

    public IbLogsPj() {
    }

    public IbLogsPj(BigDecimal id) {
        this.id = id;
    }

    public IbLogsPj(BigDecimal id, Date fechaHora, String ip, String userAgent, Date fechaHoraTx, Date fechaHoraJob) {
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
    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }
    
    @XmlTransient
    public IbServiciosPj getIdServicioPj() {
        return idServicioPj;
    }
    
    public void setIdServicioPj(IbServiciosPj idServicioPj) {
        this.idServicioPj = idServicioPj;
    }
    
    @XmlTransient
    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    @XmlTransient
    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
    }

    public IbAfiliacionesPj getIdAfiliacionPj() {
        return idAfiliacionPj;
    }

    public void setIdAfiliacionPj(IbAfiliacionesPj idAfiliacionPj) {
        this.idAfiliacionPj = idAfiliacionPj;
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
        if (!(object instanceof IbLogsPj)) {
            return false;
        }
        IbLogsPj other = (IbLogsPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbLogsPj[ id=" + id + " ]";
    }
}

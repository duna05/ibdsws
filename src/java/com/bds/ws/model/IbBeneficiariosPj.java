/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "IB_BENEFICIARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbBeneficiariosPj.findAll", query = "SELECT i FROM IbBeneficiariosPj i")
    , @NamedQuery(name = "IbBeneficiariosPj.findByIdBeneficiario", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.idBeneficiario = :idBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosPj.findByNroLineaArchivo", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.nroLineaArchivo = :nroLineaArchivo")
    , @NamedQuery(name = "IbBeneficiariosPj.findByNroIdentificacionCliente", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.nroIdentificacionCliente = :nroIdentificacionCliente")
    , @NamedQuery(name = "IbBeneficiariosPj.findByReferenciaBeneficiario", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.referenciaBeneficiario = :referenciaBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosPj.findByEstatusBeneficiario", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.estatusBeneficiario = :estatusBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosPj.findByCodigoMotivoRechazo", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.codigoMotivoRechazo = :codigoMotivoRechazo")
    , @NamedQuery(name = "IbBeneficiariosPj.findByNombreBeneficiario", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.nombreBeneficiario = :nombreBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosPj.findByEmailBeneficiario", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.emailBeneficiario = :emailBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosPj.findBySecuenciaCumplida", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.secuenciaCumplida = :secuenciaCumplida")
    , @NamedQuery(name = "IbBeneficiariosPj.findByFechaHoraCarga", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbBeneficiariosPj.findByFechaHoraModificacion", query = "SELECT i FROM IbBeneficiariosPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbBeneficiariosPj implements Serializable {
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_BENEFICIARIOS")
    @SequenceGenerator(sequenceName = "IB_S_BENEFICIARIOS_PJ", allocationSize = 1, name = "CUST_SEQ_BENEFICIARIOS")
    @Column(name = "ID_BENEFICIARIO")
    private Long idBeneficiario;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;
    @JoinColumn(name = "ESTATUS_AUTORIZACION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatusAutorizacion;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioModifica;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_LINEA_ARCHIVO")
    private int nroLineaArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "NRO_IDENTIFICACION_CLIENTE")
    private String nroIdentificacionCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "REFERENCIA_BENEFICIARIO")
    private String referenciaBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_BENEFICIARIO")
    private short estatusBeneficiario;
    @Size(max = 200)
    @Column(name = "CODIGO_MOTIVO_RECHAZO")
    private String codigoMotivoRechazo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 51)
    @Column(name = "EMAIL_BENEFICIARIO")
    private String emailBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")
    private short secuenciaCumplida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idBeneficiario")
    private List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList;
    
    
    @JoinColumn(name = "ID_CARGA_BENEFICIARIO", referencedColumnName = "ID_CARGA_BENEFICIARIO")
    @ManyToOne(optional = false)
   
    private IbCargaBeneficiariosPj idCargaBeneficiario;
    

    public IbBeneficiariosPj() {
    }

    public IbBeneficiariosPj(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public IbBeneficiariosPj(Long idBeneficiario, int nroLineaArchivo, String nroIdentificacionCliente, String referenciaBeneficiario, short estatusBeneficiario, String nombreBeneficiario, String emailBeneficiario, short secuenciaCumplida, Date fechaHoraCarga) {
        this.idBeneficiario = idBeneficiario;
        this.nroLineaArchivo = nroLineaArchivo;
        this.nroIdentificacionCliente = nroIdentificacionCliente;
        this.referenciaBeneficiario = referenciaBeneficiario;
        this.estatusBeneficiario = estatusBeneficiario;
        this.nombreBeneficiario = nombreBeneficiario;
        this.emailBeneficiario = emailBeneficiario;
        this.secuenciaCumplida = secuenciaCumplida;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public int getNroLineaArchivo() {
        return nroLineaArchivo;
    }

    public void setNroLineaArchivo(int nroLineaArchivo) {
        this.nroLineaArchivo = nroLineaArchivo;
    }

    public String getNroIdentificacionCliente() {
        return nroIdentificacionCliente;
    }

    public void setNroIdentificacionCliente(String nroIdentificacionCliente) {
        this.nroIdentificacionCliente = nroIdentificacionCliente;
    }

    public String getReferenciaBeneficiario() {
        return referenciaBeneficiario;
    }

    public void setReferenciaBeneficiario(String referenciaBeneficiario) {
        this.referenciaBeneficiario = referenciaBeneficiario;
    }

    public short getEstatusBeneficiario() {
        return estatusBeneficiario;
    }

    public void setEstatusBeneficiario(short estatusBeneficiario) {
        this.estatusBeneficiario = estatusBeneficiario;
    }

    public String getCodigoMotivoRechazo() {
        return codigoMotivoRechazo;
    }

    public void setCodigoMotivoRechazo(String codigoMotivoRechazo) {
        this.codigoMotivoRechazo = codigoMotivoRechazo;
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

    public short getSecuenciaCumplida() {
        return secuenciaCumplida;
    }

    public void setSecuenciaCumplida(short secuenciaCumplida) {
        this.secuenciaCumplida = secuenciaCumplida;
    }

    public Date getFechaHoraCarga() {
        return fechaHoraCarga;
    }

    public void setFechaHoraCarga(Date fechaHoraCarga) {
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Date getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(Date fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }
    
    @XmlTransient
    public List<IbCtasXBeneficiariosPj> getIbCtasXBeneficiariosPjList() {
        return ibCtasXBeneficiariosPjList;
    }

    public void setIbCtasXBeneficiariosPjList(List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList) {
        this.ibCtasXBeneficiariosPjList = ibCtasXBeneficiariosPjList;
    }
    
    public IbCargaBeneficiariosPj getIdCargaBeneficiario() {
        return idCargaBeneficiario;
    }
    @XmlTransient
    public void setIdCargaBeneficiario(IbCargaBeneficiariosPj idCargaBeneficiario) {
        this.idCargaBeneficiario = idCargaBeneficiario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBeneficiario != null ? idBeneficiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbBeneficiariosPj)) {
            return false;
        }
        IbBeneficiariosPj other = (IbBeneficiariosPj) object;
        if ((this.idBeneficiario == null && other.idBeneficiario != null) || (this.idBeneficiario != null && !this.idBeneficiario.equals(other.idBeneficiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbBeneficiariosPj[ idBeneficiario=" + idBeneficiario + " ]";
    }

    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public IbEstatusPagosPj getEstatusAutorizacion() {
        return estatusAutorizacion;
    }

    public void setEstatusAutorizacion(IbEstatusPagosPj estatusAutorizacion) {
        this.estatusAutorizacion = estatusAutorizacion;
    }

    public IbUsuariosPj getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuariosPj codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
    }
    
    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }
    
}

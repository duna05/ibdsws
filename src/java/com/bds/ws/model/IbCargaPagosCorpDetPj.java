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
@Table(name = "IB_CARGA_PAGOS_CORP_DET_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosCorpDetPj.findAll", query = "SELECT i FROM IbCargaPagosCorpDetPj i")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByIdPagoDetalle", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.idPagoDetalle = :idPagoDetalle")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByNroLineaArchivo", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.nroLineaArchivo = :nroLineaArchivo")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByNroIdentificacionCliente", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.nroIdentificacionCliente = :nroIdentificacionCliente")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByNroCuentaBeneficiario", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.nroCuentaBeneficiario = :nroCuentaBeneficiario")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByMontoPago", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.montoPago = :montoPago")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByCodigoMotivoRechazo", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.codigoMotivoRechazo = :codigoMotivoRechazo")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByCodigoUsuarioCarga", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.codigoUsuarioCarga = :codigoUsuarioCarga")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByCodigoUsuarioModifica", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.codigoUsuarioModifica = :codigoUsuarioModifica")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCargaPagosCorpDetPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosCorpDetPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaPagosCorpDetPj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_PAGOS_CORP_DET")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_PAGOS_CORP_DET_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_PAGOS_CORP_DET")
    @Column(name = "ID_PAGO_DETALLE")
    private Long idPagoDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private long codigoClienteAbanks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_LINEA_ARCHIVO")
    private BigInteger nroLineaArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "NRO_IDENTIFICACION_CLIENTE")
    private String nroIdentificacionCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA_BENEFICIARIO")
    private String nroCuentaBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_PAGO")
    private BigDecimal montoPago;
    @Size(max = 200)
    @Column(name = "CODIGO_MOTIVO_RECHAZO")
    private String codigoMotivoRechazo;
    @Size(min = 1, max = 160)
    @Column(name = "MOTIVO_DE_PAGO")
    private String motivoDePago;
    @Size(min = 1, max = 160)
    @Column(name = "OBSERVACION_PAGO")
    private String observacionPago;
    @Basic(optional = false)
    @NotNull
    @Size(max = 15)
    @Column(name = "REFERENCIA_PAGO")
    private String referenciaPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_USUARIO_CARGA")
    private long codigoUsuarioCarga;
    @Column(name = "CODIGO_USUARIO_MODIFICA")
    private Long codigoUsuarioModifica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPagoDetalle")
    private Collection<IbErroresPaCorpDetPj> ibErroresPaCorpDetPjCollection;
    
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne(optional = false)
    
    private IbCargaPagosCorpPj idPago;
    @JoinColumn(name = "ESTATUS_AUTORIZACION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatusAutorizacion;

    public IbEstatusPagosPj getEstatusAutorizacion() {
        return estatusAutorizacion;
    }

    public void setEstatusAutorizacion(IbEstatusPagosPj estatusAutorizacion) {
        this.estatusAutorizacion = estatusAutorizacion;
    }

    public IbCargaPagosCorpDetPj() {
    }

    public IbCargaPagosCorpDetPj(Long idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }
     
    public Long getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(Long idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public long getCodigoClienteAbanks() {
        return codigoClienteAbanks;
    }

    public void setCodigoClienteAbanks(long codigoClienteAbanks) {
        this.codigoClienteAbanks = codigoClienteAbanks;
    }

    public BigInteger getNroLineaArchivo() {
        return nroLineaArchivo;
    }

    public void setNroLineaArchivo(BigInteger nroLineaArchivo) {
        this.nroLineaArchivo = nroLineaArchivo;
    }

    public String getNroIdentificacionCliente() {
        return nroIdentificacionCliente;
    }

    public void setNroIdentificacionCliente(String nroIdentificacionCliente) {
        this.nroIdentificacionCliente = nroIdentificacionCliente;
    }

    public String getNroCuentaBeneficiario() {
        return nroCuentaBeneficiario;
    }

    public void setNroCuentaBeneficiario(String nroCuentaBeneficiario) {
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
    }

    public BigDecimal getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(BigDecimal montoPago) {
        this.montoPago = montoPago;
    }

    public String getCodigoMotivoRechazo() {
        return codigoMotivoRechazo;
    }

    public void setCodigoMotivoRechazo(String codigoMotivoRechazo) {
        this.codigoMotivoRechazo = codigoMotivoRechazo;
    }

    public long getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(long codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public Long getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(Long codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
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

    public String getMotivoDePago() {
        return motivoDePago;
    }

    public void setMotivoDePago(String motivoDePago) {
        this.motivoDePago = motivoDePago;
    }

    public String getObservacionPago() {
        return observacionPago;
    }

    public void setObservacionPago(String observacionPago) {
        this.observacionPago = observacionPago;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }
    
    @XmlTransient
    public Collection<IbErroresPaCorpDetPj> getIbErroresPaCorpDetPjCollection() {
        return ibErroresPaCorpDetPjCollection;
    }

    public void setIbErroresPaCorpDetPjCollection(Collection<IbErroresPaCorpDetPj> ibErroresPaCorpDetPjCollection) {
        this.ibErroresPaCorpDetPjCollection = ibErroresPaCorpDetPjCollection;
    }
    @XmlTransient
    public IbCargaPagosCorpPj getIdPago() {
        return idPago;
    }
    @XmlTransient
    public void setIdPago(IbCargaPagosCorpPj idPago) {
        this.idPago = idPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPagoDetalle != null ? idPagoDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbCargaPagosCorpDetPj)) {
            return false;
        }
        IbCargaPagosCorpDetPj other = (IbCargaPagosCorpDetPj) object;
        if ((this.idPagoDetalle == null && other.idPagoDetalle != null) || (this.idPagoDetalle != null && !this.idPagoDetalle.equals(other.idPagoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaPagosCorpDetPj[ idPagoDetalle=" + idPagoDetalle + " ]";
    }
    
}

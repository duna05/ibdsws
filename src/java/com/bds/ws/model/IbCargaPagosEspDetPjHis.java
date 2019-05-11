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
 * @author juan.vasquez
 */
@Entity
@Table(name = "IB_CARGA_PAGOS_ESP_DET_PJ_HIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findAll", query = "SELECT i FROM IbCargaPagosEspDetPjHis i"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByIdPagoDetalle", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.idPagoDetalle = :idPagoDetalle"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.codigoClienteAbanks = :codigoClienteAbanks"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByNroLineaArchivo", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.nroLineaArchivo = :nroLineaArchivo"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByNroIdentificacionCliente", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.nroIdentificacionCliente = :nroIdentificacionCliente"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByNroCuentaBeneficiario", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.nroCuentaBeneficiario = :nroCuentaBeneficiario"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByMontoPago", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.montoPago = :montoPago"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByCodigoMotivoRechazo", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.codigoMotivoRechazo = :codigoMotivoRechazo"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByNombreBeneficiario", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.nombreBeneficiario = :nombreBeneficiario"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.fechaHoraCarga = :fechaHoraCarga"),
    @NamedQuery(name = "IbCargaPagosEspDetPjHis.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosEspDetPjHis i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaPagosEspDetPjHis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGO_DETALLE")
    private Long idPagoDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private long codigoClienteAbanks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_LINEA_ARCHIVO")
    private int nroLineaArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "NRO_IDENTIFICACION_CLIENTE")
    private String nroIdentificacionCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NRO_CUENTA_BENEFICIARIO")
    private String nroCuentaBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 51)
    @Column(name = "EMAIL_BENEFICIARIO")
    private String emailBeneficiario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_PAGO")
    private BigDecimal montoPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "REFERENCIA_PAGO")
    private String referenciaPago;
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
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPagoDetalle")
    private Collection<IbErroresPaEspDetPjHis> ibErroresPaEspDetPjHisCollection;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne(optional = false)
    private IbCargaPagosEspPjHis idPago;

    public IbCargaPagosEspDetPjHis() {
    }

    public IbCargaPagosEspDetPjHis(Long idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public IbCargaPagosEspDetPjHis(Long idPagoDetalle, long codigoClienteAbanks, int nroLineaArchivo, String nroIdentificacionCliente, String nroCuentaBeneficiario, BigDecimal montoPago, String nombreBeneficiario, Date fechaHoraCarga) {
        this.idPagoDetalle = idPagoDetalle;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.nroLineaArchivo = nroLineaArchivo;
        this.nroIdentificacionCliente = nroIdentificacionCliente;
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
        this.montoPago = montoPago;
        this.nombreBeneficiario = nombreBeneficiario;
        this.fechaHoraCarga = fechaHoraCarga;
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

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
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
    public Collection<IbErroresPaEspDetPjHis> getIbErroresPaEspDetPjHisCollection() {
        return ibErroresPaEspDetPjHisCollection;
    }

    public void setIbErroresPaEspDetPjHisCollection(Collection<IbErroresPaEspDetPjHis> ibErroresPaEspDetPjHisCollection) {
        this.ibErroresPaEspDetPjHisCollection = ibErroresPaEspDetPjHisCollection;
    }

    public IbCargaPagosEspPjHis getIdPago() {
        return idPago;
    }

    public void setIdPago(IbCargaPagosEspPjHis idPago) {
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
        if (!(object instanceof IbCargaPagosEspDetPjHis)) {
            return false;
        }
        IbCargaPagosEspDetPjHis other = (IbCargaPagosEspDetPjHis) object;
        if ((this.idPagoDetalle == null && other.idPagoDetalle != null) || (this.idPagoDetalle != null && !this.idPagoDetalle.equals(other.idPagoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaPagosEspDetPjHis[ idPagoDetalle=" + idPagoDetalle + " ]";
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }

    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    public void setEmailBeneficiario(String emailBeneficiario) {
        this.emailBeneficiario = emailBeneficiario;
    }
}

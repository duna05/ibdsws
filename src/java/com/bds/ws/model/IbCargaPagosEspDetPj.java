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
 * @author juan.vasquez
 */
@Entity
@Table(name = "IB_CARGA_PAGOS_ESP_DET_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosEspDetPj.findAll", query = "SELECT i FROM IbCargaPagosEspDetPj i")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByIdPagoDetalle", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.idPagoDetalle = :idPagoDetalle")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByNroLineaArchivo", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.nroLineaArchivo = :nroLineaArchivo")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByNroIdentificacionCliente", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.nroIdentificacionCliente = :nroIdentificacionCliente")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByNroCuentaBeneficiario", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.nroCuentaBeneficiario = :nroCuentaBeneficiario")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByMontoPago", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.montoPago = :montoPago")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByCodigoMotivoRechazo", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.codigoMotivoRechazo = :codigoMotivoRechazo")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByNombreBeneficiario", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.nombreBeneficiario = :nombreBeneficiario")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    ,
    @NamedQuery(name = "IbCargaPagosEspDetPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosEspDetPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaPagosEspDetPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_PAGOS_ESP_DET_PJ")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_PAGOS_ESP_DET_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_PAGOS_ESP_DET_PJ")
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
    @Size(min = 1, max = 20)
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
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;
    @Size(max = 200)
    @Column(name = "CODIGO_MOTIVO_RECHAZO")
    private String codigoMotivoRechazo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;

    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne(optional = false)
    private IbCargaPagosEspPj idPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPagoDetalle")
    private Collection<IbErroresPaEspDetPj> ibErroresPaEspDetPjCollection;

    public IbCargaPagosEspDetPj() {
    }

    public IbCargaPagosEspDetPj(Long idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public IbCargaPagosEspDetPj(Long idPagoDetalle, long codigoClienteAbanks, int nroLineaArchivo, String nroIdentificacionCliente, String nroCuentaBeneficiario, BigDecimal montoPago, String nombreBeneficiario, Date fechaHoraCarga) {
        this.idPagoDetalle = idPagoDetalle;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.nroLineaArchivo = nroLineaArchivo;
        this.nroIdentificacionCliente = nroIdentificacionCliente;
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
        this.montoPago = montoPago;
        this.nombreBeneficiario = nombreBeneficiario;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public IbCargaPagosEspDetPj(Object[] registro) {
        this.idPagoDetalle = ((BigDecimal) registro[0]).longValue();
        this.codigoClienteAbanks = ((BigDecimal) registro[1]).longValue();
        this.idPago = new IbCargaPagosEspPj(((BigDecimal) registro[2]).longValue());
        this.nroLineaArchivo = ((BigDecimal) registro[3]).intValue();
        this.nroIdentificacionCliente = (String) registro[4];
        this.nroCuentaBeneficiario = (String) registro[5];
        this.montoPago = (BigDecimal) registro[6];
        this.estatus = new IbEstatusPagosPj((BigDecimal) registro[7]);
        this.codigoMotivoRechazo = (String) registro[8];
        this.nombreBeneficiario = (String) registro[9];
        this.fechaHoraCarga = (Date) registro[10];
        this.fechaHoraModificacion = (Date) registro[11];
        this.estatus.setNombre((String) registro[12]);
    }

    public Long getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(Long idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }

    @XmlTransient
    public IbCargaPagosEspPj getIdPago() {
        return idPago;
    }

    public void setIdPago(IbCargaPagosEspPj idPago) {
        this.idPago = idPago;
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
    public Collection<IbErroresPaEspDetPj> getIbErroresPaEspDetPjCollection() {
        return ibErroresPaEspDetPjCollection;
    }

    public void setIbErroresPaEspDetPjCollection(Collection<IbErroresPaEspDetPj> ibErroresPaEspDetPjCollection) {
        this.ibErroresPaEspDetPjCollection = ibErroresPaEspDetPjCollection;
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
        if (!(object instanceof IbCargaPagosEspDetPj)) {
            return false;
        }
        IbCargaPagosEspDetPj other = (IbCargaPagosEspDetPj) object;
        if ((this.idPagoDetalle == null && other.idPagoDetalle != null) || (this.idPagoDetalle != null && !this.idPagoDetalle.equals(other.idPagoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaPagosEspDetPj[ idPagoDetalle=" + idPagoDetalle + " ]";
    }

    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    public void setEmailBeneficiario(String emailBeneficiario) {
        this.emailBeneficiario = emailBeneficiario;
    }

    public String getReferenciaPago() {
        return referenciaPago;
    }

    public void setReferenciaPago(String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }
}

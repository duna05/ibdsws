/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "IB_FIDEICOMISO_DET_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbFideicomisoDetPj.findAll", query = "SELECT i FROM IbFideicomisoDetPj i")})
public class IbFideicomisoDetPj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)    
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_FIDEICOMISO_DET_PJ")
    @SequenceGenerator(sequenceName = "IB_S_FIDEICOMISO_DET_PJ", allocationSize = 1, name = "CUST_SEQ_FIDEICOMISO_DET_PJ")    
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_LINEA_ARCHIVO")
    private int nroLineaArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "NRO_IDENTIFI_BENEFICIARIO")
    private String nroIdentifiBeneficiario;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @Size(min = 1, max = 10)
    @Column(name = "NRO_CUENTA_BENEFICIARIO")
    private String nroCuentaBeneficiario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_PAGO")
    private BigDecimal montoPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_TRANSACCION")
    private short codigoTransaccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 600)
    @Column(name = "LINEA_TXT_XLS")
    private String lineaTxtXls;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;
    @JoinColumn(name = "ID_FIDE_MOVIMIENTO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbFideicomisoPj idFideMovimientoPj;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;

    public IbFideicomisoDetPj() {
    }

    public IbFideicomisoDetPj(Long id) {
        this.id = id;
    }

    public IbFideicomisoDetPj(Long id, int nroLineaArchivo, String nroIdentificacionCliente, String nroCuentaBeneficiario, BigDecimal montoPago, short codigoTransaccion, String lineaTxtXls, Date fechaHoraCarga) {
        this.id = id;
        this.nroLineaArchivo         = nroLineaArchivo;
        this.nroIdentifiBeneficiario = nroIdentificacionCliente;
        this.nroCuentaBeneficiario   = nroCuentaBeneficiario;
        this.montoPago               = montoPago;
        this.codigoTransaccion       = codigoTransaccion;
        this.lineaTxtXls = lineaTxtXls;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNroLineaArchivo() {
        return nroLineaArchivo;
    }

    public void setNroLineaArchivo(int nroLineaArchivo) {
        this.nroLineaArchivo = nroLineaArchivo;
    }

    public String getNroIdentifiBeneficiario() {
        return nroIdentifiBeneficiario;
    }

    public void setNroIdentifiBeneficiario(String nroIdentifiBeneficiario) {
        this.nroIdentifiBeneficiario = nroIdentifiBeneficiario;
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

    public short getCodigoTransaccion() {
        return codigoTransaccion;
    }

    public void setCodigoTransaccion(short codigoTransaccion) {
        this.codigoTransaccion = codigoTransaccion;
    }

    public String getLineaTxtXls() {
        return lineaTxtXls;
    }

    public void setLineaTxtXls(String lineaTxtXls) {
        this.lineaTxtXls = lineaTxtXls;
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

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }

    public IbFideicomisoPj getIdFideMovimientoPj() {
        return idFideMovimientoPj;
    }
    @XmlTransient
    public void setIdFideMovimientoPj(IbFideicomisoPj idFideMovimientoPj) {
        this.idFideMovimientoPj = idFideMovimientoPj;
    }

    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
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
        if (!(object instanceof IbFideicomisoDetPj)) {
            return false;
        }
        IbFideicomisoDetPj other = (IbFideicomisoDetPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbFideicomisoDetPj[ id=" + id + " ]";
    }
    
}

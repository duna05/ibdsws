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
@Table(name = "IB_CARGA_NOMINA_DETALLE_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaNominaDetallePj.findAll", query = "SELECT i FROM IbCargaNominaDetallePj i"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByIdPagoDetalle", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.idPagoDetalle = :idPagoDetalle"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByNroLineaArchivo", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.nroLineaArchivo = :nroLineaArchivo"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByNroIdentificacionCliente", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.nroIdentificacionCliente = :nroIdentificacionCliente"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByNroCuentaBeneficiario", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.nroCuentaBeneficiario = :nroCuentaBeneficiario"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByMontoPago", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.montoPago = :montoPago"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByCodigoMotivoRechazo", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.codigoMotivoRechazo = :codigoMotivoRechazo"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByNombreBeneficiario", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.nombreBeneficiario = :nombreBeneficiario"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.fechaHoraCarga = :fechaHoraCarga"),
    @NamedQuery(name = "IbCargaNominaDetallePj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaNominaDetallePj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaNominaDetallePj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGO_DETALLE")
    private BigDecimal idPagoDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private BigDecimal codigoClienteAbanks;
    @Basic(optional = false)
    @Column(name = "NRO_LINEA_ARCHIVO")
    private BigDecimal nroLineaArchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_IDENTIFICACION_CLIENTE")
    private String nroIdentificacionCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NRO_CUENTA_BENEFICIARIO")
    private String nroCuentaBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_PAGO")
    private BigDecimal montoPago;
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
    private Collection<IbErroresNominaDetallePj> ibErroresNominaDetallePjCollection;
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;
    
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne(optional = false)
    private IbCargaNominaPj idPago;

    public IbCargaNominaDetallePj() {
    }

    public IbCargaNominaDetallePj(BigDecimal idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public IbCargaNominaDetallePj(BigDecimal idPagoDetalle, BigDecimal codigoClienteAbanks, BigDecimal nroLineaArchivo, String nroIdentificacionCliente, String nroCuentaBeneficiario, BigDecimal montoPago, String nombreBeneficiario, Date fechaHoraCarga) {
        this.idPagoDetalle = idPagoDetalle;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.nroLineaArchivo = nroLineaArchivo;
        this.nroIdentificacionCliente = nroIdentificacionCliente;
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
        this.montoPago = montoPago;
        this.nombreBeneficiario = nombreBeneficiario;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public IbCargaNominaDetallePj(Object[] registro) {
        //ORDEN USADO EN IbCargaNominaDetallePjFacade
        this.idPagoDetalle             = (BigDecimal)registro[0];
        this.codigoClienteAbanks       = (BigDecimal)registro[1];
        this.idPago                    = new IbCargaNominaPj((BigDecimal)registro[2]);
        this.nroLineaArchivo           = (BigDecimal)registro[3];
        this.nroIdentificacionCliente  = (String)registro[4];
        this.nroCuentaBeneficiario     = (String)registro[5];
        this.montoPago                 = (BigDecimal)registro[6];
        this.estatus                   = new IbEstatusPagosPj((BigDecimal)registro[7]);
        this.codigoMotivoRechazo       = (String)registro[8];
        this.nombreBeneficiario        = (String)registro[9];
        this.fechaHoraCarga            = (Date)registro[10];
        this.fechaHoraModificacion     = (Date)registro[11];        
        this.estatus.setNombre(          (String)registro[12]);
    }


    public BigDecimal getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(BigDecimal idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public BigDecimal getCodigoClienteAbanks() {
        return codigoClienteAbanks;
    }

    public void setCodigoClienteAbanks(BigDecimal codigoClienteAbanks) {
        this.codigoClienteAbanks = codigoClienteAbanks;
    }

    public BigDecimal getNroLineaArchivo() {
        return nroLineaArchivo;
    }

    public void setNroLineaArchivo(BigDecimal nroLineaArchivo) {
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
    public Collection<IbErroresNominaDetallePj> getIbErroresNominaDetallePjCollection() {
        return ibErroresNominaDetallePjCollection;
    }

    public void setIbErroresNominaDetallePjCollection(Collection<IbErroresNominaDetallePj> ibErroresNominaDetallePjCollection) {
        this.ibErroresNominaDetallePjCollection = ibErroresNominaDetallePjCollection;
    }

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }
    @XmlTransient
    public IbCargaNominaPj getIdPago() {
        return idPago;
    }

   @XmlTransient
   public void setIdPago(IbCargaNominaPj idPago) {
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
        if (!(object instanceof IbCargaNominaDetallePj)) {
            return false;
        }
        IbCargaNominaDetallePj other = (IbCargaNominaDetallePj) object;
        if ((this.idPagoDetalle == null && other.idPagoDetalle != null) || (this.idPagoDetalle != null && !this.idPagoDetalle.equals(other.idPagoDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaNominaDetallePj[ idPagoDetalle=" + idPagoDetalle + " ]";
    }
    
}

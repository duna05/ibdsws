/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "IB_CARGA_NOMINA_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaNominaPj.findAll", query = "SELECT i FROM IbCargaNominaPj i"),
    @NamedQuery(name = "IbCargaNominaPj.findByIdPago", query = "SELECT i FROM IbCargaNominaPj i WHERE i.idPago = :idPago"),
    @NamedQuery(name = "IbCargaNominaPj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaNominaPj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks"),
    @NamedQuery(name = "IbCargaNominaPj.findByNroConvenio", query = "SELECT i FROM IbCargaNominaPj i WHERE i.nroConvenio = :nroConvenio"),
    @NamedQuery(name = "IbCargaNominaPj.findByNombreArchivo", query = "SELECT i FROM IbCargaNominaPj i WHERE i.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "IbCargaNominaPj.findByMotivoDePago", query = "SELECT i FROM IbCargaNominaPj i WHERE i.motivoDePago = :motivoDePago"),
    @NamedQuery(name = "IbCargaNominaPj.findByMontoTotalAplicar", query = "SELECT i FROM IbCargaNominaPj i WHERE i.montoTotalAplicar = :montoTotalAplicar"),
    @NamedQuery(name = "IbCargaNominaPj.findByCantidadCreditosAplicar", query = "SELECT i FROM IbCargaNominaPj i WHERE i.cantidadCreditosAplicar = :cantidadCreditosAplicar"),
    @NamedQuery(name = "IbCargaNominaPj.findByNroCuentaDebito", query = "SELECT i FROM IbCargaNominaPj i WHERE i.nroCuentaDebito = :nroCuentaDebito"),
    @NamedQuery(name = "IbCargaNominaPj.findByMontoTotalAplicado", query = "SELECT i FROM IbCargaNominaPj i WHERE i.montoTotalAplicado = :montoTotalAplicado"),
    @NamedQuery(name = "IbCargaNominaPj.findByCantidadCreditosAplicados", query = "SELECT i FROM IbCargaNominaPj i WHERE i.cantidadCreditosAplicados = :cantidadCreditosAplicados"),
    @NamedQuery(name = "IbCargaNominaPj.findByCantidadCreditosRechazados", query = "SELECT i FROM IbCargaNominaPj i WHERE i.cantidadCreditosRechazados = :cantidadCreditosRechazados"),
    @NamedQuery(name = "IbCargaNominaPj.findByCantidadAprobadoresServicio", query = "SELECT i FROM IbCargaNominaPj i WHERE i.cantidadAprobadoresServicio = :cantidadAprobadoresServicio"),
    @NamedQuery(name = "IbCargaNominaPj.findBySecuenciaCumplida", query = "SELECT i FROM IbCargaNominaPj i WHERE i.secuenciaCumplida = :secuenciaCumplida"),
    @NamedQuery(name = "IbCargaNominaPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaNominaPj i WHERE i.fechaHoraCarga = :fechaHoraCarga"),
    @NamedQuery(name = "IbCargaNominaPj.findByFechaHoraPago", query = "SELECT i FROM IbCargaNominaPj i WHERE i.fechaHoraPago = :fechaHoraPago"),
    @NamedQuery(name = "IbCargaNominaPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaNominaPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})

public class IbCargaNominaPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_NOMINA")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_NOMINA_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_NOMINA")
    @Column(name = "ID_PAGO")
    private BigDecimal idPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private BigDecimal codigoClienteAbanks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NRO_CONVENIO")
    private BigDecimal nroConvenio;
    @Basic(optional = false)
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MOTIVO_DE_PAGO")
    private String motivoDePago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_TOTAL_APLICAR")
    private BigDecimal montoTotalAplicar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_CREDITOS_APLICAR")
    private BigDecimal cantidadCreditosAplicar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA_DEBITO")
    private String nroCuentaDebito;
    @Column(name = "MONTO_TOTAL_APLICADO")
    private BigDecimal montoTotalAplicado;
    @Column(name = "CANTIDAD_CREDITOS_APLICADOS")
    private BigDecimal cantidadCreditosAplicados;
    @Column(name = "CANTIDAD_CREDITOS_RECHAZADOS")
    private BigDecimal cantidadCreditosRechazados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_APROBADORES_SERVICIO")
    private BigDecimal cantidadAprobadoresServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")
    private BigDecimal secuenciaCumplida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_PAGO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraPago;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;
    
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago" , fetch = FetchType.EAGER)
    private Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago")
    private Collection<IbErroresNominaPj> ibErroresNominaPjCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_CARGA_NOMINA")
    private BigDecimal tipoCargaNomina;

    public IbCargaNominaPj() {
    }

    public IbCargaNominaPj(BigDecimal idPago) {
        this.idPago = idPago;
    }

    public IbCargaNominaPj(BigDecimal idPago, BigDecimal codigoClienteAbanks, BigDecimal nroConvenio, 
            String nombreArchivo, String motivoDePago, 
            BigDecimal montoTotalAplicar, 
            BigDecimal cantidadCreditosAplicar, 
            String nroCuentaDebito, 
            BigDecimal cantidadAprobadoresServicio, BigDecimal secuenciaCumplida, Date fechaHoraCarga, Date fechaHoraPago) {
        this.idPago = idPago;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.nroConvenio = nroConvenio;
        this.nombreArchivo = nombreArchivo;
        this.motivoDePago = motivoDePago;
        this.montoTotalAplicar = montoTotalAplicar;
        this.cantidadCreditosAplicar = cantidadCreditosAplicar;
        this.nroCuentaDebito = nroCuentaDebito;
        this.cantidadAprobadoresServicio = cantidadAprobadoresServicio;
        this.secuenciaCumplida = secuenciaCumplida;
        this.fechaHoraCarga = fechaHoraCarga;
        this.fechaHoraPago = fechaHoraPago;
    }

    public IbCargaNominaPj(Object[] registro ) {
        //orden definido en IbCargaNominaFacade
        this.idPago                     = (BigDecimal) registro[0];
        this.codigoClienteAbanks        = (BigDecimal) registro[1];
        this.codigoUsuarioCarga         = new IbUsuariosPj((BigDecimal) registro[2]);
        this.nroConvenio                = (BigDecimal) registro[3];
        this.nombreArchivo              = (String)registro[4];
        this.motivoDePago               = (String)registro[5];
        this.montoTotalAplicar          = (BigDecimal) registro[6];
        this.cantidadCreditosAplicar    = (BigDecimal) registro[7];
        this.nroCuentaDebito            = (String)registro[8];
        this.estatus                    = new IbEstatusPagosPj((BigDecimal) registro[9]);
        this.montoTotalAplicado         = (BigDecimal) registro[10];
        this.cantidadCreditosAplicados  = ((registro[11]==null)?BigDecimal.ZERO:(BigDecimal) registro[11]);
        this.cantidadCreditosRechazados = ((registro[12]==null)?BigDecimal.ZERO:(BigDecimal) registro[12]);        
        this.cantidadAprobadoresServicio = (BigDecimal) registro[13];
        this.secuenciaCumplida          = (BigDecimal) registro[14];
        this.fechaHoraCarga             = (Timestamp) registro[15];
        this.fechaHoraPago              = (Timestamp) registro[16];
        this.fechaHoraModificacion      = (Timestamp) registro[17];
        this.tipoCargaNomina            = (BigDecimal) registro[18];
        this.estatus.setNombre(           (String) registro[19]);
    }

    public BigDecimal getIdPago() {
        return idPago;
    }

    public void setIdPago(BigDecimal idPago) {
        this.idPago = idPago;
    }

    public BigDecimal getCodigoClienteAbanks() {
        return codigoClienteAbanks;
    }

    public void setCodigoClienteAbanks(BigDecimal codigoClienteAbanks) {
        this.codigoClienteAbanks = codigoClienteAbanks;
    }

    public BigDecimal getNroConvenio() {
        return nroConvenio;
    }

    public void setNroConvenio(BigDecimal nroConvenio) {
        this.nroConvenio = nroConvenio;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMotivoDePago() {
        return motivoDePago;
    }

    public void setMotivoDePago(String motivoDePago) {
        this.motivoDePago = motivoDePago;
    }

    public BigDecimal getMontoTotalAplicar() {
        return montoTotalAplicar;
    }

    public void setMontoTotalAplicar(BigDecimal montoTotalAplicar) {
        this.montoTotalAplicar = montoTotalAplicar;
    }

    public BigDecimal getCantidadCreditosAplicar() {
        return cantidadCreditosAplicar;
    }

    public void setCantidadCreditosAplicar(BigDecimal cantidadCreditosAplicar) {
        this.cantidadCreditosAplicar = cantidadCreditosAplicar;
    }

    public String getNroCuentaDebito() {
        return nroCuentaDebito;
    }

    public void setNroCuentaDebito(String nroCuentaDebito) {
        this.nroCuentaDebito = nroCuentaDebito;
    }

    public BigDecimal getMontoTotalAplicado() {
        return montoTotalAplicado;
    }

    public void setMontoTotalAplicado(BigDecimal montoTotalAplicado) {
        this.montoTotalAplicado = montoTotalAplicado;
    }

    public BigDecimal getCantidadCreditosAplicados() {
        return cantidadCreditosAplicados;
    }

    public void setCantidadCreditosAplicados(BigDecimal cantidadCreditosAplicados) {
        this.cantidadCreditosAplicados = cantidadCreditosAplicados;
    }

    public BigDecimal getCantidadCreditosRechazados() {
        return cantidadCreditosRechazados;
    }

    public void setCantidadCreditosRechazados(BigDecimal cantidadCreditosRechazados) {
        this.cantidadCreditosRechazados = cantidadCreditosRechazados;
    }

    public BigDecimal getCantidadAprobadoresServicio() {
        return cantidadAprobadoresServicio;
    }

    public void setCantidadAprobadoresServicio(BigDecimal cantidadAprobadoresServicio) {
        this.cantidadAprobadoresServicio = cantidadAprobadoresServicio;
    }

    public BigDecimal getSecuenciaCumplida() {
        return secuenciaCumplida;
    }

    public void setSecuenciaCumplida(BigDecimal secuenciaCumplida) {
        this.secuenciaCumplida = secuenciaCumplida;
    }

    public Date getFechaHoraCarga() {
        return fechaHoraCarga;
    }

    public void setFechaHoraCarga(Date fechaHoraCarga) {
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Date getFechaHoraPago() {
        return fechaHoraPago;
    }

    public void setFechaHoraPago(Date fechaHoraPago) {
        this.fechaHoraPago = fechaHoraPago;
    }

    public Date getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(Date fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }

    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }


    public Collection<IbCargaNominaDetallePj> getIbCargaNominaDetallePjCollection() {
        return ibCargaNominaDetallePjCollection;
    }
 
    public void setIbCargaNominaDetallePjCollection(Collection<IbCargaNominaDetallePj> ibCargaNominaDetallePjCollection) {
        this.ibCargaNominaDetallePjCollection = ibCargaNominaDetallePjCollection;
    }

    @XmlTransient
    public Collection<IbErroresNominaPj> getIbErroresNominaPjCollection() {
        return ibErroresNominaPjCollection;
    }

    public void setIbErroresNominaPjCollection(Collection<IbErroresNominaPj> ibErroresNominaPjCollection) {
        this.ibErroresNominaPjCollection = ibErroresNominaPjCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbCargaNominaPj)) {
            return false;
        }
        IbCargaNominaPj other = (IbCargaNominaPj) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaNominaPj[ idPago=" + idPago + " ]";
    }
    public BigDecimal getTipoCargaNomina() {
        return tipoCargaNomina;
    }

    public void setTipoCargaNomina(BigDecimal tipoCargaNomina) {
        this.tipoCargaNomina = tipoCargaNomina;
    }
}

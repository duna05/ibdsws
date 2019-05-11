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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CARGA_PAGOS_ESP_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosEspPj.findAll", query = "SELECT i FROM IbCargaPagosEspPj i"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByIdPago", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.idPago = :idPago"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByNombreArchivo", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByMotivoDePago", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.motivoDePago = :motivoDePago"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByMontoTotalAplicar", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.montoTotalAplicar = :montoTotalAplicar"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByCantidadCreditosAplicar", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.cantidadCreditosAplicar = :cantidadCreditosAplicar"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByNroCuentaDebito", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.nroCuentaDebito = :nroCuentaDebito"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByMontoTotalAplicado", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.montoTotalAplicado = :montoTotalAplicado"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByCantidadCreditosAplicados", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.cantidadCreditosAplicados = :cantidadCreditosAplicados"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByCantidadCreditosRechazados", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.cantidadCreditosRechazados = :cantidadCreditosRechazados"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByCantidadAprobadoresServicio", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.cantidadAprobadoresServicio = :cantidadAprobadoresServicio"),
    @NamedQuery(name = "IbCargaPagosEspPj.findBySecuenciaCumplida", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.secuenciaCumplida = :secuenciaCumplida"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.fechaHoraCarga = :fechaHoraCarga"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByFechaHoraPago", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.fechaHoraPago = :fechaHoraPago"),
    @NamedQuery(name = "IbCargaPagosEspPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosEspPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaPagosEspPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_PAGOS_ESPECIALES")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_PAGOS_ESP_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_PAGOS_ESPECIALES")
    @Column(name = "ID_PAGO")
    private Long idPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private long codigoClienteAbanks;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    @Size(max = 200)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MOTIVO_DE_PAGO")
    private String motivoDePago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
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
    @Size(min = 1, max = 10)
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
    private int cantidadAprobadoresServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")
    private BigDecimal secuenciaCumplida;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false)
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
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;

    @XmlTransient
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "idPago", fetch = FetchType.EAGER)
    private Collection<IbCargaPagosEspDetPj> ibCargaPagosEspDetPjCollection;

    public IbCargaPagosEspPj() {
    }

    public IbCargaPagosEspPj(Long idPago) {
        this.idPago = idPago;
    }

    public IbCargaPagosEspPj(Long idPago, long codigoClienteAbanks, String motivoDePago, BigDecimal montoTotalAplicar, BigDecimal cantidadCreditosAplicar, String nroCuentaDebito, short cantidadAprobadoresServicio, BigDecimal secuenciaCumplida, Date fechaHoraCarga, Date fechaHoraPago) {
        this.idPago = idPago;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.motivoDePago = motivoDePago;
        this.montoTotalAplicar = montoTotalAplicar;
        this.cantidadCreditosAplicar = cantidadCreditosAplicar;
        this.nroCuentaDebito = nroCuentaDebito;
        this.cantidadAprobadoresServicio = cantidadAprobadoresServicio;
        this.secuenciaCumplida = secuenciaCumplida;
        this.fechaHoraCarga = fechaHoraCarga;
        this.fechaHoraPago = fechaHoraPago;
    }

    public IbCargaPagosEspPj(Object[] registro) {
        this.idPago = ((BigDecimal) registro[0]).longValue();
        this.codigoClienteAbanks = ((BigDecimal) registro[1]).longValue();
        this.codigoUsuarioCarga = new IbUsuariosPj((BigDecimal) registro[2]);
        this.nombreArchivo = (String) registro[3];
        this.motivoDePago = (String) registro[4];
        this.montoTotalAplicar = (BigDecimal) registro[5];
        this.cantidadCreditosAplicar = (BigDecimal) registro[6];
        this.nroCuentaDebito = (String) registro[7];
        this.estatus = new IbEstatusPagosPj((BigDecimal) registro[8]);
        this.montoTotalAplicado = (BigDecimal) registro[9];
        this.cantidadCreditosAplicados = (BigDecimal) ((registro[10] == null) ? 0 : (BigDecimal) registro[10]);
        this.cantidadCreditosRechazados = (BigDecimal) ((registro[11] == null) ? 0 : (BigDecimal) registro[11]);
        this.cantidadAprobadoresServicio = ((BigDecimal) registro[12]).shortValue();
        this.secuenciaCumplida = (BigDecimal) registro[13];
        this.fechaHoraCarga = (Date) registro[14];
        this.fechaHoraPago = (Date) registro[15];
        this.fechaHoraModificacion = (Date) registro[16];
        this.estatus.setNombre((String) registro[17]);
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public long getCodigoClienteAbanks() {
        return codigoClienteAbanks;
    }

    public void setCodigoClienteAbanks(long codigoClienteAbanks) {
        this.codigoClienteAbanks = codigoClienteAbanks;
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

    public int getCantidadAprobadoresServicio() {
        return cantidadAprobadoresServicio;
    }

    public void setCantidadAprobadoresServicio(int cantidadAprobadoresServicio) {
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

    public Collection<IbCargaPagosEspDetPj> getIbCargaPagosEspDetPjCollection() {
        return ibCargaPagosEspDetPjCollection;
    }

    public void setIbCargaPagosEspDetPjCollection(Collection<IbCargaPagosEspDetPj> ibCargaPagosEspDetPjCollection) {
        this.ibCargaPagosEspDetPjCollection = ibCargaPagosEspDetPjCollection;
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
        if (!(object instanceof IbCargaPagosEspPj)) {
            return false;
        }
        IbCargaPagosEspPj other = (IbCargaPagosEspPj) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaPagosEspPj[ idPago=" + idPago + " ]";
    }

}

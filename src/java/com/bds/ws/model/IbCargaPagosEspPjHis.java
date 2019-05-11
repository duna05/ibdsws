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
@Table(name = "IB_CARGA_PAGOS_ESP_PJ_HIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosEspPjHis.findAll", query = "SELECT i FROM IbCargaPagosEspPjHis i"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByIdPago", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.idPago = :idPago"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.codigoClienteAbanks = :codigoClienteAbanks"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByNombreArchivo", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByMotivoDePago", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.motivoDePago = :motivoDePago"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByMontoTotalAplicar", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.montoTotalAplicar = :montoTotalAplicar"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByCantidadCreditosAplicar", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.cantidadCreditosAplicar = :cantidadCreditosAplicar"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByNroCuentaDebito", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.nroCuentaDebito = :nroCuentaDebito"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByMontoTotalAplicado", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.montoTotalAplicado = :montoTotalAplicado"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByCantidadCreditosAplicados", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.cantidadCreditosAplicados = :cantidadCreditosAplicados"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByCantidadCreditosRechazados", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.cantidadCreditosRechazados = :cantidadCreditosRechazados"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByCantidadAprobadoresServicio", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.cantidadAprobadoresServicio = :cantidadAprobadoresServicio"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findBySecuenciaCumplida", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.secuenciaCumplida = :secuenciaCumplida"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.fechaHoraCarga = :fechaHoraCarga"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByFechaHoraPago", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.fechaHoraPago = :fechaHoraPago"),
    @NamedQuery(name = "IbCargaPagosEspPjHis.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosEspPjHis i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaPagosEspPjHis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PAGO")
    private Long idPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private long codigoClienteAbanks;
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
    private int cantidadCreditosAplicar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA_DEBITO")
    private String nroCuentaDebito;
    @Column(name = "MONTO_TOTAL_APLICADO")
    private BigDecimal montoTotalAplicado;
    @Column(name = "CANTIDAD_CREDITOS_APLICADOS")
    private Integer cantidadCreditosAplicados;
    @Column(name = "CANTIDAD_CREDITOS_RECHAZADOS")
    private Integer cantidadCreditosRechazados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_APROBADORES_SERVICIO")
    private short cantidadAprobadoresServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")
    private short secuenciaCumplida;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago")
    private Collection<IbErroresPaEspPjHis> ibErroresPaEspPjHisCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago")
    private Collection<IbCargaPagosEspDetPjHis> ibCargaPagosEspDetPjHisCollection;

    public IbCargaPagosEspPjHis() {
    }

    public IbCargaPagosEspPjHis(Long idPago) {
        this.idPago = idPago;
    }

    public IbCargaPagosEspPjHis(Long idPago, long codigoClienteAbanks, String motivoDePago, BigDecimal montoTotalAplicar, int cantidadCreditosAplicar, String nroCuentaDebito, short cantidadAprobadoresServicio, short secuenciaCumplida, Date fechaHoraCarga, Date fechaHoraPago) {
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

    public int getCantidadCreditosAplicar() {
        return cantidadCreditosAplicar;
    }

    public void setCantidadCreditosAplicar(int cantidadCreditosAplicar) {
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

    public Integer getCantidadCreditosAplicados() {
        return cantidadCreditosAplicados;
    }

    public void setCantidadCreditosAplicados(Integer cantidadCreditosAplicados) {
        this.cantidadCreditosAplicados = cantidadCreditosAplicados;
    }

    public Integer getCantidadCreditosRechazados() {
        return cantidadCreditosRechazados;
    }

    public void setCantidadCreditosRechazados(Integer cantidadCreditosRechazados) {
        this.cantidadCreditosRechazados = cantidadCreditosRechazados;
    }

    public short getCantidadAprobadoresServicio() {
        return cantidadAprobadoresServicio;
    }

    public void setCantidadAprobadoresServicio(short cantidadAprobadoresServicio) {
        this.cantidadAprobadoresServicio = cantidadAprobadoresServicio;
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

    @XmlTransient
    public Collection<IbErroresPaEspPjHis> getIbErroresPaEspPjHisCollection() {
        return ibErroresPaEspPjHisCollection;
    }

    public void setIbErroresPaEspPjHisCollection(Collection<IbErroresPaEspPjHis> ibErroresPaEspPjHisCollection) {
        this.ibErroresPaEspPjHisCollection = ibErroresPaEspPjHisCollection;
    }

    @XmlTransient
    public Collection<IbCargaPagosEspDetPjHis> getIbCargaPagosEspDetPjHisCollection() {
        return ibCargaPagosEspDetPjHisCollection;
    }

    public void setIbCargaPagosEspDetPjHisCollection(Collection<IbCargaPagosEspDetPjHis> ibCargaPagosEspDetPjHisCollection) {
        this.ibCargaPagosEspDetPjHisCollection = ibCargaPagosEspDetPjHisCollection;
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
        if (!(object instanceof IbCargaPagosEspPjHis)) {
            return false;
        }
        IbCargaPagosEspPjHis other = (IbCargaPagosEspPjHis) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaPagosEspPjHis[ idPago=" + idPago + " ]";
    }
    
}

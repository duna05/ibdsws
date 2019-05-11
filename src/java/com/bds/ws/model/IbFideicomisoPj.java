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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_FIDEICOMISO_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbFideicomisoPj.findAll", query = "SELECT i FROM IbFideicomisoPj i")})
public class IbFideicomisoPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_FIDEICOMISO")
    @SequenceGenerator(sequenceName = "IB_S_FIDEICOMISO_PJ", allocationSize = 1, name = "CUST_SEQ_FIDEICOMISO")
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")
    private long codigoClienteAbanks;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "CODIGO_FIDEICOMISO")
    private String codigoFideicomiso;
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
    @Column(name = "CANTIDAD_APROBADORES_SERVICIO")
    private short cantidadAprobadoresServicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")
    private short secuenciaCumplida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "INDICADOR_TIPO_OPERACION")
    private String indicadorTipoOperacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFideMovimientoPj")
    private Collection<IbFideicomisoDetPj> ibFideicomisoDetPjCollection;
    
    @JoinColumn(name = "ESTATUS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatus;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;

    public IbFideicomisoPj() {
    }

    public IbFideicomisoPj(Long id) {
        this.id = id;
    }

    public IbFideicomisoPj(Long id, long codigoClienteAbanks, String nombreArchivo, String codigoFideicomiso, BigDecimal montoTotalAplicar, int cantidadCreditosAplicar, short cantidadAprobadoresServicio, short secuenciaCumplida, String indicadorTipoOperacion, Date fechaHoraCarga) {
        this.id = id;
        this.codigoClienteAbanks = codigoClienteAbanks;
        this.nombreArchivo = nombreArchivo;
        this.codigoFideicomiso = codigoFideicomiso;
        this.montoTotalAplicar = montoTotalAplicar;
        this.cantidadCreditosAplicar = cantidadCreditosAplicar;
        this.cantidadAprobadoresServicio = cantidadAprobadoresServicio;
        this.secuenciaCumplida = secuenciaCumplida;
        this.indicadorTipoOperacion = indicadorTipoOperacion;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCodigoFideicomiso() {
        return codigoFideicomiso;
    }

    public void setCodigoFideicomiso(String codigoFideicomiso) {
        this.codigoFideicomiso = codigoFideicomiso;
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

    public String getIndicadorTipoOperacion() {
        return indicadorTipoOperacion;
    }

    public void setIndicadorTipoOperacion(String indicadorTipoOperacion) {
        this.indicadorTipoOperacion = indicadorTipoOperacion;
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

 
    public Collection<IbFideicomisoDetPj> getIbFideicomisoDetPjCollection() {
        return ibFideicomisoDetPjCollection;
    }

    public void setIbFideicomisoDetPjCollection(Collection<IbFideicomisoDetPj> ibFideicomisoDetPjCollection) {
        this.ibFideicomisoDetPjCollection = ibFideicomisoDetPjCollection;
    }

    public IbEstatusPagosPj getEstatus() {
        return estatus;
    }

    public void setEstatus(IbEstatusPagosPj estatus) {
        this.estatus = estatus;
    }

    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
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
        if (!(object instanceof IbFideicomisoPj)) {
            return false;
        }
        IbFideicomisoPj other = (IbFideicomisoPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbFideicomisoPj[ id=" + id + " ]";
    }

}

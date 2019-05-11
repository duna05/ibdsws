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
 * @author cesar.mujica
 */
@Entity
@Table(name = "IB_AGENDA_TRANSACCIONES_PN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAgendaTransaccionesPn.findAll", query = "SELECT i FROM IbAgendaTransaccionesPn i"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findById", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.id = :id"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByTipo", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByCtaOrigen", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.cuentaOrigen = :ctaOrigen"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByCtaDestino", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.cuentaDestino = :ctaDestino"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByMonto", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.monto = :monto"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByFrecuencia", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.frecuencia = :frecuencia"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByFechaFrecuencia", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.diaFrecuencia = :fechaFrecuencia"),
    @NamedQuery(name = "IbAgendaTransaccionesPn.findByFechaLimite", query = "SELECT i FROM IbAgendaTransaccionesPn i WHERE i.fechaLimite = :fechaLimite")})
public class IbAgendaTransaccionesPn implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUENTA_ORIGEN")
    private String cuentaOrigen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUENTA_DESTINO")
    private String cuentaDestino;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DIA_FRECUENCIA")
    private int diaFrecuencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Character tipo;    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FRECUENCIA")
    private Character frecuencia;    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_LIMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLimite;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgenda", fetch = FetchType.EAGER)
    private Collection<IbDetalleAgendaTransPn> ibDetalleAgendaTransPnCollection;

    public IbAgendaTransaccionesPn() {
    }

    public IbAgendaTransaccionesPn(BigDecimal id) {
        this.id = id;
    }

    public IbAgendaTransaccionesPn(BigDecimal id, Character tipo, String ctaOrigen, String ctaDestino, BigDecimal monto, Character frecuencia, int fechaFrecuencia, Date fechaLimite) {
        this.id = id;
        this.tipo = tipo;
        this.cuentaOrigen = ctaOrigen;
        this.cuentaDestino = ctaDestino;
        this.monto = monto;
        this.frecuencia = frecuencia;
        this.diaFrecuencia = fechaFrecuencia;
        this.fechaLimite = fechaLimite;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Character getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Character frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

        public Collection<IbDetalleAgendaTransPn> getIbDetalleAgendaTransPnCollection() {
        return ibDetalleAgendaTransPnCollection;
    }

    public void setIbDetalleAgendaTransPnCollection(Collection<IbDetalleAgendaTransPn> ibDetalleAgendaTransPnCollection) {
        this.ibDetalleAgendaTransPnCollection = ibDetalleAgendaTransPnCollection;
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
        if (!(object instanceof IbAgendaTransaccionesPn)) {
            return false;
        }
        IbAgendaTransaccionesPn other = (IbAgendaTransaccionesPn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAgendaTransaccionesPn[ id=" + id + " ]";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public int getDiaFrecuencia() {
        return diaFrecuencia;
    }

    public void setDiaFrecuencia(int diaFrecuencia) {
        this.diaFrecuencia = diaFrecuencia;
    }
    
}

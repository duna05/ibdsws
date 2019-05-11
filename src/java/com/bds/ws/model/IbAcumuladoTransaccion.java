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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_ACUMULADO_TRANSACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAcumuladoTransaccion.findAll", query = "SELECT i FROM IbAcumuladoTransaccion i"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findById", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.id = :id"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findByFecha", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.fecha = :fecha"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findByAcumuladoInternas", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.acumuladoInternas = :acumuladoInternas"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findByAcumuladoInternasTerceros", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.acumuladoInternasTerceros = :acumuladoInternasTerceros"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findByAcumuladoExternas", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.acumuladoExternas = :acumuladoExternas"),
    @NamedQuery(name = "IbAcumuladoTransaccion.findByAcumuladoExternasTerceros", query = "SELECT i FROM IbAcumuladoTransaccion i WHERE i.acumuladoExternasTerceros = :acumuladoExternasTerceros")})
public class IbAcumuladoTransaccion implements Serializable {

    @Column(name = "ACUMULADO_P2C")
    private BigDecimal acumuladoP2c;
    @Column(name = "ACUMULADO_DIGITEL")
    private BigDecimal acumuladoDigitel;
    @Column(name = "ACUMULADO_P2P")
    private BigDecimal acumuladoP2p;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "ACUMULADO_INTERNAS")
    private BigDecimal acumuladoInternas;
    @Column(name = "ACUMULADO_INTERNAS_TERCEROS")
    private BigDecimal acumuladoInternasTerceros;
    @Column(name = "ACUMULADO_EXTERNAS")
    private BigDecimal acumuladoExternas;
    @Column(name = "ACUMULADO_EXTERNAS_TERCEROS")
    private BigDecimal acumuladoExternasTerceros;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    
    public IbAcumuladoTransaccion() {
    }

    public IbAcumuladoTransaccion(BigDecimal id) {
        this.id = id;
    }

    public IbAcumuladoTransaccion(BigDecimal id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getAcumuladoInternas() {
        return acumuladoInternas;
    }

    public void setAcumuladoInternas(BigDecimal acumuladoInternas) {
        this.acumuladoInternas = acumuladoInternas;
    }

    public BigDecimal getAcumuladoInternasTerceros() {
        return acumuladoInternasTerceros;
    }

    public void setAcumuladoInternasTerceros(BigDecimal acumuladoInternasTerceros) {
        this.acumuladoInternasTerceros = acumuladoInternasTerceros;
    }

    public BigDecimal getAcumuladoExternas() {
        return acumuladoExternas;
    }

    public void setAcumuladoExternas(BigDecimal acumuladoExternas) {
        this.acumuladoExternas = acumuladoExternas;
    }

    public BigDecimal getAcumuladoExternasTerceros() {
        return acumuladoExternasTerceros;
    }

    public void setAcumuladoExternasTerceros(BigDecimal acumuladoExternasTerceros) {
        this.acumuladoExternasTerceros = acumuladoExternasTerceros;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof IbAcumuladoTransaccion)) {
            return false;
        }
        IbAcumuladoTransaccion other = (IbAcumuladoTransaccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAcumuladoTransaccion[ id=" + id + " ]";
    }

    public BigDecimal getAcumuladoDigitel() {
        return acumuladoDigitel;
    }

    public void setAcumuladoDigitel(BigDecimal acumuladoDigitel) {
        this.acumuladoDigitel = acumuladoDigitel;
    }

    public BigDecimal getAcumuladoP2p() {
        return acumuladoP2p;
    }

    public void setAcumuladoP2p(BigDecimal acumuladoP2p) {
        this.acumuladoP2p = acumuladoP2p;
    }

    public BigDecimal getAcumuladoP2c() {
        return acumuladoP2c;
    }

    public void setAcumuladoP2c(BigDecimal acumuladoP2c) {
        this.acumuladoP2c = acumuladoP2c;
    }

}

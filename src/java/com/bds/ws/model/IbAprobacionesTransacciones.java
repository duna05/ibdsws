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
@Table(name = "IB_APROBACIONES_TRANSACCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAprobacionesTransacciones.findAll", query = "SELECT i FROM IbAprobacionesTransacciones i"),
    @NamedQuery(name = "IbAprobacionesTransacciones.findById", query = "SELECT i FROM IbAprobacionesTransacciones i WHERE i.id = :id"),
    @NamedQuery(name = "IbAprobacionesTransacciones.findByFechaHora", query = "SELECT i FROM IbAprobacionesTransacciones i WHERE i.fechaHora = :fechaHora")})
public class IbAprobacionesTransacciones implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_TRANSACCION_PENDIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransaccionesPendientes idTransaccionPendiente;

    public IbAprobacionesTransacciones() {
    }

    public IbAprobacionesTransacciones(BigDecimal id) {
        this.id = id;
    }

    public IbAprobacionesTransacciones(BigDecimal id, Date fechaHora) {
        this.id = id;
        this.fechaHora = fechaHora;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbTransaccionesPendientes getIdTransaccionPendiente() {
        return idTransaccionPendiente;
    }

    public void setIdTransaccionPendiente(IbTransaccionesPendientes idTransaccionPendiente) {
        this.idTransaccionPendiente = idTransaccionPendiente;
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
        if (!(object instanceof IbAprobacionesTransacciones)) {
            return false;
        }
        IbAprobacionesTransacciones other = (IbAprobacionesTransacciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAprobacionesTransacciones[ id=" + id + " ]";
    }

}

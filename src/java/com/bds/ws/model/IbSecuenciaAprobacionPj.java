/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_SECUENCIA_APROBACION_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbSecuenciaAprobacionPj.findAll", query = "SELECT i FROM IbSecuenciaAprobacionPj i")
    , @NamedQuery(name = "IbSecuenciaAprobacionPj.findById", query = "SELECT i FROM IbSecuenciaAprobacionPj i WHERE i.id = :id")
    , @NamedQuery(name = "IbSecuenciaAprobacionPj.findByIdTransaccion", query = "SELECT i FROM IbSecuenciaAprobacionPj i WHERE i.idTransaccion = :idTransaccion")
    , @NamedQuery(name = "IbSecuenciaAprobacionPj.findByFechaHoraCreacion", query = "SELECT i FROM IbSecuenciaAprobacionPj i WHERE i.fechaHoraCreacion = :fechaHoraCreacion")
    , @NamedQuery(name = "IbSecuenciaAprobacionPj.findByFechaHoraModificacion", query = "SELECT i FROM IbSecuenciaAprobacionPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbSecuenciaAprobacionPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRANSACCION")
    private long idTransaccion;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CREACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCreacion;
    @Column(name = "FECHA_HORA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServicioPj;
    @JoinColumn(name = "CODIGO_USUARIO_CREACION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCreacion;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioModifica;
  
    

    public IbSecuenciaAprobacionPj() {
    }

    public IbSecuenciaAprobacionPj(Long id) {
        this.id = id;
    }

    public IbSecuenciaAprobacionPj(Long id, long idTransaccion, Date fechaHoraCreacion) {
        this.id = id;
        this.idTransaccion = idTransaccion;
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Date getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(Date fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }

    public IbServiciosPj getIdServicioPj() {
        return idServicioPj;
    }

    public void setIdServicioPj(IbServiciosPj idServicioPj) {
        this.idServicioPj = idServicioPj;
    }

    public IbUsuariosPj getCodigoUsuarioCreacion() {
        return codigoUsuarioCreacion;
    }

    public void setCodigoUsuarioCreacion(IbUsuariosPj codigoUsuarioCreacion) {
        this.codigoUsuarioCreacion = codigoUsuarioCreacion;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbUsuariosPj getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuariosPj codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
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
        if (!(object instanceof IbSecuenciaAprobacionPj)) {
            return false;
        }
        IbSecuenciaAprobacionPj other = (IbSecuenciaAprobacionPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbSecuenciaAprobacionPj[ id=" + id + " ]";
    }
    
}

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ledwin.belen
 */
@Entity
@Table(name = "IB_HIST_CLAVES_OP_ESPECIALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbHistClavesOpEspeciales.findAll", query = "SELECT i FROM IbHistClavesOpEspeciales i")
    , @NamedQuery(name = "IbHistClavesOpEspeciales.findById", query = "SELECT i FROM IbHistClavesOpEspeciales i WHERE i.id = :id")
    , @NamedQuery(name = "IbHistClavesOpEspeciales.findByClaveEspecial", query = "SELECT i FROM IbHistClavesOpEspeciales i WHERE i.claveEspecial = :claveEspecial")
    , @NamedQuery(name = "IbHistClavesOpEspeciales.findByFechaHoraCarga", query = "SELECT i FROM IbHistClavesOpEspeciales i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbHistClavesOpEspeciales.findByFechaHoraModificacion", query = "SELECT i FROM IbHistClavesOpEspeciales i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbHistClavesOpEspeciales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "CLAVE_ESPECIAL")
    private String claveEspecial;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios codigoUsuarioCarga;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios codigoUsuarioModifica;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;

    public IbHistClavesOpEspeciales() {
    }

    public IbHistClavesOpEspeciales(Long id) {
        this.id = id;
    }

    public IbHistClavesOpEspeciales(Long id, String claveEspecial, Date fechaHoraCarga) {
        this.id = id;
        this.claveEspecial = claveEspecial;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveEspecial() {
        return claveEspecial;
    }

    public void setClaveEspecial(String claveEspecial) {
        this.claveEspecial = claveEspecial;
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

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbUsuarios getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuarios codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public IbUsuarios getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuarios codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
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
        if (!(object instanceof IbHistClavesOpEspeciales)) {
            return false;
        }
        IbHistClavesOpEspeciales other = (IbHistClavesOpEspeciales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbHistClavesOpEspeciales[ id=" + id + " ]";
    }
    
}

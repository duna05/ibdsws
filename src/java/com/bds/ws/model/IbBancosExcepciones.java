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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar.mujica
 */
@Entity
@Table(name = "IB_BANCOS_EXCEPCIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbBancosExcepciones.findAll", query = "SELECT i FROM IbBancosExcepciones i"),
    @NamedQuery(name = "IbBancosExcepciones.findByIdBanco", query = "SELECT i FROM IbBancosExcepciones i WHERE i.idBanco = :idBanco"),
    @NamedQuery(name = "IbBancosExcepciones.findByFechaCreacion", query = "SELECT i FROM IbBancosExcepciones i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbBancosExcepciones.findByUsuarioCreacion", query = "SELECT i FROM IbBancosExcepciones i WHERE i.usuarioCreacion = :usuarioCreacion")})
public class IbBancosExcepciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ID_BANCO")
    private String idBanco;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    public IbBancosExcepciones() {
    }

    public IbBancosExcepciones(String idBanco) {
        this.idBanco = idBanco;
    }

    public IbBancosExcepciones(String idBanco, Date fechaCreacion, String usuarioCreacion) {
        this.idBanco = idBanco;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBanco != null ? idBanco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbBancosExcepciones)) {
            return false;
        }
        IbBancosExcepciones other = (IbBancosExcepciones) object;
        if ((this.idBanco == null && other.idBanco != null) || (this.idBanco != null && !this.idBanco.equals(other.idBanco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbBancosExcepciones[ idBanco=" + idBanco + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_MENSAJES_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbMensajesUsuarios.findAll", query = "SELECT i FROM IbMensajesUsuarios i"),
    @NamedQuery(name = "IbMensajesUsuarios.findById", query = "SELECT i FROM IbMensajesUsuarios i WHERE i.id = :id"),
    @NamedQuery(name = "IbMensajesUsuarios.findByEstatus", query = "SELECT i FROM IbMensajesUsuarios i WHERE i.estatus = :estatus")})
public class IbMensajesUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_MENSAJE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbMensajes idMensaje;

    public IbMensajesUsuarios() {
    }

    public IbMensajesUsuarios(BigDecimal id) {
        this.id = id;
    }

    public IbMensajesUsuarios(BigDecimal id, Character estatus) {
        this.id = id;
        this.estatus = estatus;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbMensajes getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(IbMensajes idMensaje) {
        this.idMensaje = idMensaje;
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
        if (!(object instanceof IbMensajesUsuarios)) {
            return false;
        }
        IbMensajesUsuarios other = (IbMensajesUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbMensajesUsuarios[ id=" + id + " ]";
    }

}

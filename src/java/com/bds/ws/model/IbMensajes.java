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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_MENSAJES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbMensajes.findAll", query = "SELECT i FROM IbMensajes i"),
    @NamedQuery(name = "IbMensajes.findById", query = "SELECT i FROM IbMensajes i WHERE i.id = :id"),
    @NamedQuery(name = "IbMensajes.findByFechaHora", query = "SELECT i FROM IbMensajes i WHERE i.fechaHora = :fechaHora")})
public class IbMensajes implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "TEXTO")
    private String texto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMensaje")
    private Collection<IbMensajesUsuarios> ibMensajesUsuariosCollection;

    public IbMensajes() {
    }

    public IbMensajes(BigDecimal id) {
        this.id = id;
    }

    public IbMensajes(BigDecimal id, Date fechaHora, String texto) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.texto = texto;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @XmlTransient
    public Collection<IbMensajesUsuarios> getIbMensajesUsuariosCollection() {
        return ibMensajesUsuariosCollection;
    }

    public void setIbMensajesUsuariosCollection(Collection<IbMensajesUsuarios> ibMensajesUsuariosCollection) {
        this.ibMensajesUsuariosCollection = ibMensajesUsuariosCollection;
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
        if (!(object instanceof IbMensajes)) {
            return false;
        }
        IbMensajes other = (IbMensajes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbMensajes[ id=" + id + " ]";
    }



}

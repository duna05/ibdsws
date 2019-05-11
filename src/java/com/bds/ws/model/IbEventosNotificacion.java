/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_EVENTOS_NOTIFICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbEventosNotificacion.findAll", query = "SELECT i FROM IbEventosNotificacion i"),
    @NamedQuery(name = "IbEventosNotificacion.findById", query = "SELECT i FROM IbEventosNotificacion i WHERE i.id = :id"),
    @NamedQuery(name = "IbEventosNotificacion.findByNombreEvento", query = "SELECT i FROM IbEventosNotificacion i WHERE i.nombreEvento = :nombreEvento")})
public class IbEventosNotificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_EVENTO")
    private String nombreEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSEE_MONTO")
    private Character poseeMonto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CORE")
    private BigDecimal idCore;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private Collection<IbUsuariosEventosMedios> ibUsuariosEventosMediosCollection;

    public IbEventosNotificacion() {
    }

    public IbEventosNotificacion(BigDecimal id) {
        this.id = id;
    }

    public IbEventosNotificacion(BigDecimal id, String nombreEvento) {
        this.id = id;
        this.nombreEvento = nombreEvento;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Character getPoseeMonto() {
        return poseeMonto;
    }

    public void setPoseeMonto(Character poseeMonto) {
        this.poseeMonto = poseeMonto;
    }

    public BigDecimal getIdCore() {
        return idCore;
    }

    public void setIdCore(BigDecimal idCore) {
        this.idCore = idCore;
    }
    
    @XmlTransient
    public Collection<IbUsuariosEventosMedios> getIbUsuariosEventosMediosCollection() {
        return ibUsuariosEventosMediosCollection;
    }

    public void setIbUsuariosEventosMediosCollection(Collection<IbUsuariosEventosMedios> ibUsuariosEventosMediosCollection) {
        this.ibUsuariosEventosMediosCollection = ibUsuariosEventosMediosCollection;
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
        if (!(object instanceof IbEventosNotificacion)) {
            return false;
        }
        IbEventosNotificacion other = (IbEventosNotificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbEventosNotificacion[ id=" + id + " ]";
    }

}

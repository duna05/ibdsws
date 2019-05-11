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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_USUARIOS_EVENTOS_MEDIOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_MEDIO","ID_EVENTO","ID_USUARIO"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUsuariosEventosMedios.findAll", query = "SELECT i FROM IbUsuariosEventosMedios i"),
    @NamedQuery(name = "IbUsuariosEventosMedios.findById", query = "SELECT i FROM IbUsuariosEventosMedios i WHERE i.id = :id")})
public class IbUsuariosEventosMedios implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_MEDIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbMediosNotificacion idMedio;
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEventosNotificacion idEvento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_MIN")
    private BigDecimal montoMin;

    public IbUsuariosEventosMedios() {
    }

    public IbUsuariosEventosMedios(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbMediosNotificacion getIdMedio() {
        return idMedio;
    }

    public void setIdMedio(IbMediosNotificacion idMedio) {
        this.idMedio = idMedio;
    }

    public IbEventosNotificacion getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(IbEventosNotificacion idEvento) {
        this.idEvento = idEvento;
    }

    public BigDecimal getMontoMin() {
        return montoMin;
    }

    public void setMontoMin(BigDecimal montoMin) {
        this.montoMin = montoMin;
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
        if (!(object instanceof IbUsuariosEventosMedios)) {
            return false;
        }
        IbUsuariosEventosMedios other = (IbUsuariosEventosMedios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUsuariosEventosMedios[ id=" + id + " ]";
    }

}

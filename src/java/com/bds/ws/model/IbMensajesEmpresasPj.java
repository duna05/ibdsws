/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_MENSAJES_EMPRESAS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbMensajesEmpresasPj.findAll", query = "SELECT i FROM IbMensajesEmpresasPj i")
    , @NamedQuery(name = "IbMensajesEmpresasPj.findById", query = "SELECT i FROM IbMensajesEmpresasPj i WHERE i.id = :id")
    , @NamedQuery(name = "IbMensajesEmpresasPj.findByEstatus", query = "SELECT i FROM IbMensajesEmpresasPj i WHERE i.estatus = :estatus")})
public class IbMensajesEmpresasPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @JoinColumn(name = "ID_EMPRESA_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresaPj;
    @JoinColumn(name = "ID_MENSAJE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbMensajes idMensaje;

    public IbMensajesEmpresasPj() {
    }

    public IbMensajesEmpresasPj(Long id) {
        this.id = id;
    }

    public IbMensajesEmpresasPj(Long id, Character estatus) {
        this.id = id;
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    public IbEmpresas getIdEmpresaPj() {
        return idEmpresaPj;
    }

    public void setIdEmpresaPj(IbEmpresas idEmpresaPj) {
        this.idEmpresaPj = idEmpresaPj;
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
        if (!(object instanceof IbMensajesEmpresasPj)) {
            return false;
        }
        IbMensajesEmpresasPj other = (IbMensajesEmpresasPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbMensajesEmpresasPj[ id=" + id + " ]";
    }
    
}

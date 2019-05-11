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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_SERVI_EMPRE_USUARIOS_PJ")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "IbServiEmpreUsuariosPj.findAll", query = "SELECT i FROM IbServiEmpreUsuariosPj i"),
@NamedQuery(name = "IbServiEmpreUsuariosPj.findById", query = "SELECT i FROM IbServiEmpreUsuariosPj i WHERE i.id = :id")})
public class IbServiEmpreUsuariosPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "ID_EMPRESA_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresas;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;
    @JoinColumn(name = "ID_SERVICIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbServiciosPj idServiciosPj;

    public IbServiEmpreUsuariosPj() {
    }

    public IbServiEmpreUsuariosPj(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbEmpresas getIdEmpresas() {
        return idEmpresas;
    }

    public void setIdEmpresas(IbEmpresas idEmpresas) {
        this.idEmpresas = idEmpresas;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
    }

    public IbServiciosPj getIdServiciosPj() {
        return idServiciosPj;
    }

    public void setIdServiciosPj(IbServiciosPj idServiciosPj) {
        this.idServiciosPj = idServiciosPj;
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
        if (!(object instanceof IbServiEmpreUsuariosPj)) {
            return false;
        }
        IbServiEmpreUsuariosPj other = (IbServiEmpreUsuariosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbServiEmpreUsuariosPj[ id=" + id + " ]";
    }
}
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_TRANS_USUARIOS_JURIDICOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbTransUsuariosJuridicos.findAll", query = "SELECT i FROM IbTransUsuariosJuridicos i"),
    @NamedQuery(name = "IbTransUsuariosJuridicos.findById", query = "SELECT i FROM IbTransUsuariosJuridicos i WHERE i.id = :id")})
public class IbTransUsuariosJuridicos implements Serializable {

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
    @JoinColumn(name = "ID_TRANSACCION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransacciones idTransaccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTransaccionesJuridicos")
    private Collection<IbTransJuridicosDetalle> ibTransJuridicosDetalleCollection;

    public IbTransUsuariosJuridicos() {
    }

    public IbTransUsuariosJuridicos(BigDecimal id) {
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

    public IbTransacciones getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(IbTransacciones idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @XmlTransient
    public Collection<IbTransJuridicosDetalle> getIbTransJuridicosDetalleCollection() {
        return ibTransJuridicosDetalleCollection;
    }

    public void setIbTransJuridicosDetalleCollection(Collection<IbTransJuridicosDetalle> ibTransJuridicosDetalleCollection) {
        this.ibTransJuridicosDetalleCollection = ibTransJuridicosDetalleCollection;
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
        if (!(object instanceof IbTransUsuariosJuridicos)) {
            return false;
        }
        IbTransUsuariosJuridicos other = (IbTransUsuariosJuridicos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbTransUsuariosJuridicos[ id=" + id + " ]";
    }

}

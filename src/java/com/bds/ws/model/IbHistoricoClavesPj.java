/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_HISTORICO_CLAVES_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbHistoricoClavesPj.findAll", query = "SELECT i FROM IbHistoricoClavesPj i"),
    @NamedQuery(name = "IbHistoricoClavesPj.findById", query = "SELECT i FROM IbHistoricoClavesPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbHistoricoClavesPj.findByClave", query = "SELECT i FROM IbHistoricoClavesPj i WHERE i.clave = :clave"),
    @NamedQuery(name = "IbHistoricoClavesPj.findByFechaCreacion", query = "SELECT i FROM IbHistoricoClavesPj i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbHistoricoClavesPj.findByFechaVencimiento", query = "SELECT i FROM IbHistoricoClavesPj i WHERE i.fechaVencimiento = :fechaVencimiento")})
public class IbHistoricoClavesPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "FECHA_CREACION",    insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_VENCIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;

    public IbHistoricoClavesPj() {
    }

    public IbHistoricoClavesPj(BigDecimal id) {
        this.id = id;
    }

    public IbHistoricoClavesPj(BigDecimal id, String clave, Date fechaCreacion, Date fechaVencimiento) {
        this.id = id;
        this.clave = clave;
        this.fechaCreacion = fechaCreacion;
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
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
        if (!(object instanceof IbHistoricoClavesPj)) {
            return false;
        }
        IbHistoricoClavesPj other = (IbHistoricoClavesPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbHistoricoClavesPj[ id=" + id + " ]";
    }
    
}

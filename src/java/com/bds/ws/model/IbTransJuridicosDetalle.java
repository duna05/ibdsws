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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_TRANS_JURIDICOS_DETALLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbTransJuridicosDetalle.findAll", query = "SELECT i FROM IbTransJuridicosDetalle i"),
    @NamedQuery(name = "IbTransJuridicosDetalle.findById", query = "SELECT i FROM IbTransJuridicosDetalle i WHERE i.id = :id"),
    @NamedQuery(name = "IbTransJuridicosDetalle.findByCuenta", query = "SELECT i FROM IbTransJuridicosDetalle i WHERE i.cuenta = :cuenta"),
    @NamedQuery(name = "IbTransJuridicosDetalle.findByAprobaciones", query = "SELECT i FROM IbTransJuridicosDetalle i WHERE i.aprobaciones = :aprobaciones"),
    @NamedQuery(name = "IbTransJuridicosDetalle.findByLimite", query = "SELECT i FROM IbTransJuridicosDetalle i WHERE i.limite = :limite")})
public class IbTransJuridicosDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @Size(max = 20)
    @Column(name = "CUENTA")
    private String cuenta;
    @Column(name = "APROBACIONES")
    private Long aprobaciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LIMITE")
    private BigDecimal limite;
    @JoinColumn(name = "ID_TRANSACCIONES_JURIDICOS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransUsuariosJuridicos idTransaccionesJuridicos;

    public IbTransJuridicosDetalle() {
    }

    public IbTransJuridicosDetalle(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Long getAprobaciones() {
        return aprobaciones;
    }

    public void setAprobaciones(Long aprobaciones) {
        this.aprobaciones = aprobaciones;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public IbTransUsuariosJuridicos getIdTransaccionesJuridicos() {
        return idTransaccionesJuridicos;
    }

    public void setIdTransaccionesJuridicos(IbTransUsuariosJuridicos idTransaccionesJuridicos) {
        this.idTransaccionesJuridicos = idTransaccionesJuridicos;
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
        if (!(object instanceof IbTransJuridicosDetalle)) {
            return false;
        }
        IbTransJuridicosDetalle other = (IbTransJuridicosDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbTransJuridicosDetalle[ id=" + id + " ]";
    }

}

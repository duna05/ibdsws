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
 * @author juan.vasquez
 */
@Entity
@Table(name = "IB_ERRORES_PA_ESP_DET_PJ_HIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbErroresPaEspDetPjHis.findAll", query = "SELECT i FROM IbErroresPaEspDetPjHis i"),
    @NamedQuery(name = "IbErroresPaEspDetPjHis.findById", query = "SELECT i FROM IbErroresPaEspDetPjHis i WHERE i.id = :id")})
public class IbErroresPaEspDetPjHis implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_PAGO_DETALLE", referencedColumnName = "ID_PAGO_DETALLE")
    @ManyToOne(optional = false)
    private IbCargaPagosEspDetPjHis idPagoDetalle;

    public IbErroresPaEspDetPjHis() {
    }

    public IbErroresPaEspDetPjHis(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbCargaPagosEspDetPjHis getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(IbCargaPagosEspDetPjHis idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
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
        if (!(object instanceof IbErroresPaEspDetPjHis)) {
            return false;
        }
        IbErroresPaEspDetPjHis other = (IbErroresPaEspDetPjHis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbErroresPaEspDetPjHis[ id=" + id + " ]";
    }
    
}

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
@Table(name = "IB_ERRORES_PA_ESP_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbErroresPaEspPj.findAll", query = "SELECT i FROM IbErroresPaEspPj i"),
    @NamedQuery(name = "IbErroresPaEspPj.findById", query = "SELECT i FROM IbErroresPaEspPj i WHERE i.id = :id")})
public class IbErroresPaEspPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne(optional = false)
    private IbCargaPagosEspPj idPago;

    public IbErroresPaEspPj() {
    }

    public IbErroresPaEspPj(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbCargaPagosEspPj getIdPago() {
        return idPago;
    }

    public void setIdPago(IbCargaPagosEspPj idPago) {
        this.idPago = idPago;
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
        if (!(object instanceof IbErroresPaEspPj)) {
            return false;
        }
        IbErroresPaEspPj other = (IbErroresPaEspPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbErroresPaEspPj[ id=" + id + " ]";
    }
    
}

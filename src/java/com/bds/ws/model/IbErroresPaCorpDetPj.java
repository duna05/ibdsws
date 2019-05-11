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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_ERRORES_PA_CORP_DET_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbErroresPaCorpDetPj.findAll", query = "SELECT i FROM IbErroresPaCorpDetPj i")
    , @NamedQuery(name = "IbErroresPaCorpDetPj.findById", query = "SELECT i FROM IbErroresPaCorpDetPj i WHERE i.id = :id")})
public class IbErroresPaCorpDetPj implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_PAGO_DETALLE", referencedColumnName = "ID_PAGO_DETALLE")
    @ManyToOne(optional = false)
    private IbCargaPagosCorpDetPj idPagoDetalle;
    
    
    @JoinColumn(name = "ID_ERROR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbErroresPj idErrorDetalle;

    public IbErroresPj getIdErrorDetalle() {
        return idErrorDetalle;
    }

    public void setIdErrorDetalle(IbErroresPj idErrorDetalle) {
        this.idErrorDetalle = idErrorDetalle;
    }
    
    

    public IbErroresPaCorpDetPj() {
    }

    public IbErroresPaCorpDetPj(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbCargaPagosCorpDetPj getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(IbCargaPagosCorpDetPj idPagoDetalle) {
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
        if (!(object instanceof IbErroresPaCorpDetPj)) {
            return false;
        }
        IbErroresPaCorpDetPj other = (IbErroresPaCorpDetPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbErroresPaCorpDetPj[ id=" + id + " ]";
    }
    
}

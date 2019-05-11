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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author robinson.rodriguez
 */
@Entity
@Table(name = "IB_ERRORES_PA_ESP_DET_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbErroresPaEspDetPj.findAll", query = "SELECT i FROM IbErroresPaEspDetPj i"),
    @NamedQuery(name = "IbErroresPaEspDetPj.findById", query = "SELECT i FROM IbErroresPaEspDetPj i WHERE i.id = :id")})
public class IbErroresPaEspDetPj implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_PAGO_DETALLE", referencedColumnName = "ID_PAGO_DETALLE")
    @ManyToOne(optional = false)
    private IbCargaPagosEspDetPj idPagoDetalle;
    @JoinColumn(name = "ID_ERROR", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbErroresPj idError;

    public IbErroresPaEspDetPj() {
    }

    public IbErroresPaEspDetPj(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbCargaPagosEspDetPj getIdPagoDetalle() {
        return idPagoDetalle;
    }

    public void setIdPagoDetalle(IbCargaPagosEspDetPj idPagoDetalle) {
        this.idPagoDetalle = idPagoDetalle;
    }

    public IbErroresPj getIdError() {
        return idError;
    }

    public void setIdError(IbErroresPj idError) {
        this.idError = idError;
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
        if (!(object instanceof IbErroresPaEspDetPj)) {
            return false;
        }
        IbErroresPaEspDetPj other = (IbErroresPaEspDetPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbErroresPaEspDetPj[ id=" + id + " ]";
    }

}

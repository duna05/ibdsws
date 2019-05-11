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
@Table(name = "IB_DETALLE_PAGO_PROVEEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbDetallePagoProveedor.findAll", query = "SELECT i FROM IbDetallePagoProveedor i"),
    @NamedQuery(name = "IbDetallePagoProveedor.findById", query = "SELECT i FROM IbDetallePagoProveedor i WHERE i.id = :id"),
    @NamedQuery(name = "IbDetallePagoProveedor.findByTipoDetalle", query = "SELECT i FROM IbDetallePagoProveedor i WHERE i.tipoDetalle = :tipoDetalle"),
    @NamedQuery(name = "IbDetallePagoProveedor.findByReferencia", query = "SELECT i FROM IbDetallePagoProveedor i WHERE i.referencia = :referencia"),
    @NamedQuery(name = "IbDetallePagoProveedor.findByMonto", query = "SELECT i FROM IbDetallePagoProveedor i WHERE i.monto = :monto")})
public class IbDetallePagoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TIPO_DETALLE")
    private String tipoDetalle;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "REFERENCIA")
    private String referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    @JoinColumn(name = "ID_TRANSACCION_BENEFICIARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbTransaccionesBeneficiarios idTransaccionBeneficiario;

    public IbDetallePagoProveedor() {
    }

    public IbDetallePagoProveedor(BigDecimal id) {
        this.id = id;
    }

    public IbDetallePagoProveedor(BigDecimal id, String tipoDetalle, String referencia, BigDecimal monto) {
        this.id = id;
        this.tipoDetalle = tipoDetalle;
        this.referencia = referencia;
        this.monto = monto;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public IbTransaccionesBeneficiarios getIdTransaccionBeneficiario() {
        return idTransaccionBeneficiario;
    }

    public void setIdTransaccionBeneficiario(IbTransaccionesBeneficiarios idTransaccionBeneficiario) {
        this.idTransaccionBeneficiario = idTransaccionBeneficiario;
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
        if (!(object instanceof IbDetallePagoProveedor)) {
            return false;
        }
        IbDetallePagoProveedor other = (IbDetallePagoProveedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbDetallePagoProveedor[ id=" + id + " ]";
    }

}

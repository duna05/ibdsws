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
@Table(name = "IB_PREFIJOS_OPERADORAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPrefijosOperadoras.findAll", query = "SELECT i FROM IbPrefijosOperadoras i"),
    @NamedQuery(name = "IbPrefijosOperadoras.findById", query = "SELECT i FROM IbPrefijosOperadoras i WHERE i.id = :id"),
    @NamedQuery(name = "IbPrefijosOperadoras.findByOperadora", query = "SELECT i FROM IbPrefijosOperadoras i WHERE i.operadora = :operadora")})
public class IbPrefijosOperadoras implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "OPERADORA")
    private String operadora;

    public IbPrefijosOperadoras() {
    }

    public IbPrefijosOperadoras(BigDecimal id) {
        this.id = id;
    }

    public IbPrefijosOperadoras(BigDecimal id, String operadora) {
        this.id = id;
        this.operadora = operadora;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
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
        if (!(object instanceof IbPrefijosOperadoras)) {
            return false;
        }
        IbPrefijosOperadoras other = (IbPrefijosOperadoras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPrefijosOperadoras[ id=" + id + " ]";
    }
    
}

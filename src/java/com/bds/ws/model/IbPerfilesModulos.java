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
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_PERFILES_MODULOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPerfilesModulos.findAll", query = "SELECT i FROM IbPerfilesModulos i"),
    @NamedQuery(name = "IbPerfilesModulos.findById", query = "SELECT i FROM IbPerfilesModulos i WHERE i.id = :id")})
public class IbPerfilesModulos implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "ID_PERFIL_ADM", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfilesAdm idPerfilAdm;
    @JoinColumn(name = "ID_MODULO_ADM", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbModulosAdm idModuloAdm;

    public IbPerfilesModulos() {
    }

    public IbPerfilesModulos(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public IbPerfilesAdm getIdPerfilAdm() {
        return idPerfilAdm;
    }

    public void setIdPerfilAdm(IbPerfilesAdm idPerfilAdm) {
        this.idPerfilAdm = idPerfilAdm;
    }

    public IbModulosAdm getIdModuloAdm() {
        return idModuloAdm;
    }

    public void setIdModuloAdm(IbModulosAdm idModuloAdm) {
        this.idModuloAdm = idModuloAdm;
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
        if (!(object instanceof IbPerfilesModulos)) {
            return false;
        }
        IbPerfilesModulos other = (IbPerfilesModulos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPerfilesModulos[ id=" + id + " ]";
    }

}

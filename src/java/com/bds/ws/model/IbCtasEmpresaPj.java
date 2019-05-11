/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CTAS_EMPRESA_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCtasEmpresaPj.findAll", query = "SELECT i FROM IbCtasEmpresaPj i")
    , @NamedQuery(name = "IbCtasEmpresaPj.findById", query = "SELECT i FROM IbCtasEmpresaPj i WHERE i.id = :id")
    , @NamedQuery(name = "IbCtasEmpresaPj.findByNroCuenta", query = "SELECT i FROM IbCtasEmpresaPj i WHERE i.nroCuenta = :nroCuenta")
    , @NamedQuery(name = "IbCtasEmpresaPj.findByEstatusCuenta", query = "SELECT i FROM IbCtasEmpresaPj i WHERE i.estatusCuenta = :estatusCuenta")
    , @NamedQuery(name = "IbCtasEmpresaPj.findByFechaHoraCarga", query = "SELECT i FROM IbCtasEmpresaPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCtasEmpresaPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCtasEmpresaPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCtasEmpresaPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CTAS_EMPRESA_PJ")
    @SequenceGenerator(sequenceName = "IB_S_CTAS_EMPRESA_PJ", allocationSize = 1, name = "CUST_SEQ_CTAS_EMPRESA_PJ")
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA")
    private String nroCuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_CUENTA")
    private short estatusCuenta;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioModifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCtaEmpresa")
    private Collection<IbCtasEmpresaUsuarioPj> ibCtasEmpresaUsuarioPjCollection;

    public IbCtasEmpresaPj() {
    }

    public IbCtasEmpresaPj(Long id) {
        this.id = id;
    }

    public IbCtasEmpresaPj(Long id, String nroCuenta, short estatusCuenta, Date fechaHoraCarga) {
        this.id = id;
        this.nroCuenta = nroCuenta;
        this.estatusCuenta = estatusCuenta;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public short getEstatusCuenta() {
        return estatusCuenta;
    }

    public void setEstatusCuenta(short estatusCuenta) {
        this.estatusCuenta = estatusCuenta;
    }

    public Date getFechaHoraCarga() {
        return fechaHoraCarga;
    }

    public void setFechaHoraCarga(Date fechaHoraCarga) {
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Date getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(Date fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }

    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public IbUsuariosPj getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuariosPj codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
    }

    @XmlTransient
    public Collection<IbCtasEmpresaUsuarioPj> getIbCtasEmpresaUsuarioPjCollection() {
        return ibCtasEmpresaUsuarioPjCollection;
    }

    public void setIbCtasEmpresaUsuarioPjCollection(Collection<IbCtasEmpresaUsuarioPj> ibCtasEmpresaUsuarioPjCollection) {
        this.ibCtasEmpresaUsuarioPjCollection = ibCtasEmpresaUsuarioPjCollection;
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
        if (!(object instanceof IbCtasEmpresaPj)) {
            return false;
        }
        IbCtasEmpresaPj other = (IbCtasEmpresaPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCtasEmpresaPj[ id=" + id + " ]";
    }
}
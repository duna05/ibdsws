/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CTAS_EMPRESA_USUARIO_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCtasEmpresaUsuarioPj.findAll", query = "SELECT i FROM IbCtasEmpresaUsuarioPj i")
    , @NamedQuery(name = "IbCtasEmpresaUsuarioPj.findById", query = "SELECT i FROM IbCtasEmpresaUsuarioPj i WHERE i.id = :id")
    , @NamedQuery(name = "IbCtasEmpresaUsuarioPj.findByEstatusCuenta", query = "SELECT i FROM IbCtasEmpresaUsuarioPj i WHERE i.estatusCuenta = :estatusCuenta")
    , @NamedQuery(name = "IbCtasEmpresaUsuarioPj.findByFechaHoraCarga", query = "SELECT i FROM IbCtasEmpresaUsuarioPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCtasEmpresaUsuarioPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCtasEmpresaUsuarioPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")
    })
public class IbCtasEmpresaUsuarioPj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CTAS_EMPRESA_USUARIO_PJ")
    @SequenceGenerator(sequenceName = "IB_S_CTAS_EMPRESA_USUARIO_PJ", allocationSize = 1, name = "CUST_SEQ_CTAS_EMPRESA_USUARIO_PJ")
    @Column(name = "ID")
    private Long id;
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
    @JoinColumn(name = "ID_CTA_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCtasEmpresaPj idCtaEmpresa;
    @JoinColumn(name = "ID_EMPRESA_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresaPj;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioModifica;

    public IbCtasEmpresaUsuarioPj() {
    }

    public IbCtasEmpresaUsuarioPj(Long id) {
        this.id = id;
    }

    public IbCtasEmpresaUsuarioPj(Long id, short estatusCuenta, Date fechaHoraCarga) {
        this.id = id;
        this.estatusCuenta = estatusCuenta;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public IbCtasEmpresaPj getIdCtaEmpresa() {
        return idCtaEmpresa;
    }

    public void setIdCtaEmpresa(IbCtasEmpresaPj idCtaEmpresa) {
        this.idCtaEmpresa = idCtaEmpresa;
    }

    public IbEmpresas getIdEmpresaPj() {
        return idEmpresaPj;
    }

    public void setIdEmpresaPj(IbEmpresas idEmpresaPj) {
        this.idEmpresaPj = idEmpresaPj;
    }

    public IbUsuariosPj getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuariosPj codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbUsuariosPj getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuariosPj codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
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
        if (!(object instanceof IbCtasEmpresaUsuarioPj)) {
            return false;
        }
        IbCtasEmpresaUsuarioPj other = (IbCtasEmpresaUsuarioPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCtasEmpresaUsuarioPj[ id=" + id + " ]";
    }
    
}

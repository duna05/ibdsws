/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CONEXION_USUARIO_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbConexionUsuarioPj.findAll", query = "SELECT i FROM IbConexionUsuarioPj i"),
    @NamedQuery(name = "IbConexionUsuarioPj.findById", query = "SELECT i FROM IbConexionUsuarioPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbConexionUsuarioPj.findByInicioSesion", query = "SELECT i FROM IbConexionUsuarioPj i WHERE i.inicioSesion = :inicioSesion"),
    @NamedQuery(name = "IbConexionUsuarioPj.findByFechaHoraUltimaInteraccion", query = "SELECT i FROM IbConexionUsuarioPj i WHERE i.fechaHoraUltimaInteraccion = :fechaHoraUltimaInteraccion"),
    @NamedQuery(name = "IbConexionUsuarioPj.findByIdSesion", query = "SELECT i FROM IbConexionUsuarioPj i WHERE i.idSesion = :idSesion")})
public class IbConexionUsuarioPj implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INICIO_SESION")
    private BigInteger inicioSesion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_ULTIMA_INTERACCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraUltimaInteraccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ID_SESION")
    private String idSesion;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;

    public IbConexionUsuarioPj() {
    }

    public IbConexionUsuarioPj(BigDecimal id) {
        this.id = id;
    }

    public IbConexionUsuarioPj(BigDecimal id, BigInteger inicioSesion, Date fechaHoraUltimaInteraccion, String idSesion) {
        this.id = id;
        this.inicioSesion = inicioSesion;
        this.fechaHoraUltimaInteraccion = fechaHoraUltimaInteraccion;
        this.idSesion = idSesion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(BigInteger inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public Date getFechaHoraUltimaInteraccion() {
        return fechaHoraUltimaInteraccion;
    }

    public void setFechaHoraUltimaInteraccion(Date fechaHoraUltimaInteraccion) {
        this.fechaHoraUltimaInteraccion = fechaHoraUltimaInteraccion;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
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
        if (!(object instanceof IbConexionUsuarioPj)) {
            return false;
        }
        IbConexionUsuarioPj other = (IbConexionUsuarioPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbConexionUsuarioPj[ id=" + id + " ]";
    }
    
}

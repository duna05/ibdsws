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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_USUARIOS_CANALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUsuariosCanales.findAll", query = "SELECT i FROM IbUsuariosCanales i"),
    @NamedQuery(name = "IbUsuariosCanales.findById", query = "SELECT i FROM IbUsuariosCanales i WHERE i.id = :id"),
    @NamedQuery(name = "IbUsuariosCanales.findByEstatusRegistro", query = "SELECT i FROM IbUsuariosCanales i WHERE i.estatusRegistro = :estatusRegistro"),
    @NamedQuery(name = "IbUsuariosCanales.findByEstatusAcceso", query = "SELECT i FROM IbUsuariosCanales i WHERE i.estatusAcceso = :estatusAcceso"),
    @NamedQuery(name = "IbUsuariosCanales.findByLimiteInternas", query = "SELECT i FROM IbUsuariosCanales i WHERE i.limiteInternas = :limiteInternas"),
    @NamedQuery(name = "IbUsuariosCanales.findByLimiteInternasTerceros", query = "SELECT i FROM IbUsuariosCanales i WHERE i.limiteInternasTerceros = :limiteInternasTerceros"),
    @NamedQuery(name = "IbUsuariosCanales.findByLimiteExternas", query = "SELECT i FROM IbUsuariosCanales i WHERE i.limiteExternas = :limiteExternas"),
    @NamedQuery(name = "IbUsuariosCanales.findByLimiteExternasTerceros", query = "SELECT i FROM IbUsuariosCanales i WHERE i.limiteExternasTerceros = :limiteExternasTerceros"),
    @NamedQuery(name = "IbUsuariosCanales.findByInicioSesion", query = "SELECT i FROM IbUsuariosCanales i WHERE i.inicioSesion = :inicioSesion"),
    @NamedQuery(name = "IbUsuariosCanales.findByFechaHoraUltimaInteraccion", query = "SELECT i FROM IbUsuariosCanales i WHERE i.fechaHoraUltimaInteraccion = :fechaHoraUltimaInteraccion")})
public class IbUsuariosCanales implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ID_SESION")
    private String idSesion;

    @Basic(optional = false)
    @NotNull
    @Column(name = "INTENTOS_FALLIDOS")
    private int intentosFallidos;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 2)
    @Column(name = "ESTATUS_REGISTRO")
    private String estatusRegistro;
    @Size(max = 2)
    @Column(name = "ESTATUS_ACCESO")
    private String estatusAcceso;
    @Column(name = "LIMITE_INTERNAS")
    private BigDecimal limiteInternas;
    @Column(name = "LIMITE_INTERNAS_TERCEROS")
    private BigDecimal limiteInternasTerceros;
    @Column(name = "LIMITE_EXTERNAS")
    private BigDecimal limiteExternas;
    @Column(name = "LIMITE_EXTERNAS_TERCEROS")
    private BigDecimal limiteExternasTerceros;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INICIO_SESION")
    private Character inicioSesion;
    @Column(name = "FECHA_HORA_ULTIMA_INTERACCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraUltimaInteraccion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;

    public IbUsuariosCanales() {
    }

    public IbUsuariosCanales(BigDecimal id) {
        this.id = id;
    }

    public IbUsuariosCanales(BigDecimal id, Character inicioSesion) {
        this.id = id;
        this.inicioSesion = inicioSesion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    public String getEstatusAcceso() {
        return estatusAcceso;
    }

    public void setEstatusAcceso(String estatusAcceso) {
        this.estatusAcceso = estatusAcceso;
    }

    public BigDecimal getLimiteInternas() {
        return limiteInternas;
    }

    public void setLimiteInternas(BigDecimal limiteInternas) {
        this.limiteInternas = limiteInternas;
    }

    public BigDecimal getLimiteInternasTerceros() {
        return limiteInternasTerceros;
    }

    public void setLimiteInternasTerceros(BigDecimal limiteInternasTerceros) {
        this.limiteInternasTerceros = limiteInternasTerceros;
    }

    public BigDecimal getLimiteExternas() {
        return limiteExternas;
    }

    public void setLimiteExternas(BigDecimal limiteExternas) {
        this.limiteExternas = limiteExternas;
    }

    public BigDecimal getLimiteExternasTerceros() {
        return limiteExternasTerceros;
    }

    public void setLimiteExternasTerceros(BigDecimal limiteExternasTerceros) {
        this.limiteExternasTerceros = limiteExternasTerceros;
    }

    public Character getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(Character inicioSesion) {
        this.inicioSesion = inicioSesion;
    }

    public Date getFechaHoraUltimaInteraccion() {
        return fechaHoraUltimaInteraccion;
    }

    public void setFechaHoraUltimaInteraccion(Date fechaHoraUltimaInteraccion) {
        this.fechaHoraUltimaInteraccion = fechaHoraUltimaInteraccion;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof IbUsuariosCanales)) {
            return false;
        }
        IbUsuariosCanales other = (IbUsuariosCanales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUsuariosCanales[ id=" + id + " ]";
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

}

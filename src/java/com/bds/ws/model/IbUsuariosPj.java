/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "IB_USUARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUsuariosPj.findAll", query = "SELECT i FROM IbUsuariosPj i"),
    @NamedQuery(name = "IbUsuariosPj.findById", query = "SELECT i FROM IbUsuariosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbUsuariosPj.findByTipoDoc", query = "SELECT i FROM IbUsuariosPj i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbUsuariosPj.findByNroDoc", query = "SELECT i FROM IbUsuariosPj i WHERE i.nroDoc = :nroDoc"),
    @NamedQuery(name = "IbUsuariosPj.findByNombre", query = "SELECT i FROM IbUsuariosPj i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbUsuariosPj.findByClave", query = "SELECT i FROM IbUsuariosPj i WHERE i.clave = :clave"),
    @NamedQuery(name = "IbUsuariosPj.findByLogin", query = "SELECT i FROM IbUsuariosPj i WHERE i.login = :login"),
    @NamedQuery(name = "IbUsuariosPj.findByEstatusAcceso", query = "SELECT i FROM IbUsuariosPj i WHERE i.estatusAcceso = :estatusAcceso"),
    @NamedQuery(name = "IbUsuariosPj.findByIntentosFallidos", query = "SELECT i FROM IbUsuariosPj i WHERE i.intentosFallidos = :intentosFallidos"),
    @NamedQuery(name = "IbUsuariosPj.findByIntentosFallidosPreguntas", query = "SELECT i FROM IbUsuariosPj i WHERE i.intentosFallidosPreguntas = :intentosFallidosPreguntas"),
    @NamedQuery(name = "IbUsuariosPj.findByFechaCreacion", query = "SELECT i FROM IbUsuariosPj i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbUsuariosPj.findByFechaModificacion", query = "SELECT i FROM IbUsuariosPj i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbUsuariosPj implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPj")
    private Collection<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPjList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPj")
    @OrderBy("fechaHoraUltimaInteraccion DESC")
    private Collection<IbConexionUsuarioPj> ibConexionUsuarioPjCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_REGISTRO")
    private Character estatusRegistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPj")
    private Collection<IbHistoricoClavesPj> ibHistoricoClavesPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPj")
    private Collection<IbLogsPj> ibLogsPjCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "NRO_DOC")
    private String nroDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 250)
    @Column(name = "CLAVE")
    private String clave;
    @Size(max = 20)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_ACCESO")
    private Character estatusAcceso;
    @Column(name = "INTENTOS_FALLIDOS", insertable = false)
    private BigInteger intentosFallidos;
    @Column(name = "INTENTOS_FALLIDOS_PREGUNTAS", insertable = false)
    private BigInteger intentosFallidosPreguntas;
    @Column(name = "FECHA_CREACION",     insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPj")
    private Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection;

    public IbUsuariosPj() {
    }

    public IbUsuariosPj(BigDecimal id) {
        this.id = id;
    }
    
    public IbUsuariosPj(BigDecimal id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public IbUsuariosPj(BigDecimal id, Character tipoDoc, String nroDoc, String nombre, Character estatusAcceso, Date fechaCreacion) {
        this.id = id;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.nombre = nombre;
        this.estatusAcceso = estatusAcceso;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Character getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Character tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Character getEstatusAcceso() {
        return estatusAcceso;
    }

    public void setEstatusAcceso(Character estatusAcceso) {
        this.estatusAcceso = estatusAcceso;
    }

    public BigInteger getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(BigInteger intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public BigInteger getIntentosFallidosPreguntas() {
        return intentosFallidosPreguntas;
    }

    public void setIntentosFallidosPreguntas(BigInteger intentosFallidosPreguntas) {
        this.intentosFallidosPreguntas = intentosFallidosPreguntas;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @XmlTransient
    public Collection<IbEmpresasUsuariosPj> getIbEmpresasUsuariosPjCollection() {
        return ibEmpresasUsuariosPjCollection;
    }

    public void setIbEmpresasUsuariosPjCollection(Collection<IbEmpresasUsuariosPj> ibEmpresasUsuariosPjCollection) {
        this.ibEmpresasUsuariosPjCollection = ibEmpresasUsuariosPjCollection;
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
        if (!(object instanceof IbUsuariosPj)) {
            return false;
        }
        IbUsuariosPj other = (IbUsuariosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUsuariosPj[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbLogsPj> getIbLogsPjCollection() {
        return ibLogsPjCollection;
    }

    public void setIbLogsPjCollection(Collection<IbLogsPj> ibLogsPjCollection) {
        this.ibLogsPjCollection = ibLogsPjCollection;
    }

    @XmlTransient
    public Collection<IbHistoricoClavesPj> getIbHistoricoClavesPjCollection() {
        return ibHistoricoClavesPjCollection;
    }

    public void setIbHistoricoClavesPjCollection(Collection<IbHistoricoClavesPj> ibHistoricoClavesPjCollection) {
        this.ibHistoricoClavesPjCollection = ibHistoricoClavesPjCollection;
    }

    public Character getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(Character estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    @XmlTransient
    public Collection<IbConexionUsuarioPj> getIbConexionUsuarioPjCollection() {
        return ibConexionUsuarioPjCollection;
    }

    public void setIbConexionUsuarioPjCollection(Collection<IbConexionUsuarioPj> ibConexionUsuarioPjCollection) {
        this.ibConexionUsuarioPjCollection = ibConexionUsuarioPjCollection;
    }

    @XmlTransient
    public Collection<IbServiEmpreUsuariosPj> getIbServiEmpreUsuariosPjList() {
        return ibServiEmpreUsuariosPjList;
    }

    public void setIbServiEmpreUsuariosPjList(Collection<IbServiEmpreUsuariosPj> ibServiEmpreUsuariosPjList) {
        this.ibServiEmpreUsuariosPjList = ibServiEmpreUsuariosPjList;
    }
}

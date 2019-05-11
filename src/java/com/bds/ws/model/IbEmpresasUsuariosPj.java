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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "IB_EMPRESAS_USUARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbEmpresasUsuariosPj.findAll", query = "SELECT i FROM IbEmpresasUsuariosPj i"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findById", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByEmail", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.email = :email"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByTelfOficina", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.telfOficina = :telfOficina"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByTelfCelular", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.telfCelular = :telfCelular"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByEsRepresentanteLegal", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.esRepresentanteLegal = :esRepresentanteLegal"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByEstatusRegistro", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.estatusRegistro = :estatusRegistro"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByEstatusAcceso", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.estatusAcceso = :estatusAcceso"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByFechaHoraUltimaInteraccion", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.fechaHoraUltimaInteraccion = :fechaHoraUltimaInteraccion"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByFechaCreacion", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbEmpresasUsuariosPj.findByFechaModificacion", query = "SELECT i FROM IbEmpresasUsuariosPj i WHERE i.fechaModificacion = :fechaModificacion")})
public class IbEmpresasUsuariosPj implements Serializable {
    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumns({
        @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID_USUARIO_PJ", insertable = false, updatable = false)})
    private IbMontosUsuariosPj ibMontosUsuariosPj;
    @Size(max = 100)
    @Column(name = "CARGO")
    private String cargo;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 20)
    @Column(name = "TELF_OFICINA")
    private String telfOficina;
    @Size(max = 20)
    @Column(name = "TELF_CELULAR")
    private String telfCelular;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ES_REPRESENTANTE_LEGAL")
    private Character esRepresentanteLegal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_REGISTRO")
    private Character estatusRegistro;
    @Column(name = "ESTATUS_ACCESO")
    private Character estatusAcceso;
    @Column(name = "FECHA_HORA_ULTIMA_INTERACCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraUltimaInteraccion;
    @Column(name = "FECHA_CREACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "PERFIL_ACCESO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfilesPj perfilAcceso;
    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;

    public IbEmpresasUsuariosPj() {
    }

    public IbEmpresasUsuariosPj(BigDecimal id) {
        this.id = id;
    }

    public IbEmpresasUsuariosPj(BigDecimal id, Character esRepresentanteLegal, Character estatusRegistro, Date fechaCreacion) {
        this.id = id;
        this.esRepresentanteLegal = esRepresentanteLegal;
        this.estatusRegistro = estatusRegistro;
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelfOficina() {
        return telfOficina;
    }

    public void setTelfOficina(String telfOficina) {
        this.telfOficina = telfOficina;
    }

    public String getTelfCelular() {
        return telfCelular;
    }

    public void setTelfCelular(String telfCelular) {
        this.telfCelular = telfCelular;
    }

    public Character getEsRepresentanteLegal() {
        return esRepresentanteLegal;
    }

    public void setEsRepresentanteLegal(Character esRepresentanteLegal) {
        this.esRepresentanteLegal = esRepresentanteLegal;
    }

    public Character getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(Character estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    public Character getEstatusAcceso() {
        return estatusAcceso;
    }

    public void setEstatusAcceso(Character estatusAcceso) {
        this.estatusAcceso = estatusAcceso;
    }

    public Date getFechaHoraUltimaInteraccion() {
        return fechaHoraUltimaInteraccion;
    }

    public void setFechaHoraUltimaInteraccion(Date fechaHoraUltimaInteraccion) {
        this.fechaHoraUltimaInteraccion = fechaHoraUltimaInteraccion;
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

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbPerfilesPj getPerfilAcceso() {
        return perfilAcceso;
    }

    public void setPerfilAcceso(IbPerfilesPj perfilAcceso) {
        this.perfilAcceso = perfilAcceso;
    }

    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
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
        if (!(object instanceof IbEmpresasUsuariosPj)) {
            return false;
        }
        IbEmpresasUsuariosPj other = (IbEmpresasUsuariosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbEmpresasUsuariosPj[ id=" + id + " ]";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public IbMontosUsuariosPj getIbMontosUsuariosPj() {
        return ibMontosUsuariosPj;
    }

    public void setIbMontosUsuariosPj(IbMontosUsuariosPj ibMontosUsuariosPj) {
        this.ibMontosUsuariosPj = ibMontosUsuariosPj;
    }
}

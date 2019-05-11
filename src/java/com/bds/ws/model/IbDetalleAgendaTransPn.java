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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cesar.mujica
 */
@Entity
@Table(name = "IB_DETALLE_AGENDA_TRANS_PN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbDetalleAgendaTransPn.findAll", query = "SELECT i FROM IbDetalleAgendaTransPn i"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findById", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.id = :id"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByIdTransaccion", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.idTransaccion = :idTransaccion"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByEstatus", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.estatus = :estatus"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByFechaCreacion", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByFechaAgendada", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.fechaAgendada = :fechaAgendada"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByMonto", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.monto = :monto"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByTipoDoc", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByDocumento", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByCuentaOrigen", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.cuentaOrigen = :cuentaOrigen"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByCuentaDestino", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.cuentaDestino = :cuentaDestino"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByDescripcion", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByNombre", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByEmail", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.email = :email"),
    @NamedQuery(name = "IbDetalleAgendaTransPn.findByFechaEjecucion", query = "SELECT i FROM IbDetalleAgendaTransPn i WHERE i.fechaEjecucion = :fechaEjecucion")})
public class IbDetalleAgendaTransPn implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO")
    private BigDecimal monto;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TRANSACCION")
    private int idTransaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_AGENDADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAgendada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DOCUMENTO")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CUENTA_ORIGEN")
    private String cuentaOrigen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUENTA_DESTINO")
    private String cuentaDestino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "NOMBRE")
    private String nombre;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @XmlTransient
    @JoinColumn(name = "ID_AGENDA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    
    private IbAgendaTransaccionesPn idAgenda;

    public IbDetalleAgendaTransPn() {
    }

    public IbDetalleAgendaTransPn(BigDecimal id) {
        this.id = id;
    }

    public IbDetalleAgendaTransPn(BigDecimal id, int idTransaccion, Character estatus, Date fechaCreacion, Date fechaAgendada, BigDecimal monto, Character tipoDoc, String documento, String cuentaOrigen, String cuentaDestino, String descripcion, String nombre, String email, Date fechaEjecucion) {
        this.id = id;
        this.idTransaccion = idTransaccion;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
        this.fechaAgendada = fechaAgendada;
        this.monto = monto;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.email = email;
        this.fechaEjecucion = fechaEjecucion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaAgendada() {
        return fechaAgendada;
    }

    public void setFechaAgendada(Date fechaAgendada) {
        this.fechaAgendada = fechaAgendada;
    }


    public Character getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Character tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }
    @XmlTransient
    public IbAgendaTransaccionesPn getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(IbAgendaTransaccionesPn idAgenda) {
        this.idAgenda = idAgenda;
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
        if (!(object instanceof IbDetalleAgendaTransPn)) {
            return false;
        }
        IbDetalleAgendaTransPn other = (IbDetalleAgendaTransPn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbDetalleAgendaTransPn[ id=" + id + " ]";
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}

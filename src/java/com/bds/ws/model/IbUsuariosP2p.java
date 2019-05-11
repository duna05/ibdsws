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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cesar.mujica
 */
@Entity
@Table(name = "IB_USUARIOS_P2P")
@XmlRootElement

    
public class IbUsuariosP2p implements Serializable {
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
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "NRO_TELEFONO")
    private String nroTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA")
    private String nroCuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MTO_MAX_TRANSACCION")
    private BigDecimal mtoMaxTransaccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MTO_MAX_DIARIO")
    private BigDecimal mtoMaxDiario;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;

    public IbUsuariosP2p() {
    }

    public IbUsuariosP2p(BigDecimal id) {
        this.id = id;
    }

    public IbUsuariosP2p(BigDecimal id, String nroTelefono, String tipoDocumento, String nroDocumento, String email, String nroCuenta, BigDecimal mtoMaxTransaccion, BigDecimal mtoMaxDiario) {
        this.id = id;
        this.nroTelefono = nroTelefono;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.email = email;
        this.nroCuenta = nroCuenta;
        this.mtoMaxTransaccion = mtoMaxTransaccion;
        this.mtoMaxDiario = mtoMaxDiario;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNroTelefono() {
        return nroTelefono;
    }

    public void setNroTelefono(String nroTelefono) {
        this.nroTelefono = nroTelefono;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public BigDecimal getMtoMaxTransaccion() {
        return mtoMaxTransaccion;
    }

    public void setMtoMaxTransaccion(BigDecimal mtoMaxTransaccion) {
        this.mtoMaxTransaccion = mtoMaxTransaccion;
    }

    public BigDecimal getMtoMaxDiario() {
        return mtoMaxDiario;
    }

    public void setMtoMaxDiario(BigDecimal mtoMaxDiario) {
        this.mtoMaxDiario = mtoMaxDiario;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof IbUsuariosP2p)) {
            return false;
        }
        IbUsuariosP2p other = (IbUsuariosP2p) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUsuariosP2p[ id=" + id + " ]";
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
}

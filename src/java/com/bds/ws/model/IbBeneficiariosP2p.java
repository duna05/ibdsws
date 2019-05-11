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
 * @author ledwin.belen
 */
@Entity
@Table(name = "IB_BENEFICIARIOS_P2P")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbBeneficiariosP2p.findAll", query = "SELECT i FROM IbBeneficiariosP2p i")
    , @NamedQuery(name = "IbBeneficiariosP2p.findById", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.id = :id")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByNroTelefono", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.nroTelefono = :nroTelefono")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByTipoDocumento", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.tipoDocumento = :tipoDocumento")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByNroDocumento", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.nroDocumento = :nroDocumento")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByCodigoBanco", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.codigoBanco = :codigoBanco")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByEstatusBeneficiario", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.estatusBeneficiario = :estatusBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByNombreBeneficiario", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.nombreBeneficiario = :nombreBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByAliasBeneficiario", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.aliasBeneficiario = :aliasBeneficiario")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByFechaHoraCarga", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbBeneficiariosP2p.findByFechaHoraModificacion", query = "SELECT i FROM IbBeneficiariosP2p i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbBeneficiariosP2p implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_BANCO")
    private short codigoBanco;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_BENEFICIARIO")
    private short estatusBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "NOMBRE_BENEFICIARIO")
    private String nombreBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "ALIAS_BENEFICIARIO")
    private String aliasBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuarios codigoUsuarioModifica;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios codigoUsuarioCarga;

    public IbBeneficiariosP2p() {
    }

    public IbBeneficiariosP2p(Long id) {
        this.id = id;
    }

    public IbBeneficiariosP2p(Long id, String nroTelefono, String tipoDocumento, String nroDocumento, short codigoBanco, short estatusBeneficiario, String nombreBeneficiario, String aliasBeneficiario, Date fechaHoraCarga) {
        this.id = id;
        this.nroTelefono = nroTelefono;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
        this.codigoBanco = codigoBanco;
        this.estatusBeneficiario = estatusBeneficiario;
        this.nombreBeneficiario = nombreBeneficiario;
        this.aliasBeneficiario = aliasBeneficiario;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public short getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(short codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public short getEstatusBeneficiario() {
        return estatusBeneficiario;
    }

    public void setEstatusBeneficiario(short estatusBeneficiario) {
        this.estatusBeneficiario = estatusBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getAliasBeneficiario() {
        return aliasBeneficiario;
    }

    public void setAliasBeneficiario(String aliasBeneficiario) {
        this.aliasBeneficiario = aliasBeneficiario;
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

    public IbUsuarios getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(IbUsuarios codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
    }

    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbUsuarios getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(IbUsuarios codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
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
        if (!(object instanceof IbBeneficiariosP2p)) {
            return false;
        }
        IbBeneficiariosP2p other = (IbBeneficiariosP2p) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbBeneficiariosP2p[ id=" + id + " ]";
    }
    
}

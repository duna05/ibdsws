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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CTAS_X_BENEFICIARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCtasXBeneficiariosPj.findAll", query = "SELECT i FROM IbCtasXBeneficiariosPj i")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByIdCuenta", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.idCuenta = :idCuenta")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByEstatusCuenta", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.estatusCuenta = :estatusCuenta")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByFormaPago", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.formaPago = :formaPago")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByPrincipal", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.principal = :principal")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByNroCuentaBeneficiario", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.nroCuentaBeneficiario = :nroCuentaBeneficiario")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByProducto", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.producto = :producto")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByCuentaDelSur", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.cuentaDelSur = :cuentaDelSur")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByFechaHoraCarga", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByBeneficiarioXEmpresa", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.idEmpresa.id = :idEmpresa and i.idBeneficiario.idBeneficiario = :idBeneficiario")
    , @NamedQuery(name = "IbCtasXBeneficiariosPj.findByFechaHoraReactivacion", query = "SELECT i FROM IbCtasXBeneficiariosPj i WHERE i.fechaHoraReactivacion = :fechaHoraReactivacion")})
public class IbCtasXBeneficiariosPj implements Serializable {

    @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEmpresas idEmpresa;
    @JoinColumn(name = "ESTATUS_AUTORIZACION", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatusAutorizacion;
    @JoinColumn(name = "CODIGO_USUARIO_CARGA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj codigoUsuarioCarga;
    @JoinColumn(name = "CODIGO_USUARIO_MODIFICA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioModifica;
    @JoinColumn(name = "CODIGO_USUARIO_REACTIVA", referencedColumnName = "ID")
    @ManyToOne
    private IbUsuariosPj codigoUsuarioReactiva;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CTASXBENEFICIARIOS")
    @SequenceGenerator(sequenceName = "IB_S_CTAS_X_BENEFICIARIOS_PJ", allocationSize = 1, name = "CUST_SEQ_CTASXBENEFICIARIOS")
    @Column(name = "ID_CUENTA")
    private Long idCuenta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS_CUENTA")
    private short estatusCuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "FORMA_PAGO")
    private String formaPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRINCIPAL")
    private short principal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NRO_CUENTA_BENEFICIARIO")
    private String nroCuentaBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTO")
    private short producto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUENTA_DEL_SUR")
    private short cuentaDelSur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUENTA_PROPIA")
    private short cuentaPropia;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @Column(name = "FECHA_HORA_REACTIVACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraReactivacion;
    @JoinColumn(name = "ID_BENEFICIARIO", referencedColumnName = "ID_BENEFICIARIO")
    @ManyToOne(optional = false)
    private IbBeneficiariosPj idBeneficiario;

    public IbCtasXBeneficiariosPj() {
    }

    public IbCtasXBeneficiariosPj(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public IbCtasXBeneficiariosPj(Long idCuenta, short estatusCuenta, String formaPago, short principal, String nroCuentaBeneficiario, short producto, short cuentaDelSur, Date fechaHoraCarga) {
        this.idCuenta = idCuenta;
        this.estatusCuenta = estatusCuenta;
        this.formaPago = formaPago;
        this.principal = principal;
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
        this.producto = producto;
        this.cuentaDelSur = cuentaDelSur;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public short getEstatusCuenta() {
        return estatusCuenta;
    }

    public void setEstatusCuenta(short estatusCuenta) {
        this.estatusCuenta = estatusCuenta;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public short getPrincipal() {
        return principal;
    }

    public void setPrincipal(short principal) {
        this.principal = principal;
    }

    public String getNroCuentaBeneficiario() {
        return nroCuentaBeneficiario;
    }

    public void setNroCuentaBeneficiario(String nroCuentaBeneficiario) {
        this.nroCuentaBeneficiario = nroCuentaBeneficiario;
    }

    public short getProducto() {
        return producto;
    }

    public void setProducto(short producto) {
        this.producto = producto;
    }

    public short getCuentaDelSur() {
        return cuentaDelSur;
    }

    public void setCuentaDelSur(short cuentaDelSur) {
        this.cuentaDelSur = cuentaDelSur;
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

    public Date getFechaHoraReactivacion() {
        return fechaHoraReactivacion;
    }

    public void setFechaHoraReactivacion(Date fechaHoraReactivacion) {
        this.fechaHoraReactivacion = fechaHoraReactivacion;
    }

    public IbBeneficiariosPj getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(IbBeneficiariosPj idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbCtasXBeneficiariosPj)) {
            return false;
        }
        IbCtasXBeneficiariosPj other = (IbCtasXBeneficiariosPj) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCtasXBeneficiariosPj[ idCuenta=" + idCuenta + " ]";
    }

    public IbEmpresas getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(IbEmpresas idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public IbEstatusPagosPj getEstatusAutorizacion() {
        return estatusAutorizacion;
    }

    public void setEstatusAutorizacion(IbEstatusPagosPj estatusAutorizacion) {
        this.estatusAutorizacion = estatusAutorizacion;
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

    public IbUsuariosPj getCodigoUsuarioReactiva() {
        return codigoUsuarioReactiva;
    }

    public void setCodigoUsuarioReactiva(IbUsuariosPj codigoUsuarioReactiva) {
        this.codigoUsuarioReactiva = codigoUsuarioReactiva;
    }

    public short getCuentaPropia() {
        return cuentaPropia;
    }

    public void setCuentaPropia(short cuentaPropia) {
        this.cuentaPropia = cuentaPropia;
    }
}

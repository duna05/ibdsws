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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_SERVICIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbServiciosPj.findAll", query = "SELECT i FROM IbServiciosPj i"),
    @NamedQuery(name = "IbServiciosPj.findById", query = "SELECT i FROM IbServiciosPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbServiciosPj.findByNombre", query = "SELECT i FROM IbServiciosPj i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbServiciosPj.findByPosicion", query = "SELECT i FROM IbServiciosPj i WHERE i.posicion = :posicion"),
    @NamedQuery(name = "IbServiciosPj.findByEstatus", query = "SELECT i FROM IbServiciosPj i WHERE i.estatus = :estatus"),
    @NamedQuery(name = "IbServiciosPj.findByPoseeBeneficiario", query = "SELECT i FROM IbServiciosPj i WHERE i.poseeBeneficiario = :poseeBeneficiario"),
    @NamedQuery(name = "IbServiciosPj.findByPoseeOtp", query = "SELECT i FROM IbServiciosPj i WHERE i.poseeOtp = :poseeOtp"),
    @NamedQuery(name = "IbServiciosPj.findByPoseeServicio", query = "SELECT i FROM IbServiciosPj i WHERE i.poseeServicio = :poseeServicio"),
    @NamedQuery(name = "IbServiciosPj.findByIdCore", query = "SELECT i FROM IbServiciosPj i WHERE i.idCore = :idCore")})
    public class IbServiciosPj implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicioPj")
    private Collection<IbUrlServicios> ibUrlServiciosCollection;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "NOMBRE",   referencedColumnName = "CODIGO",  insertable = false, updatable = false),
        @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID_CANAL", insertable = false, updatable = false)})
    private IbParametros idParametros;
    @ManyToOne
    @JoinColumn(name = "NOMBRE", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    private IbTextos idTextos;
    @JoinColumn(name = "ID_CANAL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbCanal idCanal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "REQUIERE_APROBADOR")
    private BigInteger requiereAprobador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTRATACION")
    private BigInteger contratacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VISIBLE")
    private Character visible;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicioPj")
    private Collection<IbLogsPj> ibLogsPjCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicioPj")
    private Collection<IbServiciosEmpresa> ibServiciosEmpresaCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSICION")
    private BigInteger posicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSEE_BENEFICIARIO")
    private Character poseeBeneficiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSEE_OTP")
    private Character poseeOtp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSEE_SERVICIO")
    private Character poseeServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "ID_CORE")
    private String idCore;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicioPj")
    private Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection;
    @JoinColumn(name = "ID_MODULO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbModulosPj idModuloPj;
    
    public IbServiciosPj() {
    }

    public IbServiciosPj(BigDecimal id) {
        this.id = id;
    }

    public IbServiciosPj(BigDecimal id, String nombre, BigInteger posicion, Character estatus, Character poseeBeneficiario, Character poseeOtp, Character poseeServicio, String idCore) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.estatus = estatus;
        this.poseeBeneficiario = poseeBeneficiario;
        this.poseeOtp = poseeOtp;
        this.poseeServicio = poseeServicio;
        this.idCore = idCore;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public BigInteger getPosicion() {
        return posicion;
    }

    public void setPosicion(BigInteger posicion) {
        this.posicion = posicion;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    public Character getPoseeBeneficiario() {
        return poseeBeneficiario;
    }

    public void setPoseeBeneficiario(Character poseeBeneficiario) {
        this.poseeBeneficiario = poseeBeneficiario;
    }

    public Character getPoseeOtp() {
        return poseeOtp;
    }

    public void setPoseeOtp(Character poseeOtp) {
        this.poseeOtp = poseeOtp;
    }

    public Character getPoseeServicio() {
        return poseeServicio;
    }

    public void setPoseeServicio(Character poseeServicio) {
        this.poseeServicio = poseeServicio;
    }

    public String getIdCore() {
        return idCore;
    }

    public void setIdCore(String idCore) {
        this.idCore = idCore;
    }

    @XmlTransient
    public Collection<IbServiciosPerfilesPj> getIbServiciosPerfilesPjCollection() {
        return ibServiciosPerfilesPjCollection;
    }

    public void setIbServiciosPerfilesPjCollection(Collection<IbServiciosPerfilesPj> ibServiciosPerfilesPjCollection) {
        this.ibServiciosPerfilesPjCollection = ibServiciosPerfilesPjCollection;
    }

    public IbModulosPj getIdModuloPj() {
        return idModuloPj;
    }

    public void setIdModuloPj(IbModulosPj idModuloPj) {
        this.idModuloPj = idModuloPj;
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
        if (!(object instanceof IbServiciosPj)) {
            return false;
        }
        IbServiciosPj other = (IbServiciosPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbServiciosPj[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbServiciosEmpresa> getIbServiciosEmpresaCollection() {
        return ibServiciosEmpresaCollection;
    }

    public void setIbServiciosEmpresaCollection(Collection<IbServiciosEmpresa> ibServiciosEmpresaCollection) {
        this.ibServiciosEmpresaCollection = ibServiciosEmpresaCollection;
    }

    @XmlTransient
    public Collection<IbLogsPj> getIbLogsPjCollection() {
        return ibLogsPjCollection;
    }

    public void setIbLogsPjCollection(Collection<IbLogsPj> ibLogsPjCollection) {
        this.ibLogsPjCollection = ibLogsPjCollection;
    }

    public Character getVisible() {
        return visible;
    }

    public void setVisible(Character visible) {
        this.visible = visible;
    }

    public BigInteger getContratacion() {
        return contratacion;
    }

    public void setContratacion(BigInteger contratacion) {
        this.contratacion = contratacion;
    }

    public BigInteger getRequiereAprobador() {
        return requiereAprobador;
    }

    public void setRequiereAprobador(BigInteger requiereAprobador) {
        this.requiereAprobador = requiereAprobador;
    }

    public IbCanal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(IbCanal idCanal) {
        this.idCanal = idCanal;
    }
    
    public IbParametros getIdParametros() {
        return idParametros;
    }

    public void setIdParametros(IbParametros idParametros) {
        this.idParametros = idParametros;
    }

    public IbTextos getIdTextos() {
        return idTextos;
    }

    public void setIdTextos(IbTextos idTextos) {
        this.idTextos = idTextos;
    }

    @XmlTransient
    public Collection<IbUrlServicios> getIbUrlServiciosCollection() {
        return ibUrlServiciosCollection;
    }

    public void setIbUrlServiciosCollection(Collection<IbUrlServicios> ibUrlServiciosCollection) {
        this.ibUrlServiciosCollection = ibUrlServiciosCollection;
    }
}

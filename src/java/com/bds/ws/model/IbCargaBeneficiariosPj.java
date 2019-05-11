/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author robinson.rodriguez
 */
@Entity
@Table(name = "IB_CARGA_BENEFICIARIOS_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaBeneficiariosPj.findAll", query = "SELECT i FROM IbCargaBeneficiariosPj i")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByIdCargaBeneficiario", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.idCargaBeneficiario = :idCargaBeneficiario")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByNombreArchivo", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByCantidadRegistros", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.cantidadRegistros = :cantidadRegistros")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByCantidadRegRechazados", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.cantidadRegRechazados = :cantidadRegRechazados")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCargaBeneficiariosPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaBeneficiariosPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")})
public class IbCargaBeneficiariosPj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_BENEFICIARIO")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_BENEFICIARIOS_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_BENEFICIARIO")
    @Column(name = "ID_CARGA_BENEFICIARIO")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargaBeneficiario")
    private Long idCargaBeneficiario;
    
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargaBeneficiario" , fetch = FetchType.EAGER)
    private Collection<IbBeneficiariosPj> ibBeneficiariosPjCollection;
    
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
    @Size(max = 200)
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_REGISTROS")
    private int cantidadRegistros;
    @Column(name = "CANTIDAD_REG_RECHAZADOS")
    private Integer cantidadRegRechazados;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;

    public IbCargaBeneficiariosPj() {
    }

    public IbCargaBeneficiariosPj(Long idCargaBeneficiario) {
        this.idCargaBeneficiario = idCargaBeneficiario;
    }

    public IbCargaBeneficiariosPj(Long idCargaBeneficiario, int cantidadRegistros, Date fechaHoraCarga) {
        this.idCargaBeneficiario = idCargaBeneficiario;
        this.cantidadRegistros = cantidadRegistros;
        this.fechaHoraCarga = fechaHoraCarga;
    }

    public Long getIdCargaBeneficiario() {
        return idCargaBeneficiario;
    }

    public void setIdCargaBeneficiario(Long idCargaBeneficiario) {
        this.idCargaBeneficiario = idCargaBeneficiario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public Integer getCantidadRegRechazados() {
        return cantidadRegRechazados;
    }

    public void setCantidadRegRechazados(Integer cantidadRegRechazados) {
        this.cantidadRegRechazados = cantidadRegRechazados;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargaBeneficiario != null ? idCargaBeneficiario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbCargaBeneficiariosPj)) {
            return false;
        }
        IbCargaBeneficiariosPj other = (IbCargaBeneficiariosPj) object;
        if ((this.idCargaBeneficiario == null && other.idCargaBeneficiario != null) || (this.idCargaBeneficiario != null && !this.idCargaBeneficiario.equals(other.idCargaBeneficiario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbCargaBeneficiariosPj[ idCargaBeneficiario=" + idCargaBeneficiario + " ]";
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


    public Collection<IbBeneficiariosPj> getIbBeneficiariosPjCollection() {
        return ibBeneficiariosPjCollection;
    }
   
    public void setIbBeneficiariosPjCollection(Collection<IbBeneficiariosPj> ibBeneficiariosPjCollection) {
        this.ibBeneficiariosPjCollection = ibBeneficiariosPjCollection;
    }   
}

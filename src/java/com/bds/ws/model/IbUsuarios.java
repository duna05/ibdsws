/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUsuarios.findAll", query = "SELECT i FROM IbUsuarios i"),
    @NamedQuery(name = "IbUsuarios.findById", query = "SELECT i FROM IbUsuarios i WHERE i.id = :id"),
    @NamedQuery(name = "IbUsuarios.findByNombre", query = "SELECT i FROM IbUsuarios i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IbUsuarios.findByTipoDoc", query = "SELECT i FROM IbUsuarios i WHERE i.tipoDoc = :tipoDoc"),
    @NamedQuery(name = "IbUsuarios.findByDocumento", query = "SELECT i FROM IbUsuarios i WHERE i.documento = :documento"),
    @NamedQuery(name = "IbUsuarios.findByTdd", query = "SELECT i FROM IbUsuarios i WHERE i.tdd = :tdd"),
    @NamedQuery(name = "IbUsuarios.findByTddClave", query = "SELECT i FROM IbUsuarios i WHERE i.tdd = :tdd and i.clave = :clave"),
    @NamedQuery(name = "IbUsuarios.findByEmail", query = "SELECT i FROM IbUsuarios i WHERE i.email = :email"),
    @NamedQuery(name = "IbUsuarios.findByCelular", query = "SELECT i FROM IbUsuarios i WHERE i.celular = :celular"),
    @NamedQuery(name = "IbUsuarios.findByFechaHoraCreacion", query = "SELECT i FROM IbUsuarios i WHERE i.fechaHoraCreacion = :fechaHoraCreacion"),
    @NamedQuery(name = "IbUsuarios.findByFechaHoraModificacion", query = "SELECT i FROM IbUsuarios i WHERE i.fechaHoraModificacion = :fechaHoraModificacion"),
    @NamedQuery(name = "IbUsuarios.findByCodUsuario", query = "SELECT i FROM IbUsuarios i WHERE i.codUsuario = :codUsuario")})
public class IbUsuarios implements Serializable {

    @OneToMany(mappedBy = "codigoUsuarioModifica")
    private Collection<IbBeneficiariosP2p> ibBeneficiariosP2pCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    
    private Collection<IbUsuariosP2p> ibUsuariosP2pCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbAgendaTransaccionesPn> ibAgendaTransaccionesPnCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbDetalleAgendaTransPn> ibDetalleAgendaTransPnCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "INTENTOS_FALLIDOS_PREGUNTAS")
    private int intentosFallidosPreguntas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbHistoricoClaves> ibHistoricoClavesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbUsuariosEventosMedios> ibUsuariosEventosMediosCollection;
    @Size(max = 50)
    @Column(name = "CLAVE")
    private String clave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbPregDesafioUsuario> ibPregDesafioUsuarioCollection;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 180)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_DOC")
    private Character tipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "DOCUMENTO")
    private String documento;
    @Size(max = 20)
    @Column(name = "TDD")
    private String tdd;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 75)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 45)
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "FECHA_HORA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCreacion;
    @Column(name = "FECHA_HORA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "COD_USUARIO")
    private String codUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbAcumuladoTransaccion> ibAcumuladoTransaccionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbAprobacionesTransacciones> ibAprobacionesTransaccionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbAfiliaciones> ibAfiliacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbLogs> ibLogsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbTransaccionesPendientes> ibTransaccionesPendientesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbTransUsuariosJuridicos> ibTransUsuariosJuridicosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbMensajesUsuarios> ibMensajesUsuariosCollection;
    @JoinColumn(name = "ID_ROL_JURIDICO", referencedColumnName = "ID")
    @ManyToOne
    private IbRolesJuridicos idRolJuridico;
    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfiles idPerfil;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbUsuariosCanales> ibUsuariosCanalesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private Collection<IbUsuariosPerfilPiloto> ibUsuariosPerfilPilotoCollection;

    public IbUsuarios() {
    }

    public IbUsuarios(BigDecimal id) {
        this.id = id;
    }

    public IbUsuarios(BigDecimal id, String nombre, Character tipoDoc, String documento, String codUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.codUsuario = codUsuario;
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

    public String getTdd() {
        return tdd;
    }

    public void setTdd(String tdd) {
        this.tdd = tdd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Date getFechaHoraModificacion() {
        return fechaHoraModificacion;
    }

    public void setFechaHoraModificacion(Date fechaHoraModificacion) {
        this.fechaHoraModificacion = fechaHoraModificacion;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    @XmlTransient
    public Collection<IbAcumuladoTransaccion> getIbAcumuladoTransaccionCollection() {
        return ibAcumuladoTransaccionCollection;
    }

    public void setIbAcumuladoTransaccionCollection(Collection<IbAcumuladoTransaccion> ibAcumuladoTransaccionCollection) {
        this.ibAcumuladoTransaccionCollection = ibAcumuladoTransaccionCollection;
    }

    @XmlTransient
    public Collection<IbAprobacionesTransacciones> getIbAprobacionesTransaccionesCollection() {
        return ibAprobacionesTransaccionesCollection;
    }

    public void setIbAprobacionesTransaccionesCollection(Collection<IbAprobacionesTransacciones> ibAprobacionesTransaccionesCollection) {
        this.ibAprobacionesTransaccionesCollection = ibAprobacionesTransaccionesCollection;
    }

    @XmlTransient
    public Collection<IbAfiliaciones> getIbAfiliacionesCollection() {
        return ibAfiliacionesCollection;
    }

    public void setIbAfiliacionesCollection(Collection<IbAfiliaciones> ibAfiliacionesCollection) {
        this.ibAfiliacionesCollection = ibAfiliacionesCollection;
    }

    @XmlTransient
    public Collection<IbLogs> getIbLogsCollection() {
        return ibLogsCollection;
    }

    public void setIbLogsCollection(Collection<IbLogs> ibLogsCollection) {
        this.ibLogsCollection = ibLogsCollection;
    }

    @XmlTransient
    public Collection<IbTransaccionesPendientes> getIbTransaccionesPendientesCollection() {
        return ibTransaccionesPendientesCollection;
    }

    public void setIbTransaccionesPendientesCollection(Collection<IbTransaccionesPendientes> ibTransaccionesPendientesCollection) {
        this.ibTransaccionesPendientesCollection = ibTransaccionesPendientesCollection;
    }

    @XmlTransient
    public Collection<IbTransUsuariosJuridicos> getIbTransUsuariosJuridicosCollection() {
        return ibTransUsuariosJuridicosCollection;
    }

    public void setIbTransUsuariosJuridicosCollection(Collection<IbTransUsuariosJuridicos> ibTransUsuariosJuridicosCollection) {
        this.ibTransUsuariosJuridicosCollection = ibTransUsuariosJuridicosCollection;
    }

    @XmlTransient
    public Collection<IbMensajesUsuarios> getIbMensajesUsuariosCollection() {
        return ibMensajesUsuariosCollection;
    }

    public void setIbMensajesUsuariosCollection(Collection<IbMensajesUsuarios> ibMensajesUsuariosCollection) {
        this.ibMensajesUsuariosCollection = ibMensajesUsuariosCollection;
    }

    public IbRolesJuridicos getIdRolJuridico() {
        return idRolJuridico;
    }

    public void setIdRolJuridico(IbRolesJuridicos idRolJuridico) {
        this.idRolJuridico = idRolJuridico;
    }

    public IbPerfiles getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(IbPerfiles idPerfil) {
        this.idPerfil = idPerfil;
    }

    @XmlTransient
    public Collection<IbUsuariosCanales> getIbUsuariosCanalesCollection() {
        return ibUsuariosCanalesCollection;
    }

    public void setIbUsuariosCanalesCollection(Collection<IbUsuariosCanales> ibUsuariosCanalesCollection) {
        this.ibUsuariosCanalesCollection = ibUsuariosCanalesCollection;
    }

    @XmlTransient
    public Collection<IbUsuariosPerfilPiloto> getIbUsuariosPerfilPilotoCollection() {
        return ibUsuariosPerfilPilotoCollection;
    }

    public void setIbUsuariosPerfilPilotoCollection(Collection<IbUsuariosPerfilPiloto> ibUsuariosPerfilPilotoCollection) {
        this.ibUsuariosPerfilPilotoCollection = ibUsuariosPerfilPilotoCollection;
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
        if (!(object instanceof IbUsuarios)) {
            return false;
        }
        IbUsuarios other = (IbUsuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbUsuarios[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbPregDesafioUsuario> getIbPregDesafioUsuarioCollection() {
        return ibPregDesafioUsuarioCollection;
    }

    public void setIbPregDesafioUsuarioCollection(Collection<IbPregDesafioUsuario> ibPregDesafioUsuarioCollection) {
        this.ibPregDesafioUsuarioCollection = ibPregDesafioUsuarioCollection;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @XmlTransient
    public Collection<IbUsuariosEventosMedios> getIbUsuariosEventosMediosCollection() {
        return ibUsuariosEventosMediosCollection;
    }

    public void setIbUsuariosEventosMediosCollection(Collection<IbUsuariosEventosMedios> ibUsuariosEventosMediosCollection) {
        this.ibUsuariosEventosMediosCollection = ibUsuariosEventosMediosCollection;
    }

    @XmlTransient
    public Collection<IbHistoricoClaves> getIbHistoricoClavesCollection() {
        return ibHistoricoClavesCollection;
    }

    public void setIbHistoricoClavesCollection(Collection<IbHistoricoClaves> ibHistoricoClavesCollection) {
        this.ibHistoricoClavesCollection = ibHistoricoClavesCollection;
    }

    public int getIntentosFallidosPreguntas() {
        return intentosFallidosPreguntas;
    }

    public void setIntentosFallidosPreguntas(int intentosFallidosPreguntas) {
        this.intentosFallidosPreguntas = intentosFallidosPreguntas;
    }

    @XmlTransient
    public Collection<IbAgendaTransaccionesPn> getIbAgendaTransaccionesPnCollection() {
        return ibAgendaTransaccionesPnCollection;
    }

    public void setIbAgendaTransaccionesPnCollection(Collection<IbAgendaTransaccionesPn> ibAgendaTransaccionesPnCollection) {
        this.ibAgendaTransaccionesPnCollection = ibAgendaTransaccionesPnCollection;
    }

    @XmlTransient
    public Collection<IbDetalleAgendaTransPn> getIbDetalleAgendaTransPnCollection() {
        return ibDetalleAgendaTransPnCollection;
    }

    public void setIbDetalleAgendaTransPnCollection(Collection<IbDetalleAgendaTransPn> ibDetalleAgendaTransPnCollection) {
        this.ibDetalleAgendaTransPnCollection = ibDetalleAgendaTransPnCollection;
    }

    @XmlTransient
    public Collection<IbUsuariosP2p> getIbUsuariosP2pCollection() {
        return ibUsuariosP2pCollection;
    }

    public void setIbUsuariosP2pCollection(Collection<IbUsuariosP2p> ibUsuariosP2pCollection) {
        this.ibUsuariosP2pCollection = ibUsuariosP2pCollection;
    }

    @XmlTransient
    public Collection<IbBeneficiariosP2p> getIbBeneficiariosP2pCollection() {
        return ibBeneficiariosP2pCollection;
    }

    public void setIbBeneficiariosP2pCollection(Collection<IbBeneficiariosP2p> ibBeneficiariosP2pCollection) {
        this.ibBeneficiariosP2pCollection = ibBeneficiariosP2pCollection;
    }

}

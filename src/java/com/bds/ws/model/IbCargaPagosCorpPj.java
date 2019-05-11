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
 * @author luis.perez
 */
@Entity
@Table(name = "IB_CARGA_PAGOS_CORP_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbCargaPagosCorpPj.findAll", query = "SELECT i FROM IbCargaPagosCorpPj i")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByIdPago", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.idPago = :idPago")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByCodigoClienteAbanks", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.codigoClienteAbanks = :codigoClienteAbanks")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByNombreArchivo", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.nombreArchivo = :nombreArchivo")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByMotivoDePago", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.motivoDePago = :motivoDePago")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByMontoTotalAplicar", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.montoTotalAplicar = :montoTotalAplicar")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByCantidadCreditosAplicar", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.cantidadCreditosAplicar = :cantidadCreditosAplicar")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByNroCuentaDebito", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.nroCuentaDebito = :nroCuentaDebito")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByMontoTotalAplicado", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.montoTotalAplicado = :montoTotalAplicado")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByCantidadCreditosAplicados", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.cantidadCreditosAplicados = :cantidadCreditosAplicados")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByCantidadCreditosRechazados", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.cantidadCreditosRechazados = :cantidadCreditosRechazados")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findBySecuenciaCumplida", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.secuenciaCumplida = :secuenciaCumplida")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByFechaHoraCarga", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.fechaHoraCarga = :fechaHoraCarga")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByFechaHoraModificacion", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.fechaHoraModificacion = :fechaHoraModificacion")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByFechaHoraPago", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.fechaHoraPago = :fechaHoraPago")
    , @NamedQuery(name = "IbCargaPagosCorpPj.findByTipoCargaPago", query = "SELECT i FROM IbCargaPagosCorpPj i WHERE i.tipoCargaPago = :tipoCargaPago")})
public class IbCargaPagosCorpPj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ_CARGA_PAGOS_CORP")
    @SequenceGenerator(sequenceName = "IB_S_CARGA_PAGOS_CORP_PJ", allocationSize = 1, name = "CUST_SEQ_CARGA_PAGOS_CORP")
    @Column(name = "ID_PAGO")
    private Long idPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_CLIENTE_ABANKS")//este
    private long codigoClienteAbanks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDENTIFICACION_ORDENANTE")//este
    @Size(max = 13)
    private String identificacionOrdenante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NOMBRE_ORDENANTE")//este
    @Size(max = 100)
    private String nombreOrdenante;
    @Size(max = 200)
    @Column(name = "NOMBRE_ARCHIVO")// colocar guion(-)
    private String nombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MOTIVO_DE_PAGO")//este
    private String motivoDePago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTO_TOTAL_APLICAR")//este
    private BigDecimal montoTotalAplicar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD_CREDITOS_APLICAR")// se le coloca uno(1) por ser manual  
    private BigDecimal cantidadCreditosAplicar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NRO_CUENTA_DEBITO") //este
    private String nroCuentaDebito;
    @Column(name = "MONTO_TOTAL_APLICADO")// null
    private BigDecimal montoTotalAplicado;
    @Column(name = "CANTIDAD_CREDITOS_APLICADOS")//null
    private BigDecimal cantidadCreditosAplicados;
    @Column(name = "CANTIDAD_CREDITOS_RECHAZADOS")//null
    private BigDecimal cantidadCreditosRechazados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA_CUMPLIDA")//pendiente 
    private BigDecimal secuenciaCumplida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_USUARIO_CARGA")//este id usuario
    private long codigoUsuarioCarga;
    @Column(name = "CODIGO_USUARIO_MODIFICA")//null
    private Long codigoUsuarioModifica;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA_CARGA", insertable = false, updatable = false)//este sysdate
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraCarga;
    @Column(name = "FECHA_HORA_MODIFICACION", insertable = false, updatable = false)//null
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_PAGO")//este //este
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraPago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO_CARGA_PAGO")//tipo carga enun 
    private short tipoCargaPago;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago")
    private Collection<IbErroresPaCorpPj> ibErroresPaCorpPjCollection;
    @JoinColumn(name = "ESTATUS_AUTORIZACION", referencedColumnName = "ID")//este es desde el enun estatuspagosenun 
    @ManyToOne(optional = false)
    private IbEstatusPagosPj estatusAutorizacion;
    //Se le agregó para la asignación de la lista detalle en la cabecera.
    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPago")
    private Collection<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjCollection;

    public IbCargaPagosCorpPj() {
    }

    public IbCargaPagosCorpPj(Long idPago) {
        this.idPago = idPago;
    }

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public long getCodigoClienteAbanks() {
        return codigoClienteAbanks;
    }

    public void setCodigoClienteAbanks(long codigoClienteAbanks) {
        this.codigoClienteAbanks = codigoClienteAbanks;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMotivoDePago() {
        return motivoDePago;
    }

    public void setMotivoDePago(String motivoDePago) {
        this.motivoDePago = motivoDePago;
    }

    public BigDecimal getMontoTotalAplicar() {
        return montoTotalAplicar;
    }

    public void setMontoTotalAplicar(BigDecimal montoTotalAplicar) {
        this.montoTotalAplicar = montoTotalAplicar;
    }

    public BigDecimal getCantidadCreditosAplicar() {
        return cantidadCreditosAplicar;
    }

    public void setCantidadCreditosAplicar(BigDecimal cantidadCreditosAplicar) {
        this.cantidadCreditosAplicar = cantidadCreditosAplicar;
    }

    public String getNroCuentaDebito() {
        return nroCuentaDebito;
    }

    public void setNroCuentaDebito(String nroCuentaDebito) {
        this.nroCuentaDebito = nroCuentaDebito;
    }

    public BigDecimal getMontoTotalAplicado() {
        return montoTotalAplicado;
    }

    public void setMontoTotalAplicado(BigDecimal montoTotalAplicado) {
        this.montoTotalAplicado = montoTotalAplicado;
    }

    public BigDecimal getCantidadCreditosAplicados() {
        return cantidadCreditosAplicados;
    }

    public void setCantidadCreditosAplicados(BigDecimal cantidadCreditosAplicados) {
        this.cantidadCreditosAplicados = cantidadCreditosAplicados;
    }

    public BigDecimal getCantidadCreditosRechazados() {
        return cantidadCreditosRechazados;
    }

    public void setCantidadCreditosRechazados(BigDecimal cantidadCreditosRechazados) {
        this.cantidadCreditosRechazados = cantidadCreditosRechazados;
    }

    public BigDecimal getSecuenciaCumplida() {
        return secuenciaCumplida;
    }

    public void setSecuenciaCumplida(BigDecimal secuenciaCumplida) {
        this.secuenciaCumplida = secuenciaCumplida;
    }

    public long getCodigoUsuarioCarga() {
        return codigoUsuarioCarga;
    }

    public void setCodigoUsuarioCarga(long codigoUsuarioCarga) {
        this.codigoUsuarioCarga = codigoUsuarioCarga;
    }

    public Long getCodigoUsuarioModifica() {
        return codigoUsuarioModifica;
    }

    public void setCodigoUsuarioModifica(Long codigoUsuarioModifica) {
        this.codigoUsuarioModifica = codigoUsuarioModifica;
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

    public Date getFechaHoraPago() {
        return fechaHoraPago;
    }

    public void setFechaHoraPago(Date fechaHoraPago) {
        this.fechaHoraPago = fechaHoraPago;
    }

    public short getTipoCargaPago() {
        return tipoCargaPago;
    }

    public void setTipoCargaPago(short tipoCargaPago) {
        this.tipoCargaPago = tipoCargaPago;
    }

    public Collection<IbErroresPaCorpPj> getIbErroresPaCorpPjCollection() {
        return ibErroresPaCorpPjCollection;
    }

    public void setIbErroresPaCorpPjCollection(Collection<IbErroresPaCorpPj> ibErroresPaCorpPjCollection) {
        this.ibErroresPaCorpPjCollection = ibErroresPaCorpPjCollection;
    }

    public IbEstatusPagosPj getEstatusAutorizacion() {
        return estatusAutorizacion;
    }

    public void setEstatusAutorizacion(IbEstatusPagosPj estatusAutorizacion) {
        this.estatusAutorizacion = estatusAutorizacion;
    }

    //Nuevos metodos agregados

    public Collection<IbCargaPagosCorpDetPj> getIbCargaPagosCorpDetPjCollection() {
        return ibCargaPagosCorpDetPjCollection;
    }

    public void setIbCargaPagosCorpDetPjCollection(Collection<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjCollection) {
        this.ibCargaPagosCorpDetPjCollection = ibCargaPagosCorpDetPjCollection;
    }

    public String getIdentificacionOrdenante() {
        return identificacionOrdenante;
    }

    public void setIdentificacionOrdenante(String identificacionOrdenante) {
        this.identificacionOrdenante = identificacionOrdenante;
    }

    public String getNombreOrdenante() {
        return nombreOrdenante;
    }

    public void setNombreOrdenante(String nombreOrdenante) {
        this.nombreOrdenante = nombreOrdenante;
    }
}

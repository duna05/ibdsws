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
 * @author alejandro.flores
 */
@Entity
@Table(name = "SUBTIPO_CLIENTE_JURIDICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubtipoClienteJuridico.findAll", query = "SELECT s FROM SubtipoClienteJuridico s")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByCodigoSubtipo", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.codigoSubtipo = :codigoSubtipo")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByDescripcion", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByAdicionadoPor", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.adicionadoPor = :adicionadoPor")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByFechaAdicion", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.fechaAdicion = :fechaAdicion")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByModificadoPor", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.modificadoPor = :modificadoPor")
    , @NamedQuery(name = "SubtipoClienteJuridico.findByFechaModificacion", query = "SELECT s FROM SubtipoClienteJuridico s WHERE s.fechaModificacion = :fechaModificacion")})
public class SubtipoClienteJuridico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_SUBTIPO")
    private short codigoSubtipo;
    @Size(max = 90)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 30)
    @Column(name = "ADICIONADO_POR")
    private String adicionadoPor;
    @Column(name = "FECHA_ADICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdicion;
    @Size(max = 30)
    @Column(name = "MODIFICADO_POR")
    private String modificadoPor;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    public SubtipoClienteJuridico() {
    }

    public short getCodigoSubtipo() {
        return codigoSubtipo;
    }

    public void setCodigoSubtipo(short codigoSubtipo) {
        this.codigoSubtipo = codigoSubtipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAdicionadoPor() {
        return adicionadoPor;
    }

    public void setAdicionadoPor(String adicionadoPor) {
        this.adicionadoPor = adicionadoPor;
    }

    public Date getFechaAdicion() {
        return fechaAdicion;
    }

    public void setFechaAdicion(Date fechaAdicion) {
        this.fechaAdicion = fechaAdicion;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
}

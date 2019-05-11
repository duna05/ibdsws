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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_PREGUNTAS_AUTENTICACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPreguntasAutenticacion.findAll", query = "SELECT i FROM IbPreguntasAutenticacion i ORDER BY i.pregunta ASC"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findAllAct", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.estatus = 'A' ORDER BY i.pregunta ASC"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findById", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.id = :id"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findByPregunta", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.pregunta = :pregunta"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findByTipo", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.tipo = :tipo"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findByTamano", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.tamano = :tamano"),
    @NamedQuery(name = "IbPreguntasAutenticacion.findByEstatus", query = "SELECT i FROM IbPreguntasAutenticacion i WHERE i.estatus = :estatus")})
public class IbPreguntasAutenticacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PREGUNTA")
    private String pregunta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIPO")
    private Character tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TAMANO")
    private BigInteger tamano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @OneToMany(mappedBy = "idPreguntaPadre")
    private Collection<IbPreguntasAutenticacion> ibPreguntasAutenticacionCollection;
    @JoinColumn(name = "ID_PREGUNTA_PADRE", referencedColumnName = "ID")
    @ManyToOne
    private IbPreguntasAutenticacion idPreguntaPadre;

    public IbPreguntasAutenticacion() {
    }

    public IbPreguntasAutenticacion(BigDecimal id) {
        this.id = id;
    }

    public IbPreguntasAutenticacion(BigDecimal id, String pregunta, Character tipo, BigInteger tamano, Character estatus) {
        this.id = id;
        this.pregunta = pregunta;
        this.tipo = tipo;
        this.tamano = tamano;
        this.estatus = estatus;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public BigInteger getTamano() {
        return tamano;
    }

    public void setTamano(BigInteger tamano) {
        this.tamano = tamano;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }

    @XmlTransient
    public Collection<IbPreguntasAutenticacion> getIbPreguntasAutenticacionCollection() {
        return ibPreguntasAutenticacionCollection;
    }

    public void setIbPreguntasAutenticacionCollection(Collection<IbPreguntasAutenticacion> ibPreguntasAutenticacionCollection) {
        this.ibPreguntasAutenticacionCollection = ibPreguntasAutenticacionCollection;
    }

    public IbPreguntasAutenticacion getIdPreguntaPadre() {
        return idPreguntaPadre;
    }

    public void setIdPreguntaPadre(IbPreguntasAutenticacion idPreguntaPadre) {
        this.idPreguntaPadre = idPreguntaPadre;
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
        if (!(object instanceof IbPreguntasAutenticacion)) {
            return false;
        }
        IbPreguntasAutenticacion other = (IbPreguntasAutenticacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPreguntasAutenticacion[ id=" + id + " ]";
    }

}

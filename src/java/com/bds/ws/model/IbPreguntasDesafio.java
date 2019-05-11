/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "IB_PREGUNTAS_DESAFIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPreguntasDesafio.findAll", query = "SELECT i FROM IbPreguntasDesafio i ORDER BY i.pregunta ASC"),
    @NamedQuery(name = "IbPreguntasDesafio.findAllAct", query = "SELECT i FROM IbPreguntasDesafio i WHERE i.estatus = :estatus ORDER BY i.pregunta ASC"),
    @NamedQuery(name = "IbPreguntasDesafio.findById", query = "SELECT i FROM IbPreguntasDesafio i WHERE i.id = :id"),
    @NamedQuery(name = "IbPreguntasDesafio.findByPregunta", query = "SELECT i FROM IbPreguntasDesafio i WHERE i.pregunta = :pregunta")})
public class IbPreguntasDesafio implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTATUS")
    private Character estatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPreguntaDesafio")
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
    @Size(min = 1, max = 150)
    @Column(name = "PREGUNTA")
    private String pregunta;

    public IbPreguntasDesafio() {
    }

    public IbPreguntasDesafio(BigDecimal id) {
        this.id = id;
    }

    public IbPreguntasDesafio(BigDecimal id, String pregunta) {
        this.id = id;
        this.pregunta = pregunta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IbPreguntasDesafio)) {
            return false;
        }
        IbPreguntasDesafio other = (IbPreguntasDesafio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPreguntasDesafio[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<IbPregDesafioUsuario> getIbPregDesafioUsuarioCollection() {
        return ibPregDesafioUsuarioCollection;
    }

    public void setIbPregDesafioUsuarioCollection(Collection<IbPregDesafioUsuario> ibPregDesafioUsuarioCollection) {
        this.ibPregDesafioUsuarioCollection = ibPregDesafioUsuarioCollection;
    }

    public Character getEstatus() {
        return estatus;
    }

    public void setEstatus(Character estatus) {
        this.estatus = estatus;
    }
    
}

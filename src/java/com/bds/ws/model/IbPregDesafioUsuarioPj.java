/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis.perez
 */
@Entity
@Table(name = "IB_PREG_DESAFIO_USUARIO_PJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbPregDesafioUsuarioPj.findAll", query = "SELECT i FROM IbPregDesafioUsuarioPj i"),
    @NamedQuery(name = "IbPregDesafioUsuarioPj.findById", query = "SELECT i FROM IbPregDesafioUsuarioPj i WHERE i.id = :id"),
    @NamedQuery(name = "IbPregDesafioUsuarioPj.findByRespuesta", query = "SELECT i FROM IbPregDesafioUsuarioPj i WHERE i.respuesta = :respuesta")})
public class IbPregDesafioUsuarioPj implements Serializable {
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
    @Column(name = "RESPUESTA")
    private String respuesta;
    @JoinColumn(name = "ID_USUARIO_PJ", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuariosPj idUsuarioPj;
    @JoinColumn(name = "ID_PREGUNTA_DESAFIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPreguntasDesafio idPreguntaDesafio;

    public IbPregDesafioUsuarioPj() {
    }

    public IbPregDesafioUsuarioPj(BigDecimal id) {
        this.id = id;
    }

    public IbPregDesafioUsuarioPj(BigDecimal id, String respuesta) {
        this.id = id;
        this.respuesta = respuesta;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public IbUsuariosPj getIdUsuarioPj() {
        return idUsuarioPj;
    }

    public void setIdUsuarioPj(IbUsuariosPj idUsuarioPj) {
        this.idUsuarioPj = idUsuarioPj;
    }

    public IbPreguntasDesafio getIdPreguntaDesafio() {
        return idPreguntaDesafio;
    }

    public void setIdPreguntaDesafio(IbPreguntasDesafio idPreguntaDesafio) {
        this.idPreguntaDesafio = idPreguntaDesafio;
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
        if (!(object instanceof IbPregDesafioUsuarioPj)) {
            return false;
        }
        IbPregDesafioUsuarioPj other = (IbPregDesafioUsuarioPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbPregDesafioUsuarioPj[ id=" + id + " ]";
    }
    
}

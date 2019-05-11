/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_USUARIOS_PERFIL_PILOTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbUsuariosPerfilPiloto.findAll", query = "SELECT i FROM IbUsuariosPerfilPiloto i")})
public class IbUsuariosPerfilPiloto implements Serializable {
    @Id
    private BigDecimal id;
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbUsuarios idUsuario;
    @JoinColumn(name = "ID_PERFIL_PILOTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private IbPerfilesPiloto idPerfilPiloto;
    
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }
    public IbUsuariosPerfilPiloto() {
    }


    public IbUsuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IbUsuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    public IbPerfilesPiloto getIdPerfilPiloto() {
        return idPerfilPiloto;
    }

    public void setIdPerfilPiloto(IbPerfilesPiloto idPerfilPiloto) {
        this.idPerfilPiloto = idPerfilPiloto;
    }
}

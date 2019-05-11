/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author juan.faneite
 */
@Entity
@Table(name = "IB_AUDITBK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IbAuditbk.findAll", query = "SELECT i FROM IbAuditbk i"),
    @NamedQuery(name = "IbAuditbk.findById", query = "SELECT i FROM IbAuditbk i WHERE i.id = :id"),
    @NamedQuery(name = "IbAuditbk.findByDatetime", query = "SELECT i FROM IbAuditbk i WHERE i.datetime = :datetime"),
    @NamedQuery(name = "IbAuditbk.findByScript", query = "SELECT i FROM IbAuditbk i WHERE i.script = :script"),
    @NamedQuery(name = "IbAuditbk.findByUsername", query = "SELECT i FROM IbAuditbk i WHERE i.username = :username"),
    @NamedQuery(name = "IbAuditbk.findByAction", query = "SELECT i FROM IbAuditbk i WHERE i.action = :action"),
    @NamedQuery(name = "IbAuditbk.findByField", query = "SELECT i FROM IbAuditbk i WHERE i.field = :field"),
    @NamedQuery(name = "IbAuditbk.findByKeyvalue", query = "SELECT i FROM IbAuditbk i WHERE i.keyvalue = :keyvalue"),
    @NamedQuery(name = "IbAuditbk.findByOldvalue", query = "SELECT i FROM IbAuditbk i WHERE i.oldvalue = :oldvalue"),
    @NamedQuery(name = "IbAuditbk.findByNewvalue", query = "SELECT i FROM IbAuditbk i WHERE i.newvalue = :newvalue"),
    @NamedQuery(name = "IbAuditbk.findByIp", query = "SELECT i FROM IbAuditbk i WHERE i.ip = :ip")})
public class IbAuditbk implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Size(max = 255)
    @Column(name = "SCRIPT")
    private String script;
    @Size(max = 100)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "ACTION")
    private String action;
    @Size(max = 255)
    @Column(name = "FIELD")
    private String field;
    @Size(max = 25)
    @Column(name = "KEYVALUE")
    private String keyvalue;
    @Size(max = 300)
    @Column(name = "OLDVALUE")
    private String oldvalue;
    @Size(max = 300)
    @Column(name = "NEWVALUE")
    private String newvalue;
    @Size(max = 50)
    @Column(name = "IP")
    private String ip;
    @JoinColumn(name = "ID_MODULO_ADM", referencedColumnName = "ID")
    @ManyToOne
    private IbModulosAdm idModuloAdm;

    public IbAuditbk() {
    }

    public IbAuditbk(BigDecimal id) {
        this.id = id;
    }

    public IbAuditbk(BigDecimal id, Date datetime) {
        this.id = id;
        this.datetime = datetime;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    public String getNewvalue() {
        return newvalue;
    }

    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IbModulosAdm getIdModuloAdm() {
        return idModuloAdm;
    }

    public void setIdModuloAdm(IbModulosAdm idModuloAdm) {
        this.idModuloAdm = idModuloAdm;
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
        if (!(object instanceof IbAuditbk)) {
            return false;
        }
        IbAuditbk other = (IbAuditbk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bds.ws.model.IbAuditbk[ id=" + id + " ]";
    }

}

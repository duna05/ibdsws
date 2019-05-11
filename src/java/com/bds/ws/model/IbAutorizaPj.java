/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author christian.gutierrez
 */

public class IbAutorizaPj implements Serializable {
    private static final long serialVersionUID = 1L;
  
    public BigDecimal id;

   

    public IbAutorizaPj() {
    }

    public IbAutorizaPj(BigDecimal id) {
        this.id = id;
    }

   

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
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
        if (!(object instanceof IbAutorizaPj)) {
            return false;
        }
        IbAutorizaPj other = (IbAutorizaPj) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

   
    
}

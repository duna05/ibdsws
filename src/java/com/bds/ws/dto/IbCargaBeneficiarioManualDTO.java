/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author robinson.rodriguez
 */
public class IbCargaBeneficiarioManualDTO {

    private BigDecimal codigoUsuario;
    private BigDecimal cdClienteAbanks;
    private String idCanal;
    private String codigoCanal;
    private BigDecimal idEmpresa;
    private String rifBeneficiario;
    private String nombreBeneficiario;
    private String emailBeneficiario;
    private String cuentaBeneficiario;
    private String nombreBancoBeneficiario;
    private Date fechaCarga;

    public BigDecimal getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(BigDecimal codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public BigDecimal getCdClienteAbanks() {
        return cdClienteAbanks;
    }

    public void setCdClienteAbanks(BigDecimal cdClienteAbanks) {
        this.cdClienteAbanks = cdClienteAbanks;
    }

    public String getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
    }

    public String getCodigoCanal() {
        return codigoCanal;
    }

    public void setCodigoCanal(String codigoCanal) {
        this.codigoCanal = codigoCanal;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRifBeneficiario() {
        return rifBeneficiario;
    }

    public void setRifBeneficiario(String rifBeneficiario) {
        this.rifBeneficiario = rifBeneficiario;
    }

    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    public void setEmailBeneficiario(String emailBeneficiario) {
        this.emailBeneficiario = emailBeneficiario;
    }

    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    public void setCuentaBeneficiario(String cuentaBeneficiario) {
        this.cuentaBeneficiario = cuentaBeneficiario;
    }

    public String getNombreBancoBeneficiario() {
        return nombreBancoBeneficiario;
    }

    public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
        this.nombreBancoBeneficiario = nombreBancoBeneficiario;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }
    
}

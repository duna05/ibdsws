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
 * @author luis.perez
 */
public class IbCargaArchivoDTO {
    private byte[] dataFile;
    private BigDecimal codigoUsuario;
    private BigDecimal cdClienteAbanks;
    private String fileName;
    private String nroCtaDebito; //PARA EL CASO DE ARCHIVOS SIN CABECERA
    private Date   fechaPago;    //PARA EL CASO DE ARCHIVOS SIN CABECERA
    private String idCanal;
    private String codigoCanal;
    private String codigoFideicomiso;
    private String formatoArchivo;
    private BigDecimal idEmpresa;
    private String estructura;    
    private String rifEmpresa;

    public byte[] getDataFile() {
        return dataFile;
    }

    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getNroCtaDebito() {
        return nroCtaDebito;
    }

    public void setNroCtaDebito(String nroCtaDebito) {
        this.nroCtaDebito = nroCtaDebito;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCodigoFideicomiso() {
        return codigoFideicomiso;
    }

    public void setCodigoFideicomiso(String codigoFideicomiso) {
        this.codigoFideicomiso = codigoFideicomiso;
    }
    
    public String getFormatoArchivo() {
        return formatoArchivo;
    }

    public void setFormatoArchivo(String formatoArchivo) {
        this.formatoArchivo = formatoArchivo;
    }

    public BigDecimal getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigDecimal idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }
}

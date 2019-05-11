/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import com.bds.ws.util.BDSUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author humberto.rojas
 */
public class FichaClienteEmpleosDTO extends BDSUtil implements Serializable{
    
         private String nombreEmpresa; /*MG_EMPLEOS_CLIENTE.NOMBRE_EMPRESA*/
         private String cargo; /*MG_EMPLEOS_CLIENTE.CARGO*/
         private BigDecimal ultimoSueldo; /*MG_EMPLEOS_CLIENTE.VALOR_INGRESO*/
         private String fechaEntrada; /*MG_EMPLEOS_CLIENTE.FECHA_ENTRADA*/
         private String rif; /*mg_ds_empleos_cliente.RIF*/
         private String ramo; /*mg_ds_empleos_cliente.RAMO*/
         private String direccion; /*mg_ds_empleos_cliente.DIRECCION*/
         private String telefono; /*mg_ds_empleos_cliente.TELEFONO*/
         private BigDecimal secuencia;
         private RespuestaDTO respuesta;
         private List<FichaClienteEmpleosDTO> ibEmpleoList;

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }
         
         

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getUltimoSueldo() {
        return ultimoSueldo;
    }

    public void setUltimoSueldo(BigDecimal ultimoSueldo) {
        this.ultimoSueldo = ultimoSueldo;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<FichaClienteEmpleosDTO> getIbEmpleoList() {
        return ibEmpleoList;
    }

    public void setIbEmpleoList(List<FichaClienteEmpleosDTO> ibEmpleoList) {
        this.ibEmpleoList = ibEmpleoList;
    }
    
    
    
    
    
}

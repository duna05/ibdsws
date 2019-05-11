/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jose.Perez
 */
public class AfiliacionesCargaDTO implements Serializable {

    private String lineaTxt;          //lineas de archivo
    private String nombreArchivo;     //nombre de archivo
    private Integer numeroLinea;      //numero de linea en el archivo    
    private String codigoOrdenante;   //codigo ordenante
    List<AfiliacionesCargaDTO> listAfiliacionesCargaDTO;

    public String getLineaTxt() {
        return lineaTxt;
    }

    public void setLineaTxt(String lineaTxt) {
        this.lineaTxt = lineaTxt;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(Integer numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getCodigoOrdenante() {
        return codigoOrdenante;
    }

    public void setCodigoOrdenante(String codigoOrdenante) {
        this.codigoOrdenante = codigoOrdenante;
    }

    public List<AfiliacionesCargaDTO> getListAfiliacionesCargaDTO() {
        return listAfiliacionesCargaDTO;
    }

    public void setListAfiliacionesCargaDTO(List<AfiliacionesCargaDTO> listAfiliacionesCargaDTO) {
        this.listAfiliacionesCargaDTO = listAfiliacionesCargaDTO;
    }

}

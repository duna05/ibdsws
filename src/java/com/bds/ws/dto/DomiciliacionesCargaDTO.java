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
public class DomiciliacionesCargaDTO implements Serializable {

    private String codigoOrdenante;           //codigo ordenante
    private String lineaTxt;                  //lineas del archivo
    private String nombreArchivo;             //nombre de archivo cargado
    private Integer numeroLinea;              //numero de linea del archivo
    private String tipoTxt;                   //Tipo archivo TXT
    List<DomiciliacionesCargaDTO> listDomiciliacionesCargaDTO;

    public String getCodigoOrdenante() {
        return codigoOrdenante;
    }

    public void setCodigoOrdenante(String codigoOrdenante) {
        this.codigoOrdenante = codigoOrdenante;
    }

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

    public String getTipoTxt() {
        return tipoTxt;
    }

    public void setTipoTxt(String tipoTxt) {
        this.tipoTxt = tipoTxt;
    }

    public List<DomiciliacionesCargaDTO> getListDomiciliacionesCargaDTO() {
        return listDomiciliacionesCargaDTO;
    }

    public void setListDomiciliacionesCargaDTO(List<DomiciliacionesCargaDTO> listDomiciliacionesCargaDTO) {
        this.listDomiciliacionesCargaDTO = listDomiciliacionesCargaDTO;
    }
}

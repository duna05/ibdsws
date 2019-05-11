/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.AfiliacionesCargaDTO;
import com.bds.ws.dto.AfiliacionesConsultaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author jose.perez
 */
public interface AfiliacionesDomiciliacionesPjDAO {

    /**
     * Metodo procesarArchivoAfiliacionesPj requerido para procesar las
     * afiliaciones
     *
     * @param ibCargaArchivoDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    public UtilDTO procesarArchivoAfiliacionesPj(IbCargaArchivoDTO ibCargaArchivoDTO, String idCanal, String codigoCanal);

    /**
     *
     * @param codigoOrdenante
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoCanal
     * @return
     */
    public AfiliacionesConsultaDTO consolidarAfiliacionesPj(String codigoOrdenante, String fechaDesde, String fechaHasta, String codigoCanal);

    /**
     * Metodo para insertar afiliaciones en Core
     *
     * @param afiliacionesCargaDTO
     * @param idCana
     * @param codigoCanal
     * @return
     */
    public UtilDTO insertarAfiliacionesPjCore(AfiliacionesCargaDTO afiliacionesCargaDTO, String idCana, String codigoCanal);

}

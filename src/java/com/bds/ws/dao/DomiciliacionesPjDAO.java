/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.DomiciliacionesCargaDTO;
import com.bds.ws.dto.DomiciliacionesConsultaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author jose.perez
 */
public interface DomiciliacionesPjDAO {

    /**
     *
     * @param ibCargaArchivoDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    public UtilDTO procesarArchivoDomiciliacionesPj(IbCargaArchivoDTO ibCargaArchivoDTO, String idCanal, String codigoCanal);

    /**
     *
     * @param codigoOrdenante
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoCanal
     * @return
     */
    public DomiciliacionesConsultaDTO consultarDomiciliacion(String codigoOrdenante, String fechaDesde, String fechaHasta, String codigoCanal);

    /**
     * Metodo para insertar domiciliaciones exitosas en el Core
     *
     * @param domiciliacionesCargaDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    public UtilDTO insertarDomiciliacionPjCore(DomiciliacionesCargaDTO domiciliacionesCargaDTO, String idCanal, String codigoCanal);

}

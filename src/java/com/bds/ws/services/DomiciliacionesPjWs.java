/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.DomiciliacionesPjDAO;
import com.bds.ws.dto.DomiciliacionesCargaDTO;
import com.bds.ws.dto.DomiciliacionesConsultaDTO;
import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.bds.ws.dto.IbErroresCargaPjDTO;

/**
 *
 * @author jose.perez
 */
@WebService(serviceName = "DomiciliacionesPjWs")
public class DomiciliacionesPjWs {

    @EJB
    private DomiciliacionesPjDAO domiciliacionesPjDAO;

    /**
     * Metodo procesarArchivoDomiciliacionesPj requerido para procesar las
     * domiciliaciones
     *
     * @param ibCargaArchivoDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "procesarArchivoDomiciliacionesPj")
    public UtilDTO procesarArchivoDomiciliacionesPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return this.domiciliacionesPjDAO.procesarArchivoDomiciliacionesPj(ibCargaArchivoDTO, idCanal, codigoCanal);
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return
     */
    @WebMethod(operationName = "ibErroresCargaDomiciliacionesMasivaPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaMasivaPjDTO() {
        return new IbErroresCargaPjDTO();
    }

    /**
     * Metodo DomiciliacionesConsultaDTO requerido para consultar las
     * domiciliaciones
     *
     * @param codigoOrdenante
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "consultarDomiciliacion")
    public DomiciliacionesConsultaDTO consultarDomiciliacion(
            @WebParam(name = "codigoOrdenante") String codigoOrdenante,
            @WebParam(name = "fechaDesde") String fechaDesde,
            @WebParam(name = "fechaHasta") String fechaHasta,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.domiciliacionesPjDAO.consultarDomiciliacion(codigoOrdenante, fechaDesde, fechaHasta, codigoCanal);
    }

    /**
     * Metodo domiciliacionesCargaDTO requerido para cargar la clase
     * DomiciliacionesCargaDTO Oracle 11
     *
     * @return
     */
    @WebMethod(operationName = "domiciliacionesCargaMasivaPJDTO")
    public DomiciliacionesCargaDTO domiciliacionesCargaMasivaPJDTO() {
        return new DomiciliacionesCargaDTO();
    }

    /**
     * Metodo para insertar domiciliaciones exitosas en el Core
     *
     * @param domiciliacionesCargaDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "insertarDomiciliacionPjCore")
    public UtilDTO insertarDomiciliacionPjCore(
            @WebParam(name = "domiciliacionesCargaDTO") DomiciliacionesCargaDTO domiciliacionesCargaDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.domiciliacionesPjDAO.insertarDomiciliacionPjCore(domiciliacionesCargaDTO, idCanal, codigoCanal);
    }
}

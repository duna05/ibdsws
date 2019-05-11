/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.bds.ws.dao.AfiliacionesDomiciliacionesPjDAO;
import com.bds.ws.dto.AfiliacionesCargaDTO;
import com.bds.ws.dto.AfiliacionesConsultaDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;

/**
 *
 * @author jose.perez
 */
@WebService(serviceName = "AfiliacionesDomiciliacionesPjWs")
public class AfiliacionesDomiciliacionesPjWs {

    @EJB
    private AfiliacionesDomiciliacionesPjDAO afiliacionesPjDAO;//Metodo procesarArchivoAfiliacionesPj requerido para procesar las afiliaciones

    @WebMethod(operationName = "procesarArchivoAfiliacionesPj")
    public UtilDTO procesarArchivoAfiliacionesPj(
            @WebParam(name = "ibCargaArchivoDTO") IbCargaArchivoDTO ibCargaArchivoDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.afiliacionesPjDAO.procesarArchivoAfiliacionesPj(ibCargaArchivoDTO, idCanal, codigoCanal);
    }

    /**
     * Metodo afiliacionesCargaMasivaPjDTO requerido para cargar la clase
     * AfiliacionesCargaDTO Oracle 11
     *
     * @return
     */
    @WebMethod(operationName = "afiliacionesCargaMasivaPjDTO")
    public AfiliacionesCargaDTO afiliacionesCargaMasivaPjDTO() {
        return new AfiliacionesCargaDTO();
    }

    /**
     * Metodo ibErroresCargaPjDTO requerido para cargar la clase
     * IbErroresCargaPjDTO Oracle 11
     *
     * @return
     */
    @WebMethod(operationName = "ibErroresCargaAfiliacionesMasivaPjDTO")
    public IbErroresCargaPjDTO ibErroresCargaMasivaPjDTO() {
        return new IbErroresCargaPjDTO();
    }

    /**
     * Metodo requerido para consultar las afiliaciones
     *
     * @param codigoOrdenante
     * @param fechaDesde
     * @param fechaHasta
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "consolidarAfiliacionesPj")
    public AfiliacionesConsultaDTO consolidarAfiliacionesPj(
            @WebParam(name = "codigoOrdenante") String codigoOrdenante,
            @WebParam(name = "fechaDesde") String fechaDesde,
            @WebParam(name = "fechaHasta") String fechaHasta,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return this.afiliacionesPjDAO.consolidarAfiliacionesPj(codigoOrdenante, fechaDesde, fechaHasta, codigoCanal);

    }

    /**
     * Metodo para insertar afiliaciones en Core
     *
     * @param afiliacionesCargaDTO
     * @param idCanal
     * @param codigoCanal
     * @return
     */
    @WebMethod(operationName = "insertarAfiliacionesPjCore")
    public UtilDTO insertarAfiliacionesPjCore(
            @WebParam(name = "afiliacionesCargaDTO") AfiliacionesCargaDTO afiliacionesCargaDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return this.afiliacionesPjDAO.insertarAfiliacionesPjCore(afiliacionesCargaDTO, idCanal, codigoCanal);
    }
}
